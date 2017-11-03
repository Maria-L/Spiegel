function appendText(text) {
	var paragraph = document.createElement("p");
	paragraph.innerHTML = "<strong>" + text + "</strong>";
	document.getElementById("playground").appendChild(paragraph);
}


var connector = new MiddlewareConnector("ws://127.0.0.1:8080/connect");

connector.subscribe("EchoRequest", EchoDeserializer, function(obj) {
	if(obj instanceof GetEcho) {
		appendText("GetEcho: " + obj.s);
		connector.publish("EchoAnswer", new Echo(obj.s));
	}
});

connector.subscribe("EchoAnswer", EchoDeserializer, function(obj) {
	if(obj instanceof Echo)
		appendText("Echo: " + obj.s);
});

window.setInterval(function() {
	connector.publish("EchoRequest", new GetEcho("" + Math.random()));
}, 3000);
