import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClubadminSharedModule } from '../../shared';
import {
    ContributionGroupService,
    ContributionGroupPopupService,
    ContributionGroupComponent,
    ContributionGroupDetailComponent,
    ContributionGroupDialogComponent,
    ContributionGroupPopupComponent,
    ContributionGroupDeletePopupComponent,
    ContributionGroupDeleteDialogComponent,
    contributionGroupRoute,
    contributionGroupPopupRoute,
} from './';

const ENTITY_STATES = [
    ...contributionGroupRoute,
    ...contributionGroupPopupRoute,
];

@NgModule({
    imports: [
        ClubadminSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ContributionGroupComponent,
        ContributionGroupDetailComponent,
        ContributionGroupDialogComponent,
        ContributionGroupDeleteDialogComponent,
        ContributionGroupPopupComponent,
        ContributionGroupDeletePopupComponent,
    ],
    entryComponents: [
        ContributionGroupComponent,
        ContributionGroupDialogComponent,
        ContributionGroupPopupComponent,
        ContributionGroupDeleteDialogComponent,
        ContributionGroupDeletePopupComponent,
    ],
    providers: [
        ContributionGroupService,
        ContributionGroupPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClubadminContributionGroupModule {}
