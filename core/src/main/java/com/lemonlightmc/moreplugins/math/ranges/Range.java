package com.lemonlightmc.moreplugins.math.ranges;

import java.util.Comparator;

import com.lemonlightmc.moreplugins.wrapper.Cloneable;

public interface Range<T extends Range<T, V>, V extends Number> extends Cloneable<T>, Comparable<T> {

  V getMin();

  V getMiddle();

  V getMax();

  boolean isLower(V i);

  boolean isHigher(V i);

  boolean isInRange(V i);

  boolean isOutsideRange(V i);

  boolean isAfter(V num);

  boolean isAfter(T range);

  boolean isBefore(V num);

  boolean isBefore(T range);

  boolean startsWith(V num);

  boolean startsWith(T range);

  boolean endsWith(V num);

  boolean endsWith(T range);

  boolean isEmpty();

  boolean contains(V num);

  boolean contains(T range);

  boolean overlaps(T range);

  T intersection(T range);

  V clamp(V num);

  T clamp(T range);

  V getLength();

  boolean isMaxValue();

  boolean isMinValue();

  int compareTo(T o);

  Comparator<T> getComparator();

  T clone();

  String toString();

  int hashCode();

  boolean equals(Object obj);

}