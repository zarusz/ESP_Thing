package com.zarusz.control.domain.expressions.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.zarusz.control.domain.expressions.core.Expression;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonTypeName(SwitchOnExpr.TYPE)
public class SwitchOnExpr extends Expression implements HasFeatureRef, HasManyFeatureRef {

    public static final String TYPE = "switchOn";

    @Getter
    @Setter
    private String featureRef;

    @Getter
    @Setter
    private List<String> featureRefs;

}

