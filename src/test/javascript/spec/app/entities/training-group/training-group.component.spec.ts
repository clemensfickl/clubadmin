/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ClubadminTestModule } from '../../../test.module';
import { TrainingGroupComponent } from '../../../../../../main/webapp/app/entities/training-group/training-group.component';
import { TrainingGroupService } from '../../../../../../main/webapp/app/entities/training-group/training-group.service';
import { TrainingGroup } from '../../../../../../main/webapp/app/entities/training-group/training-group.model';

describe('Component Tests', () => {

    describe('TrainingGroup Management Component', () => {
        let comp: TrainingGroupComponent;
        let fixture: ComponentFixture<TrainingGroupComponent>;
        let service: TrainingGroupService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClubadminTestModule],
                declarations: [TrainingGroupComponent],
                providers: [
                    TrainingGroupService
                ]
            })
            .overrideTemplate(TrainingGroupComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TrainingGroupComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TrainingGroupService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new TrainingGroup(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.trainingGroups[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
