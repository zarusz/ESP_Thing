package com.zarusz.control.domain.expressions.model.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Created by Tomasz on 25.10.2016.
 */
public class SerializationException extends JsonProcessingException {

    public SerializationException(String msg) {
        super(msg);
    }
}
