import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMessageArchive } from '../message-archive.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../message-archive.test-samples';

import { MessageArchiveService, RestMessageArchive } from './message-archive.service';

const requireRestSample: RestMessageArchive = {
  ...sampleWithRequiredData,
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
};

describe('MessageArchive Service', () => {
  let service: MessageArchiveService;
  let httpMock: HttpTestingController;
  let expectedResult: IMessageArchive | IMessageArchive[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MessageArchiveService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a MessageArchive', () => {
      const messageArchive = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(messageArchive).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MessageArchive', () => {
      const messageArchive = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(messageArchive).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MessageArchive', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MessageArchive', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MessageArchive', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMessageArchiveToCollectionIfMissing', () => {
      it('should add a MessageArchive to an empty array', () => {
        const messageArchive: IMessageArchive = sampleWithRequiredData;
        expectedResult = service.addMessageArchiveToCollectionIfMissing([], messageArchive);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(messageArchive);
      });

      it('should not add a MessageArchive to an array that contains it', () => {
        const messageArchive: IMessageArchive = sampleWithRequiredData;
        const messageArchiveCollection: IMessageArchive[] = [
          {
            ...messageArchive,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMessageArchiveToCollectionIfMissing(messageArchiveCollection, messageArchive);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MessageArchive to an array that doesn't contain it", () => {
        const messageArchive: IMessageArchive = sampleWithRequiredData;
        const messageArchiveCollection: IMessageArchive[] = [sampleWithPartialData];
        expectedResult = service.addMessageArchiveToCollectionIfMissing(messageArchiveCollection, messageArchive);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(messageArchive);
      });

      it('should add only unique MessageArchive to an array', () => {
        const messageArchiveArray: IMessageArchive[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const messageArchiveCollection: IMessageArchive[] = [sampleWithRequiredData];
        expectedResult = service.addMessageArchiveToCollectionIfMissing(messageArchiveCollection, ...messageArchiveArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const messageArchive: IMessageArchive = sampleWithRequiredData;
        const messageArchive2: IMessageArchive = sampleWithPartialData;
        expectedResult = service.addMessageArchiveToCollectionIfMissing([], messageArchive, messageArchive2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(messageArchive);
        expect(expectedResult).toContain(messageArchive2);
      });

      it('should accept null and undefined values', () => {
        const messageArchive: IMessageArchive = sampleWithRequiredData;
        expectedResult = service.addMessageArchiveToCollectionIfMissing([], null, messageArchive, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(messageArchive);
      });

      it('should return initial array if no MessageArchive is added', () => {
        const messageArchiveCollection: IMessageArchive[] = [sampleWithRequiredData];
        expectedResult = service.addMessageArchiveToCollectionIfMissing(messageArchiveCollection, undefined, null);
        expect(expectedResult).toEqual(messageArchiveCollection);
      });
    });

    describe('compareMessageArchive', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMessageArchive(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMessageArchive(entity1, entity2);
        const compareResult2 = service.compareMessageArchive(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMessageArchive(entity1, entity2);
        const compareResult2 = service.compareMessageArchive(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMessageArchive(entity1, entity2);
        const compareResult2 = service.compareMessageArchive(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
