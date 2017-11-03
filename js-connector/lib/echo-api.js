


function GetEcho(s) {
    
    this.s = s;
    
    this.tpe = "de.hawhamburg.csti.example.GetEcho";
}

function Echo(s) {
    
    this.s = s;
    
    this.tpe = "de.hawhamburg.csti.example.Echo";
}




var EchoDeserializer = new function() {
	var transform = function(data, deser) {
    	var re = {};
    	Object.keys(data).forEach(function(k) {
    	    var value = data[k];

			if (Array.isArray(value)) {
            	value = data[k].map(function(val) {
                	console.log(val);
                	if(typeof val === 'object')
                    	return deser.deserialize(val);
                	else return val;
            	});
        	} else if(typeof value === 'object') {
    	        value = deser.deserialize(value);
   		    }
        	re[k] = {
            	writable: false,
            	configurable: false,
            	value: value
        	};
    	});
    	return re;
	}

    this.deserialize = function(json) {
        
        if(json.tpe === "de.hawhamburg.csti.example.GetEcho")
            return this.deserializeGetEcho(json);
        
        if(json.tpe === "de.hawhamburg.csti.example.Echo")
            return this.deserializeEcho(json);
        
    };

    
    this.deserializeGetEcho = function (data) {
        return Object.create(GetEcho.prototype, transform(data, this));
    };
    
    this.deserializeEcho = function (data) {
        return Object.create(Echo.prototype, transform(data, this));
    };
    
};