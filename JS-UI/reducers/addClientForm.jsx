import * as A from '../actions/client/addClientForm';
import * as auth from '../actions/auth';
import {emptyClient} from '../constants/constants';
import {Stack} from 'immutable';

const defaultAddClientFormState = {
    isOpen: false,
    flight: emptyClient,
    validations: Stack()
};

export default function addClientForm(state = defaultAddClientFormState, action) {
    switch (action.type) {
        case A.OPEN_ADD_CLIENT_FORM:
            return {
                ...state,
                isOpen: true
            };
        case A.CLOSE_ADD_CLIENT_FORM:
            return defaultAddClientFormState;
        case A.RESET_ADD_CLIENT_FORM:
            return {
                ...defaultAddClientFormState,
                isOpen: true
            };
        case A.UPDATE_ADD_CLIENT:
            return {
                ...state,
                flight: state.flight.set(action.payload.fieldName, action.payload.value)
            };
        case A.VALIDATIONS_ADD_CLIENT:
            const c = action.payload.fieldNames;
            const cl = state.client.normalize();
            const v = c && c.length > 0 ? state.validations.filter((v) => {
                return c.indexOf(v.fieldName) === -1
            }).concat(cl.validate(action.payload.fieldNames)) : cl.validate(action.payload.fieldNames);

            return {
                ...state,
                client: cl,
                validations: v
            };
        case auth.LOGOUT_SUCCESS:
            return defaultAddClientFormState;
        default:
            return state;
    }
}