import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { TrainingGroup } from './training-group.model';
import { TrainingGroupService } from './training-group.service';

@Component({
    selector: 'jhi-training-group-detail',
    templateUrl: './training-group-detail.component.html'
})
export class TrainingGroupDetailComponent implements OnInit, OnDestroy {

    trainingGroup: TrainingGroup;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private trainingGroupService: TrainingGroupService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTrainingGroups();
    }

    load(id) {
        this.trainingGroupService.find(id)
            .subscribe((trainingGroupResponse: HttpResponse<TrainingGroup>) => {
                this.trainingGroup = trainingGroupResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTrainingGroups() {
        this.eventSubscriber = this.eventManager.subscribe(
            'trainingGroupListModification',
            (response) => this.load(this.trainingGroup.id)
        );
    }
}
