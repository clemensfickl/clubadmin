import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClubadminSharedModule } from '../../shared';
import {
    MemberService,
    MemberPopupService,
    MemberComponent,
    MemberDetailComponent,
    MemberDialogComponent,
    MemberPopupComponent,
    MemberDeletePopupComponent,
    MemberDeleteDialogComponent,
    memberRoute,
    memberPopupRoute,
} from './';

const ENTITY_STATES = [
    ...memberRoute,
    ...memberPopupRoute,
];

@NgModule({
    imports: [
        ClubadminSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MemberComponent,
        MemberDetailComponent,
        MemberDialogComponent,
        MemberDeleteDialogComponent,
        MemberPopupComponent,
        MemberDeletePopupComponent,
    ],
    entryComponents: [
        MemberComponent,
        MemberDialogComponent,
        MemberPopupComponent,
        MemberDeleteDialogComponent,
        MemberDeletePopupComponent,
    ],
    providers: [
        MemberService,
        MemberPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClubadminMemberModule {}
