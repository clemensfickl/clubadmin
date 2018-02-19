import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Member } from './member.model';
import { MemberPopupService } from './member-popup.service';
import { MemberService } from './member.service';

@Component({
    selector: 'jhi-member-dialog',
    templateUrl: './member-dialog.component.html'
})
export class MemberDialogComponent implements OnInit {

    member: Member;
    isSaving: boolean;
    birthdateDp: any;
    entryDateDp: any;
    terminationDateDp: any;
    exitDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private memberService: MemberService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.member.id !== undefined) {
            this.subscribeToSaveResponse(
                this.memberService.update(this.member));
        } else {
            this.subscribeToSaveResponse(
                this.memberService.create(this.member));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Member>>) {
        result.subscribe((res: HttpResponse<Member>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Member) {
        this.eventManager.broadcast({ name: 'memberListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-member-popup',
    template: ''
})
export class MemberPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private memberPopupService: MemberPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.memberPopupService
                    .open(MemberDialogComponent as Component, params['id']);
            } else {
                this.memberPopupService
                    .open(MemberDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
