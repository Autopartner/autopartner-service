import React, {Component} from "react";
import {bindActionCreators} from 'redux';
import {connect} from 'react-redux';
import Header from '../components/Header';
import MainSection from '../components/MainSection';
import Footer from '../components/Footer';

import * as auth from '../actions/auth';
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
            auth: bindActionCreators(auth, dispatch),
            rest: bindActionCreators(API.actions, dispatch)
        }
    };
}

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(App);
