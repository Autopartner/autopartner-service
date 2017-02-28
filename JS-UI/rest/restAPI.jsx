import reduxApi, {transformers} from "redux-api";
import adapterFetch from "redux-api/lib/adapters/fetch";
import * as T from '../utils/transform';
import fetch from "fbjs/lib/fetch";

import {host} from '../constants/constants';
import ES6Promise from 'es6-promise';

export const headers = {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
};

export const API = reduxApi({
    jwtUpdate: {
        url: host + 'auth/refresh',
        options: {
            method: "GET"
        },
        postfetch: [
            function ({dispatch, actions, getState}) {
                if (getState().auth.isAuthenticated)
                    dispatch(function (dispatch) {
                        setTimeout(() => dispatch(actions.jwtUpdate()), localStorage.getItem('tm'))
                    })
            },
            function ({dispatch, actions}) {
                dispatch(actions.profile())
            }
        ]
    },
    profile: {
        url: host + 'api/profile',
        transformer: T.userTransformer,
        postfetch: [
            function ({dispatch, actions}) {
                dispatch(actions.clients())
            }
        ]
    },
    orders: {
        url: host + 'api/orders',
        transformer: T.orderTransformer
    },
    clients: {
        url: host + 'api/client',
        transformer: T.clientTransformer
    },
    addClient: {
        url: host + 'api/client',
        options: function (url, params, getState) {
            const client = getState().client.addClientForm.client;
            return {
                ...params,
                method: "POST",
                body: client.toJSON()
            };
        },
        postfetch: [
            function ({actions, dispatch, getState}) {
                if (!getState().client.addClientForm.isOpen) {
                    dispatch(actions.clients());
                }
            }
        ]
    },
    editClient: {
        url: host + 'api/client',
        options: function (url, params, getState) {
            const client = getState().client.editClientForm.client;
            return {
                ...params,
                method: "POST",
                body: client.toJSON()
            };
        },
        postfetch: [
            function ({actions, dispatch, getState}) {
                if (!getState().client.editClientForm.isOpen) {
                    dispatch(actions.clients());
                }
            }
        ]
    },
    deleteClient: {
        url: host + 'api/client',
        options: function (url, params, getState) {
            const client = getState().client.deleteClientDialog.client;
            client.set('active', false);
            return {
                ...params,
                method: "POST",
                body: client.toJSON()
            };
        },
        postfetch: [
            function ({actions, dispatch, getState}) {
                if (!getState().client.deleteClientDialog.isOpen) {
                    dispatch(actions.clients());
                }
            }
        ]
    },


    /////////////////////////// CAR TYPE REST ///////////////////////////

    carTypes: {
        url: host + 'api/car/type',
        transformer: T.carTypeTransformer
    },
    addCarType: {
        url: host + 'api/car/type',
        options: function (url, params, getState) {
            const carType = getState().carType.addCarTypeForm.carType;
            return {
                ...params,
                method: "POST",
                body: carType.toJSON()
            };
        },
        postfetch: [
            function ({actions, dispatch, getState}) {
                if (!getState().carType.addCarTypeForm.isOpen) {
                    dispatch(actions.carTypes());
                }
            }
        ]
    },
    editCarType: {
        url: host + 'api/car/type',
        options: function (url, params, getState) {
            const carType = getState().carType.editCarTypeForm.carType;
            return {
                ...params,
                method: "POST",
                body: carType.toJSON()
            };
        },
        postfetch: [
            function ({actions, dispatch, getState}) {
                if (!getState().carType.editCarTypeForm.isOpen) {
                    dispatch(actions.carTypes());
                }
            }
        ]
    },
    deleteCarType: {
        url: host + 'api/car/type',
        options: function (url, params, getState) {

            const carType = getState().carType.deleteCarTypeDialog.carType;
            carType.set('active', false);

            return {
                ...params,
                method: "POST",
                body: carType.toJSON()
            };
        },
        postfetch: [
            function ({actions, dispatch, getState}) {
                if (!getState().carType.deleteCarTypeDialog.isOpen) {
                    dispatch(actions.carTypes());
                }
            }
        ]
    },

    /////////////////////////// CAR BRAND REST ///////////////////////////

    carBrands: {
        url: host + 'api/car/brand',
        transformer: T.carBrandTransformer
    },
    addFormCarTypes: {
        url: host + 'api/car/type',
        transformer: T.carTypeTransformer
    },
    editFormCarTypes: {
        url: host + 'api/car/type',
        transformer: T.carTypeTransformer
    },
    addCarBrand: {
        url: host + 'api/car/brand',
        options: function (url, params, getState) {
            const carBrand = getState().carBrand.addCarBrandForm.carBrand;
            return {
                ...params,
                method: "POST",
                body: carBrand.toJSON()
            };
        },
        postfetch: [
            function ({actions, dispatch, getState}) {
                if (!getState().carBrand.addCarBrandForm.isOpen) {
                    dispatch(actions.carBrands());
                }
            }
        ]
    },
    editCarBrand: {
        url: host + 'api/car/brand',
        options: function (url, params, getState) {
            const carBrand = getState().carBrand.editCarBrandForm.carBrand;
            return {
                ...params,
                method: "POST",
                body: carBrand.toJSON()
            };
        },
        postfetch: [
            function ({actions, dispatch, getState}) {
                if (!getState().carBrand.editCarBrandForm.isOpen) {
                    dispatch(actions.carBrands());
                }
            }
        ]
    },
    deleteCarBrand: {
        url: host + 'api/car/brand',
        options: function (url, params, getState) {

            const carBrand = getState().carBrand.deleteCarBrandDialog.carBrand;
            carBrand.set('active', false);

            return {
                ...params,
                method: "POST",
                body: carBrand.toJSON()
            };
        },
        postfetch: [
            function ({actions, dispatch, getState}) {
                if (!getState().carBrand.deleteCarBrandDialog.isOpen) {
                    dispatch(actions.carBrands());
                }
            }
        ]
    },

    /////////////////////////// CAR MODEL REST ///////////////////////////

    carModels: {
        url: host + 'api/car/model',
        transformer: T.carModelTransformer
    },
    addFormCarBrands: {
        url: host + 'api/car/brand',
        transformer: T.carBrandTransformer
    },
    editFormCarBrands: {
        url: host + 'api/car/brand',
        transformer: T.carBrandTransformer
    },
    addCarModel: {
        url: host + 'api/car/model',
        options: function (url, params, getState) {
            const carModel = getState().carModel.addCarModelForm.carModel;
            return {
                ...params,
                method: "POST",
                body: carModel.toJSON()
            };
        },
        postfetch: [
            function ({actions, dispatch, getState}) {
                if (!getState().carModel.addCarModelForm.isOpen) {
                    dispatch(actions.carModels());
                }
            }
        ]
    },
    editCarModel: {
        url: host + 'api/car/model',
        options: function (url, params, getState) {
            const carModel = getState().carModel.editCarModelForm.carModel;
            return {
                ...params,
                method: "POST",
                body: carModel.toJSON()
            };
        },
        postfetch: [
            function ({actions, dispatch, getState}) {
                if (!getState().carModel.editCarModelForm.isOpen) {
                    dispatch(actions.carModels());
                }
            }
        ]
    },
    deleteCarModel: {
        url: host + 'api/car/model',
        options: function (url, params, getState) {

            const carModel = getState().carModel.deleteCarModelDialog.carModel;
            carModel.set('active', false);

            return {
                ...params,
                method: "POST",
                body: carModel.toJSON()
            };
        },
        postfetch: [
            function ({actions, dispatch, getState}) {
                if (!getState().carModel.deleteCarModelDialog.isOpen) {
                    dispatch(actions.carModels());
                }
            }
        ]
    }

}).use("fetch", adapterFetch(fetch))
    .use("options", function () {
        const h = {
            ...headers,
            'X-Auth-Token': localStorage.getItem('WWW-Token')
        };
        return {
            headers: h
        }
    });