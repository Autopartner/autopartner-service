import { combineReducers } from 'redux';
import { routerReducer } from 'react-router-redux';
import auth from './auth';
import client from './clientsTable';
import { API } from '../rest/restAPI';

export default combineReducers({
    auth,
    client,
    routing: routerReducer
}, API.reducers);