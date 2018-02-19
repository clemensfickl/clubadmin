import { BaseEntity } from './../../shared';

export class ContributionGroupEntry implements BaseEntity {
    constructor(
        public id?: number,
        public year?: number,
        public amount?: number,
        public groupId?: number,
    ) {
    }
}
