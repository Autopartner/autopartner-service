import React from 'react';
import CarBrandsTable from './car/brand/CarBrandsTable';

class CarBrand extends React.Component {
    render() {
        return (
            <CarBrandsTable {...this.props}/>
        );
    }
}

export default CarBrand;
