import React from 'react';
import MaterialsTable from './order/material/MaterialsTable';

class Material extends React.Component {
    render() {
        return (
            <MaterialsTable {...this.props}/>
        );
    }
}

export default Material;