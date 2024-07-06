
import { createForSendMessage } from "./util";
import { useRef } from "react";


export function SenderMessageWrapper(props) {
    const client = useRef(props.client);

    document.addEventListener('keydown', (event) => {
        if (event.ctrlKey && event.key === "Enter") {
            const message = createForSendMessage();

            if (message) {
                client.current.send(message);
            }
        }
    });
    console.log("sender before");
    return (
        <div className="sender-wrapper">
            <textarea id="text-message" />
            <button onClick={
                function () {
                    const message = createForSendMessage();

                    if (message) {
                        client.current.send(message);
                    }
                }
            } />
        </div>);
}