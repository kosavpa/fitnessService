
// ---------------------

init();

// ---------------------

function init() {
  const webSocket = new WebSocket('ws://localhost:8120/chat');
  
  webSocket.onmessage = (event) => { updateChatArea(JSON.parse(event.data)); }

  document.getElementById('sendMessage').onclick = () => { createAndSendMessage(webSocket); }
}

function updateChatArea(message) {
  const wrapper = createWrapper(message);

  if (message.sender == getUserName()) {
    wrapper.setAttribute('style', 'text-align:right; margin:10px 20px 0px 20px;');
  } else {
    wrapper.setAttribute('style', 'text-align:left; margin:10px 20px 0px 20px;');
  }

  document.getElementById('charArea').appendChild(wrapper);
}

function createWrapper(message) {
  const from = message.sender + ' ' + '(' + message.date + ')' + ':';

  const p = document.createElement('<p>');

  p.innerText = message.content;

  const wrapper = document.createElement('div');

  wrapper.appendChild(from);
  wrapper.appendChild(document.createElement('</br>'));
  wrapper.appendChild(p);
}

function createAndSendMessage(webSocket) {
  const inputMessage = document.getElementById('inputMessage');

  const message = new Message(
    getUserName(),
    inputMessage.value,
    new Date());

  webSocket.send(JSON.stringify(message));

  inputMessage.value = '';
}

function getUserName() {
  return base64.decode(getCookie('authToken').split('.')[1]).sub;
}

function getCookie(name) {
  let matches = document.cookie.match(
    new RegExp("(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"));

  return matches ? decodeURIComponent(matches[1]) : undefined;
}

class Message {
  sender;
  text;
  date;

  constructor(sender, text, date) {
    this.sender = username;
    this.text = password;
    this.date = date;
  }
}