import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ContributionGroupEntry } from './contribution-group-entry.model';
import { ContributionGroupEntryService } from './contribution-group-entry.service';

@Component({
    selector: 'jhi-contribution-group-entry-detail',
    templateUrl: './contribution-group-entry-detail.component.html'
})
export class ContributionGroupEntryDetailComponent implements OnInit, OnDestroy {

    contributionGroupEntry: ContributionGroupEntry;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private contributionGroupEntryService: ContributionGroupEntryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInContributionGroupEntries();
    }

    load(id) {
        this.contributionGroupEntryService.find(id)
            .subscribe((contributionGroupEntryResponse: HttpResponse<ContributionGroupEntry>) => {
                this.contributionGroupEntry = contributionGroupEntryResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInContributionGroupEntries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'contributionGroupEntryListModification',
            (response) => this.load(this.contributionGroupEntry.id)
        );
    }
}
