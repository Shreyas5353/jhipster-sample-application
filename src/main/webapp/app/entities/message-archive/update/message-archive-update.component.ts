import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IMessageArchive } from '../message-archive.model';
import { MessageArchiveService } from '../service/message-archive.service';
import { MessageArchiveFormService, MessageArchiveFormGroup } from './message-archive-form.service';

@Component({
  standalone: true,
  selector: 'jhi-message-archive-update',
  templateUrl: './message-archive-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MessageArchiveUpdateComponent implements OnInit {
  isSaving = false;
  messageArchive: IMessageArchive | null = null;

  protected messageArchiveService = inject(MessageArchiveService);
  protected messageArchiveFormService = inject(MessageArchiveFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: MessageArchiveFormGroup = this.messageArchiveFormService.createMessageArchiveFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ messageArchive }) => {
      this.messageArchive = messageArchive;
      if (messageArchive) {
        this.updateForm(messageArchive);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const messageArchive = this.messageArchiveFormService.getMessageArchive(this.editForm);
    if (messageArchive.id !== null) {
      this.subscribeToSaveResponse(this.messageArchiveService.update(messageArchive));
    } else {
      this.subscribeToSaveResponse(this.messageArchiveService.create(messageArchive));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMessageArchive>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(messageArchive: IMessageArchive): void {
    this.messageArchive = messageArchive;
    this.messageArchiveFormService.resetForm(this.editForm, messageArchive);
  }
}
