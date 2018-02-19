import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClubadminSharedModule } from '../../shared';
import {
    TrainingGroupService,
    TrainingGroupPopupService,
    TrainingGroupComponent,
    TrainingGroupDetailComponent,
    TrainingGroupDialogComponent,
    TrainingGroupPopupComponent,
    TrainingGroupDeletePopupComponent,
    TrainingGroupDeleteDialogComponent,
    trainingGroupRoute,
    trainingGroupPopupRoute,
} from './';

const ENTITY_STATES = [
    ...trainingGroupRoute,
    ...trainingGroupPopupRoute,
];

@NgModule({
    imports: [
        ClubadminSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TrainingGroupComponent,
        TrainingGroupDetailComponent,
        TrainingGroupDialogComponent,
        TrainingGroupDeleteDialogComponent,
        TrainingGroupPopupComponent,
        TrainingGroupDeletePopupComponent,
    ],
    entryComponents: [
        TrainingGroupComponent,
        TrainingGroupDialogComponent,
        TrainingGroupPopupComponent,
        TrainingGroupDeleteDialogComponent,
        TrainingGroupDeletePopupComponent,
    ],
    providers: [
        TrainingGroupService,
        TrainingGroupPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClubadminTrainingGroupModule {}
