import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { ContributionGroupEntry } from './contribution-group-entry.model';
import { ContributionGroupEntryService } from './contribution-group-entry.service';

@Injectable()
export class ContributionGroupEntryPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private contributionGroupEntryService: ContributionGroupEntryService

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
                this.contributionGroupEntryService.find(id)
                    .subscribe((contributionGroupEntryResponse: HttpResponse<ContributionGroupEntry>) => {
                        const contributionGroupEntry: ContributionGroupEntry = contributionGroupEntryResponse.body;
                        this.ngbModalRef = this.contributionGroupEntryModalRef(component, contributionGroupEntry);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.contributionGroupEntryModalRef(component, new ContributionGroupEntry());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    contributionGroupEntryModalRef(component: Component, contributionGroupEntry: ContributionGroupEntry): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.contributionGroupEntry = contributionGroupEntry;
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
