import { BaseEntity } from './../../shared';

export const enum Sex {
    'MALE',
    'FEMALE'
}

export class Member implements BaseEntity {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public title?: string,
        public sex?: Sex,
        public birthdate?: any,
        public email?: string,
        public phoneNumber?: string,
        public entryDate?: any,
        public terminationDate?: any,
        public exitDate?: any,
        public streetAddress?: string,
        public postalCode?: string,
        public city?: string,
        public province?: string,
        public country?: string,
        public vote?: boolean,
        public oerv?: boolean,
        public comment?: string,
    ) {
        this.vote = false;
        this.oerv = false;
    }
}
