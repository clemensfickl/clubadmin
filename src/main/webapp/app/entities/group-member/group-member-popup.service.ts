import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { GroupMember } from './group-member.model';
import { GroupMemberService } from './group-member.service';

@Injectable()
export class GroupMemberPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private groupMemberService: GroupMemberService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.groupMemberService.find(id)
                    .subscribe((groupMemberResponse: HttpResponse<GroupMember>) => {
                        const groupMember: GroupMember = groupMemberResponse.body;
                        if (groupMember.startDate) {
                            groupMember.startDate = {
                                year: groupMember.startDate.getFullYear(),
                                month: groupMember.startDate.getMonth() + 1,
                                day: groupMember.startDate.getDate()
                            };
                        }
                        if (groupMember.endDate) {
                            groupMember.endDate = {
                                year: groupMember.endDate.getFullYear(),
                                month: groupMember.endDate.getMonth() + 1,
                                day: groupMember.endDate.getDate()
                            };
                        }
                        this.ngbModalRef = this.groupMemberModalRef(component, groupMember);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.groupMemberModalRef(component, new GroupMember());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    groupMemberModalRef(component: Component, groupMember: GroupMember): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.groupMember = groupMember;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
