import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Member } from './member.model';
import { MemberPopupService } from './member-popup.service';
import { MemberService } from './member.service';
import { Country, CountryService } from '../country';
import { ContributionGroup, ContributionGroupService } from '../contribution-group';

@Component({
    selector: 'jhi-member-dialog',
    templateUrl: './member-dialog.component.html'
})
export class MemberDialogComponent implements OnInit {

    member: Member;
    isSaving: boolean;

    countries: Country[];

    contributiongroups: ContributionGroup[];
    birthdateDp: any;
    entryDateDp: any;
    terminationDateDp: any;
    exitDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private memberService: MemberService,
        private countryService: CountryService,
        private contributionGroupService: ContributionGroupService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.countryService.query()
            .subscribe((res: HttpResponse<Country[]>) => { this.countries = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.contributionGroupService.query()
            .subscribe((res: HttpResponse<ContributionGroup[]>) => { this.contributiongroups = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
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

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCountryById(index: number, item: Country) {
        return item.id;
    }

    trackContributionGroupById(index: number, item: ContributionGroup) {
        return item.id;
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
