import React, {Component} from "react";
import {bindActionCreators} from 'redux';
import {connect} from 'react-redux';
import Header from '../components/Header';
import MainSection from '../components/MainSection';
import Footer from '../components/Footer';

import * as auth from '../actions/auth';
/*import * as pagination from '../actions/pagination';
import * as search from '../actions/search';
import * as addFlightForm from '../actions/addFlightForm';
import * as editFlightForm from '../actions/editFlightForm';
import * as sorter from '../actions/sorter';
import * as globalMessaging from '../actions/globalMessaging';
import * as flightsTable from '../actions/flightsTable';*/
import {API} from "../rest/restAPI";

class App extends Component {
    render() {
        return (
            <div>
                <Header {...this.props}/>
                <MainSection {...this.props}/>
                <Footer/>
            </div>
        );
    }
}

function mapStateToProps(state) {
    return state;
}

function mapDispatchToProps(dispatch) {
    return {
        actions: {
            auth: bindActionCreators(auth, dispatch)
            /*main: {
                pagination: bindActionCreators(pagination, dispatch),
                search: bindActionCreators(search, dispatch),
                addFlightForm: bindActionCreators(addFlightForm, dispatch),
                editFlightForm: bindActionCreators(editFlightForm, dispatch),
                sorter: bindActionCreators(sorter, dispatch),
                flightsTable: bindActionCreators(flightsTable, dispatch)
            },
            rest: bindActionCreators(restAPI.actions, dispatch),
            globalMessaging: bindActionCreators(globalMessaging, dispatch)*/
        }
    };
}

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(App);
