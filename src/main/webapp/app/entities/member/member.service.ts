import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Member } from './member.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Member>;

@Injectable()
export class MemberService {

    private resourceUrl =  SERVER_API_URL + 'api/members';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(member: Member): Observable<EntityResponseType> {
        const copy = this.convert(member);
        return this.http.post<Member>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(member: Member): Observable<EntityResponseType> {
        const copy = this.convert(member);
        return this.http.put<Member>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Member>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Member[]>> {
        const options = createRequestOption(req);
        return this.http.get<Member[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Member[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Member = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Member[]>): HttpResponse<Member[]> {
        const jsonResponse: Member[] = res.body;
        const body: Member[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Member.
     */
    private convertItemFromServer(member: Member): Member {
        const copy: Member = Object.assign({}, member);
        copy.birthdate = this.dateUtils
            .convertLocalDateFromServer(member.birthdate);
        copy.entryDate = this.dateUtils
            .convertLocalDateFromServer(member.entryDate);
        copy.terminationDate = this.dateUtils
            .convertLocalDateFromServer(member.terminationDate);
        copy.exitDate = this.dateUtils
            .convertLocalDateFromServer(member.exitDate);
        return copy;
    }

    /**
     * Convert a Member to a JSON which can be sent to the server.
     */
    private convert(member: Member): Member {
        const copy: Member = Object.assign({}, member);
        copy.birthdate = this.dateUtils
            .convertLocalDateToServer(member.birthdate);
        copy.entryDate = this.dateUtils
            .convertLocalDateToServer(member.entryDate);
        copy.terminationDate = this.dateUtils
            .convertLocalDateToServer(member.terminationDate);
        copy.exitDate = this.dateUtils
            .convertLocalDateToServer(member.exitDate);
        return copy;
    }
}
