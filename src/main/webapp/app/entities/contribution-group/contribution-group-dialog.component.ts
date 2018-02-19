import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ContributionGroup } from './contribution-group.model';
import { ContributionGroupPopupService } from './contribution-group-popup.service';
import { ContributionGroupService } from './contribution-group.service';

@Component({
    selector: 'jhi-contribution-group-dialog',
    templateUrl: './contribution-group-dialog.component.html'
})
export class ContributionGroupDialogComponent implements OnInit {

    contributionGroup: ContributionGroup;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private contributionGroupService: ContributionGroupService,
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
        if (this.contributionGroup.id !== undefined) {
            this.subscribeToSaveResponse(
                this.contributionGroupService.update(this.contributionGroup));
        } else {
            this.subscribeToSaveResponse(
                this.contributionGroupService.create(this.contributionGroup));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ContributionGroup>>) {
        result.subscribe((res: HttpResponse<ContributionGroup>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ContributionGroup) {
        this.eventManager.broadcast({ name: 'contributionGroupListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-contribution-group-popup',
    template: ''
})
export class ContributionGroupPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private contributionGroupPopupService: ContributionGroupPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.contributionGroupPopupService
                    .open(ContributionGroupDialogComponent as Component, params['id']);
            } else {
                this.contributionGroupPopupService
                    .open(ContributionGroupDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
