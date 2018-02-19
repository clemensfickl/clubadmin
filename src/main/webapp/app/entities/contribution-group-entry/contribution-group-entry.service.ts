import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { ContributionGroupEntry } from './contribution-group-entry.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ContributionGroupEntry>;

@Injectable()
export class ContributionGroupEntryService {

    private resourceUrl =  SERVER_API_URL + 'api/contribution-group-entries';

    constructor(private http: HttpClient) { }

    create(contributionGroupEntry: ContributionGroupEntry): Observable<EntityResponseType> {
        const copy = this.convert(contributionGroupEntry);
        return this.http.post<ContributionGroupEntry>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(contributionGroupEntry: ContributionGroupEntry): Observable<EntityResponseType> {
        const copy = this.convert(contributionGroupEntry);
        return this.http.put<ContributionGroupEntry>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ContributionGroupEntry>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ContributionGroupEntry[]>> {
        const options = createRequestOption(req);
        return this.http.get<ContributionGroupEntry[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ContributionGroupEntry[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ContributionGroupEntry = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ContributionGroupEntry[]>): HttpResponse<ContributionGroupEntry[]> {
        const jsonResponse: ContributionGroupEntry[] = res.body;
        const body: ContributionGroupEntry[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ContributionGroupEntry.
     */
    private convertItemFromServer(contributionGroupEntry: ContributionGroupEntry): ContributionGroupEntry {
        const copy: ContributionGroupEntry = Object.assign({}, contributionGroupEntry);
        return copy;
    }

    /**
     * Convert a ContributionGroupEntry to a JSON which can be sent to the server.
     */
    private convert(contributionGroupEntry: ContributionGroupEntry): ContributionGroupEntry {
        const copy: ContributionGroupEntry = Object.assign({}, contributionGroupEntry);
        return copy;
    }
}
