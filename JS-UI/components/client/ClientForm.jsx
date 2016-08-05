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
import * as V from '../../utils/validation';
import * as H from '../../utils/helpers';
import {fieldDescriptionsMap} from '../../constants/constants';

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

    /*autocomplete(fieldName, validateFields, hintText, data, toString, toLongString, rest) {
        const filtered = this.getValidations(fieldName);
        const info = fieldDescriptionsMap.get(fieldName);
        const mc = V.getMainColor(filtered);
        return (
            <AutoComplete
                ref={(ref) => this[fieldName] = ref}
                hintText={hintText}
                floatingLabelFixed={true}
                floatingLabelText={info.shortTitle}
                filter={AutoComplete.noFilter}
                dataSource={data().map((r) => H.autoCompleteItem(toLongString, r))}
                openOnFocus={true}
                searchText={toLongString(this.properties().flight[fieldName])}
                onFocus={() => rest(toString(this.properties().flight[fieldName]))}
                onUpdateInput={(s) =>  {
                    if(s === '')
                        this.actions().update(fieldName, undefined);
                    rest(s)
                }}
                onNewRequest={(choose, i) => {
                    if(choose.row)
                        this.actions().update(fieldName, choose.row);
                }}
                onBlur={((e) => {
                    this.actions().validate(validateFields);
                    rest(toString(this.properties().flight[fieldName]))
                }).bind(this)}
                errorText={V.errorText(filtered)}
                errorStyle={mc}
                floatingLabelStyle={mc}
                hintStyle={V.getSecondaryColor(filtered)}
                onMouseEnter={() => this.setInfoCard(info)}
            />
        )
    }*/

    number(fieldName, validateFields, focusField) {
        const filtered = this.getValidations(fieldName);
        const info = fieldDescriptionsMap.get(fieldName);
        const mc = V.getMainColor(filtered);
        const hintText = "0";
        return (
            <TextField
                ref={(ref) => this[fieldName] = ref}
                hintText={hintText}
                floatingLabelFixed={true}
                floatingLabelText={info.shortTitle}
                onChange={(e) => {
                    const v = e.target.value.substr(0, 8);
                    this.actions().update(fieldName, v)
                }}
                onBlur={(e) => this.actions().validate(validateFields)}
                onKeyDown={((e) => {
                    if (e.keyCode === 13)
                         this.focus(focusField)
                }).bind(this)}
                value={this.properties().flight[fieldName]}
                errorText={V.errorText(filtered)}
                errorStyle={mc}
                floatingLabelStyle={mc}
                hintStyle={V.getSecondaryColor(filtered)}
                onMouseEnter={() => this.setInfoCard(info)}
                onFocus={(e) => this.setInfoCard(info)}
            />
        )
    }

    datetime(fieldName, validateFields, focusField) {
        const filtered = this.getValidations(fieldName);
        const info = fieldDescriptionsMap.get(fieldName);
        const mc = V.getMainColor(filtered);
        const hintText = "00:00";
        return (
            <TextField
                ref={(ref) => this[fieldName] = ref}
                hintText={hintText}
                floatingLabelFixed={true}
                floatingLabelText={info.shortTitle}
                onChange={(e) => {
                    const v = e.target.value.substr(0, 5);
                    this.actions().update(fieldName, v)
                }}
                onBlur={(e) => this.actions().validate(validateFields)}
                onKeyDown={((e) => {
                    if (e.keyCode === 13)
                         this.focus(focusField)
                }).bind(this)}
                value={H.hhmm2str(this.properties().flight[fieldName])}
                errorText={V.errorText(filtered)}
                errorStyle={mc}
                floatingLabelStyle={mc}
                hintStyle={V.getSecondaryColor(filtered)}
                onMouseEnter={() => this.setInfoCard(info)}
                onFocus={(e) => this.setInfoCard(info)}
            />
        )
    }

    dateOfFlightField() {
        const fieldName = "dof";
        const filtered = this.getValidations(fieldName);
        const info = fieldDescriptionsMap.get(fieldName);
        const mc = V.getMainColor(filtered);
        const hintText = H.date2str(new Date());
        return (
            <div>
                <DatePicker
                    ref={(ref) => this[fieldName] = ref}
                    hintText={hintText}
                    floatingLabelFixed={true}
                    floatingLabelText={info.shortTitle}
                    container="inline"
                    mode="landscape"
                    value={this.properties().flight[fieldName]}
                    onChange={(n, date) => {
                        this.actions().update(fieldName, date);
                        this.actions().validate([fieldName])
                    }}
                    onDismiss={() => this.actions().validate([fieldName])}
                    errorText={V.errorText(filtered)}
                    style={{display: 'inline-block', float: 'left'}}
                    errorStyle={mc}
                    floatingLabelStyle={mc}
                    hintStyle={V.getSecondaryColor(filtered)}
                    onMouseEnter={() => this.setInfoCard(info)}
                    onFocus={(e) => this.setInfoCard(info)}
                />
                <IconButton
                    style={{display: this.properties().flight[fieldName] ? 'inline-block' : 'none', float: 'left', marginTop: '26px', marginLeft: '-40px'}}
                    onTouchTap={(e) => {
                        this.actions().update(fieldName, undefined);
                        this.actions().validate([fieldName])
                    }}>
                    <Backspace color={palette.accent3Color} hoverColor={palette.primary2Color}/>
                </IconButton>
            </div>
        )
    }

    fieldInformationCard() {
        const info = this.state.info;
        return (
            <div style={{width: '50%', float: 'right'}}>
                { info ? (
                    <Card>
                        <CardHeader
                            title={`${info.shortTitle} - ${info.longTitle}`}
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
                        {/*{this.autocomplete("aircraft", ["aircraft"], "XX-XXX", () => {
                            return this.properties().aircraftsData
                        }, H.aircraft2str, H.aircraft2str, (s) => this.actions().rest.aircrafts({}, {search: s}))}
                        {this.dateOfFlightField()}*/}
                    </div>
                );
            case 1:
                return (
                    <div>
                        {this.datetime("blockOffTime", ["blockOffTime"], "takeOffTime")}<br/>
                        {this.datetime("takeOffTime", ["takeOffTime"], "landingTime")}<br/>
                        {this.datetime("landingTime", ["landingTime"], "blockOnTime")}<br/>
                        {this.datetime("blockOnTime", ["blockOnTime"])}
                    </div>

                );
            case 2:
                return (
                    <div>
                        {/*{this.autocomplete("dep", ["dep", "arr"], "ICAO", () => {
                            return this.properties().depData
                        }, H.airport2str, H.airport2longStr, (s) => this.actions().rest.depData({search: s}))}<br/>
                        {this.autocomplete("arr", ["dep", "arr"], "ICAO", () => {
                            return this.properties().arrData
                        }, H.airport2str, H.airport2longStr, (s) => this.actions().rest.arrData({search: s}))}<br/>
                        {this.number("pax", ["pax"])}*/}
                    </div>
                );
            case 3:
                return (
                    <div>
                        {this.number("upliftedFuel", ["upliftedFuel"], "takeOffFuel")}<br/>
                        {this.number("takeOffFuel", ["takeOffFuel", "landingFuel", "totalBurnedFuel"], "landingFuel")}<br/>
                        {this.number("landingFuel", ["takeOffFuel", "landingFuel", "totalBurnedFuel"], "totalBurnedFuel")}<br/>
                        {this.number("totalBurnedFuel", ["takeOffFuel", "landingFuel", "totalBurnedFuel"])}
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
                    label="Reset"
                    secondary={true}
                    onTouchTap={(event) => {
                            event.preventDefault();
                            this.actions().reset()
                        }}/>
                <FlatButton
                    label="Submit"
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
                <div class="flightGroup">
                    <div style={styles.root}>
                        <div onMouseEnter={() => this.resetInfoCard()}>
                            <Stepper linear={false}>
                                <Step>{this.getStepButton(0, ["aircraft", "dof"], "Flight Info")}</Step>
                                <Step>{this.getStepButton(1, ["blockOffTime", "takeOffTime", "landingTime", "blockOnTime"], "Times")}</Step>
                                <Step>{this.getStepButton(2, ["dep", "arr", "pax"], "Routing")}</Step>
                                <Step>{this.getStepButton(3, ["upliftedFuel", "takeOffFuel", "landingFuel", "totalBurnedFuel"], "Fueling")}</Step>
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
                        <FlatButton label="Back" onTouchTap={this.handlePrev}/>
                        <FlatButton ref={(ref) => this.nextButton = ref} label="Next" primary={true}
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