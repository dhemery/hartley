package com.dhemery.polling;

import com.dhemery.core.Query;
import org.hamcrest.Description;

public class QueryNot<S> implements Query<S, Boolean> {
    private final Query<? super S, Boolean> query;

    public QueryNot(Query<? super S, Boolean> query) {
        this.query = query;
    }

    @Override
    public Boolean query(S object) {
        return !query.query(object);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("not ").appendDescriptionOf(query);
    }


    public static <S> Query<S,Boolean> not(Query<? super S, Boolean> query) {
        return new QueryNot<S>(query);
    }
}
