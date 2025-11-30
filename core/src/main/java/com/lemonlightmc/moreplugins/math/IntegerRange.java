package com.lemonlightmc.moreplugins.math;

import com.lemonlightmc.moreplugins.wrapper.Cloneable;

public class IntegerRange implements Cloneable<IntegerRange>, Comparable<IntegerRange> {

  private final int low;
  private final int high;

  public IntegerRange(final int low, final int high) {
    this.low = low;
    this.high = high;
  }

  public IntegerRange(final int high) {
    this.high = high;
    this.low = Integer.MAX_VALUE;
  }

  public IntegerRange() {
    this.high = Integer.MIN_VALUE;
    this.low = Integer.MAX_VALUE;
  }

  public static IntegerRange at(final int pos) {
    return new IntegerRange(pos, pos);
  }

  public static IntegerRange between(final int low, final int high) {
    return new IntegerRange(low, high);
  }

  public static IntegerRange encompassing(final IntegerRange a, final IntegerRange b) {
    return new IntegerRange(Math.min(a.getLowerBound(), b.getLowerBound()),
        Math.max(a.getUpperBound(), b.getUpperBound()));
  }

  public static IntegerRange rangeGreaterThanOrEq(final int min) {
    return new IntegerRange(min, Integer.MAX_VALUE);
  }

  public static IntegerRange rangeLessThanOrEq(final int max) {
    return new IntegerRange(Integer.MIN_VALUE, max);
  }

  public int getLowerBound() {
    return this.low;
  }

  public int getUpperBound() {
    return this.high;
  }

  public boolean isLower(final int i) {
    return i < low;
  }

  public boolean isHigher(final int i) {
    return i > high;
  }

  public boolean isInRange(final int i) {
    return i >= low && i <= high;
  }

  public boolean isOutsideRange(final int i) {
    return i < low && i > high;
  }

  public boolean isEmpty() {
    return low == high;
  }

  public int getLength() {
    return high - low;
  }

  @Override
  public String toString() {
    if (this.high == Integer.MAX_VALUE) {
      return this.low + "..";
    } else if (this.low == Integer.MIN_VALUE) {
      return ".." + this.high;
    } else {
      return this.low + ".." + this.high;
    }
  }

  @Override
  public int hashCode() {
    return 31 * (31 + low) + high;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final IntegerRange other = (IntegerRange) obj;
    return low == other.low && high == other.high;
  }

  @Override
  public int compareTo(IntegerRange o) {
    if (o == null) {
      return 1;
    }
    return Integer.compare(this.high - this.low, o.high - o.low);
  }

  public IntegerRange clone() {
    return new IntegerRange(low, high);
  }
}
