import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMessageArchive } from '../message-archive.model';
import { MessageArchiveService } from '../service/message-archive.service';

const messageArchiveResolve = (route: ActivatedRouteSnapshot): Observable<null | IMessageArchive> => {
  const id = route.params['id'];
  if (id) {
    return inject(MessageArchiveService)
      .find(id)
      .pipe(
        mergeMap((messageArchive: HttpResponse<IMessageArchive>) => {
          if (messageArchive.body) {
            return of(messageArchive.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default messageArchiveResolve;
