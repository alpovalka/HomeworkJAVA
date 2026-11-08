package org.Zadanie8.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Range {
  double min() default (Double.MAX_VALUE)*(-1);
  double max() default Double.MAX_VALUE;
  String message() default "number must be within the allowed range";
}
