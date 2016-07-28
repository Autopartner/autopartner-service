import * as T from './models';

export function userTransformer(data, prevData, action) {
    if(data && data.payload) {
        const t = T.o2u(data.payload);
        return {
            ...data,
            payload: t
        }
    }
    return data
}

export function aircraftTransformer(data, prevData, action) {
    if(data && data.payload)
        return {
            ...data,
            payload: data.payload.map(T.o2ac)
        };
    return data
}

export function airportTransformer(data, prevData, action) {
    if(data && data.payload)
        return {
            ...data,
            payload: data.payload.map(T.o2ap)
        };
    return data
}

export function flightTransformer(data, prevData, action) {
    if(data && data.payload) {
        const l = data.payload.data.map(T.o2f);
        return {
            ...data,
            payload: {
                ...data.payload,
                data: l
            }
        }
    }
    return data
}