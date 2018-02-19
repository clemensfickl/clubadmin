import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { GroupMember } from './group-member.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<GroupMember>;

@Injectable()
export class GroupMemberService {

    private resourceUrl =  SERVER_API_URL + 'api/group-members';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(groupMember: GroupMember): Observable<EntityResponseType> {
        const copy = this.convert(groupMember);
        return this.http.post<GroupMember>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(groupMember: GroupMember): Observable<EntityResponseType> {
        const copy = this.convert(groupMember);
        return this.http.put<GroupMember>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<GroupMember>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<GroupMember[]>> {
        const options = createRequestOption(req);
        return this.http.get<GroupMember[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<GroupMember[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: GroupMember = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<GroupMember[]>): HttpResponse<GroupMember[]> {
        const jsonResponse: GroupMember[] = res.body;
        const body: GroupMember[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to GroupMember.
     */
    private convertItemFromServer(groupMember: GroupMember): GroupMember {
        const copy: GroupMember = Object.assign({}, groupMember);
        copy.startDate = this.dateUtils
            .convertLocalDateFromServer(groupMember.startDate);
        copy.endDate = this.dateUtils
            .convertLocalDateFromServer(groupMember.endDate);
        return copy;
    }

    /**
     * Convert a GroupMember to a JSON which can be sent to the server.
     */
    private convert(groupMember: GroupMember): GroupMember {
        const copy: GroupMember = Object.assign({}, groupMember);
        copy.startDate = this.dateUtils
            .convertLocalDateToServer(groupMember.startDate);
        copy.endDate = this.dateUtils
            .convertLocalDateToServer(groupMember.endDate);
        return copy;
    }
}
