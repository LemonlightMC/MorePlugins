package com.lemonlightmc.moreplugins.modular;

import java.util.List;

public abstract class Module implements IModule {
  protected final String key;
  protected boolean isEnabled = false;
  protected boolean canEnable = true;
  protected List<String> depends;
  protected List<String> softDepends;

  public Module(final String key, final List<String> depends, final List<String> softDepends) {
    if (key == null || key.length() == 0) {
      throw new IllegalArgumentException("Key cant be empty!");
    }
    if (depends == null) {
      throw new IllegalArgumentException("Depends List cant be null!");
    }
    if (softDepends == null) {
      throw new IllegalArgumentException("SoftDepends list cant be null!");
    }
    this.key = key;
    this.depends = depends;
    this.softDepends = softDepends;
  }

  public Module(final String key, final List<String> depends) {
    this(key, depends, List.of());
  }

  public Module(final String key) {
    this(key, List.of(), List.of());
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public List<String> getDepends() {
    return depends;
  }

  @Override
  public List<String> getSoftDepends() {
    return softDepends;
  }

  @Override
  public boolean isEnabled() {
    return isEnabled;
  }

  @Override
  public void enable() {
    if (!canEnable || isEnabled) {
      return;
    }
    this.isEnabled = true;
    onEnable();
  }

  @Override
  public void disable() {
    if (!isEnabled) {
      return;
    }
    this.isEnabled = false;
    disable();
  }

  @Override
  public void reload() {
  }

  @Override
  abstract public void onReload();

  @Override
  abstract public void onEnable();

  @Override
  abstract public void onDisable();

  @Override
  public int hashCode() {
    int result = 31 + key.hashCode();
    result = 31 * result + depends.hashCode();
    result = 31 * result + softDepends.hashCode();
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
    final Module other = (Module) obj;
    if (key == null && other.key != null || depends == null && other.depends != null
        || softDepends == null && other.softDepends != null) {
      return false;
    }
    return key.equals(other.key) && depends.equals(other.depends) && softDepends.equals(other.softDepends);
  }

  @Override
  public String toString() {
    return "Module [key=" + key + ", isEnabled=" + isEnabled + ", depends=" + depends + ", softDepends=" + softDepends
        + "]";
  }

}
