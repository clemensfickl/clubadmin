/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ClubadminTestModule } from '../../../test.module';
import { GroupMemberDetailComponent } from '../../../../../../main/webapp/app/entities/group-member/group-member-detail.component';
import { GroupMemberService } from '../../../../../../main/webapp/app/entities/group-member/group-member.service';
import { GroupMember } from '../../../../../../main/webapp/app/entities/group-member/group-member.model';

describe('Component Tests', () => {

    describe('GroupMember Management Detail Component', () => {
        let comp: GroupMemberDetailComponent;
        let fixture: ComponentFixture<GroupMemberDetailComponent>;
        let service: GroupMemberService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClubadminTestModule],
                declarations: [GroupMemberDetailComponent],
                providers: [
                    GroupMemberService
                ]
            })
            .overrideTemplate(GroupMemberDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GroupMemberDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GroupMemberService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new GroupMember(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.groupMember).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
