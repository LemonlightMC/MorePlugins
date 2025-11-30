package com.lemonlightmc.moreplugins.math;

import com.lemonlightmc.moreplugins.wrapper.Cloneable;

public class DoubleRange implements Cloneable<DoubleRange>, Comparable<DoubleRange> {

  private final double low;
  private final double high;

  public DoubleRange(final double low, final double high) {
    this.low = low;
    this.high = high;
  }

  public DoubleRange(final double high) {
    this.high = high;
    this.low = Double.MAX_VALUE;
  }

  public DoubleRange() {
    this.high = Double.MIN_VALUE;
    this.low = Double.MAX_VALUE;
  }

  public static DoubleRange rangeGreaterThanOrEq(final double min) {
    return new DoubleRange(min, Double.MAX_VALUE);
  }

  public static DoubleRange rangeLessThanOrEq(final double max) {
    return new DoubleRange(Double.MIN_VALUE, max);
  }

  public double getLowerBound() {
    return this.low;
  }

  public double getUpperBound() {
    return this.high;
  }

  public boolean isLower(final double i) {
    return i < low;
  }

  public boolean isHigher(final double i) {
    return i > high;
  }

  public boolean isInRange(final double i) {
    return i >= low && i <= high;
  }

  public boolean isOutsideRange(final double i) {
    return i < low && i > high;
  }

  @Override
  public String toString() {
    if (this.high == Double.MAX_VALUE) {
      return this.low + "..";
    } else if (this.low == Double.MIN_VALUE) {
      return ".." + this.high;
    } else {
      return this.low + ".." + this.high;
    }
  }

  @Override
  public int hashCode() {
    return 31 * (31 * Double.hashCode(low)) + Double.hashCode(high);
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final DoubleRange other = (DoubleRange) obj;
    return Double.doubleToLongBits(low) == Double.doubleToLongBits(other.low)
        && Double.doubleToLongBits(high) == Double.doubleToLongBits(other.high);
  }

  @Override
  public int compareTo(final DoubleRange o) {
    if (o == null) {
      return 1;
    }
    return Double.compare(this.high - this.low, o.high - o.low);
  }

  public DoubleRange clone() {
    return new DoubleRange(low, high);
  }
}
