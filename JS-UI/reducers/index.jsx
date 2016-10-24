import { combineReducers } from 'redux';
import { routerReducer } from 'react-router-redux';
import auth from './auth';
import client from './client/clientsTable';
import carType from './car/type/carTypesTable';
import carBrand from './car/brand/carBrandTable';
import { API } from '../rest/restAPI';

export default combineReducers({
    auth,
    client,
    carType,
    carBrand,
    routing: routerReducer
}, API.reducers);