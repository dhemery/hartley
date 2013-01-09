package com.dhemery.core;

import com.dhemery.generator.Generate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
@Generate(
        className="com.dhemery.expressing.Expressions",
        description="Factory methods for operators, features, and other expression items.")
public @interface Expression {
}
