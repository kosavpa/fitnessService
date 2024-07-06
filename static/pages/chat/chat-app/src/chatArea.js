import { useState, useRef } from "react";
import { createForDrawMessage } from "./util";


export function ChatArea(props) {
    const client = useRef(props.client);

    const [messages, setMessage] = useState([])

    console.log("Area before");
    client.current.onMessage = (event) => {
        setMessage([...[JSON.parse(event.data)], ...messages]);
    }

    return messages.map(message => createForDrawMessage(message));
}