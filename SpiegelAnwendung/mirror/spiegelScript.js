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