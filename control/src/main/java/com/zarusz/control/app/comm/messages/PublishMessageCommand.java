package com.zarusz.control.app.comm.messages;

import com.google.protobuf.MessageLite;
import lombok.Getter;

/**
 * Created by Tomasz on 4/10/2016.
 */
public class PublishMessageCommand<T extends MessageLite> {

    @Getter
    private final String topic;
    @Getter
    private final T message;

    public PublishMessageCommand(String topic, T message) {
        this.topic = topic;
        this.message = message;
    }
}
