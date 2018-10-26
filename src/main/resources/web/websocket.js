let id = id => document.getElementById(id);

let ws = new WebSocket("ws://" + location.hostname + ":" + location.port + "/websocket");

ws.onmessage = msg => {
    id("currentTime").innerHTML = msg.data
};

ws.onclose = () => alert("server closed connection");
