import React from 'react';
import OrdersTable from './order/OrdersTable';

class Order extends React.Component {
    render() {
        return (
            <OrdersTable {...this.props}/>
        );
    }
}

export default Order;
