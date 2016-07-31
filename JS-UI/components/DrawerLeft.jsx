import React from 'react';
import Drawer from 'material-ui/Drawer';
import MenuItem from 'material-ui/MenuItem';
import { hashHistory } from 'react-router'

export default class DrawerLeft extends React.Component {

    route(path) {
        this.props.onToggleDrawer();
        hashHistory.push(path);
        // TODO this.props.dispatch(push(path));
    }

    render() {
        return (
            <div>
                <Drawer
                    docked={false}
                    width={200}
                    open={this.props.open}
                    onRequestChange={this.props.onToggleDrawer}>
                    <MenuItem onClick={() => this.route("client")}>Клиенты</MenuItem>
                    <MenuItem onClick={() => this.route("order")}>Заказы</MenuItem>
                    <MenuItem onClick={() => this.route("material")}>Материалы</MenuItem>
                </Drawer>
            </div>
        );
    }
}