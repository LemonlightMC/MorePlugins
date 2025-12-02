package com.lemonlightmc.moreplugins.exceptions;

@FunctionalInterface
public interface DynamicExceptionFunction {

  public String apply(Object[] args);

  @FunctionalInterface
  public static interface DynamicExceptionFunktion extends DynamicExceptionFunction {
    String apply();

    default String apply(Object[] args) {
      return apply();
    }
  }

  @FunctionalInterface
  public static interface Dynamic1ExceptionFunktion extends DynamicExceptionFunction {
    String apply(Object a);

    default String apply(Object[] args) {
      return apply(args[0]);
    }
  }

  @FunctionalInterface
  public static interface Dynamic2ExceptionFunktion extends DynamicExceptionFunction {
    String apply(Object a, Object b);

    default String apply(Object[] args) {
      return apply(args[0], args[1]);
    }
  }

  @FunctionalInterface
  public static interface Dynamic3ExceptionFunktion extends DynamicExceptionFunction {
    String apply(Object a, Object b, Object c);

    default String apply(Object[] args) {
      return apply(args[0], args[1], args[2]);
    }
  }
}
