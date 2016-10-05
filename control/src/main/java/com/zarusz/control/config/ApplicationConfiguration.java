package com.zarusz.control.config;

import com.zarusz.control.app.comm.*;
import com.zarusz.control.app.comm.base.mqtt.MqttBrokerGatewayHandler;
import com.zarusz.control.domain.common.EventBus;
import com.zarusz.control.web.websocket.FeatureStateChangedNotifier;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.bus.config.BusConfiguration;
import net.engio.mbassy.bus.config.Feature;
import net.engio.mbassy.bus.config.IBusConfiguration;
import net.engio.mbassy.bus.error.IPublicationErrorHandler;
import org.fusesource.mqtt.client.MQTT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URISyntaxException;

/**
 * Created by Tomasz on 4/5/2016.
 */
@Configuration
public class ApplicationConfiguration {

    @Value("${control.mqtt.client_id}")
    private String mqttClientId;
    @Value("${control.mqtt.host}")
    private String mqttHost;
    @Value("${control.mqtt.port}")
    private Integer mqttPort;

    @Bean
    public MBassador bus() {
        MBassador bus = new MBassador(new BusConfiguration()
            .addFeature(Feature.SyncPubSub.Default())
            .addFeature(Feature.AsynchronousHandlerInvocation.Default())
            .addFeature(Feature.AsynchronousMessageDispatch.Default())
            .addPublicationErrorHandler(new IPublicationErrorHandler.ConsoleLogger())
            .setProperty(IBusConfiguration.Properties.BusId, "global bus")); // this is used for identification in #toString

        EventBus.setInstance(bus);
        return bus;
    }

    @Bean
    public MQTT mqttClient() throws URISyntaxException {
        MQTT mqttClient = new MQTT();
        mqttClient.setClientId(mqttClientId);
        mqttClient.setHost(mqttHost, mqttPort);
        // indefinite
        mqttClient.setConnectAttemptsMax(-1);
        mqttClient.setReconnectAttemptsMax(-1);
        mqttClient.setVersion("3.1.1");
        return mqttClient;
    }

    @Bean
    public MqttBrokerGatewayHandler mqttBrokerGatewayHandler(MBassador bus, MQTT mqttClient) throws Exception {
        return new MqttBrokerGatewayHandler(bus, mqttClient);
    }

    @Bean
    public SwitchFeatureHandler switchFeatureHandler(MBassador bus) throws Exception {
        return new SwitchFeatureHandler(bus);
    }

    @Bean
    public TemperatureFeatureHandler temperatureFeatureHandler(MBassador bus) throws Exception {
        return new TemperatureFeatureHandler(bus);
    }

    @Bean
    public IRSensorFeatureHandler irReceiverFeatureHandler(MBassador bus) throws Exception {
        return new IRSensorFeatureHandler(bus);
    }

    @Bean
    public IRFeatureHandler irTransceiverFeatureHandler(MBassador bus) throws Exception {
        return new IRFeatureHandler(bus);
    }

    @Bean
    public DeviceDescriptionHandler deviceDescriptionHandler(MBassador bus) throws Exception {
        return new DeviceDescriptionHandler(bus);
    }

    @Bean
    public DeviceHeartbeatHandler deviceHeartbeatHandler(MBassador bus) throws Exception {
        return new DeviceHeartbeatHandler(bus);
    }

    @Bean
    public ServiceCommandHandler serviceCommandHandler(MBassador bus) throws Exception {
        return new ServiceCommandHandler(bus);
    }

    @Bean
    public FeatureStateChangedNotifier featureStateChangedNotifier(MBassador bus) throws Exception {
        return new FeatureStateChangedNotifier(bus);
    }

}
