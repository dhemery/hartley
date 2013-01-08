package com.dhemery.expressing;

import com.dhemery.core.DefaultingTo;
import com.dhemery.core.Requiring;
import com.dhemery.core.UnaryOperator;
import com.dhemery.strings.Trimmed;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.allOf;

/**
 * Static utility methods to create operators.
 */
public class OperatorExpressions {
    /**
     * An operator that returns its a default value if its operand is {@code null}.
     * @param defaultValue the value to return if the operand is {@code null}
     * @return the operand if it is non-null, otherwise the default value.
     */
    public static <T> UnaryOperator<T> defaultingTo(T defaultValue) {
        return new DefaultingTo<T>(defaultValue);
    }

    /**
     * An operator that returns its operand if the operand satisfies the criteria.
     * Otherwise the operator returns {@code null}.
     * @param criteria the criteria that the operand is to satisfy
     * @return the operand if it satisfies the criteria, otherwise {@code null}
     */
    public static <T> UnaryOperator<T> requiring(Matcher<T>... criteria) {
        return new Requiring<T>(allOf(criteria));
    }

    /**
     * An operator that trims its string operand.
     */
    public static UnaryOperator<String> trimmed() {
        return new Trimmed();
    }
}
