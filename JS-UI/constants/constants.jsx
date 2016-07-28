import {Map, Stack} from 'immutable';
import {o2f} from '../utils/models';
import React from 'react';

/*export const emptyFlight = o2f({});

export const emptyFlightsFiltersMap = Map(
    {
        "aircraft": {
            fieldName: "aircraft",
            condition: "ee",
            value: undefined
        },
        "dofFrom": {
            fieldName: "dof",
            condition: "ge",
            value: undefined
        },
        "dofTo": {
            fieldName: "dof",
            condition: "le",
            value: undefined
        },
        "dep": {
            fieldName: "dep",
            condition: "ee",
            value: undefined
        },
        "arr": {
            fieldName: "arr",
            condition: "ee",
            value: undefined
        }
    }
);
*/
export const fieldDescriptionsMap = Map(
    {
        "dof": {
            shortTitle: "DOF",
            longTitle: "Day Of Flight",
            description: <div>Choose Date of Flight from Date Picker, this is mandatory field</div>
        },
        "aircraft": {
            shortTitle: "Reg No",
            longTitle: "Aircraft Registration No",
            description: <div>Type here Aircraft Registration No, this is mandatory field</div>
        },
        "dep": {
            shortTitle: "DEP",
            longTitle: "Departure airport",
            description: <div>This is mandatory field</div>
        },
        "arr": {
            shortTitle: "ARR",
            longTitle: "Arrival airport",
            description: <div>This is mandatory field</div>
        },
        "pax": {
            shortTitle: "PAX",
            longTitle: "Number of Passengers",
            description: <div>This is mandatory field</div>
        },
        "blockOffTime": {
            shortTitle: "Block Off",
            longTitle: "Block Off Time",
            description: <div>Type here Block Off Time, this is mandatory field</div>
        },
        "takeOffTime": {
            shortTitle: "Take Off",
            longTitle: "Take Off Time",
            description: <div>Type here Take Off Time, this is mandatory field</div>
        },
        "landingTime": {
            shortTitle: "Landing",
            longTitle: "Landing Time",
            description: <div>Type here Landing Time, this is mandatory field</div>

        },
        "blockOnTime": {
            shortTitle: "Block On",
            longTitle: "Block On Time",
            description: <div>Type here Block On Time, this is mandatory field</div>
        },
        "upliftedFuel": {
            shortTitle: "Uplifted",
            longTitle: "Uplifted Fuel",
            description: <div>Type here Uplifted Fuel, this is mandatory field</div>
        },
        "takeOffFuel": {
            shortTitle: "Take Off",
            longTitle: "Take Off Fuel",
            description: <div>Type here Take Off Fuel, this is mandatory field</div>
        },
        "landingFuel": {
            shortTitle: "Landing",
            longTitle: "Landing Fuel",
            description: <div>Type here Landing Fuel, this is mandatory field</div>
        },
        "totalBurnedFuel": {
            shortTitle: "Total Burned",
            longTitle: "Total Burned Fuel",
            description: <div>Type here Total Burned Fuel, this is mandatory field</div>
        }
    }
);

export const sortedFieldList = Stack(['dof', 'aircraft', 'dep', 'arr', 'pax', 'blockOffTime', 'takeOffTime', 'landingTime', 'blockOnTime', 'upliftedFuel', 'takeOffFuel', 'landingFuel', 'totalBurnedFuel']);

export const sortOrders = ['asc', 'desc', 'none'];