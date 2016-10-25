package com.zarusz.control.domain.expressions.model.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zarusz.control.domain.expressions.model.FeatureRef;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Tomasz on 25.10.2016.
 */
public class FeatureRefSerializerTest {

    @Test
    public void canDeserialize() throws IOException {
        // arrange
        ObjectMapper m = new ObjectMapper();
        String json = TestUtil.readTestData("/data/FeatureRef.json", this);

        // act
        FeatureRef ref = m.readValue(json, FeatureRef.class);

        // assert
        assertNotNull(ref);
        assertEquals("dev_sufit", ref.getDeviceId());
        assertEquals(new Integer(1), ref.getPort());
    }

}

