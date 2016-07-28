import React, {Component, PropTypes} from 'react';

class AppComponent extends Component {

    auth() {
        return this.props.auth;
    }

    main() {
        return this.props.main;
    }


    actions() {
        return this.props.actions;
    }

    authActions() {
        return this.actions().auth;
    }

    mainActions() {
        return this.actions().main;
    }
    
    rest() {
        return this.actions().rest;
    }
}

export default AppComponent;
