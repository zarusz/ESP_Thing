package com.zarusz.control.domain.expressions.runtime;

import com.zarusz.control.domain.expressions.core.ExpressionRuntimeException;
import com.zarusz.control.domain.expressions.core.LayeredScope;
import com.zarusz.control.domain.expressions.core.Scope;
import com.zarusz.control.domain.expressions.core.Undefined;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by Tomasz on 23.10.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class LayeredScopeTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    Scope s1;
    @Mock
    Scope s2;

    @Before
    public void init() {
        when(s1.getVariable(anyString())).thenReturn(Undefined.INSTANCE);
        when(s2.getVariable(anyString())).thenReturn(Undefined.INSTANCE);
    }

    @Test
    public void when_empty_getVariable_throwsException() {
        // arrange
        LayeredScope s = new LayeredScope();
        thrown.expect(ExpressionRuntimeException.class);

        // act
        s.getVariable("var1");

        // assert
        fail();
    }

    @Test
    public void when_empty_setVariable_throwsException() {
        // arrange
        LayeredScope s = new LayeredScope();
        thrown.expect(ExpressionRuntimeException.class);

        // act
        s.setVariable("var1", 0);

        // assert
        fail();
    }

    @Test
    public void when_empty_clearVariable_throwsException() {
        // arrange
        LayeredScope s = new LayeredScope();
        thrown.expect(ExpressionRuntimeException.class);

        // act
        s.clearVariable("var1");

        // assert
        fail();
    }

    @Test
    public void when_setVariable_works() {
        // arrange
        when(s1.getVariable("var1")).thenReturn("s1");
        when(s2.getVariable("var1")).thenReturn("s2");

        final LayeredScope s = new LayeredScope();
        s.pushScope(s1);
        s.pushScope(s2);

        // act
        s.setVariable("var1", "s");

        // assert
        verify(s1, never()).getVariable(anyString());
        verify(s1, never()).setVariable(anyString(), anyObject());
        verify(s2, times(1)).getVariable("var1");
        verify(s2, times(1)).setVariable("var1", "s");
    }

    @Test
    public void when_getVariable_works() {
        // arrange
        when(s1.getVariable("var1")).thenReturn("s1");
        when(s1.getVariable("var2")).thenReturn("s1");
        when(s2.getVariable("var1")).thenReturn("s2");

        final LayeredScope s = new LayeredScope();
        s.pushScope(s1);
        s.pushScope(s2);

        // act
        final Object v1 = s.getVariable("var1");
        final Object v2 = s.getVariable("var2");

        // assert
        assertEquals("s2", v1);
        assertEquals("s1", v2);
        verify(s1, never()).getVariable("var1");
        verify(s2, times(2)).getVariable("var1");
        verify(s1, times(2)).getVariable("var2");
        verify(s2, times(1)).getVariable("var2");
    }
}
