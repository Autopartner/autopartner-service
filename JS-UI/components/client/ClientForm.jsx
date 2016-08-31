import React, {Component, PropTypes} from 'react';
import {
    FlatButton,
    Dialog,
    TextField
} from 'material-ui';
import {clientFieldsMap} from '../../constants/constants';
import * as V from '../../utils/validation';

const getStyles = () => {
    return {
        root: {
            width: '100%',
            margin: 'auto'
        },
        content: {
            margin: '0 15px'
        },
        actions: {
            width: '100%',
            margin: '5% auto',
            textAlign: 'center',
            display: 'inline-block'
        }
    };
};

class ClientForm extends Component {

    properties() {
        return this.props.properties;
    }

    actions() {
        return this.props.actions;
    }

    getValidations = (fieldName) => {
        return this.properties().validations.filter((v) => {
            return v.fieldName === fieldName
        })
    };

    focus(fieldName) {
        const f = this[fieldName];
        f ? f.focus() : f
    }

    number(fieldName, validateFields, focusField) {
        const filtered = this.getValidations(fieldName);
        const info = clientFieldsMap.get(fieldName);
        const mc = V.getMainColor(filtered);
        const hintText = "0";
        return (
            <TextField
                ref={(ref) => this[fieldName] = ref}
                hintText={hintText}
                floatingLabelFixed={true}
                floatingLabelText={info.title}
                onChange={(e) => {
                    const v = e.target.value.substr(0, 8);
                    this.actions().update(fieldName, v)
                }}
                onBlur={(e) => this.actions().validate(validateFields)}
                onKeyDown={((e) => {
                    if (e.keyCode === 13)
                         this.focus(focusField)
                }).bind(this)}
                value={this.properties().client[fieldName]}
                errorText={V.errorText(filtered)}
                errorStyle={mc}
                floatingLabelStyle={mc}
                hintStyle={V.getSecondaryColor(filtered)}
            />
        )
    }

    text(fieldName, validateFields, focusField) {
        const filtered = this.getValidations(fieldName);
        const info = clientFieldsMap.get(fieldName);
        const mc = V.getMainColor(filtered);
        const hintText = "";
        return (
            <TextField
                ref={(ref) => this[fieldName] = ref}
                hintText={hintText}
                floatingLabelFixed={true}
                floatingLabelText={info.title}
                onChange={(e) => {
                    const v = e.target.value;
                    this.actions().update(fieldName, v)
                }}
                onBlur={(e) => this.actions().validate(validateFields)}
                onKeyDown={((e) => {
                    if (e.keyCode === 13)
                        this.focus(focusField)
                }).bind(this)}
                value={this.properties().client[fieldName]}
                errorText={V.errorText(filtered)}
                errorStyle={mc}
                floatingLabelStyle={mc}
                hintStyle={V.getSecondaryColor(filtered)}
            />
        )
    }

    render() {
        const actions = [
            <div>
                <FlatButton
                    label="Сохранить"
                    primary={true}
                    keyboardFocused={true}
                    onTouchTap={() => {
                        this.actions().validate();
                        if(!this.properties().validations.find((v) => {return v.level === 'error'}))
                            this.actions().rest.push();
                    }}
                />
            </div>
        ];

        const styles = getStyles();

        return (
            <Dialog
                title={this.props.title}
                actions={actions}
                open={this.properties().isOpen}
                autoDetectWindowHeight={false}
                onRequestClose={this.actions().close}
                autoScrollBodyContent={true}>
                <div class="clientGroup">
                    <div style={styles.root}>
                        <div style={styles.content}>
                            <div>
                                {this.text("firstName", ["firstName"], "firstName")}<br/>
                                {this.text("lastName", ["lastName"], "lastName")}<br/>
                                {this.text("type", ["type"], "type")}<br/>
                                {this.text("companyName", ["companyName"], "companyName")}<br/>
                                {this.text("address", ["address"], "address")}<br/>
                                {this.text("phone", ["phone"], "phone")}<br/>
                                {this.text("email", ["email"], "email")}<br/>
                                {this.number("discountService", ["discountService"], "discountService")}<br/>
                                {this.number("discountMaterial", ["discountMaterial"], "discountMaterial")}<br/>
                                {this.text("note", ["note"], "note")}
                            </div>
                        </div>
                    </div>
                </div>
            </Dialog>
        );
    }
}

ClientForm.propTypes = {
    title: PropTypes.string.isRequired,
    properties: PropTypes.object.isRequired,
    actions: PropTypes.object.isRequired
};

export default ClientForm;