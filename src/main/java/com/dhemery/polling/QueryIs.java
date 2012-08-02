package com.dhemery.polling;

import com.dhemery.core.Query;
import org.hamcrest.Description;

public class QueryIs<S> implements Query<S, Boolean> {
    private final Query<? super S, Boolean> query;

    public QueryIs(Query<? super S, Boolean> query) {
        this.query = query;
    }

    @Override
    public Boolean query(S object) {
        return query.query(object);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("is ").appendDescriptionOf(query);
    }

    public static <S> Query<S,Boolean> is(Query<? super S, Boolean> query) {
        return new QueryIs<S>(query);
    }
}
