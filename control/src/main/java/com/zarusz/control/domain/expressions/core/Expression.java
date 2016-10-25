package com.zarusz.control.domain.expressions.core;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.zarusz.control.domain.expressions.model.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tomasz on 23.10.2016.
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = SwitchOffExpr.class),
    @JsonSubTypes.Type(value = SwitchOnExpr.class),
    @JsonSubTypes.Type(value = IsSwitchOffExpr.class),
    @JsonSubTypes.Type(value = IsSwitchOnExpr.class),
    @JsonSubTypes.Type(value = IfExpr.class),
    @JsonSubTypes.Type(value = FeatureChangedExpr.class),
    @JsonSubTypes.Type(value = RuleExpr.class),
})
public abstract class Expression {

}

