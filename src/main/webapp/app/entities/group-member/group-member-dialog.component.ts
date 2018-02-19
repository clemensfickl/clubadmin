import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { GroupMember } from './group-member.model';
import { GroupMemberPopupService } from './group-member-popup.service';
import { GroupMemberService } from './group-member.service';
import { TrainingGroup, TrainingGroupService } from '../training-group';
import { Member, MemberService } from '../member';

@Component({
    selector: 'jhi-group-member-dialog',
    templateUrl: './group-member-dialog.component.html'
})
export class GroupMemberDialogComponent implements OnInit {

    groupMember: GroupMember;
    isSaving: boolean;

    traininggroups: TrainingGroup[];

    members: Member[];
    startDateDp: any;
    endDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private groupMemberService: GroupMemberService,
        private trainingGroupService: TrainingGroupService,
        private memberService: MemberService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.trainingGroupService.query()
            .subscribe((res: HttpResponse<TrainingGroup[]>) => { this.traininggroups = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.memberService.query()
            .subscribe((res: HttpResponse<Member[]>) => { this.members = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.groupMember.id !== undefined) {
            this.subscribeToSaveResponse(
                this.groupMemberService.update(this.groupMember));
        } else {
            this.subscribeToSaveResponse(
                this.groupMemberService.create(this.groupMember));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<GroupMember>>) {
        result.subscribe((res: HttpResponse<GroupMember>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: GroupMember) {
        this.eventManager.broadcast({ name: 'groupMemberListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackTrainingGroupById(index: number, item: TrainingGroup) {
        return item.id;
    }

    trackMemberById(index: number, item: Member) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-group-member-popup',
    template: ''
})
export class GroupMemberPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private groupMemberPopupService: GroupMemberPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.groupMemberPopupService
                    .open(GroupMemberDialogComponent as Component, params['id']);
            } else {
                this.groupMemberPopupService
                    .open(GroupMemberDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
