package com.zarusz.control.domain.expressions.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.zarusz.control.domain.expressions.core.Expression;
import lombok.Getter;
import lombok.Setter;

@JsonTypeName(IfExpr.TYPE)
public class IfExpr extends Expression {

    public static final String TYPE = "if";

    @Getter
    @Setter
    private Expression when;

    @Getter
    @Setter
    private Expression then;

    @Getter
    @Setter
    @JsonProperty("else")
    private Expression _else;
}
