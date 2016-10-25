package com.zarusz.control.domain.expressions.model.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.zarusz.control.domain.expressions.model.FeatureRef;

import java.io.IOException;

/**
 * Created by Tomasz on 25.10.2016.
 */
public class FeatureRefDeserializer extends JsonDeserializer<FeatureRef> {

    @Override
    public FeatureRef deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String value = jsonParser.getValueAsString();
        return FeatureRef.parse(value);
    }

}
