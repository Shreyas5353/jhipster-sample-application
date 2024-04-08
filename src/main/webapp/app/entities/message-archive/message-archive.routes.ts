import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { MessageArchiveComponent } from './list/message-archive.component';
import { MessageArchiveDetailComponent } from './detail/message-archive-detail.component';
import { MessageArchiveUpdateComponent } from './update/message-archive-update.component';
import MessageArchiveResolve from './route/message-archive-routing-resolve.service';

const messageArchiveRoute: Routes = [
  {
    path: '',
    component: MessageArchiveComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MessageArchiveDetailComponent,
    resolve: {
      messageArchive: MessageArchiveResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MessageArchiveUpdateComponent,
    resolve: {
      messageArchive: MessageArchiveResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MessageArchiveUpdateComponent,
    resolve: {
      messageArchive: MessageArchiveResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default messageArchiveRoute;
