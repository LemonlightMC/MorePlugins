package com.julizey.moreplugins.messages;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public interface IAudience<V> extends Iterable<V>, Comparable<IAudience<V>>, Cloneable {

  public IAudience<V> addViewers(V viewer);

  public IAudience<V> addViewers(V[] viewer);

  public IAudience<V> addViewers(List<V> viewer);

  public IAudience<V> addViewers(Set<V> viewer);

  public IAudience<V> removeViewers(V viewer);

  public IAudience<V> removeViewers(V[] viewer);

  public IAudience<V> removeViewers(List<V> viewer);

  public IAudience<V> removeViewers(Set<V> viewer);

  public boolean hasViewer(V viewer);

  public IAudience<V> clearViewers();

  public Set<V> viewers();

  public IAudience<V> withPermissions(String permission);

  public boolean hasPermissions(String permission);

  public IAudience<V> removePermissions(String permission);

  public IAudience<V> clearPermissions(String permission);

  public IAudience<V> withRequirements(Predicate<V> requirement);

  public boolean hasRequirements(Predicate<V> requirement);

  public IAudience<V> removeRequirements(Predicate<V> requirement);

  public IAudience<V> clearRequirements(Predicate<V> requirement);

  public IAudience<V> filter(Predicate<V> consumer);

  public IAudience<V> difference(IAudience<V> audience);

  public IAudience<V> intersection(IAudience<V> audience);

  public IAudience<V> union(IAudience<V> audience);

  public Set<V> toSet();

  public List<V> toList();

  public V[] toArray();

  public IAudience<V> clone();

  public String toString();

  public int hashCode();

  public boolean equals(Object audience);
}