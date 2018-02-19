/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ClubadminTestModule } from '../../../test.module';
import { ContributionGroupDetailComponent } from '../../../../../../main/webapp/app/entities/contribution-group/contribution-group-detail.component';
import { ContributionGroupService } from '../../../../../../main/webapp/app/entities/contribution-group/contribution-group.service';
import { ContributionGroup } from '../../../../../../main/webapp/app/entities/contribution-group/contribution-group.model';

describe('Component Tests', () => {

    describe('ContributionGroup Management Detail Component', () => {
        let comp: ContributionGroupDetailComponent;
        let fixture: ComponentFixture<ContributionGroupDetailComponent>;
        let service: ContributionGroupService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClubadminTestModule],
                declarations: [ContributionGroupDetailComponent],
                providers: [
                    ContributionGroupService
                ]
            })
            .overrideTemplate(ContributionGroupDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ContributionGroupDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContributionGroupService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ContributionGroup(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.contributionGroup).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
