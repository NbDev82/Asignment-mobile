package com.example.calculator.customexpression;

import com.udojava.evalex.AbstractFunction;

import java.math.BigDecimal;
import java.util.List;

public class CubeRootFunction extends AbstractFunction {
    public CubeRootFunction() {
        super("cbrt", 1);
    }

    @Override
    public BigDecimal eval(List<BigDecimal> parameters) {
        BigDecimal n = parameters.get(0);
        BigDecimal result = BigDecimal.valueOf(Math.cbrt(n.doubleValue()));
        return result;
    }
}
