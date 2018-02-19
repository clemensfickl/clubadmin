/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ClubadminTestModule } from '../../../test.module';
import { ContributionGroupEntryDetailComponent } from '../../../../../../main/webapp/app/entities/contribution-group-entry/contribution-group-entry-detail.component';
import { ContributionGroupEntryService } from '../../../../../../main/webapp/app/entities/contribution-group-entry/contribution-group-entry.service';
import { ContributionGroupEntry } from '../../../../../../main/webapp/app/entities/contribution-group-entry/contribution-group-entry.model';

describe('Component Tests', () => {

    describe('ContributionGroupEntry Management Detail Component', () => {
        let comp: ContributionGroupEntryDetailComponent;
        let fixture: ComponentFixture<ContributionGroupEntryDetailComponent>;
        let service: ContributionGroupEntryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClubadminTestModule],
                declarations: [ContributionGroupEntryDetailComponent],
                providers: [
                    ContributionGroupEntryService
                ]
            })
            .overrideTemplate(ContributionGroupEntryDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ContributionGroupEntryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContributionGroupEntryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ContributionGroupEntry(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.contributionGroupEntry).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
