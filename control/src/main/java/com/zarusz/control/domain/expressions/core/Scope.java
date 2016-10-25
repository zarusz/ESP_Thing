package com.zarusz.control.domain.expressions.core;

public interface Scope {

    Object getVariable(String name);
    void setVariable(String name, Object value);
    void clearVariable(String name);

}
