import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { GroupMember } from './group-member.model';
import { GroupMemberService } from './group-member.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-group-member',
    templateUrl: './group-member.component.html'
})
export class GroupMemberComponent implements OnInit, OnDestroy {
groupMembers: GroupMember[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private groupMemberService: GroupMemberService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.groupMemberService.query().subscribe(
            (res: HttpResponse<GroupMember[]>) => {
                this.groupMembers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInGroupMembers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: GroupMember) {
        return item.id;
    }
    registerChangeInGroupMembers() {
        this.eventSubscriber = this.eventManager.subscribe('groupMemberListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
