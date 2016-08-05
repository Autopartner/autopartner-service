import {Map, Stack} from 'immutable';
import React from 'react';
import {o2c} from '../utils/models';

export const host = "http://localhost:8888/";

export const emptyClient = o2c({});

export const clientFieldsMap = Map(
    {
        "firstName": {
            title: "Имя",
            description: <div>Введите имя клиента, это обязательное поле</div>
        },
        "lastName": {
            title: "Фамилия",
            description: <div>Введите фамилию клиента, это обязательное поле</div>
        },
        "companyName": {
            title: "Название Компании",
            description: <div>Это обязательное поле</div>
        },
        "address": {
            title: "Адрес",
            description: <div></div>
        },
        "phone": {
            title: "Номер телефона",
            description: <div>Это обязательное поле</div>
        },
        "email": {
            title: "E-mail",
            description: <div></div>
        },
        "discountService": {
            title: "Скидка на работы",
            description: <div></div>
        },
        "discountMaterial": {
            title: "Скидка на материалы",
            description: <div></div>
        },
        "type": {
            title: "Тип клиента",
            description: <div>Это обязательное поле</div>
        },
        "note": {
            title: "Примечание",
            description: <div></div>
        }
    }
);

export const clientSortedFieldList = Stack(['firstName', 'lastName', 'companyName', 'address', 'phone', 'email', 'discountService', 'discountMaterial', 'type', 'note']);
