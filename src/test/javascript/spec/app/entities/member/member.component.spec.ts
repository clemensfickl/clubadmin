/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ClubadminTestModule } from '../../../test.module';
import { MemberComponent } from '../../../../../../main/webapp/app/entities/member/member.component';
import { MemberService } from '../../../../../../main/webapp/app/entities/member/member.service';
import { Member } from '../../../../../../main/webapp/app/entities/member/member.model';

describe('Component Tests', () => {

    describe('Member Management Component', () => {
        let comp: MemberComponent;
        let fixture: ComponentFixture<MemberComponent>;
        let service: MemberService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClubadminTestModule],
                declarations: [MemberComponent],
                providers: [
                    MemberService
                ]
            })
            .overrideTemplate(MemberComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MemberComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MemberService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Member(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.members[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
