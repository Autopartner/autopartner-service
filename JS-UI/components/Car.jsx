import React from 'react';
import CarsTable from './car/CarsTable';

class Car extends React.Component {
    render() {
        return (
            <CarsTable {...this.props}/>
        );
    }
}

export default Car;
