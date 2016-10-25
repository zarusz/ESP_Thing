package com.zarusz.control.domain.expressions.runtime;

import com.zarusz.control.domain.expressions.model.SwitchOffExpr;
import com.zarusz.control.domain.expressions.core.ExpressionHandler;
import com.zarusz.control.domain.expressions.core.ExpressionRuntimeContext;
import com.zarusz.control.domain.expressions.core.Undefined;

/**
 * Created by Tomasz on 24.10.2016.
 */
public class SwitchOffExpressionHandler implements ExpressionHandler<SwitchOffExpr> {
    @Override
    public String getExpressionType() {
        return SwitchOffExpr.TYPE;
    }

    @Override
    public Object evaluate(SwitchOffExpr expression, ExpressionRuntimeContext context) {
        return Undefined.INSTANCE;
    }
}
