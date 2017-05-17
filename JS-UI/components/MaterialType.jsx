import React from 'react';
import MaterialTypeTable from './order/material/MaterialTypesTable';

class MaterialType extends React.Component {
    render() {
        return (
            <MaterialTypeTable {...this.props}/>
        );
    }
}

export default MaterialType;