import React, {Component, PropTypes} from 'react';
//import FlightsTable from './FlightsTable';
import AppComponent from './AppComponent';


const defaultStyle = {
    width: '100%',
    minWidth: 607,
    position: 'absolute',
    top: 64
};

class MainSection extends AppComponent {
    render() {
        if (this.auth().isAuthenticated)
            return (
                <section className="main" style={defaultStyle}>
                    <p>hello</p>
                    <FlightsTable {...this.props}/>
                </section>);
        else
            return (
                <section className="main" style={defaultStyle}>
                </section>);
    }
}

export default MainSection;