import React from 'react';
import CarTypesTable from './car/type/CarTypesTable';

class CarType extends React.Component {
    render() {
        return (
            <CarTypesTable {...this.props}/>
        );
    }
}

export default CarType;