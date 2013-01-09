package com.dhemery.core;

import com.dhemery.factory.FactoryAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@FactoryAnnotation("com.dhemery.expressing.Expressions")
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface Expression {
}
