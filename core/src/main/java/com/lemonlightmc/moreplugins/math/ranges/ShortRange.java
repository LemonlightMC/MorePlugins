package com.lemonlightmc.moreplugins.math.ranges;

import java.util.Comparator;

import com.lemonlightmc.moreplugins.exceptions.RangeException;

public class ShortRange implements Range<ShortRange, Short> {

  private final Short min;
  private final Short max;
  private static final Comparator<ShortRange> comparator = new Comparator<ShortRange>() {
    @Override
    public int compare(final ShortRange o1, final ShortRange o2) {
      if (o1 == null) {
        return -1;
      }
      if (o2 == null) {
        return 1;
      }
      return Integer.compare(o1.max - o2.min, o2.max - o2.min);
    }
  };;

  public ShortRange() {
    this(Short.MIN_VALUE, Short.MAX_VALUE);
  }

  public ShortRange(final Short min) {
    this(min, Short.MAX_VALUE);
  }

  public ShortRange(final Short min, final Short max) {
    this.min = min == null ? Short.MIN_VALUE : min;
    this.max = max == null ? Short.MAX_VALUE : max;
    if (Math.signum(this.max - this.min) == -1.0d) {
      throw new RangeException("Short Argument Maximum is smaller Minimum: " + this.min + " - " + this.max);
    }
  }

  public static ShortRange at(final Short pos) {
    return new ShortRange(pos, pos);
  }

  public static ShortRange of(final Short min, final Short max) {
    return new ShortRange(min, max);
  }

  public static ShortRange of(final ShortRange range) {
    if (range == null) {
      throw new IllegalArgumentException("Range cant be null");
    }
    return new ShortRange(range.min, range.max);
  }

  public static ShortRange encompassing(final ShortRange a, final ShortRange b) {
    if (a == null || b == null) {
      throw new IllegalArgumentException("Range cant be null");
    }
    return new ShortRange((a.getMin() <= b.getMin()) ? a.getMin() : b.getMin(),
        (a.getMax() >= b.getMax()) ? a.getMax() : b.getMax());
  }

  public static ShortRange rangeGreaterThanOrEq(final Short min) {
    return new ShortRange(min, Short.MAX_VALUE);
  }

  public static ShortRange rangeLessThanOrEq(final Short max) {
    return new ShortRange(Short.MIN_VALUE, max);
  }

  @Override
  public Short getMin() {
    return this.min;
  }

  @Override
  public Short getMiddle() {
    throw new UnsupportedOperationException("Short cant be converted correctly!");
  }

  @Override
  public Short getMax() {
    return this.max;
  }

  @Override
  public boolean isLower(final Short num) {
    return num == null ? true : num < min;
  }

  @Override
  public boolean isHigher(final Short num) {
    return num == null ? false : num > max;
  }

  @Override
  public boolean isInRange(final Short num) {
    return num == null ? false : num >= min && num <= max;
  }

  @Override
  public boolean isOutsideRange(final Short num) {
    return num == null ? true : num < min && num > max;
  }

  @Override
  public boolean isAfter(final Short num) {
    return min > num;
  }

  @Override
  public boolean isAfter(final ShortRange range) {
    return range == null ? true : min > range.max;
  }

  @Override
  public boolean isBefore(final Short num) {
    return max < num;
  }

  @Override
  public boolean isBefore(final ShortRange range) {
    return max < range.min;
  }

  @Override
  public boolean startsWith(final Short num) {
    return num == null ? false : min == num;
  }

  @Override
  public boolean startsWith(final ShortRange range) {
    return range == null ? false : min == range.max;
  }

  @Override
  public boolean endsWith(final Short num) {
    return num == null ? false : max == num;
  }

  @Override
  public boolean endsWith(final ShortRange range) {
    return range == null ? false : max == range.min;
  }

  @Override
  public boolean isEmpty() {
    return min == max;
  }

  @Override
  public boolean contains(final Short num) {
    return num == null ? false : isInRange(num);
  }

  @Override
  public boolean contains(final ShortRange range) {
    return range == null ? false : isInRange(range.min) && isInRange(range.max);
  }

  @Override
  public boolean overlaps(final ShortRange range) {
    return range == null ? false : isInRange(range.min) || isInRange(range.max);
  }

  @Override
  public ShortRange intersection(final ShortRange range) {
    if (range == null || !overlaps(range)) {
      return null;
    }
    return new ShortRange(min < range.min ? range.min : min, max < range.max ? range.max : max);
  }

  @Override
  public Short clamp(final Short num) {
    return num == null ? min : num < min ? min : num > max ? max : num;
  }

  @Override
  public ShortRange clamp(final ShortRange range) {
    return range != null && contains(range) ? range : new ShortRange();
  }

  @Override
  public Short getLength() {
    throw new UnsupportedOperationException("Short cant be converted correctly!");
  }

  @Override
  public boolean isMaxValue() {
    return max == Short.MAX_VALUE;
  }

  @Override
  public boolean isMinValue() {
    return max == Short.MIN_VALUE;
  }

  @Override
  public int compareTo(final ShortRange o) {
    if (o == null) {
      return 1;
    }
    return Integer.compare(this.max - this.min, o.max - o.min);
  }

  @Override
  public Comparator<ShortRange> getComparator() {
    return ShortRange.comparator;
  }

  @Override
  public ShortRange clone() {
    return new ShortRange(min, max);
  }

  @Override
  public String toString() {
    if (this.min == Short.MIN_VALUE && this.max == Short.MAX_VALUE) {
      return "";
    } else if (this.max == Short.MAX_VALUE) {
      return this.min + "..";
    } else if (this.min == Short.MIN_VALUE) {
      return ".." + this.max;
    } else {
      return this.min + ".." + this.max;
    }
  }

  @Override
  public int hashCode() {
    return 31 * (31 * min) + max;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final ShortRange other = (ShortRange) obj;
    if (min == null && other.min != null || max == null && other.max != null) {
      return false;
    }
    return min.equals(other.min) && max.equals(other.max);
  }
}
