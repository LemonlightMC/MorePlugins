package com.lemonlightmc.moreplugins.interfaces;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An element annotated with NotNull claims {@code null} value is
 * <em>forbidden</em>
 * to return (for methods), pass to (parameters) and hold (local variables and
 * fields).
 * 
 * The annotated element must not be null.
 * <p>
 * Annotated fields must not be null after construction has completed.
 * <p>
 * When this annotation is applied to a method it applies to the method return
 * value.
 */

@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER,
    ElementType.LOCAL_VARIABLE, ElementType.TYPE_USE })

public @interface NotNull {
  String value() default "";

  Class<? extends Exception> exception() default Exception.class;

  Class<?> applicableTo() default Object.class;
}