package com.zarusz.control.domain.expressions.core;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by Tomasz on 23.10.2016.
 */
public class LayeredScope implements Scope {

    private final Deque<Scope> scopes = new LinkedList<>();

    public Scope pushScope() {
        return pushScope(new MapScope());
    }

    public Scope pushScope(Scope scope) {
        scopes.push(scope);
        return scope;
    }

    public Scope popScope() {
        assertNotEmpty();
        return scopes.pop();
    }

    @Override
    public Object getVariable(String name) {
        assertNotEmpty();

        Scope scope = getClosestScopeWithVariable(name);
        if (scope == null) {
            return Undefined.INSTANCE;
        }
        return scope.getVariable(name);
    }

    @Override
    public void setVariable(String name, Object value) {
        assertNotEmpty();

        Scope scope = getClosestScopeWithVariable(name);
        if (scope == null) {
            scope = scopes.peek();
        }
        scope.setVariable(name, value);
    }

    @Override
    public void clearVariable(String name) {
        assertNotEmpty();

        Scope scope = getClosestScopeWithVariable(name);
        if (scope != null) {
            scope.clearVariable(name);
        }
    }

    private void assertNotEmpty() {
        if (scopes.isEmpty()) {
            throw new ExpressionRuntimeException("There are no scopes");
        }
    }

    private Scope getClosestScopeWithVariable(String name) {
        for (Scope scope : scopes) {
            Object value = scope.getVariable(name);
            if (!Undefined.INSTANCE.equals(value)) {
                return scope;
            }
        }
        return null;
    }
}
