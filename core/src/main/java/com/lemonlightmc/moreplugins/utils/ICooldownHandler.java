package com.lemonlightmc.moreplugins.utils;

import java.util.HashMap;
import java.util.Set;

public interface ICooldownHandler<K> extends Cloneable, Iterable<K> {

  public void setDefaultDuration(long defaultDuration);

  public long getDefaultDuration();

  public void clear(K key);

  public void clearAll();

  public boolean hasCooldown(K key);

  public void setCooldown(K key, long time);

  public void setCooldown(K key);

  public void addCooldown(K key, long time);

  public void addCooldown(K key);

  public void removeCooldown(K key);

  public void clearCooldowns();

  public HashMap<K, Long> getCooldowns();

  public Set<K> getCooldownKeys();

  public boolean isOnCooldown(K key);

  public boolean isOnCooldownStart(K key, long duration);

  public boolean isOnCooldownStop(K key);

  public long getRemaining(K key);

  public long getElapsed(K key);

  public ICooldownHandler<K> clone();

  public int hashCode();

  public boolean equals(Object obj);

  public String toString();

}