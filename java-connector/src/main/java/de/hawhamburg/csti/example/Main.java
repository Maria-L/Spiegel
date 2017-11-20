package de.hawhamburg.csti.example;

import de.hawhamburg.csti.config.Config;
import de.hawhamburg.csti.config.ConfigFactory;
import de.hawhamburg.csti.messaging.Protocol;
import de.hawhamburg.csti.messaging.japi.*;
import de.hawhamburg.csti.Spiegel.japi.SpiegelSerialization;
import de.hawhamburg.csti.Spiegel.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.*;

public class Main {
    private static String generateString() {
        return java.util.UUID.randomUUID().toString();
    }

    static public void main(String[] args) {
        Config config = ConfigFactory.load();

        //Connect to Middleware
        MiddlewareConnector connector;
        if (!config.getBoolean("addresses.middleware.auto-discovery")) {
            String hostname = config.getString("addresses.middleware.hostname");
            int port = config.getInt("addresses.middleware.port");
            Protocol defaultProtocol = Protocol.valueOf(config.getString("addresses.middleware.defaultProtocol"));
            System.out.println(defaultProtocol + " - " + hostname + ":" + port);

            connector = MiddlewareConnector.connect(hostname, port, defaultProtocol);
        } else {
            connector = MiddlewareConnector.connect();
        }

        //Register the application
        RegisteredMiddlewareConnection rc = connector.register("JavaSpiegelClient");

        //Send and receive messages
        System.out.println("java: subscribe");
        rc.subscribe("EchoRequest", SpiegelSerialization.SpiegelDeserializer, msg -> {
                    if (msg instanceof GetEcho) {
                        GetEcho ge = (GetEcho) msg;
                     //   rc.publish(new Echo(ge.s()), "EchoAnswer", SpiegelSerialization.EchoFormat);
                    }
                }
        );
		//Send and receive Spiegel messages
		System.out.println("java: subscribe Spiegel");
        rc.subscribe("Spiegel", SpiegelSerialization.SpiegelDeserializer, msg -> {
                    if (msg instanceof GetRefresh) {
                        GetRefresh gr = (GetRefresh) msg;
						System.out.println("GetRefresh bekommen" + gr.id());
                        rc.publish(new Bloodpresure(77, 122), "Spiegel", SpiegelSerialization.BloodpresureFormat);
                        //rc.publish(new Echo(gr.s()), "EchoAnswer", SpiegelSerialization.EchoFormat);
                    }
                }
        );
		
         rc.subscribe("Spiegel", SpiegelSerialization.SpiegelDeserializer, msg -> {
             if (msg instanceof Bloodpresure){
                    System.out.println("java: received msg: " + msg);
             }
                }
        );
        
        rc.subscribe("EchoAnswer", SpiegelSerialization.SpiegelDeserializer, msg -> {
                    System.out.println("java: received msg: " + msg);
                }
        );

        //Request message every 5 seconds
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("java: publish");
           // rc.publish(new GetEcho(generateString()), "EchoRequest", SpiegelSerialization.GetEchoFormat);
			//rc.publish(new Bloodpresure(76, 122), "Spiegel", SpiegelSerialization.BloodpresureFormat);
        }, 5, 5, SECONDS);
    }
}