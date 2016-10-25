package com.zarusz.control.domain.expressions.core;

/**
 * Created by Tomasz on 24.10.2016.
 */
public interface ExpressionHandler<T extends Expression> {

    String getExpressionType();
    Object evaluate(T expression, ExpressionRuntimeContext context);

}
