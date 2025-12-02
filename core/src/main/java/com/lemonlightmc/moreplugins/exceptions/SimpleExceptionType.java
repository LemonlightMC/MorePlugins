package com.lemonlightmc.moreplugins.exceptions;

public class SimpleExceptionType<T, C> extends ExceptionType<T> {
  private final String message;

  public SimpleExceptionType(final Class<T> cls, final String message) {
    super(cls);
    this.message = message;
  }

  public static <T, C> SimpleExceptionType<T, C> from(final Class<T> cls, final String message) {
    return new SimpleExceptionType<T, C>(cls, message);
  }

  public static <T, C> SimpleExceptionType<T, C> from(final Class<T> cls) {
    return new SimpleExceptionType<T, C>(cls, null);
  }

  public T create() {
    return create(this, message);
  }

  public T createWithContext(final C ctx) {
    return create(this, message, ctx);
  }

  @Override
  public String toString() {
    return message;
  }
}
