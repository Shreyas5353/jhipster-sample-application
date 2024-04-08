import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'message-archive',
    data: { pageTitle: 'MessageArchives' },
    loadChildren: () => import('./message-archive/message-archive.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
