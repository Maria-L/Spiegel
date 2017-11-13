


function GetEcho(s) {
    
    this.s = s;
    
    this.tpe = "de.hawhamburg.csti.Spiegel.GetEcho";
}
GetEcho.prototype.toJSON = function() {
    return {
        
        "s": this.s,
        
        "tpe": "de.hawhamburg.csti.Spiegel.GetEcho"
    }
}

function Echo(s) {
    
    this.s = s;
    
    this.tpe = "de.hawhamburg.csti.Spiegel.Echo";
}
Echo.prototype.toJSON = function() {
    return {
        
        "s": this.s,
        
        "tpe": "de.hawhamburg.csti.Spiegel.Echo"
    }
}

function GetRefresh(id) {
    
    this.id = id;
    
    this.tpe = "de.hawhamburg.csti.Spiegel.GetRefresh";
}
GetRefresh.prototype.toJSON = function() {
    return {
        
        "id": this.id,
        
        "tpe": "de.hawhamburg.csti.Spiegel.GetRefresh"
    }
}

function RefreshedData(diastole, systole, heartrate) {
    
    this.diastole = diastole;
    
    this.systole = systole;
    
    this.heartrate = heartrate;
    
    this.tpe = "de.hawhamburg.csti.Spiegel.RefreshedData";
}
RefreshedData.prototype.toJSON = function() {
    return {
        
        "diastole": this.diastole,
        
        "systole": this.systole,
        
        "heartrate": this.heartrate,
        
        "tpe": "de.hawhamburg.csti.Spiegel.RefreshedData"
    }
}

function Bloodpresure(diastole, systole) {
    
    this.diastole = diastole;
    
    this.systole = systole;
    
    this.tpe = "de.hawhamburg.csti.Spiegel.Bloodpresure";
}
Bloodpresure.prototype.toJSON = function() {
    return {
        
        "diastole": this.diastole,
        
        "systole": this.systole,
        
        "tpe": "de.hawhamburg.csti.Spiegel.Bloodpresure"
    }
}

function GetBloodpresure(bloodpresure) {
    
    this.bloodpresure = bloodpresure;
    
    this.tpe = "de.hawhamburg.csti.Spiegel.GetBloodpresure";
}
GetBloodpresure.prototype.toJSON = function() {
    return {
        
        "bloodpresure": this.bloodpresure,
        
        "tpe": "de.hawhamburg.csti.Spiegel.GetBloodpresure"
    }
}




var SpiegelDeserializer = new function() {
	var transform = function(data, deser, paramInfo) {
    	var re = {};
    	var paramNum = 0;
    	Object.keys(data).forEach(function(k) {
            var value = data[k];
            var tpeInfo = paramInfo[k];

            if (Array.isArray(value)) {
                if(tpeInfo.startsWith("Seq")) {
                    var expectedTpe = tpeInfo.substring(4, tpeInfo.length - 1)
                    value = data[k].map(function(val) {
                        if(typeof val === 'object')
                            return SpiegelDeserializer.deserialize(val, expectedTpe);
                        else return val;
                    });
                } else {
                    console.log("ERROR: can not deserialize message. Expected Sequence, was " + value)
                }
            } else if(typeof value === 'object') {
                value = deser.deserialize(value, tpeInfo);
            }
            re[k] = {
                writable: false,
                configurable: false,
                value: value
            };
            paramNum += 1;
    	});
    	return re;
	}

    this.deserialize = function(json, expectedTpe) {
        
        if(json.tpe === "de.hawhamburg.csti.Spiegel.GetEcho" || expectedTpe === "de.hawhamburg.csti.Spiegel.GetEcho")
            return this.deserializeGetEcho(json);
        
        if(json.tpe === "de.hawhamburg.csti.Spiegel.Echo" || expectedTpe === "de.hawhamburg.csti.Spiegel.Echo")
            return this.deserializeEcho(json);
        
        if(json.tpe === "de.hawhamburg.csti.Spiegel.GetRefresh" || expectedTpe === "de.hawhamburg.csti.Spiegel.GetRefresh")
            return this.deserializeGetRefresh(json);
        
        if(json.tpe === "de.hawhamburg.csti.Spiegel.RefreshedData" || expectedTpe === "de.hawhamburg.csti.Spiegel.RefreshedData")
            return this.deserializeRefreshedData(json);
        
        if(json.tpe === "de.hawhamburg.csti.Spiegel.Bloodpresure" || expectedTpe === "de.hawhamburg.csti.Spiegel.Bloodpresure")
            return this.deserializeBloodpresure(json);
        
        if(json.tpe === "de.hawhamburg.csti.Spiegel.GetBloodpresure" || expectedTpe === "de.hawhamburg.csti.Spiegel.GetBloodpresure")
            return this.deserializeGetBloodpresure(json);
        
    };

    
    this.deserializeGetEcho = function (data) {
        return Object.create(GetEcho.prototype, transform(data, this, {"s": "String"}));
    };
    
    this.deserializeEcho = function (data) {
        return Object.create(Echo.prototype, transform(data, this, {"s": "String"}));
    };
    
    this.deserializeGetRefresh = function (data) {
        return Object.create(GetRefresh.prototype, transform(data, this, {"id": "Int"}));
    };
    
    this.deserializeRefreshedData = function (data) {
        return Object.create(RefreshedData.prototype, transform(data, this, {"diastole": "Int", "systole": "Int", "heartrate": "Int"}));
    };
    
    this.deserializeBloodpresure = function (data) {
        return Object.create(Bloodpresure.prototype, transform(data, this, {"diastole": "Int", "systole": "Int"}));
    };
    
    this.deserializeGetBloodpresure = function (data) {
        return Object.create(GetBloodpresure.prototype, transform(data, this, {"bloodpresure": "de.hawhamburg.csti.Spiegel.Bloodpresure"}));
    };
    
};