document.write(6 + 6);

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

//Globale Variablen definieren	und mit erstem Wert füllen

var diastole = getDiastole();
var Systole = 102;
var puls = 74;
var gender = "W";
var age = 24;
var groesse = "1,75";

var Datum = new Date();
var Tag = Datum.getDate();
var Monat = Datum.getMonth() + 1;
var Jahr = Datum.getFullYear();
	
var welcomeName = "Maria"

$(document).ready(function(){
    //Dinge die hier stehen müssen weil es sonst nicht funktionert. Keine Ahnung warum
	//document.getElementById("dia").innerHTML = 5 + 6;
	
	//ids aus dem html den hier gesetzten Variablen zuweisen
	($('#datum').html(Tag + "." + Monat + "." + Jahr));
    ($('#dia').html(diastole));
	($('#sys').html(Systole));
	($('#puls').html(puls));
	($('#gender').html(gender));
	($('#age').html(age));
	($('#groesse').html(groesse));
	($('#welcome-text').html(welcomeName));
	
});

function graphSchritte() {
    var x = "hello wolrd";
    $("p").html(x);
};

$("#graphSchritte").click(function() {
    graphSchritte();
});

function refreshA() {
	
    return 56;
};
