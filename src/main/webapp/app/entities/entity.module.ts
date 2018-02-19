import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ClubadminMemberModule } from './member/member.module';
import { ClubadminCountryModule } from './country/country.module';
import { ClubadminTrainingGroupModule } from './training-group/training-group.module';
import { ClubadminGroupMemberModule } from './group-member/group-member.module';
import { ClubadminContributionGroupModule } from './contribution-group/contribution-group.module';
import { ClubadminContributionGroupEntryModule } from './contribution-group-entry/contribution-group-entry.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ClubadminMemberModule,
        ClubadminCountryModule,
        ClubadminTrainingGroupModule,
        ClubadminGroupMemberModule,
        ClubadminContributionGroupModule,
        ClubadminContributionGroupEntryModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClubadminEntityModule {}
