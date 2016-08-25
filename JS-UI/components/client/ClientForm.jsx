import React, {Component, PropTypes} from 'react';
import {
    FlatButton,
    Dialog,
    TextField,
    Stepper,
    StepButton,
    Step,
    RaisedButton,
    AutoComplete,
    DatePicker,
    FloatingActionButton,
    MenuItem,
    IconButton,
    Card,
    CardText,
    CardHeader
} from 'material-ui';
import FlightIcon from 'material-ui/svg-icons/maps/flight';
import ClockIcon from 'material-ui/svg-icons/action/alarm';
import DirIcon from 'material-ui/svg-icons/maps/directions';
import FuelIcon from 'material-ui/svg-icons/maps/local-gas-station';
import Backspace from 'material-ui/svg-icons/content/backspace';
import {palette} from '../../src/material_ui_raw_theme_file';
import {clientFieldsMap} from '../../constants/constants';
import * as V from '../../utils/validation';
import * as H from '../../utils/helpers';

const getStyles = () => {
    return {
        root: {
            width: '100%',
            maxWidth: 700,
            margin: 'auto',
            minWidth: 609
        },
        content: {
            margin: '0 16px'
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

    state = {
        stepIndex: 0,
        info: undefined
    };

    properties() {
        return this.props.properties;
    }

    actions() {
        return this.props.actions;
    }

    handleNext = () => {
        const {stepIndex} = this.state;
        if (stepIndex < 3) {
            this.setState({...this.state, stepIndex: stepIndex + 1});
        } else
            this.setState({...this.state, stepIndex: 0});
    };

    handlePrev = () => {
        const {stepIndex} = this.state;
        if (stepIndex > 0) {
            this.setState({...this.state, stepIndex: stepIndex - 1});
        } else
            this.setState({...this.state, stepIndex: 3});
    };

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
                onMouseEnter={() => this.setInfoCard(info)}
                onFocus={(e) => this.setInfoCard(info)}
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
                onMouseEnter={() => this.setInfoCard(info)}
                onFocus={(e) => this.setInfoCard(info)}
            />
        )
    }

    fieldInformationCard() {
        const info = this.state.info;
        return (
            <div style={{width: '50%', float: 'right'}}>
                { info ? (
                    <Card>
                        <CardHeader
                            title={info.title}
                            actAsExpander={false}
                            showExpandableButton={false}
                        />
                        <CardText expandable={false}>
                            {info.description}
                        </CardText>
                    </Card>
                ) : ''}
            </div>
        )
    }

    setInfoCard(info) {
        this.setState({
            ...this.state,
            info: info
        })
    }

    resetInfoCard() {
        this.setState({
            ...this.state,
            info: undefined
        })
    }

    getStepContent(stepIndex) {
        switch (stepIndex) {
            case 0:
                return (
                    <div>
                        {this.text("firstName", ["firstName"], "firstName")}<br/>
                        {this.text("lastName", ["lastName"], "lastName")}<br/>
                        {this.text("type", ["type"], "type")}
                        {this.text("companyName", ["companyName"], "companyName")}
                    </div>
                );
            case 1:
                return (
                    <div>
                        {this.text("address", ["address"], "address")}<br/>
                        {this.text("phone", ["phone"], "phone")}<br/>
                        {this.text("email", ["email"], "email")}
                    </div>

                );
            case 2:
                return (
                    <div>
                        {this.number("discountService", ["discountService"], "discountService")}<br/>
                        {this.number("discountMaterial", ["discountMaterial"], "discountMaterial")}
                    </div>
                );
            case 3:
                return (
                    <div>
                        {this.text("note", ["note"], "note")}<br/>
                    </div>
                );
            default:
                return 'Click a step to get started.';
        }
    }

    getStepButton(si, fields, title) {
        const {stepIndex} = this.state;
        const isActive = stepIndex === si;

        const errors = this.properties().validations.filter((v) => {
            return fields.indexOf(v.fieldName) > -1
        });

        const isFailed = errors.size !== 0;

        function generalColor() {
            return isActive ? palette.primary1Color : palette.accent3Color
        }

        function errorColor() {
            return isActive ? V.getMainColor(errors) : V.getSecondaryColor(errors)
        }

        const color = isFailed ? errorColor().color : generalColor();

        function icon() {
            switch (si) {
                case 0:
                    return <FlightIcon color={color}/>;
                case 1:
                    return <ClockIcon color={color}/>;
                case 2:
                    return <DirIcon color={color}/>;
                case 3:
                    return <FuelIcon color={color}/>;
            }
        }

        return (
            <StepButton
                onClick={() => this.setState({...this.state, stepIndex: si})}
                icon={icon()}>
                <div style={{color: color}}>{isActive ? title : ""}</div>
            </StepButton>
        )
    };

    render() {
        const actions = [
            <div onMouseEnter={() => this.resetInfoCard()}>
                <FlatButton
                    label="Сбросить"
                    secondary={true}
                    onTouchTap={(event) => {
                            event.preventDefault();
                            this.actions().reset()
                        }}/>
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

        const {stepIndex} = this.state;
        const styles = getStyles();

        return (
            <Dialog
                title={this.props.title}
                actions={actions}
                open={this.properties().isOpen}
                autoDetectWindowHeight={false}
                onRequestClose={this.actions().close}>
                <div class="clientGroup">
                    <div style={styles.root}>
                        <div onMouseEnter={() => this.resetInfoCard()}>
                            <Stepper linear={false}>
                                <Step>{this.getStepButton(0, ["firstName", "lastName", "type", "companyName"], "Инфо")}</Step>
                                <Step>{this.getStepButton(1, ["address", "phone", "email"], "Контакты")}</Step>
                                <Step>{this.getStepButton(2, ["discountService", "discountMaterial"], "Скидки")}</Step>
                                <Step>{this.getStepButton(3, ["note"], "Дополнительно")}</Step>
                            </Stepper>
                        </div>
                        <div style={styles.content}>
                            <div style={{width: '50%', float: 'left'}}>
                                {this.getStepContent(stepIndex)}
                            </div>
                            {this.fieldInformationCard()}
                        </div>
                    </div>
                </div>
                {stepIndex !== null && (
                    <div style={styles.actions} onMouseEnter={() => this.resetInfoCard()}>
                        <FlatButton label="Назад" onTouchTap={this.handlePrev}/>
                        <FlatButton ref={(ref) => this.nextButton = ref} label="Вперед" primary={true}
                                    onTouchTap={this.handleNext}/>
                    </div>
                )}
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