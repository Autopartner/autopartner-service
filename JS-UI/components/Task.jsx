import React from 'react';
import TasksTable from './order/task/TasksTable';

class Task extends React.Component {
    render() {
        return (
            <TasksTable {...this.props}/>
        );
    }
}

export default Task;
