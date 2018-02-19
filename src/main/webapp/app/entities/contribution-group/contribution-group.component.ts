import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ContributionGroup } from './contribution-group.model';
import { ContributionGroupService } from './contribution-group.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-contribution-group',
    templateUrl: './contribution-group.component.html'
})
export class ContributionGroupComponent implements OnInit, OnDestroy {
contributionGroups: ContributionGroup[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private contributionGroupService: ContributionGroupService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.contributionGroupService.query().subscribe(
            (res: HttpResponse<ContributionGroup[]>) => {
                this.contributionGroups = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInContributionGroups();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ContributionGroup) {
        return item.id;
    }
    registerChangeInContributionGroups() {
        this.eventSubscriber = this.eventManager.subscribe('contributionGroupListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
