
package com.lemonlightmc.moreplugins.base;

import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public abstract class BaseListener implements Listener {

  protected String name;
  protected EventPriority priority;
  protected boolean enabled;
  protected boolean isRegistered;

  public BaseListener(final String name) {
    if (name == null || name.length() == 0) {
      throw new IllegalArgumentException("Invalid Listener Name");
    }
    this.name = name;
    this.priority = EventPriority.NORMAL;
    this.enabled = true;
  }

  public BaseListener(final String name, EventPriority priority) {
    if (name == null || name.length() == 0) {
      throw new IllegalArgumentException("Invalid Listener Name");
    }
    this.name = name;
    this.priority = EventPriority.NORMAL;
    this.enabled = true;
  }

  public BaseListener(final String name, final boolean enabled) {
    if (name == null || name.length() == 0) {
      throw new IllegalArgumentException("Invalid Listener Name");
    }
    this.name = name;
    this.priority = EventPriority.NORMAL;
    this.enabled = enabled;
  }

  public BaseListener(final String name, EventPriority priority, final boolean enabled) {
    if (name == null || name.length() == 0) {
      throw new IllegalArgumentException("Invalid Listener Name");
    }
    this.name = name;
    this.priority = priority;
    this.enabled = enabled;
  }

  public abstract void onRegister();

  public abstract void onUnRegister();

  public String getName() {
    return name;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(final boolean value) {
    enabled = value;
  }

  protected boolean shouldHandle() {
    return enabled && isRegistered;
  }

  public void setRegistered(boolean value) {
    if (value != isRegistered) {
      if (value = true) {
        ListenerManager.register(this);
      } else {
        ListenerManager.unregister(this);
      }
    }
  }

  @Override
  public int hashCode() {
    return 31 * (31 + name.hashCode()) + (enabled ? 1231 : 1237);
  }

  @Override
  public String toString() {
    return "BaseListener [name=" + name + ", enabled=" + enabled + "]";
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    final BaseListener other = (BaseListener) obj;
    return name.equals(other.name) && enabled == other.enabled;
  }
}