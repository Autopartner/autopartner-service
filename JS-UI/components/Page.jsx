import React from 'react';
import {bindActionCreators} from 'redux';
import {connect} from 'react-redux';
import Header from '../components/Header';
import Footer from '../components/Footer';
import AppComponent from './AppComponent';

import * as auth from '../actions/auth';

import * as addClientForm from '../actions/client/addClientForm';
import * as editClientForm from '../actions/client/editClientForm';
import * as deleteClientDialog from '../actions/client/deleteClientDialog';

import * as addCarTypeForm from '../actions/car/type/addCarTypeForm';
import * as editCarTypeForm from '../actions/car/type/editCarTypeForm';
import * as deleteCarTypeDialog from '../actions/car/type/deleteCarTypeDialog';

import * as addCarBrandForm from '../actions/car/brand/addCarBrandForm';
import * as editCarBrandForm from '../actions/car/brand/editCarBrandForm';
import * as deleteCarBrandDialog from '../actions/car/brand/deleteCarBrandDialog';

import * as addCarModelForm from '../actions/car/model/addCarModelForm';
import * as editCarModelForm from '../actions/car/model/editCarModelForm';
import * as deleteCarModelDialog from '../actions/car/model/deleteCarModelDialog';

import * as addCarForm from '../actions/car/addCarForm';
import * as editCarForm from '../actions/car/editCarForm';
import * as deleteCarDialog from '../actions/car/deleteCarDialog';

import {API} from "../rest/restAPI";

const defaultStyle = {
    width: '100%',
    minWidth: 607,
    position: 'absolute',
    top: 64
};

class Page extends AppComponent {

    render() {
        return (
            (this.auth().isAuthenticated) ?
            <div>
                <Header {...this.props} />
                <section className="main" style={defaultStyle}>
                    {React.cloneElement(this.props.children, {...this.props})}
                </section>
                <Footer/>
            </div>
            :
            <div>
                <Header {...this.props} />
                <section className="main" style={defaultStyle} />
            </div>);
    }
}

function mapStateToProps(state) {
    return state;
}

function mapDispatchToProps(dispatch) {
    return {
        actions: {
            auth: bindActionCreators(auth, dispatch),
            client: {
                addClientForm: bindActionCreators(addClientForm, dispatch),
                editClientForm: bindActionCreators(editClientForm, dispatch),
                deleteClientDialog: bindActionCreators(deleteClientDialog, dispatch)
            },
            carType: {
                addCarTypeForm: bindActionCreators(addCarTypeForm, dispatch),
                editCarTypeForm: bindActionCreators(editCarTypeForm, dispatch),
                deleteCarTypeDialog: bindActionCreators(deleteCarTypeDialog, dispatch)
            },
            carBrand: {
                addCarBrandForm: bindActionCreators(addCarBrandForm, dispatch),
                editCarBrandForm: bindActionCreators(editCarBrandForm, dispatch),
                deleteCarBrandDialog: bindActionCreators(deleteCarBrandDialog, dispatch)
            },
            carModel: {
                addCarModelForm: bindActionCreators(addCarModelForm, dispatch),
                editCarModelForm: bindActionCreators(editCarModelForm, dispatch),
                deleteCarModelDialog: bindActionCreators(deleteCarModelDialog, dispatch)
            },
            car: {
                addCarForm: bindActionCreators(addCarForm, dispatch),
                editCarForm: bindActionCreators(editCarForm, dispatch),
                deleteCarDialog: bindActionCreators(deleteCarDialog, dispatch)
            },
            rest: bindActionCreators(API.actions, dispatch)
        }
    };
}

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(Page);