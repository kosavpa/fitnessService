import base64 from 'base-64'
import React from 'react'


export function getUserName() {
    return JSON.parse(base64.decode(getCookie('authToken').split('.')[1])).sub;
}

export function getCookie(name) {
    let matches = document.cookie.match(
        new RegExp("(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"));

    return matches ? decodeURIComponent(matches[1]) : undefined;
}

export function createForDrawMessage(message) {
    if (message.sender === getUserName()) {
        return (
            <div className='from-message'>
                <p>{`${message.sender} ${(message.date)}:`}</p>
                <p>{`${message.text}`}</p>
            </div>);
    } else {
        return (
            <div className='to-message'>
                <p>{`${message.sender} (${message.date}):`}</p>
                <p>{`${message.text}`}</p>
            </div>);
    }
}

export function createForSendMessage() {
    const senderMessage = document.getElementById('text-message').value;

    if (senderMessage) {
        document.getElementById('text-message').value = ''

        return new Message(getUserName(), senderMessage, new Date());
    }

    return undefined;
}

class Message {
    sender;
    text;
    date;

    constructor(sender, text, date) {
        this.sender = sender;
        this.text = text;
        this.date = date;
    }
}