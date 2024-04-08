import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IMessageArchive } from '../message-archive.model';

@Component({
  standalone: true,
  selector: 'jhi-message-archive-detail',
  templateUrl: './message-archive-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class MessageArchiveDetailComponent {
  @Input() messageArchive: IMessageArchive | null = null;

  previousState(): void {
    window.history.back();
  }
}
