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

export function orderTransformer(data, prevData, action) {
    if(data && data.payload)
        return {
            ...data,
            payload: data.payload.map(T.o2o)
        };
    return data
}

export function clientTransformer(data, prevData, action) {
    // TODO
    return data;
}

export function carTypeTransformer(data, prevData, action) {
    // TODO
    return data;
}

export function carBrandTransformer(data, prevData, action) {
    // TODO
    return data;
}

export function carModelTransformer(data, prevData, action) {
    return data;
}

export function carTransformer(data, prevData, action) {
    return data;
}

export function taskTypeTransformer(data, prevData, action) {
    return data;
}