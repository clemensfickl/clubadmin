import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClubadminSharedModule } from '../../shared';
import {
    GroupMemberService,
    GroupMemberPopupService,
    GroupMemberComponent,
    GroupMemberDetailComponent,
    GroupMemberDialogComponent,
    GroupMemberPopupComponent,
    GroupMemberDeletePopupComponent,
    GroupMemberDeleteDialogComponent,
    groupMemberRoute,
    groupMemberPopupRoute,
} from './';

const ENTITY_STATES = [
    ...groupMemberRoute,
    ...groupMemberPopupRoute,
];

@NgModule({
    imports: [
        ClubadminSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        GroupMemberComponent,
        GroupMemberDetailComponent,
        GroupMemberDialogComponent,
        GroupMemberDeleteDialogComponent,
        GroupMemberPopupComponent,
        GroupMemberDeletePopupComponent,
    ],
    entryComponents: [
        GroupMemberComponent,
        GroupMemberDialogComponent,
        GroupMemberPopupComponent,
        GroupMemberDeleteDialogComponent,
        GroupMemberDeletePopupComponent,
    ],
    providers: [
        GroupMemberService,
        GroupMemberPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClubadminGroupMemberModule {}
