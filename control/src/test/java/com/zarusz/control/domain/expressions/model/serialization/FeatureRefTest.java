package com.zarusz.control.domain.expressions.model.serialization;

import com.zarusz.control.domain.expressions.model.FeatureRef;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FeatureRefTest {

    @Test
    public void testParse() {

        // arrange
        String value = "dev_sufit:10";

        // act
        FeatureRef ref = FeatureRef.parse(value);

        // assert
        assertEquals("dev_sufit", ref.getDeviceId());
        assertEquals(new Integer(10), ref.getPort());
    }

}
