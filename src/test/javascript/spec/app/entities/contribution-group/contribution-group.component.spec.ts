/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ClubadminTestModule } from '../../../test.module';
import { ContributionGroupComponent } from '../../../../../../main/webapp/app/entities/contribution-group/contribution-group.component';
import { ContributionGroupService } from '../../../../../../main/webapp/app/entities/contribution-group/contribution-group.service';
import { ContributionGroup } from '../../../../../../main/webapp/app/entities/contribution-group/contribution-group.model';

describe('Component Tests', () => {

    describe('ContributionGroup Management Component', () => {
        let comp: ContributionGroupComponent;
        let fixture: ComponentFixture<ContributionGroupComponent>;
        let service: ContributionGroupService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClubadminTestModule],
                declarations: [ContributionGroupComponent],
                providers: [
                    ContributionGroupService
                ]
            })
            .overrideTemplate(ContributionGroupComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ContributionGroupComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContributionGroupService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ContributionGroup(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.contributionGroups[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
