import { combineReducers } from 'redux';
import { routerReducer } from 'react-router-redux';
import auth from './auth';
import client from './clientsTable';

const rootReducer = combineReducers({
    auth,
    client,
    routing: routerReducer
});

export default rootReducer;
