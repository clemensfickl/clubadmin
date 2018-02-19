import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { ContributionGroup } from './contribution-group.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ContributionGroup>;

@Injectable()
export class ContributionGroupService {

    private resourceUrl =  SERVER_API_URL + 'api/contribution-groups';

    constructor(private http: HttpClient) { }

    create(contributionGroup: ContributionGroup): Observable<EntityResponseType> {
        const copy = this.convert(contributionGroup);
        return this.http.post<ContributionGroup>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(contributionGroup: ContributionGroup): Observable<EntityResponseType> {
        const copy = this.convert(contributionGroup);
        return this.http.put<ContributionGroup>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ContributionGroup>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ContributionGroup[]>> {
        const options = createRequestOption(req);
        return this.http.get<ContributionGroup[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ContributionGroup[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ContributionGroup = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ContributionGroup[]>): HttpResponse<ContributionGroup[]> {
        const jsonResponse: ContributionGroup[] = res.body;
        const body: ContributionGroup[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ContributionGroup.
     */
    private convertItemFromServer(contributionGroup: ContributionGroup): ContributionGroup {
        const copy: ContributionGroup = Object.assign({}, contributionGroup);
        return copy;
    }

    /**
     * Convert a ContributionGroup to a JSON which can be sent to the server.
     */
    private convert(contributionGroup: ContributionGroup): ContributionGroup {
        const copy: ContributionGroup = Object.assign({}, contributionGroup);
        return copy;
    }
}
