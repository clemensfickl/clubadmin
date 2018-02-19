import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ContributionGroupComponent } from './contribution-group.component';
import { ContributionGroupDetailComponent } from './contribution-group-detail.component';
import { ContributionGroupPopupComponent } from './contribution-group-dialog.component';
import { ContributionGroupDeletePopupComponent } from './contribution-group-delete-dialog.component';

export const contributionGroupRoute: Routes = [
    {
        path: 'contribution-group',
        component: ContributionGroupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clubadminApp.contributionGroup.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'contribution-group/:id',
        component: ContributionGroupDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clubadminApp.contributionGroup.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contributionGroupPopupRoute: Routes = [
    {
        path: 'contribution-group-new',
        component: ContributionGroupPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clubadminApp.contributionGroup.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'contribution-group/:id/edit',
        component: ContributionGroupPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clubadminApp.contributionGroup.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'contribution-group/:id/delete',
        component: ContributionGroupDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clubadminApp.contributionGroup.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
