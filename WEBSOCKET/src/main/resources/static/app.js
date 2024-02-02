const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/websocket'
});

stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/chat/common', (clientMsg) => {
        showMsg(JSON.parse(clientMsg.body));
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function showMsg(clientMsg) {

    const msgBox = document.getElementById("msg-box")

    const newMsg = document.createElement("div")

    newMsg.innerText = clientMsg.fromLogin + ": " + clientMsg.msg

    const br = document.createElement("br")

    msgBox.append(newMsg, br)
}

function connect() {
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
}

function sendMsg(login, msg) {
    stompClient.publish({
        destination: "/app/chatting",
        body: JSON.stringify({'fromLogin': login, 'msg' : msg})
    });
}

document.body.onload = () => connect()


const loginFld = document.getElementById("login-fld")
const msgFld = document.getElementById("msg-fld")
const actionBtn = document.getElementById("action-btn")

actionBtn.onclick = () => {
    sendMsg(loginFld.value, msgFld.value)
}

