import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClubadminSharedModule } from '../../shared';
import {
    ContributionGroupEntryService,
    ContributionGroupEntryPopupService,
    ContributionGroupEntryComponent,
    ContributionGroupEntryDetailComponent,
    ContributionGroupEntryDialogComponent,
    ContributionGroupEntryPopupComponent,
    ContributionGroupEntryDeletePopupComponent,
    ContributionGroupEntryDeleteDialogComponent,
    contributionGroupEntryRoute,
    contributionGroupEntryPopupRoute,
} from './';

const ENTITY_STATES = [
    ...contributionGroupEntryRoute,
    ...contributionGroupEntryPopupRoute,
];

@NgModule({
    imports: [
        ClubadminSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ContributionGroupEntryComponent,
        ContributionGroupEntryDetailComponent,
        ContributionGroupEntryDialogComponent,
        ContributionGroupEntryDeleteDialogComponent,
        ContributionGroupEntryPopupComponent,
        ContributionGroupEntryDeletePopupComponent,
    ],
    entryComponents: [
        ContributionGroupEntryComponent,
        ContributionGroupEntryDialogComponent,
        ContributionGroupEntryPopupComponent,
        ContributionGroupEntryDeleteDialogComponent,
        ContributionGroupEntryDeletePopupComponent,
    ],
    providers: [
        ContributionGroupEntryService,
        ContributionGroupEntryPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClubadminContributionGroupEntryModule {}
