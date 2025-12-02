package com.lemonlightmc.moreplugins.exceptions;

public abstract class ExceptionType<T> {
  private final Class<T> cls;

  public ExceptionType(final Class<T> cls) {
    this.cls = cls;
  }

  public Class<T> getCls() {
    return cls;
  }

  protected T create(Object... args) {
    try {
      return (T) cls.getDeclaredConstructor().newInstance(args);
    } catch (Exception e) {
      throw new Error("Failed to create Exception " + e.getClass().getName(), e);
    }
  }
}
