import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IMessageArchive, NewMessageArchive } from '../message-archive.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMessageArchive for edit and NewMessageArchiveFormGroupInput for create.
 */
type MessageArchiveFormGroupInput = IMessageArchive | PartialWithRequiredKeyOf<NewMessageArchive>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IMessageArchive | NewMessageArchive> = Omit<T, 'lastModified'> & {
  lastModified?: string | null;
};

type MessageArchiveFormRawValue = FormValueOf<IMessageArchive>;

type NewMessageArchiveFormRawValue = FormValueOf<NewMessageArchive>;

type MessageArchiveFormDefaults = Pick<
  NewMessageArchive,
  'id' | 'hasRead' | 'hasEmergAlert' | 'hasSignOut' | 'hasPrivateActive' | 'lastModified'
>;

type MessageArchiveFormGroupContent = {
  id: FormControl<MessageArchiveFormRawValue['id'] | NewMessageArchive['id']>;
  message: FormControl<MessageArchiveFormRawValue['message']>;
  hasRead: FormControl<MessageArchiveFormRawValue['hasRead']>;
  hasEmergAlert: FormControl<MessageArchiveFormRawValue['hasEmergAlert']>;
  hasSignOut: FormControl<MessageArchiveFormRawValue['hasSignOut']>;
  senderRefTable: FormControl<MessageArchiveFormRawValue['senderRefTable']>;
  senderRefId: FormControl<MessageArchiveFormRawValue['senderRefId']>;
  receiverRefTable: FormControl<MessageArchiveFormRawValue['receiverRefTable']>;
  receiverRefId: FormControl<MessageArchiveFormRawValue['receiverRefId']>;
  status: FormControl<MessageArchiveFormRawValue['status']>;
  studentId: FormControl<MessageArchiveFormRawValue['studentId']>;
  hasPrivateActive: FormControl<MessageArchiveFormRawValue['hasPrivateActive']>;
  schoolId: FormControl<MessageArchiveFormRawValue['schoolId']>;
  lastModified: FormControl<MessageArchiveFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<MessageArchiveFormRawValue['lastModifiedBy']>;
};

export type MessageArchiveFormGroup = FormGroup<MessageArchiveFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MessageArchiveFormService {
  createMessageArchiveFormGroup(messageArchive: MessageArchiveFormGroupInput = { id: null }): MessageArchiveFormGroup {
    const messageArchiveRawValue = this.convertMessageArchiveToMessageArchiveRawValue({
      ...this.getFormDefaults(),
      ...messageArchive,
    });
    return new FormGroup<MessageArchiveFormGroupContent>({
      id: new FormControl(
        { value: messageArchiveRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      message: new FormControl(messageArchiveRawValue.message),
      hasRead: new FormControl(messageArchiveRawValue.hasRead),
      hasEmergAlert: new FormControl(messageArchiveRawValue.hasEmergAlert),
      hasSignOut: new FormControl(messageArchiveRawValue.hasSignOut),
      senderRefTable: new FormControl(messageArchiveRawValue.senderRefTable),
      senderRefId: new FormControl(messageArchiveRawValue.senderRefId),
      receiverRefTable: new FormControl(messageArchiveRawValue.receiverRefTable),
      receiverRefId: new FormControl(messageArchiveRawValue.receiverRefId),
      status: new FormControl(messageArchiveRawValue.status),
      studentId: new FormControl(messageArchiveRawValue.studentId),
      hasPrivateActive: new FormControl(messageArchiveRawValue.hasPrivateActive),
      schoolId: new FormControl(messageArchiveRawValue.schoolId),
      lastModified: new FormControl(messageArchiveRawValue.lastModified),
      lastModifiedBy: new FormControl(messageArchiveRawValue.lastModifiedBy),
    });
  }

  getMessageArchive(form: MessageArchiveFormGroup): IMessageArchive | NewMessageArchive {
    return this.convertMessageArchiveRawValueToMessageArchive(
      form.getRawValue() as MessageArchiveFormRawValue | NewMessageArchiveFormRawValue,
    );
  }

  resetForm(form: MessageArchiveFormGroup, messageArchive: MessageArchiveFormGroupInput): void {
    const messageArchiveRawValue = this.convertMessageArchiveToMessageArchiveRawValue({ ...this.getFormDefaults(), ...messageArchive });
    form.reset(
      {
        ...messageArchiveRawValue,
        id: { value: messageArchiveRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): MessageArchiveFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      hasRead: false,
      hasEmergAlert: false,
      hasSignOut: false,
      hasPrivateActive: false,
      lastModified: currentTime,
    };
  }

  private convertMessageArchiveRawValueToMessageArchive(
    rawMessageArchive: MessageArchiveFormRawValue | NewMessageArchiveFormRawValue,
  ): IMessageArchive | NewMessageArchive {
    return {
      ...rawMessageArchive,
      lastModified: dayjs(rawMessageArchive.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertMessageArchiveToMessageArchiveRawValue(
    messageArchive: IMessageArchive | (Partial<NewMessageArchive> & MessageArchiveFormDefaults),
  ): MessageArchiveFormRawValue | PartialWithRequiredKeyOf<NewMessageArchiveFormRawValue> {
    return {
      ...messageArchive,
      lastModified: messageArchive.lastModified ? messageArchive.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
