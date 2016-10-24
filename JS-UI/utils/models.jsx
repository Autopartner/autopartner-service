import * as V from './validation';
import {Record} from 'immutable';
import {clientRequiredFieldList, carBrandRequiredFieldList, carTypeRequiredFieldList} from '../constants/constants';

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
    discountService: 0,
    discountMaterial: 0,
    type: "PERSON",
    note: undefined,
    active: true
});

class Client extends ClientT {
    constructor(o) {
        const no = {
            id: o.id ? parseInt(o.id) : null,
            firstName: o.firstName,
            lastName: o.lastName,
            companyName: o.companyName,
            address: o.address,
            phone: o.phone,
            email: o.email,
            discountService: o.discountService,
            discountMaterial: o.discountMaterial,
            type: o.type ? o.type : "PERSON",
            note: o.note,
            active: o.active ? o.active : true
        };
        super(no);
    }

    toString() {
        return `${this.id} ${this.firstName} ${this.lastName} ${this.companyName} ${this.address} ${this.phone} 
                ${this.email} ${this.discountService} ${this.discountMaterial} ${this.type} ${this.note} ${this.active}`
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
        const rules = clientRequiredFieldList
            .map((fn) => {
                return V.required(fn, this[fn])
            });

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

const CarTypeT = Record({
    id: null,
    name: undefined,
    active: true
});

class CarType extends CarTypeT {
    constructor(o) {
        const no = {
            id: o.id ? parseInt(o.id) : null,
            name: o.name,
            active: o.active ? o.active : true
        };
        super(no);
    }

    toString() {
        return `${this.id} ${this.name} ${this.active}`
    }

    isEqual(that) {
        return that && (that instanceof CarType && this.id === that.id)
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
        const rules = carTypeRequiredFieldList
            .map((fn) => {
                return V.required(fn, this[fn])
            });

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

const CarBrandT = Record({
    id: null,
    name: undefined,
    active: true
});

class CarBrand extends CarBrandT {
    constructor(o) {
        const no = {
            id: o.id ? parseInt(o.id) : null,
            name: o.name,
            active: o.active ? o.active : true
        };
        super(no);
    }

    toString() {
        return `${this.id} ${this.name} ${this.active}`
    }

    isEqual(that) {
        return that && (that instanceof CarBrand && this.id === that.id)
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
        const rules = carBrandRequiredFieldList
            .map((fn) => {
                return V.required(fn, this[fn])
            });

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

export function o2ct(o) {
    return o ? new CarType(o) : o
}

export function o2cb(o) {
    return o ? new CarBrand(o) : o
}
