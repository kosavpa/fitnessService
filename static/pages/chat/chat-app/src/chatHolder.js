import React, { useCallback, useState, useEffect } from "react";
import { createForDrawMessage, scrollToLastMessageWrapper, createForSendMessage } from "./util";
import useWebSocket from 'react-use-websocket';


export function ChatHolder() {
    const [messages, setMessage] = useState([])

    const { sendJsonMessage } = useWebSocket('ws:localhost:8120/chat', {
        onOpen: () => {
            document.addEventListener('keydown', (event) => {
                if (event.ctrlKey && event.key === "Enter") {
                    handleClickSendMessage.call();
                }
            });

            console.log('opened')
        },

        onMessage: (event) => {
            setMessage([...[event.data], ...messages]);
        }
    });

    const handleClickSendMessage = useCallback(() => {
        const message = createForSendMessage();

        if (message) {
            sendJsonMessage(message);
        }
    }, []);

    useEffect(() => scrollToLastMessageWrapper())

    return (
        <div className='chat-holder'>
            <div className="chat-area">
                <div className="chat-messages-area">
                    {messages.map((message) => createForDrawMessage(JSON.parse(message))).reverse()}
                </div>
            </div>
            <div className="sender-wrapper">
                <textarea id="text-message" />
                <button className="send-btn" onClick={handleClickSendMessage} >Отправить </button>
            </div>
        </div>
    );
}