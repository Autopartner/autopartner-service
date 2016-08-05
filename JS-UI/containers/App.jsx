import React from "react";
import { Router, Route, IndexRoute} from 'react-router'

import Page from '../components/Page'
import Order from '../components/Order'
import Client from '../components/Client'
import Material from '../components/Material'
import NotFound from '../components/NotFound'

class App extends React.Component {
    render() {
        return (
            <Router history={this.props.history}>
                <Route path='/' component={Page}>
                    <IndexRoute component={Client} />
                    <Route path='/client' component={Client} {...this.props} handler={ (props,state,params) => <Client/> }/>
                    <Route path='/order' component={Order} {...this.props}/>
                    <Route path='/material' component={Material} {...this.props}/>
                    <Route path='*' component={NotFound} {...this.props}/>
                </Route>
            </Router>
        )
    }
}

export default App;