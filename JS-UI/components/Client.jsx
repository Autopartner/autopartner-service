import React from 'react';
import ClientsTable from './client/ClientsTable';

class Client extends React.Component {
    render() {
        return (
            <ClientsTable {...this.props}/>
        );
    }
}

export default Client;