import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ContributionGroupEntry } from './contribution-group-entry.model';
import { ContributionGroupEntryService } from './contribution-group-entry.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-contribution-group-entry',
    templateUrl: './contribution-group-entry.component.html'
})
export class ContributionGroupEntryComponent implements OnInit, OnDestroy {
contributionGroupEntries: ContributionGroupEntry[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private contributionGroupEntryService: ContributionGroupEntryService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.contributionGroupEntryService.query().subscribe(
            (res: HttpResponse<ContributionGroupEntry[]>) => {
                this.contributionGroupEntries = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInContributionGroupEntries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ContributionGroupEntry) {
        return item.id;
    }
    registerChangeInContributionGroupEntries() {
        this.eventSubscriber = this.eventManager.subscribe('contributionGroupEntryListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
