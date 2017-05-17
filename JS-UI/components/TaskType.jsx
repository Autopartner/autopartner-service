import React from 'react';
import TaskTypesTable from './order/task/TaskTypesTable';

class TaskType extends React.Component {
    render() {
        return (
            <TaskTypesTable {...this.props}/>
        );
    }
}

export default TaskType;