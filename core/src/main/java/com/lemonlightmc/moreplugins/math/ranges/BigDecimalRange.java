package com.lemonlightmc.moreplugins.math.ranges;

import java.math.BigDecimal;
import java.util.Comparator;

import com.lemonlightmc.moreplugins.exceptions.RangeException;

public class BigDecimalRange implements Range<BigDecimalRange, BigDecimal> {

  private final BigDecimal min;
  private final BigDecimal max;
  public static final BigDecimal MIN_VALUE = BigDecimal.valueOf(Long.MIN_VALUE);
  public static final BigDecimal MAX_VALUE = BigDecimal.valueOf(Long.MAX_VALUE);

  private static final Comparator<BigDecimalRange> comparator = new Comparator<BigDecimalRange>() {
    @Override
    public int compare(final BigDecimalRange o1, final BigDecimalRange o2) {
      if (o1 == null) {
        return -1;
      }
      if (o2 == null) {
        return 1;
      }
      return o1.max.subtract(o2.min).compareTo(o2.max.subtract(o2.min));
    }
  };;

  public BigDecimalRange() {
    this(MIN_VALUE, MAX_VALUE);
  }

  public BigDecimalRange(final BigDecimal min) {
    this(min, MAX_VALUE);
  }

  public BigDecimalRange(final BigDecimal min, final BigDecimal max) {
    this.min = min == null ? MIN_VALUE : min;
    this.max = max == null ? MAX_VALUE : max;
    if (this.max.subtract(this.min).signum() == -1) {
      throw new RangeException("BigDecimal Argument Maximum is smaller Minimum: " + this.min + " - " + this.max);
    }
  }

  public static BigDecimalRange at(final BigDecimal pos) {
    return new BigDecimalRange(pos, pos);
  }

  public static BigDecimalRange of(final BigDecimal min, final BigDecimal max) {
    return new BigDecimalRange(min, max);
  }

  public static BigDecimalRange of(final BigDecimalRange range) {
    if (range == null) {
      throw new IllegalArgumentException("Range cant be null");
    }
    return new BigDecimalRange(range.min, range.max);
  }

  public static BigDecimalRange encompassing(final BigDecimalRange a, final BigDecimalRange b) {
    if (a == null || b == null) {
      throw new IllegalArgumentException("Range cant be null");
    }
    return new BigDecimalRange(a.getMin().compareTo(b.getMin()) < 0 ? a.getMin() : b.getMin(),
        a.getMax().compareTo(b.getMax()) > 0 ? a.getMax() : b.getMax());
  }

  public static BigDecimalRange rangeGreaterThanOrEq(final BigDecimal min) {
    return new BigDecimalRange(min, MAX_VALUE);
  }

  public static BigDecimalRange rangeLessThanOrEq(final BigDecimal max) {
    return new BigDecimalRange(MIN_VALUE, max);
  }

  @Override
  public BigDecimal getMin() {
    return this.min;
  }

  @Override
  public BigDecimal getMiddle() {
    return this.max.subtract(this.min).abs().divide(BigDecimal.valueOf(2));
  }

  @Override
  public BigDecimal getMax() {
    return this.max;
  }

  @Override
  public boolean isLower(final BigDecimal num) {
    return num == null ? true : num.compareTo(min) < 0;
  }

  @Override
  public boolean isHigher(final BigDecimal num) {
    return num == null ? false : num.compareTo(max) > 0;
  }

  @Override
  public boolean isInRange(final BigDecimal num) {
    return num == null ? false : num.compareTo(min) >= 0 && num.compareTo(max) <= 0;
  }

  @Override
  public boolean isOutsideRange(final BigDecimal num) {
    return num == null ? true : num.compareTo(min) < 0 || num.compareTo(max) > 0;
  }

  @Override
  public boolean isAfter(final BigDecimal num) {
    return min.compareTo(num) > 0;
  }

  @Override
  public boolean isAfter(final BigDecimalRange range) {
    return range == null ? true : min.compareTo(range.max) > 0;
  }

  @Override
  public boolean isBefore(final BigDecimal num) {
    return max.compareTo(num) < 0;
  }

  @Override
  public boolean isBefore(final BigDecimalRange range) {
    return max.compareTo(range.min) < 0;
  }

  @Override
  public boolean startsWith(final BigDecimal num) {
    return num == null ? false : min.equals(num);
  }

  @Override
  public boolean startsWith(final BigDecimalRange range) {
    return range == null ? false : min.equals(range.max);
  }

  @Override
  public boolean endsWith(final BigDecimal num) {
    return num == null ? false : max.equals(num);
  }

  @Override
  public boolean endsWith(final BigDecimalRange range) {
    return range == null ? false : max.equals(range.min);
  }

  @Override
  public boolean isEmpty() {
    return min.equals(max);
  }

  @Override
  public boolean contains(final BigDecimal num) {
    return num == null ? false : isInRange(num);
  }

  @Override
  public boolean contains(final BigDecimalRange range) {
    return range == null ? false : isInRange(range.min) && isInRange(range.max);
  }

  @Override
  public boolean overlaps(final BigDecimalRange range) {
    return range == null ? false : isInRange(range.min) || isInRange(range.max);
  }

  @Override
  public BigDecimalRange intersection(final BigDecimalRange range) {
    if (range == null || !overlaps(range)) {
      return null;
    }
    return new BigDecimalRange(min.compareTo(range.min) < 0 ? range.min : min,
        max.compareTo(range.max) < 0 ? max : range.max);
  }

  @Override
  public BigDecimal clamp(final BigDecimal num) {
    return num == null ? min : num.compareTo(min) < 0 ? min : num.compareTo(max) > 0 ? max : num;
  }

  @Override
  public BigDecimalRange clamp(final BigDecimalRange range) {
    return range != null && contains(range) ? range : new BigDecimalRange();
  }

  @Override
  public BigDecimal getLength() {
    return max.subtract(min);
  }

  @Override
  public boolean isMaxValue() {
    return max == MAX_VALUE;
  }

  @Override
  public boolean isMinValue() {
    return max == MIN_VALUE;
  }

  @Override
  public int compareTo(final BigDecimalRange o) {
    if (o == null) {
      return 1;
    }
    return this.max.subtract(this.min).compareTo(o.max.subtract(o.min));
  }

  @Override
  public Comparator<BigDecimalRange> getComparator() {
    return BigDecimalRange.comparator;
  }

  @Override
  public BigDecimalRange clone() {
    return new BigDecimalRange(min, max);
  }

  @Override
  public String toString() {
    if (this.min == MIN_VALUE && this.max == MAX_VALUE) {
      return "";
    } else if (this.max == MAX_VALUE) {
      return this.min + "..";
    } else if (this.min == MIN_VALUE) {
      return ".." + this.max;
    } else {
      return this.min + ".." + this.max;
    }
  }

  @Override
  public int hashCode() {
    return 31 * (31 * min.hashCode()) + max.hashCode();
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final BigDecimalRange other = (BigDecimalRange) obj;
    if (min == null && other.min != null || max == null && other.max != null) {
      return false;
    }
    return min.equals(other.min) && max.equals(other.max);
  }
}
