/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ClubadminTestModule } from '../../../test.module';
import { GroupMemberComponent } from '../../../../../../main/webapp/app/entities/group-member/group-member.component';
import { GroupMemberService } from '../../../../../../main/webapp/app/entities/group-member/group-member.service';
import { GroupMember } from '../../../../../../main/webapp/app/entities/group-member/group-member.model';

describe('Component Tests', () => {

    describe('GroupMember Management Component', () => {
        let comp: GroupMemberComponent;
        let fixture: ComponentFixture<GroupMemberComponent>;
        let service: GroupMemberService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClubadminTestModule],
                declarations: [GroupMemberComponent],
                providers: [
                    GroupMemberService
                ]
            })
            .overrideTemplate(GroupMemberComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GroupMemberComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GroupMemberService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new GroupMember(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.groupMembers[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
