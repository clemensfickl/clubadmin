import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Member } from './member.model';
import { MemberService } from './member.service';

@Injectable()
export class MemberPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private memberService: MemberService

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
                this.memberService.find(id)
                    .subscribe((memberResponse: HttpResponse<Member>) => {
                        const member: Member = memberResponse.body;
                        if (member.birthdate) {
                            member.birthdate = {
                                year: member.birthdate.getFullYear(),
                                month: member.birthdate.getMonth() + 1,
                                day: member.birthdate.getDate()
                            };
                        }
                        if (member.entryDate) {
                            member.entryDate = {
                                year: member.entryDate.getFullYear(),
                                month: member.entryDate.getMonth() + 1,
                                day: member.entryDate.getDate()
                            };
                        }
                        if (member.terminationDate) {
                            member.terminationDate = {
                                year: member.terminationDate.getFullYear(),
                                month: member.terminationDate.getMonth() + 1,
                                day: member.terminationDate.getDate()
                            };
                        }
                        if (member.exitDate) {
                            member.exitDate = {
                                year: member.exitDate.getFullYear(),
                                month: member.exitDate.getMonth() + 1,
                                day: member.exitDate.getDate()
                            };
                        }
                        this.ngbModalRef = this.memberModalRef(component, member);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.memberModalRef(component, new Member());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    memberModalRef(component: Component, member: Member): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.member = member;
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
