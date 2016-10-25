package com.zarusz.control.domain.expressions.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zarusz.control.domain.expressions.model.serialization.FeatureRefDeserializer;
import com.zarusz.control.domain.expressions.model.serialization.FeatureRefSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by Tomasz on 25.10.2016.
 */
@Data
@EqualsAndHashCode(of = {"deviceId", "port"})
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize(using = FeatureRefSerializer.class)
@JsonDeserialize(using = FeatureRefDeserializer.class)
public class FeatureRef {

    private String deviceId;
    private Integer port;

    public static FeatureRef parse(String value) {
        int i = value.indexOf(':');
        if (i == -1) {
            throw new RuntimeException("The value should contain a ':' sign");
        }
        String deviceId = value.substring(0, i);
        Integer port = Integer.parseInt(value.substring(i + 1));
        return new FeatureRef(deviceId, port);
    }

}
