import React from 'react';
import CarModelsTable from './car/model/CarModelsTable';

class CarModel extends React.Component {
    render() {
        return (
            <CarModelsTable {...this.props}/>
        );
    }
}

export default CarModel;
