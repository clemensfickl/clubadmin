import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ContributionGroupEntry } from './contribution-group-entry.model';
import { ContributionGroupEntryPopupService } from './contribution-group-entry-popup.service';
import { ContributionGroupEntryService } from './contribution-group-entry.service';
import { ContributionGroup, ContributionGroupService } from '../contribution-group';

@Component({
    selector: 'jhi-contribution-group-entry-dialog',
    templateUrl: './contribution-group-entry-dialog.component.html'
})
export class ContributionGroupEntryDialogComponent implements OnInit {

    contributionGroupEntry: ContributionGroupEntry;
    isSaving: boolean;

    contributiongroups: ContributionGroup[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private contributionGroupEntryService: ContributionGroupEntryService,
        private contributionGroupService: ContributionGroupService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.contributionGroupService.query()
            .subscribe((res: HttpResponse<ContributionGroup[]>) => { this.contributiongroups = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.contributionGroupEntry.id !== undefined) {
            this.subscribeToSaveResponse(
                this.contributionGroupEntryService.update(this.contributionGroupEntry));
        } else {
            this.subscribeToSaveResponse(
                this.contributionGroupEntryService.create(this.contributionGroupEntry));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ContributionGroupEntry>>) {
        result.subscribe((res: HttpResponse<ContributionGroupEntry>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ContributionGroupEntry) {
        this.eventManager.broadcast({ name: 'contributionGroupEntryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackContributionGroupById(index: number, item: ContributionGroup) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-contribution-group-entry-popup',
    template: ''
})
export class ContributionGroupEntryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private contributionGroupEntryPopupService: ContributionGroupEntryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.contributionGroupEntryPopupService
                    .open(ContributionGroupEntryDialogComponent as Component, params['id']);
            } else {
                this.contributionGroupEntryPopupService
                    .open(ContributionGroupEntryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
