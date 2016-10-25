package com.zarusz.control.domain.expressions.model.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.zarusz.control.domain.expressions.model.FeatureRef;

import java.io.IOException;

/**
 * Created by Tomasz on 25.10.2016.
 */
public class FeatureRefSerializer extends JsonSerializer<FeatureRef> {

    @Override
    public void serialize(FeatureRef featureRef, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        String value = featureRef.getDeviceId() + ":" + featureRef.getPort();
        jsonGenerator.writeString(value);
    }
}
