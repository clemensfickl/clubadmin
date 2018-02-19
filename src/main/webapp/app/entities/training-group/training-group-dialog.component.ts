import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TrainingGroup } from './training-group.model';
import { TrainingGroupPopupService } from './training-group-popup.service';
import { TrainingGroupService } from './training-group.service';

@Component({
    selector: 'jhi-training-group-dialog',
    templateUrl: './training-group-dialog.component.html'
})
export class TrainingGroupDialogComponent implements OnInit {

    trainingGroup: TrainingGroup;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private trainingGroupService: TrainingGroupService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.trainingGroup.id !== undefined) {
            this.subscribeToSaveResponse(
                this.trainingGroupService.update(this.trainingGroup));
        } else {
            this.subscribeToSaveResponse(
                this.trainingGroupService.create(this.trainingGroup));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<TrainingGroup>>) {
        result.subscribe((res: HttpResponse<TrainingGroup>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: TrainingGroup) {
        this.eventManager.broadcast({ name: 'trainingGroupListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-training-group-popup',
    template: ''
})
export class TrainingGroupPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private trainingGroupPopupService: TrainingGroupPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.trainingGroupPopupService
                    .open(TrainingGroupDialogComponent as Component, params['id']);
            } else {
                this.trainingGroupPopupService
                    .open(TrainingGroupDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
