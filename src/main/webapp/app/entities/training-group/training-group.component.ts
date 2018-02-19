import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TrainingGroup } from './training-group.model';
import { TrainingGroupService } from './training-group.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-training-group',
    templateUrl: './training-group.component.html'
})
export class TrainingGroupComponent implements OnInit, OnDestroy {
trainingGroups: TrainingGroup[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private trainingGroupService: TrainingGroupService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.trainingGroupService.query().subscribe(
            (res: HttpResponse<TrainingGroup[]>) => {
                this.trainingGroups = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTrainingGroups();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: TrainingGroup) {
        return item.id;
    }
    registerChangeInTrainingGroups() {
        this.eventSubscriber = this.eventManager.subscribe('trainingGroupListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
