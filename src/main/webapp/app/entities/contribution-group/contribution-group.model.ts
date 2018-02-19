import { BaseEntity } from './../../shared';

export class ContributionGroup implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
    ) {
    }
}
