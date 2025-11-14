package com.lemonlightmc.moreplugins.wrapper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.lemonlightmc.moreplugins.time.PolyTimeUnit;
import com.lemonlightmc.moreplugins.utils.ICooldownHandler;

public class CooldownHandler<K> implements ICooldownHandler<K> {

  protected final HashMap<K, Long> cooldown = new HashMap<>();
  protected long defaultDuration;

  public CooldownHandler() {
    this(1000L);
  }

  public CooldownHandler(final long defaultDuration) {
    this.defaultDuration = defaultDuration;
  }

  public static <K> CooldownHandler<K> of() {
    return new CooldownHandler<>();
  }

  public static <K> CooldownHandler<K> of(final long defaultDuration, final PolyTimeUnit unit) {
    return new CooldownHandler<>();
  }

  @Override
  public void setDefaultDuration(final long defaultDuration) {
    this.defaultDuration = defaultDuration;
  }

  @Override
  public long getDefaultDuration() {
    return this.defaultDuration;
  }

  @Override
  public void clear(final K key) {
    this.cooldown.remove(key);
  }

  @Override
  public void clearAll() {
    this.cooldown.clear();
  }

  @Override
  public boolean hasCooldown(final K key) {
    return this.cooldown.containsKey(key);
  }

  @Override
  public void setCooldown(final K key, final long time) {
    this.cooldown.put(key, System.currentTimeMillis() + time);
  }

  @Override
  public void setCooldown(final K key) {
    this.cooldown.put(key, System.currentTimeMillis() + defaultDuration);
  }

  @Override
  public void addCooldown(final K key, final long time) {
    this.cooldown.put(
        key,
        System.currentTimeMillis() + getRemaining(key) + defaultDuration);
  }

  @Override
  public void addCooldown(final K key) {
    this.cooldown.put(
        key,
        System.currentTimeMillis() + getRemaining(key) + defaultDuration);
  }

  @Override
  public void removeCooldown(final K key) {
    this.cooldown.remove(key);
  }

  @Override
  public void clearCooldowns() {
    this.cooldown.clear();
  }

  @Override
  public HashMap<K, Long> getCooldowns() {
    return this.cooldown;
  }

  @Override
  public Set<K> getCooldownKeys() {
    return this.cooldown.keySet();
  }

  @Override
  public boolean isOnCooldown(final K key) {
    return getRemaining(key) > 0;
  }

  @Override
  public boolean isOnCooldownStart(final K key, final long duration) {
    final boolean onCooldown = getRemaining(key) > 0;
    if (!onCooldown) {
      setCooldown(key);
    }
    return onCooldown;
  }

  @Override
  public boolean isOnCooldownStop(final K key) {
    final boolean onCooldown = getRemaining(key) > 0;
    if (onCooldown) {
      this.cooldown.remove(key);
    }
    return onCooldown;
  }

  @Override
  public long getRemaining(final K key) {
    final Long time = this.cooldown.get(key);
    if (time == null || time < 0)
      return 0;
    return Math.max(time - System.currentTimeMillis(), 0);
  }

  @Override
  public long getElapsed(final K key) {
    final Long time = this.cooldown.get(key);
    if (time == null || time < 0)
      return 0;
    return Math.max(System.currentTimeMillis() - time, 0);
  }

  public CooldownHandler<K> clone() {
    final CooldownHandler<K> clone = new CooldownHandler<>(this.defaultDuration);
    clone.cooldown.putAll(this.cooldown);
    return clone;
  }

  public Iterator<K> iterator() {
    return this.cooldown.keySet().iterator();
  }

  @Override
  public int hashCode() {
    int result = 31 + cooldown.hashCode();
    result = 31 * result + (int) (defaultDuration ^ (defaultDuration >>> 32));
    return result;
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final CooldownHandler<K> other = (CooldownHandler<K>) obj;
    if (cooldown == null && other.cooldown != null) {
      return false;
    }
    return cooldown.equals(other.cooldown) && defaultDuration == other.defaultDuration;
  }

  @Override
  public String toString() {
    return ("CooldownHandler [cooldown=" +
        cooldown +
        ", defaultDuration=" +
        defaultDuration +
        "]");
  }
}
