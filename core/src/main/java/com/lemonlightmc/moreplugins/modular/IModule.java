package com.lemonlightmc.moreplugins.modular;

import java.util.List;

public interface IModule {

  public String getKey();

  public List<String> getDepends();

  public List<String> getSoftDepends();

  public boolean isEnabled();

  public void enable();

  public void reload();

  public void disable();

  public void onEnable();

  public void onReload();

  public void onDisable();

  public int hashCode();

  public String toString();

  public boolean equals(IModule module);
}
