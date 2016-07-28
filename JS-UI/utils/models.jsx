import * as V from './validation';
import {Record} from 'immutable';
import {sortedFieldList} from '../constants/constants';

const CompanyT = Record({
    id: -1,
    name: undefined
});

class Company extends CompanyT {
    constructor(f) {
        const nf = {
            id: f.id ? parseInt(f.id) : -1,
            name: f.name
        };
        super(nf)
    }

    toString() {
        return this.name
    }

    isEqual(that) {
        return that && (that instanceof Company && this.id === that.id)
    }
}

const UserT = Record({
    id: -1,
    firstName: undefined,
    lastName: undefined,
    company: undefined
});

class User extends UserT {
    constructor(f) {
        const nf = {
            id: f.id ? parseInt(f.id) : -1,
            firstName: f.firstName,
            lastName: f.lastName,
            company: o2c(f.company)
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

const AircraftT = Record({
    id: -1,
    tail: undefined
});

class Aircraft extends AircraftT {
    constructor(f) {
        const nf = {
            id: f.id ? parseInt(f.id) : -1,
            tail: f.tail
        };
        super(nf)
    }

    toString() {
        return this.tail
    }

    isEqual(that) {
        return that && (that instanceof Aircraft && this.id === that.id)
    }
}

const AirportT = Record({
    id: -1,
    name: undefined,
    icao: undefined
});

class Airport extends AirportT {
    constructor(f) {
        const nf = {
            id: f.id ? parseInt(f.id) : -1,
            icao: f.icao,
            name: f.name
        };
        super(nf)
    }

    toString() {
        return this.icao
    }

    toLongString() {
        return `${this.icao} (${this.name})`
    }

    isEqual(that) {
        return that && (that instanceof Airport && this.id === that.id)
    }
}

const FlightT = Record({
    id: -1,
    aircraft: undefined,
    dof: undefined,
    blockOffTime: undefined,
    takeOffTime: undefined,
    landingTime: undefined,
    blockOnTime: undefined,
    dep: undefined,
    arr: undefined,
    pax: undefined,
    upliftedFuel: undefined,
    takeOffFuel: undefined,
    landingFuel: undefined,
    totalBurnedFuel: undefined
});

class Flight extends FlightT {
    constructor(f) {
        const m = {
            id: f.id ? parseInt(f.id) : -1,
            aircraft: o2ac(f.aircraft),
            dep: o2ap(f.dep),
            arr: o2ap(f.arr),
            dof: f.dof ? new Date(Date.parse(f.dof)) : undefined
        };

        let a = {};

        ['pax', 'upliftedFuel', 'takeOffFuel', 'landingFuel', 'totalBurnedFuel'].map((fn) => {
            a[fn] = /[0-9]/.test(f[fn]) ? parseInt(f[fn]) : undefined
        });

        ['blockOffTime', 'takeOffTime', 'landingTime', 'blockOnTime'].forEach((fn) => {
            const v = f[fn];
            if (v) {
                if (/^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/.test(v)) {
                    const [hh, mm] = v.split(':');
                    let ms = f.dof ? f.dof : new Date();
                    ms.setHours(parseInt(hh), parseInt(mm));
                    a[fn] = new Date(ms)
                } else if (v instanceof Date)
                    a[fn] = v;
                else {
                    const ms = /^\d{4}([./-])\d{2}\1\d{2}/.test(v) ? Date.parse(v) : NaN;
                    a[fn] = isNaN(ms) ? undefined : new Date(ms)
                }
            } else
                a[fn] = undefined
        });

        super({
            ...m,
            ...a
        })
    }

    isEqual(that) {
        return that && (that instanceof Flight && this.id === that.id)
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

    normalize() {
        let ret = o2f(this.toObject()).toObject();

        function normalizeTime(pickedTime, dateToCompare) {
            function correct(pickedTime) {
                let resultTime = dateToCompare ? new Date(dateToCompare.getTime()) : new Date();
                resultTime.setHours(pickedTime.getHours());
                resultTime.setMinutes(pickedTime.getMinutes());
                if (dateToCompare && resultTime.getTime() < dateToCompare.getTime()) {
                    resultTime = new Date(resultTime.getTime() + 86400000); //plus one day
                }
                return resultTime
            }

            return pickedTime ? correct(pickedTime) : pickedTime;
        }

        ret.blockOffTime = normalizeTime(ret.blockOffTime, ret.dof);
        ret.takeOffTime = normalizeTime(ret.takeOffTime, ret.blockOffTime);
        ret.landingTime = normalizeTime(ret.landingTime, ret.takeOffTime);
        ret.blockOnTime = normalizeTime(ret.blockOnTime, ret.landingTime);

        return o2f(ret);
    }

    validate(fieldNames) {
        const rules = sortedFieldList
            .map((fn) => {
                return V.required(fn, this[fn])
            })
            .concat(
                [
                    V.notEqual("arr", this.arr, this.dep, "warn", "Are you sure that arrival and departure airports should be equal?"),
                    V.greaterOrEqual("takeOffFuel", this.takeOffFuel, this.upliftedFuel, "error", "Should be greater or equal than Uplifted Fuel"),
                    V.less("landingFuel", this.landingFuel, this.takeOffFuel, "error", "Should be less than Take Off Fuel"),
                    V.equal("totalBurnedFuel", this.totalBurnedFuel, this.takeOffFuel - this.landingFuel, "info", "Does not equal to (Take Off Fuel - Landing Fuel)"),
                    V.greater("takeOffTime", this.takeOffTime, this.blockOffTime, "error", "Should be greater than Block Off"),
                    V.greater("landingTime", this.landingTime, this.takeOffTime, "error", "Should be greater than Take Off"),
                    V.greater("blockOnTime", this.blockOnTime, this.landingTime, "error", "Should be greater than Landing")
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

function o2c(o) {
    return o ? new Company(o) : o
}

export function o2u(o) {
    return o ? new User(o) : o
}

export function o2ac(o) {
    return o ? new Aircraft(o) : o
}

export function o2ap(o) {
    return o ? new Airport(o) : o
}

export function o2f(o) {
    return o ? new Flight(o) : o
}