import { combineReducers } from 'redux';
import { routerReducer } from 'react-router-redux';
import auth from './auth';
import client from './client/clientsTable';
import carType from './car/type/carTypesTable';
import carBrand from './car/brand/carBrandTable';
import carModel from './car/model/carModelTable';
import car from './car/carsTable';
import { API } from '../rest/restAPI';

export default combineReducers({
    auth,
    client,
    carType,
    carBrand,
    carModel,
    car,
    routing: routerReducer
}, API.reducers);