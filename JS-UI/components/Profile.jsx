import React, {Component, PropTypes} from 'react';

import {Popover, FlatButton, List, Avatar, Divider, ListItem, TextField} from 'material-ui';

class ProfilePopover extends Component {

    componentWillMount() {
        this.props.actions.rest.jwtUpdate()
    }

    render() {
        const u = this.props.properties.loggedUser;
        const userName = u ? u.toString() : '';
        return (
            <Popover
                open={this.props.properties.isOpen}
                anchorOrigin={{horizontal: 'right', vertical: 'bottom'}}
                targetOrigin={{horizontal: 'left', vertical: 'top'}}
                onRequestClose={this.props.actions.closeProfilePopover}
            >
                <List>
                    <ListItem
                        disabled={true}
                        leftAvatar={<Avatar>A</Avatar>}
                        style={{textAlign: 'center'}}
                    >
                        {userName}
                    </ListItem>
                    <Divider/>
                    <br/>
                    <FlatButton
                        label="Logout"
                        primary={true}
                        keyboardFocused={true}
                        onTouchTap={this.props.actions.logoutAction}
                    />
                </List>
            </Popover>
        )
    }
}

ProfilePopover.propTypes = {
    properties: PropTypes.object.isRequired,
    actions: PropTypes.object.isRequired
};

export default ProfilePopover;
