package com.example.calculator.customexpression;

import com.udojava.evalex.AbstractFunction;

import java.math.BigDecimal;
import java.util.List;

public class FactorialFunction extends AbstractFunction {

    public FactorialFunction() {
        super("factorial", 1);
    }

    @Override
    public BigDecimal eval(List<BigDecimal> parameters) {
        BigDecimal n = parameters.get(0);
        return factorial(n);
    }


    private BigDecimal factorial(BigDecimal n) {
        if (n.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ONE;
        }
        BigDecimal result = BigDecimal.ONE;
        for (BigDecimal i = BigDecimal.ONE; i.compareTo(n) <= 0; i = i.add(BigDecimal.ONE)) {
            result = result.multiply(i);
        }
        return result;
    }
}
