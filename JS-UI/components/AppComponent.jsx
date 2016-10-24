import React, {Component, PropTypes} from 'react';

class AppComponent extends Component {

    auth() {
        return this.props.auth;
    }

    client() {
        return this.props.client;
    }

    carType() {
        return this.props.carType;
    }

    carBrand() {
        return this.props.carBrand;
    }

    actions() {
        return this.props.actions;
    }

    authActions() {
        return this.actions().auth;
    }

    clientActions() {
        return this.actions().client;
    }

    carTypeActions() {
        return this.actions().carType;
    }

    carBrandActions() {
        return this.actions().carBrand;
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

    deleteClientDialog() {
        return this.client().deleteClientDialog;
    }

    addClientFormActions() {
        return this.clientActions().addClientForm;
    }

    editClientFormActions() {
        return this.clientActions().editClientForm;
    }

    deleteClientDialogActions() {
        return this.clientActions().deleteClientDialog;
    }

    clientsTable() {
        return this.client().clientsTable;
    }

    ////////////////////////// Car Type

    addCarTypeForm() {
        return this.carType().addCarTypeForm;
    }

    editCarTypeForm() {
        return this.carType().editCarTypeForm;
    }

    deleteCarTypeDialog() {
        return this.carType().deleteCarTypeDialog;
    }

    addCarTypeFormActions() {
        return this.carTypeActions().addCarTypeForm;
    }

    editCarTypeFormActions() {
        return this.carTypeActions().editCarTypeForm;
    }

    deleteCarTypeDialogActions() {
        return this.carTypeActions().deleteCarTypeDialog;
    }

    carTypesTable() {
        return this.carType().carTypesTable;
    }

    ////////////////////////// Car Brand

    addCarBrandForm() {
        return this.carBrand().addCarBrandForm;
    }

    editCarBrandForm() {
        return this.carBrand().editCarBrandForm;
    }

    deleteCarBrandDialog() {
        return this.carBrand().deleteCarBrandDialog;
    }

    addCarBrandFormActions() {
        return this.carBrandActions().addCarBrandForm;
    }

    editCarBrandFormActions() {
        return this.carBrandActions().editCarBrandForm;
    }

    deleteCarBrandDialogActions() {
        return this.carBrandActions().deleteCarBrandDialog;
    }

    carBrandsTable() {
        return this.carBrand().carBrandsTable;
    }
}

export default AppComponent;
