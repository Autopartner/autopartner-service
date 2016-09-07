import React, {PropTypes} from 'react';

import getMuiTheme from 'material-ui/styles/getMuiTheme';
import * as MyRawTheme from '../../src/material_ui_raw_theme_file';
import IconButton from 'material-ui/IconButton';
import ModeEdit from 'material-ui/svg-icons/editor/mode-edit';
import DeleteForever from 'material-ui/svg-icons/action/delete-forever';
import NoteAdd from 'material-ui/svg-icons/action/note-add';
import ClientForm from './ClientForm';
import AppComponent from '../AppComponent';
import FloatingActionButton from 'material-ui/FloatingActionButton';
import ContentAdd from 'material-ui/svg-icons/content/add';
import {clientFieldList, clientFieldsMap} from '../../constants/constants';
import {
    Table,
    TableBody,
    TableHeader,
    TableHeaderColumn,
    TableRow,
    TableRowColumn
} from 'material-ui/Table';

class ClientsTable extends AppComponent {

    static get childContextTypes() {
        return {muiTheme: PropTypes.object};
    }

    row =  (row, index) => {
        function colCell(str) {
            return ( <TableRowColumn>{str}</TableRowColumn>)
        }
        return (
            <TableRow key={row.id} selectable={false}>
                <TableHeaderColumn style={{width:'50px'}}>
                    <IconButton onTouchTap={() => this.editClientFormActions().open(row)}>
                        <ModeEdit/>
                    </IconButton>
                    <IconButton onTouchTap={() => this.editClientFormActions().open(row)}>
                        <DeleteForever/>
                    </IconButton>
                </TableHeaderColumn>
                {colCell(row.firstName)}
                {colCell(row.lastName)}
                {colCell(row.companyName)}
                {colCell(row.address)}
                {colCell(row.phone)}
                {colCell(row.email)}
                {colCell(row.discountService)}
                {colCell(row.discountService)}
                {colCell(row.type)}
                {colCell(row.note)}
            </TableRow>
        )
    };

    componentDidMount() {
        // TODO
        this.rest().clients();
    }

    getChildContext() {
        return {muiTheme: getMuiTheme(MyRawTheme)};
    }

    forms() {
        return (<div>
            {<ClientForm
                title="Добавить Клиента"
                properties={this.addClientForm()}
                actions={{
                                ...this.addClientFormActions(),
                                rest: {
                                    push: this.rest().addClient
                                }
                            }}/>}
            {<ClientForm
                title="Редактировать Клиента"
                properties={this.editClientForm()}
                actions={{
                ...this.editClientFormActions(),
                        rest: {
                            push: this.rest().editClient
                    }
                }}/>}
        </div>)
    }

    header() {
        return (
            <TableHeader selectable={false} displaySelectAll={false} enableSelectAll={false} adjustForCheckbox={false}>
                <TableRow>
                    <TableHeaderColumn style={{width:'50px'}}>
                        <FloatingActionButton mini={true} onTouchTap={this.addClientFormActions().open}>
                            <ContentAdd />
                        </FloatingActionButton>
                    </TableHeaderColumn>
                    {clientFieldList.map((t, i) => {
                            const info = clientFieldsMap.get(t);
                            return (
                                <TableHeaderColumn>{info.title}</TableHeaderColumn>
                            )
                        }
                    )}
                </TableRow>
            </TableHeader>
        )
    }

    render() {
        return (
            <div>
                {this.forms()}
                <Table
                    className="dataTable"
                    fixedHeader="true"
                    style={{'min-width':'1350px'}}>
                    {this.header()}
                    <TableBody displayRowCheckbox={false}>
                        {this.clientsTable().clients.map(this.row)}
                    </TableBody>
                </Table>
            </div>
        )
    }
}

export default ClientsTable;