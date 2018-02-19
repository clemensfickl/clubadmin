import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { GroupMember } from './group-member.model';
import { GroupMemberPopupService } from './group-member-popup.service';
import { GroupMemberService } from './group-member.service';

@Component({
    selector: 'jhi-group-member-delete-dialog',
    templateUrl: './group-member-delete-dialog.component.html'
})
export class GroupMemberDeleteDialogComponent {

    groupMember: GroupMember;

    constructor(
        private groupMemberService: GroupMemberService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.groupMemberService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'groupMemberListModification',
                content: 'Deleted an groupMember'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-group-member-delete-popup',
    template: ''
})
export class GroupMemberDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private groupMemberPopupService: GroupMemberPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.groupMemberPopupService
                .open(GroupMemberDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
