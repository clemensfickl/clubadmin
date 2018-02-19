import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ContributionGroup } from './contribution-group.model';
import { ContributionGroupPopupService } from './contribution-group-popup.service';
import { ContributionGroupService } from './contribution-group.service';

@Component({
    selector: 'jhi-contribution-group-delete-dialog',
    templateUrl: './contribution-group-delete-dialog.component.html'
})
export class ContributionGroupDeleteDialogComponent {

    contributionGroup: ContributionGroup;

    constructor(
        private contributionGroupService: ContributionGroupService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.contributionGroupService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'contributionGroupListModification',
                content: 'Deleted an contributionGroup'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-contribution-group-delete-popup',
    template: ''
})
export class ContributionGroupDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private contributionGroupPopupService: ContributionGroupPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.contributionGroupPopupService
                .open(ContributionGroupDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
