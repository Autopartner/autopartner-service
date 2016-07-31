import React, {PropTypes} from 'react';

import LoginDialog from './LoginDialog';
import AppBar from 'material-ui/AppBar';
import AppComponent from './AppComponent';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import * as MyRawTheme from '../src/material_ui_raw_theme_file';
import DrawerLeft from './DrawerLeft';

class Header extends AppComponent {

    static get childContextTypes() {
        return {muiTheme: PropTypes.object.isRequired};
    }

    getChildContext() {
        return {muiTheme: getMuiTheme(MyRawTheme)};
    }

    constructor() {
        super();
        this.state = {
            drawerOpen: false
        }
    }


    toggleDrawer() {
        this.setState({
            drawerOpen: !this.state.drawerOpen
        })
    }

    loginDialog() {
        return (
            <LoginDialog key="LoginDialog"
                         isFetching={this.auth().isFetching}
                         properties={this.auth().loginDialog}
                         actions={this.authActions()}/>)
    }

    render() {
        return (
            (this.auth().isAuthenticated) ?
                <div>
                    <DrawerLeft open={this.state.drawerOpen} onToggleDrawer={this.toggleDrawer.bind(this)} />
                    <header className="header" style={{minWidth: 607, width: '100%', position: 'static', top: 0}}>
                        <AppBar title="Автопартнер" onClick={this.toggleDrawer.bind(this)} />
                    </header>
                </div>
             :
                <header className="header">
                    {this.loginDialog()}
                </header>
        );
    }
}

export default Header;