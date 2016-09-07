import React from 'react';
import {bindActionCreators} from 'redux';
import {connect} from 'react-redux';
import Header from '../components/Header';
import Footer from '../components/Footer';
import AppComponent from './AppComponent';

import * as auth from '../actions/auth';

import * as addClientForm from '../actions/client/addClientForm';
import * as editClientForm from '../actions/client/editClientForm';
import * as clientsTable from '../actions/client/clientsTable';
import * as deleteClientDialog from '../actions/client/deleteClientDialog';


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
                clientsTable: bindActionCreators(clientsTable, dispatch),
                deleteClientDialog: bindActionCreators(deleteClientDialog, dispatch)
            },
            rest: bindActionCreators(API.actions, dispatch)
        }
    };
}

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(Page);