/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ClubadminTestModule } from '../../../test.module';
import { TrainingGroupDetailComponent } from '../../../../../../main/webapp/app/entities/training-group/training-group-detail.component';
import { TrainingGroupService } from '../../../../../../main/webapp/app/entities/training-group/training-group.service';
import { TrainingGroup } from '../../../../../../main/webapp/app/entities/training-group/training-group.model';

describe('Component Tests', () => {

    describe('TrainingGroup Management Detail Component', () => {
        let comp: TrainingGroupDetailComponent;
        let fixture: ComponentFixture<TrainingGroupDetailComponent>;
        let service: TrainingGroupService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClubadminTestModule],
                declarations: [TrainingGroupDetailComponent],
                providers: [
                    TrainingGroupService
                ]
            })
            .overrideTemplate(TrainingGroupDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TrainingGroupDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TrainingGroupService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new TrainingGroup(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.trainingGroup).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
