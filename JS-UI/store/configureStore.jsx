import {createStore, applyMiddleware} from 'redux';
import reducers from '../reducers/index';
import thunk from 'redux-thunk'
import {simpleLogger} from '../middleware/logging';
import { routerMiddleware } from 'react-router-redux'

export default function configureStore(history, initialState) {
    const store = createStore(
        reducers,
        initialState,
        applyMiddleware(simpleLogger, thunk, routerMiddleware(history))
    );

    if (module.hot) {
        // Enable Webpack hot module replacement for reducers
        module.hot.accept('../reducers', () => {
            const nextReducer = require('../reducers');
            store.replaceReducer(nextReducer);
        });
    }

    return store;
}