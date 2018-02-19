import { BaseEntity } from './../../shared';

export class TrainingGroup implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
    ) {
    }
}
