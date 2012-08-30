package com.dhemery.polling;

import com.dhemery.core.Condition;
import com.dhemery.core.Feature;
import com.dhemery.core.Sampler;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;
import org.hamcrest.StringDescription;
import org.hamcrest.internal.SelfDescribingValue;

/**
 * Asserts that a condition is satisfied.
 */
public class ExpressionAssert {
    private ExpressionAssert(){}

    /**
     * Assert that the condition is satisfied.
     * @throws AssertionError if the condition is not satisfied
     */
    public static void assertThat(Condition condition) {
        if(condition.isSatisfied()) return;
        Description description = new StringDescription();
        description.appendText("\nExpected: ")
                    .appendDescriptionOf(selfDescribing(condition));
        throw new AssertionError(description.toString());
    }

    public static <V> void assertThat(Sampler<V> variable, Matcher<? super V> criteria) {
        variable.takeSample();
        if(criteria.matches(variable.sampledValue())) return;
        Description description = new StringDescription();
        description.appendText("\nExpected: ")
                    .appendDescriptionOf(selfDescribing(variable))
                    .appendText(" ")
                    .appendDescriptionOf(criteria)
                    .appendText("\nBut: ");
        criteria.describeMismatch(variable.sampledValue(), description);
        throw new AssertionError(description.toString());
    }

    public static <S, V> void assertThat(S subject, Feature<? super S, V> feature, Matcher<? super V> criteria) {
        V value = feature.of(subject);
        if(criteria.matches(value)) return;
        Description description = new StringDescription();
        description.appendText("\nExpected: ")
                .appendDescriptionOf(selfDescribing(subject))
                .appendText(" ")
                .appendDescriptionOf(selfDescribing(feature))
                .appendText(" ")
                .appendDescriptionOf(criteria)
                .appendText("\nBut: ");
        criteria.describeMismatch(value, description);
        throw new AssertionError(description.toString());
    }

    private static <T> SelfDescribing selfDescribing(T object) {
        if(object instanceof SelfDescribing) return (SelfDescribing) object;
        return new SelfDescribingValue<T>(object);
    }
}
