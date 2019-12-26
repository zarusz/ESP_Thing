package com.zarusz.control.app.comm.base;

import com.google.protobuf.MessageLite;
import com.zarusz.control.app.comm.TemperatureFeatureHandler;
import com.zarusz.control.app.comm.messages.MessageReceivedEvent;
import com.zarusz.control.device.messages.DeviceMessageProtos;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.listener.Handler;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by Tomasz on 10/16/2016.
 */
public class TransformingHandler extends AbstractHandler {

    public class TopicRoutingSetup<T extends MessageLite> {
        private final String topic;

        public TopicRoutingSetup(String topic) {
            this.topic = topic;
        }

        public TopicRoutingSetup<T> transform(Function<T, Object> selector) {
            List<Function<MessageLite, Object>> selectors = _selectorByTopic.get(topic);
            if (selectors == null) {
                selectors = new ArrayList<>();
                _selectorByTopic.put(topic, selectors);
            }
            selectors.add((Function<MessageLite, Object>) selector);
            return this;
        }
    }

    private final Map<String, List<Function<MessageLite, Object>>> _selectorByTopic = new HashMap<>();

    public TransformingHandler(MBassador bus) {
        super(bus, LoggerFactory.getLogger(TemperatureFeatureHandler.class));
    }


    public <T extends MessageLite> TopicRoutingSetup<T> forTopic(String topic) {
        return new TopicRoutingSetup<>(topic);
    }

    @Handler
    public void onMessage(MessageReceivedEvent<DeviceMessageProtos.DeviceEvents> e) {
        List<Function<MessageLite, Object>> selectors = _selectorByTopic.get(e.getTopic());
        if (selectors == null) {
            return;
        }

        for (Function<MessageLite, Object> selector : selectors) {
            Object message = selector.apply(e.getMessage());
            if (message != null) {
                bus.publish(message);
            }
        }
    }

}
