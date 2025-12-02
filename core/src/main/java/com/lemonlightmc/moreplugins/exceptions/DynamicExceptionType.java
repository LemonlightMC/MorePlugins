package com.lemonlightmc.moreplugins.exceptions;

public class DynamicExceptionType<T, F extends DynamicExceptionFunction, C> extends ExceptionType<T> {
  private final F function;

  public DynamicExceptionType(final Class<T> cls, final F function) {
    super(cls);
    this.function = function;
  }

  public static <T, F extends DynamicExceptionFunction, C> DynamicExceptionType<T, F, C> from(final Class<T> cls,
      final F function) {
    return new DynamicExceptionType<T, F, C>(cls, function);
  }

  public static <T, F extends DynamicExceptionFunction, C> DynamicExceptionType<T, F, C> from(final Class<T> cls) {
    return new DynamicExceptionType<T, F, C>(cls, null);
  }

  public T create(final Object a, final Object... args) {
    return create(this, function.apply(args));
  }

  public T createWithContext(final C ctx, final Object... args) {
    return create(this, function.apply(args), ctx);
  }

}
