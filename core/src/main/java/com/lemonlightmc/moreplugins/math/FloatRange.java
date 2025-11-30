package com.lemonlightmc.moreplugins.math;

import com.lemonlightmc.moreplugins.wrapper.Cloneable;

public class FloatRange implements Cloneable<FloatRange>, Comparable<FloatRange> {

  private final float low;
  private final float high;

  public FloatRange(final float low, final float high) {
    this.low = low;
    this.high = high;
  }

  public FloatRange(final float high) {
    this.high = high;
    this.low = Integer.MAX_VALUE;
  }

  public FloatRange() {
    this.high = Integer.MIN_VALUE;
    this.low = Integer.MAX_VALUE;
  }

  public static FloatRange at(final int pos) {
    return new FloatRange(pos, pos);
  }

  public static FloatRange between(final int low, final int high) {
    return new FloatRange(low, high);
  }

  public static FloatRange encompassing(final FloatRange a, final FloatRange b) {
    return new FloatRange(Math.min(a.getLowerBound(), b.getLowerBound()),
        Math.max(a.getUpperBound(), b.getUpperBound()));
  }

  public static FloatRange rangeGreaterThanOrEq(final float min) {
    return new FloatRange(min, Integer.MAX_VALUE);
  }

  public static FloatRange rangeLessThanOrEq(final float max) {
    return new FloatRange(Integer.MIN_VALUE, max);
  }

  public float getLowerBound() {
    return this.low;
  }

  public float getUpperBound() {
    return this.high;
  }

  public boolean isLower(final float i) {
    return i < low;
  }

  public boolean isHigher(final float i) {
    return i > high;
  }

  public boolean isInRange(final float i) {
    return i >= low && i <= high;
  }

  public boolean isOutsideRange(final float i) {
    return i < low && i > high;
  }

  public boolean isEmpty() {
    return low == high;
  }

  public float getLength() {
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
    int result = 31 + Float.floatToIntBits(low);
    result = 31 * result + Float.floatToIntBits(high);
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
    final FloatRange other = (FloatRange) obj;
    return Float.floatToIntBits(low) == Float.floatToIntBits(other.low)
        && Float.floatToIntBits(high) == Float.floatToIntBits(other.high);
  }

  @Override
  public int compareTo(final FloatRange o) {
    if (o == null) {
      return 1;
    }
    return Float.compare(this.high - this.low, o.high - o.low);
  }

  public FloatRange clone() {
    return new FloatRange(low, high);
  }
}
