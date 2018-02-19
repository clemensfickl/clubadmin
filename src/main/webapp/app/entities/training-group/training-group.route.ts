import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { TrainingGroupComponent } from './training-group.component';
import { TrainingGroupDetailComponent } from './training-group-detail.component';
import { TrainingGroupPopupComponent } from './training-group-dialog.component';
import { TrainingGroupDeletePopupComponent } from './training-group-delete-dialog.component';

export const trainingGroupRoute: Routes = [
    {
        path: 'training-group',
        component: TrainingGroupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clubadminApp.trainingGroup.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'training-group/:id',
        component: TrainingGroupDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clubadminApp.trainingGroup.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const trainingGroupPopupRoute: Routes = [
    {
        path: 'training-group-new',
        component: TrainingGroupPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clubadminApp.trainingGroup.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'training-group/:id/edit',
        component: TrainingGroupPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clubadminApp.trainingGroup.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'training-group/:id/delete',
        component: TrainingGroupDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clubadminApp.trainingGroup.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
