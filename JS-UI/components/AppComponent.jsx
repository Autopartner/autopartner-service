import React, {Component, PropTypes} from 'react';

class AppComponent extends Component {

    auth() {
        return this.props.auth;
    }

    client() {
        return this.props.client;
    }

    actions() {
        return this.props.actions;
    }

    authActions() {
        return this.actions().auth;
    }

    mainActions() {
        return this.actions().client;
    }
    
    rest() {
        return this.actions().rest;
    }

    addClientForm() {
        return this.client().addClientForm;
    }

    editClientForm() {
        return this.client().editClientForm;
    }

    addClientFormActions() {
        return this.mainActions().addClientForm;
    }

    editClientFormActions() {
        return this.mainActions().editClientForm;
    }

    clientsTable() {
        return this.client().clientsTable;
    }
}

export default AppComponent;
