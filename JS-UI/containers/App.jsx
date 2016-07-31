import React from "react";
import { Router, Route, IndexRoute} from 'react-router'

import Home from '../components/Page'
import Order from '../components/Order'
import Client from '../components/Client'
import Material from '../components/Material'
import NotFound from '../components/NotFound'

class App extends React.Component {
    render() {
        return (
            <Router history={this.props.history}>
                <Route path='/' component={Home}>
                    <IndexRoute component={Client} />
                    <Route path='/client' component={Client} />
                    <Route path='/order' component={Order} />
                    <Route path='/material' component={Material} />
                    <Route path='*' component={NotFound} />
                </Route>
            </Router>
        )
    }
}

export default App;