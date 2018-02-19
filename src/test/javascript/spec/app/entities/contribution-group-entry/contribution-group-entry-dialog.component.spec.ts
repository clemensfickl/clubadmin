/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ClubadminTestModule } from '../../../test.module';
import { ContributionGroupEntryDialogComponent } from '../../../../../../main/webapp/app/entities/contribution-group-entry/contribution-group-entry-dialog.component';
import { ContributionGroupEntryService } from '../../../../../../main/webapp/app/entities/contribution-group-entry/contribution-group-entry.service';
import { ContributionGroupEntry } from '../../../../../../main/webapp/app/entities/contribution-group-entry/contribution-group-entry.model';
import { ContributionGroupService } from '../../../../../../main/webapp/app/entities/contribution-group';

describe('Component Tests', () => {

    describe('ContributionGroupEntry Management Dialog Component', () => {
        let comp: ContributionGroupEntryDialogComponent;
        let fixture: ComponentFixture<ContributionGroupEntryDialogComponent>;
        let service: ContributionGroupEntryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClubadminTestModule],
                declarations: [ContributionGroupEntryDialogComponent],
                providers: [
                    ContributionGroupService,
                    ContributionGroupEntryService
                ]
            })
            .overrideTemplate(ContributionGroupEntryDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ContributionGroupEntryDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContributionGroupEntryService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ContributionGroupEntry(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.contributionGroupEntry = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'contributionGroupEntryListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ContributionGroupEntry();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.contributionGroupEntry = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'contributionGroupEntryListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
