import React from 'react';
import {bindActionCreators} from 'redux';
import {connect} from 'react-redux';
import Header from '../components/Header';
import Footer from '../components/Footer';
import AppComponent from './AppComponent';

import * as auth from '../actions/auth';
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
                    {this.props.children}
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
            rest: bindActionCreators(API.actions, dispatch)
        }
    };
}

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(Page);