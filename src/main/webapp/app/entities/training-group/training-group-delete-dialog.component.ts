import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TrainingGroup } from './training-group.model';
import { TrainingGroupPopupService } from './training-group-popup.service';
import { TrainingGroupService } from './training-group.service';

@Component({
    selector: 'jhi-training-group-delete-dialog',
    templateUrl: './training-group-delete-dialog.component.html'
})
export class TrainingGroupDeleteDialogComponent {

    trainingGroup: TrainingGroup;

    constructor(
        private trainingGroupService: TrainingGroupService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.trainingGroupService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'trainingGroupListModification',
                content: 'Deleted an trainingGroup'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-training-group-delete-popup',
    template: ''
})
export class TrainingGroupDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private trainingGroupPopupService: TrainingGroupPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.trainingGroupPopupService
                .open(TrainingGroupDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
