package com.lemonlightmc.moreplugins.math;

import com.lemonlightmc.moreplugins.wrapper.Cloneable;

public class LongRange implements Cloneable<LongRange>, Comparable<LongRange> {

  private final long low;
  private final long high;

  public LongRange(final long low, final long high) {
    this.low = low;
    this.high = high;
  }

  public LongRange(final long high) {
    this.high = high;
    this.low = Integer.MAX_VALUE;
  }

  public LongRange() {
    this.high = Integer.MIN_VALUE;
    this.low = Integer.MAX_VALUE;
  }

  public static LongRange at(final int pos) {
    return new LongRange(pos, pos);
  }

  public static LongRange between(final int low, final int high) {
    return new LongRange(low, high);
  }

  public static LongRange encompassing(final LongRange a, final LongRange b) {
    return new LongRange(Math.min(a.getLowerBound(), b.getLowerBound()),
        Math.max(a.getUpperBound(), b.getUpperBound()));
  }

  public static LongRange rangeGreaterThanOrEq(final long min) {
    return new LongRange(min, Integer.MAX_VALUE);
  }

  public static LongRange rangeLessThanOrEq(final long max) {
    return new LongRange(Integer.MIN_VALUE, max);
  }

  public long getLowerBound() {
    return this.low;
  }

  public long getUpperBound() {
    return this.high;
  }

  public boolean isLower(final long i) {
    return i < low;
  }

  public boolean isHigher(final long i) {
    return i > high;
  }

  public boolean isInRange(final long i) {
    return i >= low && i <= high;
  }

  public boolean isOutsideRange(final long i) {
    return i < low && i > high;
  }

  public boolean isEmpty() {
    return low == high;
  }

  public long getLength() {
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
    int result = 31 + (int) (low ^ (low >>> 32));
    result = 31 * result + (int) (high ^ (high >>> 32));
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final LongRange other = (LongRange) obj;
    return low == other.low && high == other.high;
  }

  @Override
  public int compareTo(final LongRange o) {
    if (o == null) {
      return 1;
    }
    return Long.compare(this.high - this.low, o.high - o.low);
  }

  public LongRange clone() {
    return new LongRange(low, high);
  }
}
