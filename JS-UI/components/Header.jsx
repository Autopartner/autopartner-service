import React, {PropTypes} from 'react';

import LoginDialog from './LoginDialog';
import ProfilePopover from './Profile';
import AppBar from 'material-ui/AppBar';
import AppComponent from './AppComponent';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import * as MyRawTheme from '../src/material_ui_raw_theme_file';

class Header extends AppComponent {

    static get childContextTypes() {
        return {muiTheme: PropTypes.object.isRequired};
    }

    getChildContext() {
        return {muiTheme: getMuiTheme(MyRawTheme)};
    }

    profileDialog() {
        return (
            <ProfilePopover key="ProfilePopover"
                            properties={this.auth().profileDialog}
                            actions={{
                                ...this.authActions(),
                                rest: this.rest()
                              }}/>)
    }

    loginDialog() {
        return (
            <LoginDialog key="LoginDialog"
                         isFetching={this.auth().isFetching}
                         properties={this.auth().loginDialog}
                         actions={this.authActions()}/>)
    }


    render() {
        const u = this.auth().profileDialog.loggedUser;
        return (this.auth().isAuthenticated) ?
            <header className="header" style={{minWidth: 607, width: '100%', position: 'static', top: 0}}>
                {this.profileDialog()}
                <AppBar title="Autopartner" onLeftIconButtonTouchTap={this.authActions().openProfilePopover}>
                </AppBar>
            </header> :
            <header className="header">
                {this.loginDialog()}
                <AppBar title="Autopartner" onLeftIconButtonTouchTap={this.authActions().openLoginDialog} />
            </header>
    }
}

export default Header;
