import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MessageArchiveService } from '../service/message-archive.service';
import { IMessageArchive } from '../message-archive.model';
import { MessageArchiveFormService } from './message-archive-form.service';

import { MessageArchiveUpdateComponent } from './message-archive-update.component';

describe('MessageArchive Management Update Component', () => {
  let comp: MessageArchiveUpdateComponent;
  let fixture: ComponentFixture<MessageArchiveUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let messageArchiveFormService: MessageArchiveFormService;
  let messageArchiveService: MessageArchiveService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), MessageArchiveUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(MessageArchiveUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MessageArchiveUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    messageArchiveFormService = TestBed.inject(MessageArchiveFormService);
    messageArchiveService = TestBed.inject(MessageArchiveService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const messageArchive: IMessageArchive = { id: 456 };

      activatedRoute.data = of({ messageArchive });
      comp.ngOnInit();

      expect(comp.messageArchive).toEqual(messageArchive);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMessageArchive>>();
      const messageArchive = { id: 123 };
      jest.spyOn(messageArchiveFormService, 'getMessageArchive').mockReturnValue(messageArchive);
      jest.spyOn(messageArchiveService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ messageArchive });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: messageArchive }));
      saveSubject.complete();

      // THEN
      expect(messageArchiveFormService.getMessageArchive).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(messageArchiveService.update).toHaveBeenCalledWith(expect.objectContaining(messageArchive));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMessageArchive>>();
      const messageArchive = { id: 123 };
      jest.spyOn(messageArchiveFormService, 'getMessageArchive').mockReturnValue({ id: null });
      jest.spyOn(messageArchiveService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ messageArchive: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: messageArchive }));
      saveSubject.complete();

      // THEN
      expect(messageArchiveFormService.getMessageArchive).toHaveBeenCalled();
      expect(messageArchiveService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMessageArchive>>();
      const messageArchive = { id: 123 };
      jest.spyOn(messageArchiveService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ messageArchive });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(messageArchiveService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
