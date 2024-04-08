import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../message-archive.test-samples';

import { MessageArchiveFormService } from './message-archive-form.service';

describe('MessageArchive Form Service', () => {
  let service: MessageArchiveFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MessageArchiveFormService);
  });

  describe('Service methods', () => {
    describe('createMessageArchiveFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMessageArchiveFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            message: expect.any(Object),
            hasRead: expect.any(Object),
            hasEmergAlert: expect.any(Object),
            hasSignOut: expect.any(Object),
            senderRefTable: expect.any(Object),
            senderRefId: expect.any(Object),
            receiverRefTable: expect.any(Object),
            receiverRefId: expect.any(Object),
            status: expect.any(Object),
            studentId: expect.any(Object),
            hasPrivateActive: expect.any(Object),
            schoolId: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
          }),
        );
      });

      it('passing IMessageArchive should create a new form with FormGroup', () => {
        const formGroup = service.createMessageArchiveFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            message: expect.any(Object),
            hasRead: expect.any(Object),
            hasEmergAlert: expect.any(Object),
            hasSignOut: expect.any(Object),
            senderRefTable: expect.any(Object),
            senderRefId: expect.any(Object),
            receiverRefTable: expect.any(Object),
            receiverRefId: expect.any(Object),
            status: expect.any(Object),
            studentId: expect.any(Object),
            hasPrivateActive: expect.any(Object),
            schoolId: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
          }),
        );
      });
    });

    describe('getMessageArchive', () => {
      it('should return NewMessageArchive for default MessageArchive initial value', () => {
        const formGroup = service.createMessageArchiveFormGroup(sampleWithNewData);

        const messageArchive = service.getMessageArchive(formGroup) as any;

        expect(messageArchive).toMatchObject(sampleWithNewData);
      });

      it('should return NewMessageArchive for empty MessageArchive initial value', () => {
        const formGroup = service.createMessageArchiveFormGroup();

        const messageArchive = service.getMessageArchive(formGroup) as any;

        expect(messageArchive).toMatchObject({});
      });

      it('should return IMessageArchive', () => {
        const formGroup = service.createMessageArchiveFormGroup(sampleWithRequiredData);

        const messageArchive = service.getMessageArchive(formGroup) as any;

        expect(messageArchive).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMessageArchive should not enable id FormControl', () => {
        const formGroup = service.createMessageArchiveFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMessageArchive should disable id FormControl', () => {
        const formGroup = service.createMessageArchiveFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
