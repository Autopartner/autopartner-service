import * as V from './validation';
import {Record} from 'immutable';
import {clientSortedFieldList} from '../constants/constants';

const UserT = Record({
    id: -1,
    firstName: undefined,
    lastName: undefined
});

class User extends UserT {
    constructor(f) {
        const nf = {
            id: f.id ? parseInt(f.id) : -1,
            firstName: f.firstName,
            lastName: f.lastName
        };
        super(nf)
    }

    toString() {
        return `${this.firstName} ${this.lastName}`
    }

    isEqual(that) {
        return that && (that instanceof User && this.id === that.id)
    }
}

const ClientT = Record({
    id: null,
    firstName: undefined,
    lastName: undefined,
    companyName: undefined,
    address: undefined,
    phone: undefined,
    email: undefined,
    dateCreated: null,
    discountService: 0,
    discountMaterial: 0,
    type: 'PERSON',
    note: undefined,
    isActive: true
});

class Client extends ClientT {
    constructor(o) {
        console.log("in constructor");
        const no = {
            id: o.id ? parseInt(o.id) : null,
            firstName: o.firstName,
            lastName: o.lastName,
            companyName: o.companyName,
            address: o.address,
            phone: o.phone,
            email: o.email,
            dateCreated: o.dateCreated,
            discountService: o.discountService,
            discountMaterial: o.discountMaterial,
            type: o.type,
            note: o.note,
            isActive: o.isActive
        };
        super(no);
    }

    toString() {
        return `${this.id} ${this.firstName} ${this.lastName} ${this.companyName} ${this.address} ${this.phone} 
                ${this.email} ${this.dateCreated} ${this.discountService} ${this.discountMaterial} ${this.type} ${this.note} ${this.isActive}`
    }

    isEqual(that) {
        return that && (that instanceof Client && this.id === that.id)
    }

    toObject() {
        let ret = {};
        this.toMap().forEach((v, k) => {
            ret[k] = v
        });
        return ret
    }

    toJSON() {
        return JSON.stringify(this.toObject())
    }

    validate(fieldNames) {
        const rules = clientSortedFieldList
            .map((fn) => {
                return V.required(fn, this[fn])
            })
            .concat(
                [
                    V.notEqual("discountService", this.discountService, 0, "warn", "Вы уверены что этому клиенту не нужна скидка?")
                ]);

        const rl = (fieldNames && fieldNames.length > 0) ? rules
            .filter((v) => {
                return !fieldNames || fieldNames.indexOf(v.fieldName) > -1
            }) : rules;

        return rl
            .map((v) => {
                return v.validate()
            })
            .filter((vr) => {
                return vr !== undefined
            })
    }
}

const OrderT = Record({
    id: -1,
    dof: undefined
});

class Order extends OrderT {
    constructor(f) {
        const m = {
            id: f.id ? parseInt(f.id) : -1,
            dof: f.dof ? new Date(Date.parse(f.dof)) : undefined
        };
        super(m)
    }

    isEqual(that) {
        return that && (that instanceof Order && this.id === that.id)
    }

    toObject() {
        let ret = {};
        this.toMap().forEach((v, k) => {
            ret[k] = v
        });
        return ret
    }

    toJSON() {
        return JSON.stringify(this.toObject())
    }
}

export function o2u(o) {
    return o ? new User(o) : o
}

export function o2o(o) {
    return o ? new Order(o) : o
}

export function o2c(o) {
    return o ? new Client(o) : o
}