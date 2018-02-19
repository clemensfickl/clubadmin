import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { TrainingGroup } from './training-group.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<TrainingGroup>;

@Injectable()
export class TrainingGroupService {

    private resourceUrl =  SERVER_API_URL + 'api/training-groups';

    constructor(private http: HttpClient) { }

    create(trainingGroup: TrainingGroup): Observable<EntityResponseType> {
        const copy = this.convert(trainingGroup);
        return this.http.post<TrainingGroup>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(trainingGroup: TrainingGroup): Observable<EntityResponseType> {
        const copy = this.convert(trainingGroup);
        return this.http.put<TrainingGroup>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<TrainingGroup>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<TrainingGroup[]>> {
        const options = createRequestOption(req);
        return this.http.get<TrainingGroup[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TrainingGroup[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: TrainingGroup = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<TrainingGroup[]>): HttpResponse<TrainingGroup[]> {
        const jsonResponse: TrainingGroup[] = res.body;
        const body: TrainingGroup[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to TrainingGroup.
     */
    private convertItemFromServer(trainingGroup: TrainingGroup): TrainingGroup {
        const copy: TrainingGroup = Object.assign({}, trainingGroup);
        return copy;
    }

    /**
     * Convert a TrainingGroup to a JSON which can be sent to the server.
     */
    private convert(trainingGroup: TrainingGroup): TrainingGroup {
        const copy: TrainingGroup = Object.assign({}, trainingGroup);
        return copy;
    }
}
