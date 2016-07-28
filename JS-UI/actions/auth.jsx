import * as GM from '../actions/globalMessaging';
import {headers} from '../rest/restAPI';
import * as V from '../utils/validation';

export const OPEN_LOGIN_DIALOG = 'OPEN_LOGIN_DIALOG';
export const UPDATE_LOGIN_DIALOG = 'UPDATE_LOGIN_DIALOG';
export const CLOSE_LOGIN_DIALOG = 'CLOSE_LOGIN_DIALOG';
//export const OPEN_PROFILE_POPOVER = 'OPEN_PROFILE_POPOVER';
//export const CLOSE_PROFILE_POPOVER = 'CLOSE_PROFILE_POPOVER';

export const LOGIN_REQUEST = 'LOGIN_REQUEST';
export const LOGIN_SUCCESS = 'LOGIN_SUCCESS';
export const LOGIN_FAILURE = 'LOGIN_FAILURE';

export const LOGOUT_REQUEST = 'LOGOUT_REQUEST';
export const LOGOUT_SUCCESS = 'LOGOUT_SUCCESS';

// Authorization actions for UI
export function openLoginDialog() {
    return {type: OPEN_LOGIN_DIALOG};
}

export function updateLoginDialog(fieldName, value) {
    return {type: UPDATE_LOGIN_DIALOG, payload: {fieldName, value}};
}

export function closeLoginDialog() {
    return {type: CLOSE_LOGIN_DIALOG};
}

export function openProfilePopover() {
    return {type: OPEN_PROFILE_POPOVER};
}

export function closeProfilePopover() {
    return {type: CLOSE_PROFILE_POPOVER};
}

// Authorization actions - main logic

// Login
function requestLogin() {
    return {
        type: LOGIN_REQUEST,
        payload: {
            isFetching: true,
            isAuthenticated: false
        }
    };
}

function successLogin() {
    return {
        type: LOGIN_SUCCESS,
        payload: {
            isFetching: false,
            isAuthenticated: true
        }
    }
}

function failedLogin(validations) {
    return {
        type: LOGIN_FAILURE,
        payload: {
            validations: validations,
            isFetching: false,
            isAuthenticated: false
        }
    }
}

// Logout
function requestLogout() {
    return {
        type: LOGOUT_REQUEST,
        payload: {
            isFetching: true,
            isAuthenticated: true
        }
    }
}

function receiveLogout() {
    return {
        type: LOGOUT_SUCCESS,
        payload: {
            isFetching: false,
            isAuthenticated: false
        }
    }
}

// Calls the API to get a token and dispatches actions along the way
export function loginAction() {
    return (dispatch, getState) => {
        const config = {
            method: 'POST',
            headers: headers,
            body: JSON.stringify({
                    ...getState().auth.loginDialog.credentials,
                iss: "nreg"
    })
};

// We dispatch requestLogin to kickoff the call to the API
dispatch(requestLogin());

return fetch(`/api/auth/jwt`, config)
    .then(response =>
        response.json().then(msg => ({ msg, response }))
).then(({ msg, response }) =>  {
        if (response.ok) {
            if (msg.token) {
                // If login was successful, set the token in local storage
                const tm = msg.timeout ? msg.timeout : 300000;

                localStorage.setItem('WWW-Token', msg.token);
                localStorage.setItem('tm', tm - 5000);

                dispatch(successLogin())
            } else {
                dispatch(failedLogin([V.error('error', msg.errorMessage)]));
            }
        } else {
            // If there was a problem, we want to
            // dispatch the error condition
            dispatch(GM.pushGlobalMessage({level: 'error', msg: "Unexpected error!"}));
            dispatch(failedLogin())
        }
    }).catch(err => {
        dispatch(GM.pushGlobalMessage({level: 'error', msg: "Application error: " + err}));
        dispatch(failedLogin())
    })
}
}

// Logs the user out
export function logoutAction() {
    return dispatch => {
        dispatch(requestLogout());
        localStorage.clear();
        dispatch(receiveLogout())
    }
}
