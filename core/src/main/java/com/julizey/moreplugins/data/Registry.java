package com.julizey.moreplugins.data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class Registry<T extends Registrable> implements Iterable<T>, Cloneable, Comparable<Registry<T>> {

  private static final Pattern ID_PATTERN = Pattern.compile("[a-z0-9_]{1,100}");

  private final Map<String, T> registry;
  private boolean isLocked = false;
  private Object locker = null;

  public Registry() {
    registry = new HashMap<>();
  }

  public Registry(final Map<String, T> map) {
    registry = new HashMap<>(map);
  }

  public static <T extends Registrable> Registry<T> of() {
    return new Registry<>();
  }

  public static <T extends Registrable> Registry<T> of(final Map<String, T> map) {
    return new Registry<>(map);
  }

  public Registry<T> clone() {
    return new Registry<>(this.registry);
  }

  public T register(final T element) {
    if (element == null) {
      throw new IllegalArgumentException("Cannot register null element!");
    }
    final String id = checkID(element.getID());
    if (this.isLocked) {
      throw new IllegalStateException(
          "Cannot add to locked registry! (ID: " + id + ")");
    }

    registry.put(id, element);
    element.onRegister();
    onRegister(element);
    return element;
  }

  public T remove(final T element) {
    if (element == null) {
      throw new IllegalArgumentException("Cannot register null element!");
    }
    final String id = checkID(element.getID());
    if (this.isLocked) {
      throw new IllegalStateException(
          "Cannot remove from locked registry! (ID: " + id + ")");
    }

    element.onRemove();
    onRemove(element);
    registry.remove(id);
    return element;
  }

  public T remove(final String id) {
    checkID(id);
    if (this.isLocked) {
      throw new IllegalStateException(
          "Cannot remove from locked registry! (ID: " + id + ")");
    }

    final T element = registry.get(id);
    if (element != null) {
      return remove(element);
    }
    return element;
  }

  public T get(final String id) {
    return registry.get(id);
  }

  public void clear() {
    for (final T value : registry.values()) {
      remove(value);
    }
  }

  public Set<T> values() {
    return Set.copyOf(registry.values());
  }

  public boolean isLocked() {
    return isLocked;
  }

  public void lock(final Object locker) {
    if (locker == null) {
      throw new IllegalArgumentException("Locker cannot be null!");
    }
    if (this.isLocked && this.locker != locker) {
      throw new IllegalArgumentException(
          "Registry is already locked with a different locker!");
    }

    this.locker = locker;
    isLocked = true;
  }

  public void unlock(final Object locker) {
    if (locker == null) {
      throw new IllegalArgumentException("Locker cannot be null!");
    }
    if (this.locker != locker) {
      throw new IllegalArgumentException("Cannot unlock registry!");
    }

    this.locker = null;
    isLocked = false;
  }

  protected void onRegister(final T element) {
    // Override this method to do something when an element is registered.
  }

  protected void onRemove(final T element) {
    // Override this method to do something when an element is removed.
  }

  public boolean isEmpty() {
    return registry.isEmpty();
  }

  public boolean isNotEmpty() {
    return !registry.isEmpty();
  }

  @Override
  public Iterator<T> iterator() {
    return Set.copyOf(registry.values()).iterator();
  }

  @Override
  public int hashCode() {
    int result = 31 + ((registry == null) ? 0 : registry.hashCode());
    result = 31 * result + (isLocked ? 1231 : 1237);
    return 31 * result + ((locker == null) ? 0 : locker.hashCode());
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final Registry<?> other = (Registry<?>) obj;
    if (registry == null && other.registry != null || locker == null && other.locker != null) {
      return false;
    }
    return isLocked != other.isLocked && registry.equals(other.registry) && locker.equals(other.locker);
  }

  @Override
  public String toString() {
    return "Registry [registry=" + registry + ", isLocked=" + isLocked + ", locker=" + locker + "]";
  }

  private String checkID(final String id) {
    if (id == null || id.length() == 0 || !ID_PATTERN.matcher(id).matches()) {
      throw new IllegalArgumentException(
          "Invalid ID: " +
              id +
              " (Must match pattern: " +
              ID_PATTERN.pattern() +
              ")");
    }
    return id;
  }

  @Override
  public int compareTo(final Registry<T> o) {
    return Integer.compare(this.registry.size(), o.registry.size());
  }
}
