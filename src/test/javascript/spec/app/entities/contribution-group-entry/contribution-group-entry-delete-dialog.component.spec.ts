/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ClubadminTestModule } from '../../../test.module';
import { ContributionGroupEntryDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/contribution-group-entry/contribution-group-entry-delete-dialog.component';
import { ContributionGroupEntryService } from '../../../../../../main/webapp/app/entities/contribution-group-entry/contribution-group-entry.service';

describe('Component Tests', () => {

    describe('ContributionGroupEntry Management Delete Component', () => {
        let comp: ContributionGroupEntryDeleteDialogComponent;
        let fixture: ComponentFixture<ContributionGroupEntryDeleteDialogComponent>;
        let service: ContributionGroupEntryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClubadminTestModule],
                declarations: [ContributionGroupEntryDeleteDialogComponent],
                providers: [
                    ContributionGroupEntryService
                ]
            })
            .overrideTemplate(ContributionGroupEntryDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ContributionGroupEntryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContributionGroupEntryService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
