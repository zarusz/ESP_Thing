package com.zarusz.control.domain.expressions.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tomasz on 23.10.2016.
 */
public class MapScope implements Scope {
    private final Map<String, Object> map;

    public MapScope(Map<String, Object> map) {
        this.map = map;
    }

    public MapScope() {
        this(new HashMap<>());
    }

    @Override
    public Object getVariable(String name) {
        if (!map.containsKey(name)) {
            return Undefined.INSTANCE;
        }
        return map.get(name);
    }

    @Override
    public void setVariable(String name, Object value) {
        map.put(name, value);
    }

    @Override
    public void clearVariable(String name) {
        map.remove(name);
    }
}
