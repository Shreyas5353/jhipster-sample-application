import dayjs from 'dayjs/esm';

export interface IMessageArchive {
  id: number;
  message?: string | null;
  hasRead?: boolean | null;
  hasEmergAlert?: boolean | null;
  hasSignOut?: boolean | null;
  senderRefTable?: string | null;
  senderRefId?: number | null;
  receiverRefTable?: string | null;
  receiverRefId?: number | null;
  status?: string | null;
  studentId?: number | null;
  hasPrivateActive?: boolean | null;
  schoolId?: number | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
}

export type NewMessageArchive = Omit<IMessageArchive, 'id'> & { id: null };
