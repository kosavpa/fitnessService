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
    const date = formatDate(message.date);

    if (message.sender === getUserName()) {
        return (
            <div className='message-wrapper' key={message.date}>
                <br />
                <div className='from-message'>
                    <p className='from-header'>{`${message.sender} (${date}):`}</p>
                    <p className='from-text'>{`${message.text}`}</p>
                </div>
            </div >);
    } else {
        return (
            <div className='message-wrapper' key={message.date}>
                <br />
                <div className='to-message'>
                    <p className='from-header'>{`${message.sender} (${date}):`}</p>
                    <p className='from-text'>{`${message.text}`}</p>
                </div>
            </div>);
    }
}

function formatDate(date) {
    const options = {
        year: 'numeric',
        month: 'numeric',
        day: 'numeric',
        hour: 'numeric',
        minute: 'numeric'
    };

    return new Intl.DateTimeFormat("ru", options).format(date);
}

export function createForSendMessage() {
    const senderMessage = document.getElementById('text-message').value;

    if (senderMessage) {
        document.getElementById('text-message').value = ''

        return new Message(getUserName(), senderMessage, new Date());
    }

    return undefined;
}

export function scrollToLastMessageWrapper() {
    const messageWrappers = document.getElementsByClassName('message-wrapper');

    if (messageWrappers.length !== 0) {
        messageWrappers[messageWrappers.length - 1].scrollIntoView(true)
    }
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