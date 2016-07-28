import reduxApi, {transformers} from "redux-api";
import adapterFetch from "redux-api/lib/adapters/fetch";
import * as T from '../utils/transform';
import {normFiltersList} from '../utils/helpers';
import fetch from "fbjs/lib/fetch";
import ES6Promise from 'es6-promise';

export const headers = {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
};

export const API = reduxApi({
    jwtUpdate: {
        url: '/api/auth',
        options: {
            method: "POST"
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
        url: '/api/profile',
        transformer: T.userTransformer,
        postfetch: [
            function ({dispatch, actions}) {
                dispatch(actions.flights())
            }
        ]
    }
}).use("fetch", adapterFetch(fetch))
    .use("options", function () {
        const h = {
            ...headers,
            'Authorization': localStorage.getItem('WWW-Token')
        };
        return {
            headers: h
        }
    });