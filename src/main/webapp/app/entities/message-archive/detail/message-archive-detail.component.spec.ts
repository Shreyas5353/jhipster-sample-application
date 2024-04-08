import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { MessageArchiveDetailComponent } from './message-archive-detail.component';

describe('MessageArchive Management Detail Component', () => {
  let comp: MessageArchiveDetailComponent;
  let fixture: ComponentFixture<MessageArchiveDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MessageArchiveDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: MessageArchiveDetailComponent,
              resolve: { messageArchive: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(MessageArchiveDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MessageArchiveDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load messageArchive on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', MessageArchiveDetailComponent);

      // THEN
      expect(instance.messageArchive).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
