package com.dhemery.expressions;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import java.util.Optional;
import java.util.function.Function;

public class FunctionOfSubjectSatisfiesMatcher<T, R> extends Descriptive implements Condition {
    private final T subject;
    private final Function<? super T, R> function;
    private final Matcher<? super R> matcher;
    private R mostRecentResult;

    public FunctionOfSubjectSatisfiesMatcher(T subject, Function<? super T, R> function, Matcher<? super R> matcher) {
        super(describedAs(subject, function, matcher));
        this.subject = subject;
        this.function = function;
        this.matcher = matcher;
    }

    @Override
    public boolean isSatisfied() {
        mostRecentResult = function.apply(subject);
        return matcher.matches(mostRecentResult);
    }

    @Override
    public Optional<String> diagnosis() {
        Description mismatchDescription = new StringDescription();
        matcher.describeMismatch(mostRecentResult, mismatchDescription);
        return Optional.of(mismatchDescription.toString());
    }
}
