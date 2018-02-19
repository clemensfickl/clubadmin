import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { MemberComponent } from './member.component';
import { MemberDetailComponent } from './member-detail.component';
import { MemberPopupComponent } from './member-dialog.component';
import { MemberDeletePopupComponent } from './member-delete-dialog.component';

export const memberRoute: Routes = [
    {
        path: 'member',
        component: MemberComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clubadminApp.member.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'member/:id',
        component: MemberDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clubadminApp.member.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const memberPopupRoute: Routes = [
    {
        path: 'member-new',
        component: MemberPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clubadminApp.member.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'member/:id/edit',
        component: MemberPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clubadminApp.member.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'member/:id/delete',
        component: MemberDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clubadminApp.member.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
