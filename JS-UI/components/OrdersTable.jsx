import React, {PropTypes} from 'react';

import getMuiTheme from 'material-ui/styles/getMuiTheme';
import * as MyRawTheme from '../src/material_ui_raw_theme_file';
import IconButton from 'material-ui/IconButton';
import ModeEdit from 'material-ui/svg-icons/editor/mode-edit';
import ContentAdd from 'material-ui/svg-icons/content/add';
//import OrderForm from './OrderForm';
import AppComponent from './AppComponent';
import ArrowDropDown from 'material-ui/svg-icons/navigation/arrow-drop-down';
import ArrowDropUp from 'material-ui/svg-icons/navigation/arrow-drop-up';
import FlatButton from 'material-ui/FlatButton';
import Divider from 'material-ui/Divider';
import * as H from '../utils/helpers';
import {
    Table,
    TableBody,
    TableFooter,
    TableHeader,
    TableHeaderColumn,
    TableRow,
    TableRowColumn
} from 'material-ui/Table';
import {sortedFieldList, fieldDescriptionsMap, sortOrders} from '../constants/constants';

const noDataStyle = {
    color: MyRawTheme.palette.accent2Color,
    textAlign: 'center'
};

class OrdersTable extends AppComponent {

    state = {
        addButtonColor: "rgba(153, 153, 153, 0.5)"
    };

    static get childContextTypes() {
        return {muiTheme: PropTypes.object};
    }

    row =  (row, index) => {
        function colCell(str) {
            return ( <TableRowColumn style={{textAlign: 'center'}}>{str}</TableRowColumn>)
        }
        return (
            <TableRow key={row.id} selectable={false}>
                <TableHeaderColumn style={{width: 40, paddingLeft: 10, paddingRight: 10}}>
                    <IconButton tooltipPosition='bottom-right' tooltip='Edit Current Order' onTouchTap={() => this.editOrderFormActions().open(row)}>
                        <ModeEdit/>
                    </IconButton>
                </TableHeaderColumn>
                {colCell(H.date2str(row.dof))}
                {colCell(H.date2str(row.dof))}
            </TableRow>
        )
    };

    getChildContext() {
        return {muiTheme: getMuiTheme(MyRawTheme)};
    }

    iconOfSort(currentColumn) {
        /*if (this.sorter().fieldName === currentColumn)
            switch (this.sorter().order) {
                case 'asc':
                    return <ArrowDropDown/>;
                case 'desc':
                    return <ArrowDropUp/>;
                default:
                    return undefined;
            }
        else
            return undefined*/
    }

    nextSortOrder(currentColumn) {
        /*let res;
        let currentSortOrderIndex = sortOrders.indexOf(this.sorter().order);
        if (this.sorter().fieldName === currentColumn && currentSortOrderIndex + 1 < sortOrders.length)
            res = sortOrders[currentSortOrderIndex + 1];
        else
            res = sortOrders[0];
        return res;*/
    }

    forms() {
        return (<div>
            {/*<OrderForm
                title="Add Order Dialog"
                properties={this.addOrderForm()}
                actions={{
                                ...this.addOrderFormActions(),
                                rest: {
                                    push: this.rest().addOrder
                                }
                            }}/>
            <OrderForm
                title="Edit Order Dialog"
                properties={this.editOrderForm()}
                actions={{
                ...this.editOrderFormActions(),
                        rest: {
                            push: this.rest().editOrder
                    }
                }}/>*/}
        </div>)
    }

    header() {
        return (
            <TableHeader selectable={false} displaySelectAll={false} enableSelectAll={false} adjustForCheckbox={false}>
                <TableRow>
                    <TableHeaderColumn key={"colheader-edit"}
                                       style={{width: 40, paddingLeft: 10, paddingRight: 10}}/>
                    {sortedFieldList.map((t, i) => {
                            const info = fieldDescriptionsMap.get(t);
                            return (
                                <TableHeaderColumn key={"colheader-" + i} tooltip={info.longTitle}>
                                    <FlatButton
                                        icon={this.iconOfSort(t)}
                                        style={{minWidth: 0}}
                                        rippleColor={'transparent'}
                                        hoverColor={'transparent'}
                                        onTouchTap={
                                                    () => {
                                                        this.sortActions().sort(t, this.nextSortOrder(t));
                                                        this.rest().Orders()
                                                    }
                                                }>
                                        {info.shortTitle}
                                    </FlatButton>
                                </TableHeaderColumn>
                            )
                        }
                    )}
                </TableRow>
            </TableHeader>
        )
    }

    footer() {
        return (
            <div>
                <IconButton
                    onTouchTap={this.addOrderFormActions().open}
                    style={{width: 56, height: 56, minWidth: 56, mimHeight: 56, borderRadius: '50%', lineHeight: 0,
                            right: 25,
                            marginTop: -100,
                            position: 'absolute',
                            zIndex: 2,
                            backgroundColor: this.state.addButtonColor}}
                    onMouseEnter={() => this.setState({...this.state, addButtonColor: "rgba(153, 153, 153, 0.9)"})}
                    onMouseLeave={() => this.setState({...this.state, addButtonColor: "rgba(153, 153, 153, 0.5)"})}
                    tooltip={"Add New Order"}
                    tooltipStyles={{top: 31, right: 70}}
                    tooltipPosition={"top-left"}>
                    <ContentAdd color={MyRawTheme.palette.canvasColor}/>
                </IconButton>
                <div style={{float: 'right', minWidth: 607, position: 'relative', bottom: 0, right: 0}}>
                    <Divider />
                    <Pagination
                        pagination={this.pagination()}
                        actions={{
                            ...this.paginationActions(),
                            rest: this.rest()
                        }}/>
                </div>
            </div>
        )
    }

    render() {
        return (
            <div>
                {this.forms()}
                <Table
                    headerStyle={{minWidth: 1740}}
                    bodyStyle={{minWidth: 1740}}
                    className="dataTable">
                    {this.header()}
                    <TableBody displayRowCheckbox={false}>
                        {/*{this.ordersTable().orders.map(this.row)}*/}
                    </TableBody>
                </Table>
                {this.footer()}
            </div>
        )
    }
}

export default OrdersTable;