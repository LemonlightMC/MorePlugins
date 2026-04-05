package com.lemonlightmc.moreplugins.utils;

import java.util.Optional;
import java.util.function.Supplier;

public class FastLazy<T> implements Supplier<T> {

  private Supplier<T> supplier;
  private T value;

  public FastLazy(final Supplier<T> supplier) {
    if (supplier == null) {
      throw new IllegalArgumentException("Supplier cant be null");
    }
    this.supplier = supplier;
  }

  public static <E> Lazy<E> from(final Supplier<E> supplier) {
    return new Lazy<E>(supplier);
  }

  @Override
  public T get() {
    if (supplier != null) {
      this.value = supplier.get();
      supplier = null;
    }
    return this.value;
  }

  public Optional<T> asOptional() {
    return Optional.ofNullable(this.value);
  }

  public boolean isInitialized() {
    return supplier == null;
  }

  public boolean isPresent() {
    return value != null;
  }

  public boolean isEmptry() {
    return value == null;
  }

  @Override
  public int hashCode() {
    int result = 31 + ((supplier == null) ? 0 : supplier.hashCode());
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
    final FastLazy<?> other = (FastLazy<?>) obj;
    if (value == null && other.value != null || supplier == null && other.supplier != null) {
      return false;
    }
    return value.equals(other.value) && supplier.equals(other.supplier);
  }

  @Override
  public String toString() {
    return "FastLazy [supplier=" + supplier + ", value=" + value + "]";
  }
}
