import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { GroupMemberComponent } from './group-member.component';
import { GroupMemberDetailComponent } from './group-member-detail.component';
import { GroupMemberPopupComponent } from './group-member-dialog.component';
import { GroupMemberDeletePopupComponent } from './group-member-delete-dialog.component';

export const groupMemberRoute: Routes = [
    {
        path: 'group-member',
        component: GroupMemberComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clubadminApp.groupMember.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'group-member/:id',
        component: GroupMemberDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clubadminApp.groupMember.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const groupMemberPopupRoute: Routes = [
    {
        path: 'group-member-new',
        component: GroupMemberPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clubadminApp.groupMember.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'group-member/:id/edit',
        component: GroupMemberPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clubadminApp.groupMember.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'group-member/:id/delete',
        component: GroupMemberDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clubadminApp.groupMember.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
