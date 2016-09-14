import React from 'react';
import Drawer from 'material-ui/Drawer';
import MenuItem from 'material-ui/MenuItem';
import AppBar from 'material-ui/AppBar';
import Divider from 'material-ui/Divider';
import { hashHistory } from 'react-router'
import IconButton from 'material-ui/IconButton';
import NavigationClose from 'material-ui/svg-icons/navigation/close';

export default class DrawerLeft extends React.Component {

    route(path) {
        this.props.onToggleDrawer();
        hashHistory.push(path);
    }

    render() {
        return (
            <div>
                <Drawer
                    docked={false}
                    width={200}
                    open={this.props.open}
                    onRequestChange={this.props.onToggleDrawer}>
                    <AppBar
                        iconElementLeft={<IconButton><NavigationClose /></IconButton>}
                        onClick={this.props.onToggleDrawer.bind(this)}/>
                    <MenuItem onTouchTap={() => this.route("client")}>Клиенты</MenuItem>
                    <MenuItem onTouchTap={() => this.route("order")}>Заказы</MenuItem>
                    <MenuItem onTouchTap={() => this.route("material")}>Материалы</MenuItem>
                    <Divider />
                    <MenuItem onTouchTap={() => this.route("carType")}>Типы Авто</MenuItem>
                    <MenuItem onTouchTap={() => this.route("carBrand")}>Бренды Авто</MenuItem>
                    <MenuItem onTouchTap={() => this.route("carModel")}>Модели Авто</MenuItem>
                </Drawer>
            </div>
        );
    }
}