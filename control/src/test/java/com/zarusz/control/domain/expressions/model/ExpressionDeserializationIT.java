package com.zarusz.control.domain.expressions.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zarusz.control.domain.expressions.core.Expression;
import com.zarusz.control.domain.expressions.model.serialization.TestUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Tomasz on 25.10.2016.
 */
public class ExpressionDeserializationIT {

    private ObjectMapper om;

    @Before
    public void init() {
        om = new ObjectMapper();
    }

    @Test
    public void canDeserializeSample1() throws IOException {
        // arrange
        String sample = TestUtil.readTestData("/data/sample1.json", this);

        // act
        Expression expression = om.readValue(sample, Expression.class);

        // assert
        assertNotNull(expression);
        //assertEquals(2, expressions.length);
    }

    @Test
    public void canDeserializeSample2() throws IOException {
        // arrange
        String sample = TestUtil.readTestData("/data/sample2.json", this);

        // act
        Expression[] expressions = om.readValue(sample, Expression[].class);

        // assert
        assertNotNull(expressions);
        assertEquals(2, expressions.length);
    }
}
