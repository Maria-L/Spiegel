$(document).ready(function(){
    
	var diastole = 54;
	var Systole = 102;
	var puls = 74;
	var gender = "W";
	var age = 24;
	var groesse = "1,75";
	
	var Datum = new Date();
	var Tag = Datum.getDate();
	var Monat = Datum.getMonth() + 1;
	var Jahr = Datum.getFullYear();
	
	($('#datum').html(Tag + "." + Monat + "." + Jahr));
    ($('#dia').html(diastole));
	($('#sys').html(Systole));
	($('#puls').html(puls));
	($('#gender').html(gender));
	($('#age').html(age));
	($('#groesse').html(groesse));
});