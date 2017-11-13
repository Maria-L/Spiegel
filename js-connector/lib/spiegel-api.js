


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
        
    };

    
    this.deserializeGetEcho = function (data) {
        return Object.create(GetEcho.prototype, transform(data, this, {"s": "String"}));
    };
    
    this.deserializeEcho = function (data) {
        return Object.create(Echo.prototype, transform(data, this, {"s": "String"}));
    };
    
};