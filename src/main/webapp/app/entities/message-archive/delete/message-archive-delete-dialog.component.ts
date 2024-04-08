import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IMessageArchive } from '../message-archive.model';
import { MessageArchiveService } from '../service/message-archive.service';

@Component({
  standalone: true,
  templateUrl: './message-archive-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class MessageArchiveDeleteDialogComponent {
  messageArchive?: IMessageArchive;

  protected messageArchiveService = inject(MessageArchiveService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.messageArchiveService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
