import React, { useCallback, useState } from "react";
import { createForDrawMessage } from "./util";
import { createForSendMessage } from "./util";
import useWebSocket from 'react-use-websocket';


export function ChatHolder() {
    const [messages, setMessage] = useState([])

    const { sendJsonMessage } = useWebSocket('ws:localhost:8120/chat', {
        onOpen: () => {
            document.addEventListener('keydown', (event) => {
                if (event.ctrlKey && event.key === "Enter") {
                    send();
                }
            });

            console.log('opened')
        },

        onMessage: (event) => {
            setMessage([...[event.data], ...messages]);
        }
    });

    const handleClickSendMessage = useCallback(() => send(), []);

    function send() {
        const message = createForSendMessage();

        if (message) {
            sendJsonMessage(message);
        }
    }

    return (
        <div className='chat-holder'>
            <div>
                {messages.map((message) => { return createForDrawMessage(JSON.parse(message)) })}
            </div>
            <div className="sender-wrapper">
                <textarea id="text-message" />
                <button onClick={handleClickSendMessage} />
            </div>
        </div>
    );
}