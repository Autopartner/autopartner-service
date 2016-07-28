export const PUSH_GLOBAL_MESSAGE = 'PUSH_GLOBAL_MESSAGE';
export const REMOVE_GLOBAL_MESSAGE = 'REMOVE_GLOBAL_MESSAGE';

export function pushGlobalMessage(msg) {
    return {type: PUSH_GLOBAL_MESSAGE, payload: msg};
}

export function removeGlobalMessage(msg) {
    return {type: REMOVE_GLOBAL_MESSAGE, payload: msg};
}

