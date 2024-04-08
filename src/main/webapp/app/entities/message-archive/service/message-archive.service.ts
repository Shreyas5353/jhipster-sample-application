import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMessageArchive, NewMessageArchive } from '../message-archive.model';

export type PartialUpdateMessageArchive = Partial<IMessageArchive> & Pick<IMessageArchive, 'id'>;

type RestOf<T extends IMessageArchive | NewMessageArchive> = Omit<T, 'lastModified'> & {
  lastModified?: string | null;
};

export type RestMessageArchive = RestOf<IMessageArchive>;

export type NewRestMessageArchive = RestOf<NewMessageArchive>;

export type PartialUpdateRestMessageArchive = RestOf<PartialUpdateMessageArchive>;

export type EntityResponseType = HttpResponse<IMessageArchive>;
export type EntityArrayResponseType = HttpResponse<IMessageArchive[]>;

@Injectable({ providedIn: 'root' })
export class MessageArchiveService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/message-archives');

  create(messageArchive: NewMessageArchive): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(messageArchive);
    return this.http
      .post<RestMessageArchive>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(messageArchive: IMessageArchive): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(messageArchive);
    return this.http
      .put<RestMessageArchive>(`${this.resourceUrl}/${this.getMessageArchiveIdentifier(messageArchive)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(messageArchive: PartialUpdateMessageArchive): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(messageArchive);
    return this.http
      .patch<RestMessageArchive>(`${this.resourceUrl}/${this.getMessageArchiveIdentifier(messageArchive)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestMessageArchive>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestMessageArchive[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMessageArchiveIdentifier(messageArchive: Pick<IMessageArchive, 'id'>): number {
    return messageArchive.id;
  }

  compareMessageArchive(o1: Pick<IMessageArchive, 'id'> | null, o2: Pick<IMessageArchive, 'id'> | null): boolean {
    return o1 && o2 ? this.getMessageArchiveIdentifier(o1) === this.getMessageArchiveIdentifier(o2) : o1 === o2;
  }

  addMessageArchiveToCollectionIfMissing<Type extends Pick<IMessageArchive, 'id'>>(
    messageArchiveCollection: Type[],
    ...messageArchivesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const messageArchives: Type[] = messageArchivesToCheck.filter(isPresent);
    if (messageArchives.length > 0) {
      const messageArchiveCollectionIdentifiers = messageArchiveCollection.map(messageArchiveItem =>
        this.getMessageArchiveIdentifier(messageArchiveItem),
      );
      const messageArchivesToAdd = messageArchives.filter(messageArchiveItem => {
        const messageArchiveIdentifier = this.getMessageArchiveIdentifier(messageArchiveItem);
        if (messageArchiveCollectionIdentifiers.includes(messageArchiveIdentifier)) {
          return false;
        }
        messageArchiveCollectionIdentifiers.push(messageArchiveIdentifier);
        return true;
      });
      return [...messageArchivesToAdd, ...messageArchiveCollection];
    }
    return messageArchiveCollection;
  }

  protected convertDateFromClient<T extends IMessageArchive | NewMessageArchive | PartialUpdateMessageArchive>(
    messageArchive: T,
  ): RestOf<T> {
    return {
      ...messageArchive,
      lastModified: messageArchive.lastModified?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restMessageArchive: RestMessageArchive): IMessageArchive {
    return {
      ...restMessageArchive,
      lastModified: restMessageArchive.lastModified ? dayjs(restMessageArchive.lastModified) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestMessageArchive>): HttpResponse<IMessageArchive> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestMessageArchive[]>): HttpResponse<IMessageArchive[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
