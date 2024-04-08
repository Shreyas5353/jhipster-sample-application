import dayjs from 'dayjs/esm';

import { IMessageArchive, NewMessageArchive } from './message-archive.model';

export const sampleWithRequiredData: IMessageArchive = {
  id: 23264,
};

export const sampleWithPartialData: IMessageArchive = {
  id: 4764,
  message: 'clothes out aboard',
  hasRead: true,
  hasSignOut: false,
  senderRefId: 10117,
  hasPrivateActive: true,
  lastModified: dayjs('2024-04-08T05:22'),
  lastModifiedBy: 'inwardly mysterious',
};

export const sampleWithFullData: IMessageArchive = {
  id: 32010,
  message: 'over repel',
  hasRead: false,
  hasEmergAlert: true,
  hasSignOut: true,
  senderRefTable: 'yowza bah worriedly',
  senderRefId: 28556,
  receiverRefTable: 'fooey',
  receiverRefId: 7474,
  status: 'so',
  studentId: 10570,
  hasPrivateActive: false,
  schoolId: 30469,
  lastModified: dayjs('2024-04-07T23:35'),
  lastModifiedBy: 'petty showcase absent',
};

export const sampleWithNewData: NewMessageArchive = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
