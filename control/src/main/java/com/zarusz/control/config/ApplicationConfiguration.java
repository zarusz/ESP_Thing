package com.zarusz.control.config;

import com.zarusz.control.app.comm.*;
import com.zarusz.control.app.comm.base.TransformingHandler;
import com.zarusz.control.app.comm.mqtt.MqttBrokerGatewayHandler;
import com.zarusz.control.device.messages.DeviceMessageProtos;
import com.zarusz.control.domain.common.EventBus;
import com.zarusz.control.web.websocket.FeatureStateChangedNotifier;
import groovy.util.GroovyScriptEngine;
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
    @Value("${control.scripts}")
    private String scripts;

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

    @Bean
    public Topics topics() throws Exception {
        return new Topics();
    }

    @Bean
    public TransformingHandler transformingHandler(MBassador bus, Topics topics) throws Exception {
        TransformingHandler handler = new TransformingHandler(bus);

        handler
            .<DeviceMessageProtos.DeviceEvents>forTopic(Topics.DeviceEvents)
            .transform(x -> x.hasDeviceHearbeatEvent() ? x.getDeviceHearbeatEvent() : null)
            .transform(x -> x.hasDeviceConnectedEvent() ? x.getDeviceConnectedEvent() : null)
            .transform(x -> x.hasDeviceDisconnectedEvent() ? x.getDeviceDisconnectedEvent() : null)
            .transform(x -> x.hasTemperatureMeasureEvent() ? x.getTemperatureMeasureEvent() : null)
            .transform(x -> x.hasHumidityMeasureEvent() ? x.getHumidityMeasureEvent() : null)
            .transform(x -> x.hasIrSignalEvent() ? x.getIrSignalEvent() : null);

        handler
            .<DeviceMessageProtos.DeviceDescription>forTopic(Topics.DeviceDescription)
            .transform(x -> x);

        handler
            .<DeviceMessageProtos.Responses>forTopic(topics.getMe())
            .transform(x -> x.hasDeviceStatusResponse() ? x.getDeviceStatusResponse() : null);

        return handler;
    }

    @Bean
    public GroovyScriptEngine groovyScriptEngine() throws Exception {
        GroovyScriptEngine engine = new GroovyScriptEngine(scripts);
        return engine;
    }
}