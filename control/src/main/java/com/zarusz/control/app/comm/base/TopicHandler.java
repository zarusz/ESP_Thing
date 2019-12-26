package com.zarusz.control.app.comm.base;

import com.google.protobuf.MessageLite;
import com.zarusz.control.app.comm.messages.MessageReceivedEvent;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.listener.Handler;
import org.slf4j.Logger;

/**
 * Created by Tomasz on 9/30/2016.
 */
public abstract class TopicHandler<T extends MessageLite> extends AbstractHandler {

    private final String topic;

    public TopicHandler(MBassador bus, Logger log, String topic) {
        super(bus, log);
        this.topic = topic;
    }

    public boolean canHandle(MessageReceivedEvent<T> event) {
        return event.getTopic().equals(topic);
    }

    public abstract void handle(String topic, T msg);

    @Handler
    public final void onHandle(MessageReceivedEvent<T> event) {
        try {
            if (canHandle(event)) {
                handle(event.getTopic(), event.getMessage());
            }
        } catch (Exception e) {
            log.error("Error occurred while processing message.", e);
        }
    }

}
