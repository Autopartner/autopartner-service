import React, {Component, PropTypes} from 'react';
import OrdersTable from './OrdersTable';
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
                    <p>hello!!!</p>
                    {/*<OrdersTable {...this.props}/>*/}
                </section>);
        else
            return (
                <section className="main" style={defaultStyle}>
                </section>);
    }
}

export default MainSection;