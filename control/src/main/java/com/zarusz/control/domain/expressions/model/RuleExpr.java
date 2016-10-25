package com.zarusz.control.domain.expressions.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.zarusz.control.domain.expressions.core.Expression;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tomasz on 25.10.2016.
 */
@JsonTypeName(RuleExpr.TYPE)
public class RuleExpr extends Expression {

    public static final String TYPE = "rule";

    @Getter
    @Setter
    private Expression trigger;

    @Getter
    @Setter
    private Expression action;

    @Getter
    @Setter
    private String title;
}
