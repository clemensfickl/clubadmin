import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ContributionGroupEntryComponent } from './contribution-group-entry.component';
import { ContributionGroupEntryDetailComponent } from './contribution-group-entry-detail.component';
import { ContributionGroupEntryPopupComponent } from './contribution-group-entry-dialog.component';
import { ContributionGroupEntryDeletePopupComponent } from './contribution-group-entry-delete-dialog.component';

export const contributionGroupEntryRoute: Routes = [
    {
        path: 'contribution-group-entry',
        component: ContributionGroupEntryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clubadminApp.contributionGroupEntry.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'contribution-group-entry/:id',
        component: ContributionGroupEntryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clubadminApp.contributionGroupEntry.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contributionGroupEntryPopupRoute: Routes = [
    {
        path: 'contribution-group-entry-new',
        component: ContributionGroupEntryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clubadminApp.contributionGroupEntry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'contribution-group-entry/:id/edit',
        component: ContributionGroupEntryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clubadminApp.contributionGroupEntry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'contribution-group-entry/:id/delete',
        component: ContributionGroupEntryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clubadminApp.contributionGroupEntry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
