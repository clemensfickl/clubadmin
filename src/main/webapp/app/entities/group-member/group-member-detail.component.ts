import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { GroupMember } from './group-member.model';
import { GroupMemberService } from './group-member.service';

@Component({
    selector: 'jhi-group-member-detail',
    templateUrl: './group-member-detail.component.html'
})
export class GroupMemberDetailComponent implements OnInit, OnDestroy {

    groupMember: GroupMember;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private groupMemberService: GroupMemberService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInGroupMembers();
    }

    load(id) {
        this.groupMemberService.find(id)
            .subscribe((groupMemberResponse: HttpResponse<GroupMember>) => {
                this.groupMember = groupMemberResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInGroupMembers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'groupMemberListModification',
            (response) => this.load(this.groupMember.id)
        );
    }
}
