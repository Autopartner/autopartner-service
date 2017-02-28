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

    carModel() {
        return this.props.carModel;
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

    carModelActions() {
        return this.actions().carModel;
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

    ////////////////////////// Car Model

    addCarModelForm() {
        return this.carModel().addCarModelForm;
    }

    editCarModelForm() {
        return this.carModel().editCarModelForm;
    }

    deleteCarModelDialog() {
        return this.carModel().deleteCarModelDialog;
    }

    addCarModelFormActions() {
        return this.carModelActions().addCarModelForm;
    }

    editCarModelFormActions() {
        return this.carModelActions().editCarModelForm;
    }

    deleteCarModelDialogActions() {
        return this.carModelActions().deleteCarModelDialog;
    }

    carModelsTable() {
        return this.carModel().carModelsTable;
    }
}

export default AppComponent;
