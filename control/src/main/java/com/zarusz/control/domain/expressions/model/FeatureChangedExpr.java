package com.zarusz.control.domain.expressions.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.zarusz.control.domain.expressions.core.Expression;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Tomasz on 25.10.2016.
 */
@JsonTypeName(FeatureChangedExpr.TYPE)
public class FeatureChangedExpr extends Expression implements HasFeatureRef, HasManyFeatureRef {

    public static final String TYPE = "featureChanged";

    @Getter
    @Setter
    private String featureType;

    @Getter
    @Setter
    private String featureRef;

    @Getter
    @Setter
    private List<String> featureRefs;
}
