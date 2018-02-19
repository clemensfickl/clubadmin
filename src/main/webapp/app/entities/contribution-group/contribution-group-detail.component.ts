import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ContributionGroup } from './contribution-group.model';
import { ContributionGroupService } from './contribution-group.service';

@Component({
    selector: 'jhi-contribution-group-detail',
    templateUrl: './contribution-group-detail.component.html'
})
export class ContributionGroupDetailComponent implements OnInit, OnDestroy {

    contributionGroup: ContributionGroup;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private contributionGroupService: ContributionGroupService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInContributionGroups();
    }

    load(id) {
        this.contributionGroupService.find(id)
            .subscribe((contributionGroupResponse: HttpResponse<ContributionGroup>) => {
                this.contributionGroup = contributionGroupResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInContributionGroups() {
        this.eventSubscriber = this.eventManager.subscribe(
            'contributionGroupListModification',
            (response) => this.load(this.contributionGroup.id)
        );
    }
}
