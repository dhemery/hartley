package com.dhemery.core;

import org.hamcrest.SelfDescribing;

import java.util.function.Function;

/**
 * A {@link SelfDescribing SelfDescribing} {@link Function Function}.
 * <p>
 * This is an obsolete interface,
 * designed before Java 8 introduced functional interfaces.
 * For new self-describing functions,
 * implement both {@code FunctionFunction}
 * and {@code SelfDescribing SelfDescribing}.
 * If you do not need the function to describe itself,
 * prefer {@link Function Function}.
 * </p>
 */
public interface Feature<T, R>  extends Function<T,R>, SelfDescribing{
	/**
     * Return the subject's current value for the feature.
	 */
	R of(T t);

	/**
	 * Delegates to {@link #of(T)}.
	 * <p>
	 * NOTE: Do not override this method.
	 * If you need this method to do something else,
	 * implement {@link Function} instead.
	 */
	default R apply(T t) { return of(t); }
}
