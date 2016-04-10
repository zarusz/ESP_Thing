package com.zarusz.control.config;

import com.zarusz.control.app.comm.DeviceHeartbeatHandler;
import com.zarusz.control.app.comm.mqtt.MqttBrokerGatewayHandler;
import com.zarusz.control.app.comm.SwitchFeatureHandler;
import com.zarusz.control.domain.common.EventBus;
import net.engio.mbassy.bus.MBassador;
import org.fusesource.mqtt.client.MQTT;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URISyntaxException;

/**
 * Created by Tomasz on 4/5/2016.
 */
@Configuration
public class ApplicationConfiguration {

    @Bean
    public MBassador bus() {
        /*
        MBassador bus = new MBassador(new BusConfiguration()
            .addFeature(Feature.SyncPubSub.Default())
            .addFeature(Feature.AsynchronousHandlerInvocation.Default())
            .addFeature(Feature.AsynchronousMessageDispatch.Default())
            //.addPublicationErrorHandler(new IPublicationErrorHandler.ConsoleLogger())
            //.setProperty(IBusConfiguration.Properties.BusId, "global bus")); // this is used for identification in #toString
        */

        MBassador unboundBus = new MBassador();
        EventBus.setInstance(unboundBus);
        return unboundBus;
    }

    @Bean
    public MQTT mqttClient() throws URISyntaxException {
        MQTT mqttClient = new MQTT();
        mqttClient.setClientId("hub");
        mqttClient.setHost("raspberrypi", 1883);
        return mqttClient;
    }

    @Bean
    public MqttBrokerGatewayHandler mqttBrokerGatewayHandler(MBassador bus, MQTT mqttClient) throws Exception {
        return new MqttBrokerGatewayHandler(bus, mqttClient);
    }

    @Bean
    public SwitchFeatureHandler switchFeatureBroker(MBassador bus) throws Exception {
        return new SwitchFeatureHandler(bus);
    }

    @Bean
    public DeviceHeartbeatHandler deviceHeartbeatHandler(MBassador bus) throws Exception {
        return new DeviceHeartbeatHandler(bus);
    }
}
