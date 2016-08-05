import reduxApi, {transformers} from "redux-api";
import adapterFetch from "redux-api/lib/adapters/fetch";
import * as T from '../utils/transform';
import fetch from "fbjs/lib/fetch";
import {host} from '../constants/constants';

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
                dispatch(actions.orders())
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
        url: '/api/client',
        options: function (url, params, getState) {
            const client = getState().main.addClientForm.client.normalize();
            return {
                ...params,
                method: "POST",
                body: client.toJSON()
            };
        },
        postfetch: [
            function ({actions, dispatch, getState}) {
                if (!getState().main.addClientForm.isOpen) {
                    dispatch(actions.clients());
                }
            }
        ]
    },
    editClient: {
        url: '/api/client',
        options: function (url, params, getState) {
            const client = getState().main.editClientForm.client.normalize();
            return {
                ...params,
                method: "POST",
                body: client.toJSON()
            };
        },
        postfetch: [
            function ({actions, dispatch, getState}) {
                if (!getState().main.editClientForm.isOpen) {
                    dispatch(actions.clients());
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