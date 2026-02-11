package com.lemonlightmc.moreplugins.utils;

import java.util.function.Supplier;

public class Lazy<T> implements Supplier<T> {
  private final Supplier<T> supplier;
  private volatile boolean initialized = false;
  private volatile T value;

  public Lazy(final Supplier<T> supplier) {
    if (supplier == null) {
      throw new IllegalArgumentException("Supplier cant be null");
    }
    this.supplier = supplier;
  }

  @Override
  public T get() {
    T result = value;

    if (!initialized) {
      synchronized (this) {
        if (!initialized) {
          value = result = supplier.get();
          initialized = true;
        }
      }
    }
    return result;
  }

  public boolean isInitialized() {
    return value != null;
  }

  @Override
  public int hashCode() {
    final int result = 31 * supplier.hashCode() + 961 + (initialized ? 1231 : 1237);
    return 31 * result + ((value == null) ? 0 : value.hashCode());
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final Lazy<?> other = (Lazy<?>) obj;
    if (value == null && other.value != null) {
      return false;
    }
    return supplier.equals(other.supplier) && initialized == other.initialized && value.equals(other.value);
  }

  @Override
  public String toString() {
    return "Lazy [supplier=" + supplier + ", value=" + value + "]";
  }
}
