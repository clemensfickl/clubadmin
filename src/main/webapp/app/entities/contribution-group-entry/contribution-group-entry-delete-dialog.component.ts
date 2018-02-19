import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ContributionGroupEntry } from './contribution-group-entry.model';
import { ContributionGroupEntryPopupService } from './contribution-group-entry-popup.service';
import { ContributionGroupEntryService } from './contribution-group-entry.service';

@Component({
    selector: 'jhi-contribution-group-entry-delete-dialog',
    templateUrl: './contribution-group-entry-delete-dialog.component.html'
})
export class ContributionGroupEntryDeleteDialogComponent {

    contributionGroupEntry: ContributionGroupEntry;

    constructor(
        private contributionGroupEntryService: ContributionGroupEntryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.contributionGroupEntryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'contributionGroupEntryListModification',
                content: 'Deleted an contributionGroupEntry'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-contribution-group-entry-delete-popup',
    template: ''
})
export class ContributionGroupEntryDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private contributionGroupEntryPopupService: ContributionGroupEntryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.contributionGroupEntryPopupService
                .open(ContributionGroupEntryDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
