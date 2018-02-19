/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ClubadminTestModule } from '../../../test.module';
import { ContributionGroupEntryComponent } from '../../../../../../main/webapp/app/entities/contribution-group-entry/contribution-group-entry.component';
import { ContributionGroupEntryService } from '../../../../../../main/webapp/app/entities/contribution-group-entry/contribution-group-entry.service';
import { ContributionGroupEntry } from '../../../../../../main/webapp/app/entities/contribution-group-entry/contribution-group-entry.model';

describe('Component Tests', () => {

    describe('ContributionGroupEntry Management Component', () => {
        let comp: ContributionGroupEntryComponent;
        let fixture: ComponentFixture<ContributionGroupEntryComponent>;
        let service: ContributionGroupEntryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClubadminTestModule],
                declarations: [ContributionGroupEntryComponent],
                providers: [
                    ContributionGroupEntryService
                ]
            })
            .overrideTemplate(ContributionGroupEntryComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ContributionGroupEntryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContributionGroupEntryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ContributionGroupEntry(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.contributionGroupEntries[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
