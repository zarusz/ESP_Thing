package com.zarusz.control.domain.expressions.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.zarusz.control.domain.expressions.core.Expression;
import lombok.Getter;
import lombok.Setter;

@JsonTypeName(IsSwitchOnExpr.TYPE)
public class IsSwitchOnExpr extends Expression implements HasFeatureRef {

    public static final String TYPE = "isSwitchOn";

    @Getter
    @Setter
    private String featureRef;
}

