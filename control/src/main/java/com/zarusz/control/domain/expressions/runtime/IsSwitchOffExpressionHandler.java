package com.zarusz.control.domain.expressions.runtime;

import com.zarusz.control.domain.expressions.model.IsSwitchOffExpr;
import com.zarusz.control.domain.expressions.core.ExpressionHandler;
import com.zarusz.control.domain.expressions.core.ExpressionRuntimeContext;
import com.zarusz.control.domain.expressions.core.Undefined;

/**
 * Created by Tomasz on 24.10.2016.
 */
public class IsSwitchOffExpressionHandler implements ExpressionHandler<IsSwitchOffExpr> {
    @Override
    public String getExpressionType() {
        return IsSwitchOffExpr.TYPE;
    }

    @Override
    public Object evaluate(IsSwitchOffExpr expression, ExpressionRuntimeContext context) {

        //String featureRef = expression.getFeatureRef();


        return Undefined.INSTANCE;
    }

}
