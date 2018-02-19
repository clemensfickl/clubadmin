/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ClubadminTestModule } from '../../../test.module';
import { GroupMemberDialogComponent } from '../../../../../../main/webapp/app/entities/group-member/group-member-dialog.component';
import { GroupMemberService } from '../../../../../../main/webapp/app/entities/group-member/group-member.service';
import { GroupMember } from '../../../../../../main/webapp/app/entities/group-member/group-member.model';
import { TrainingGroupService } from '../../../../../../main/webapp/app/entities/training-group';
import { MemberService } from '../../../../../../main/webapp/app/entities/member';

describe('Component Tests', () => {

    describe('GroupMember Management Dialog Component', () => {
        let comp: GroupMemberDialogComponent;
        let fixture: ComponentFixture<GroupMemberDialogComponent>;
        let service: GroupMemberService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClubadminTestModule],
                declarations: [GroupMemberDialogComponent],
                providers: [
                    TrainingGroupService,
                    MemberService,
                    GroupMemberService
                ]
            })
            .overrideTemplate(GroupMemberDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GroupMemberDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GroupMemberService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new GroupMember(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.groupMember = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'groupMemberListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new GroupMember();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.groupMember = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'groupMemberListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
