import { combineReducers } from 'redux';
import addClientForm from './addClientForm';
import editClientForm from './editClientForm';
import {API} from "../rest/restAPI";
import * as auth from '../actions/auth';
import * as A from '../actions/client/clientsTable';

const defaultClientsTableState = {
    tableHeight: 0,
    clients: []
};

function clientsTable(state = defaultClientsTableState, action) {
    switch (action.type) {
        case auth.LOGOUT_SUCCESS:
            return {
                ...state,
                clients: []
            };
        case API.events.clients.actionSuccess:
            return {
                ...state,
                clients: action.data.payload.data
            };
        case A.CHANGE_HEIGHT_CLIENTS_TABLE:
            const h = window.innerHeight - 212;
            return {
                ...state,
                tableHeight: h
            };
        default:
            return state;
    }
}

const main = combineReducers({
    addClientForm,
    editClientForm,
    clientsTable
});

export default main;