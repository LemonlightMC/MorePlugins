package com.julizey.moreplugins.base;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginAwareness;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoadOrder;

import com.julizey.moreplugins.version.Version;

public class PluginInfo implements IPluginBase.IPluginDescription {

  public final PluginDescriptionFile descriptionFile;
  private final Version version;
  private final Version apiVersion;

  PluginInfo(final PluginDescriptionFile descriptionFile) {
    this.descriptionFile = descriptionFile;
    this.version = new Version(descriptionFile.getVersion());
    this.apiVersion = new Version(descriptionFile.getAPIVersion());
  }

  public PluginDescriptionFile getDescriptionFile() {
    return descriptionFile;
  }

  @Override
  public String getName() {
    return descriptionFile.getName();
  }

  @Override
  public String getFullName() {
    return descriptionFile.getFullName();
  }

  @Override
  public Version getVersion() {
    return version;
  }

  @Override
  public String getPrefix() {
    return descriptionFile.getPrefix();
  }

  @Override
  public String getDescription() {
    return descriptionFile.getDescription();
  }

  @Override
  public Version getApiVersion() {
    return apiVersion;
  }

  @Override
  public String getMain() {
    return descriptionFile.getMain();
  }

  @Override
  public List<String> getAuthors() {
    return descriptionFile.getAuthors();
  }

  @Override
  public List<String> getContributors() {
    return descriptionFile.getContributors();
  }

  @Override
  public String getWebsite() {
    return descriptionFile.getWebsite();
  }

  @Override
  public List<String> getDepend() {
    return descriptionFile.getDepend();
  }

  @Override
  public List<String> getSoftDepend() {
    return descriptionFile.getSoftDepend();
  }

  @Override
  public PluginLoadOrder getLoadOrder() {
    return descriptionFile.getLoad();
  }

  @Override
  public List<String> getLoadBefore() {
    return descriptionFile.getLoadBefore();
  }

  @Override
  public List<String> getProvides() {
    return descriptionFile.getProvides();
  }

  @Override
  public List<String> getLibraries() {
    return descriptionFile.getLibraries();
  }

  @Override
  public Set<String> getCommands() {
    return descriptionFile.getCommands().keySet();
  }

  @Override
  public Set<PluginAwareness> getAwareness() {
    return descriptionFile.getAwareness();
  }

  @Override
  public List<Permission> getPermissions() {
    return descriptionFile.getPermissions();
  }

  @Override
  public PermissionDefault getPermissionDefault() {
    return descriptionFile.getPermissionDefault();
  }

  @Override
  public String toString() {
    return "PluginDescription [getName()=" + getName() + ", getFullName()=" + getFullName() + ", getVersion()="
        + getVersion() + ", getPrefix()=" + getPrefix() + ", getDescription()=" + getDescription()
        + ", getApiVersion()=" + getApiVersion() + ", getMain()=" + getMain() + ", getAuthors()=" + getAuthors()
        + ", getContributors()=" + getContributors() + ", getWebsite()=" + getWebsite() + ", getDepend()=" + getDepend()
        + ", getSoftDepend()=" + getSoftDepend() + ", getLoadOrder()=" + getLoadOrder() + ", getLoadBefore()="
        + getLoadBefore() + ", getProvides()=" + getProvides() + ", getLibraries()=" + getLibraries()
        + ", getCommands()=" + getCommands() + ", getAwareness()=" + getAwareness() + ", getPermissions()="
        + getPermissions() + ", getPermissionDefault()=" + getPermissionDefault() + "]";
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final PluginInfo other = (PluginInfo) obj;
    return Objects.equals(descriptionFile.getName(), other.getName())
        && Objects.equals(version, other.version)
        && Objects.equals(apiVersion, other.apiVersion)
        && Objects.equals(descriptionFile.getMain(), other.getMain())
        && Objects.equals(descriptionFile.getFullName(), other.getFullName())
        && Objects.equals(descriptionFile.getPrefix(), other.getPrefix());
  }

  @Override
  public int hashCode() {
    return Objects.hash(descriptionFile.getName(), version, apiVersion, descriptionFile.getMain(),
        descriptionFile.getFullName(), descriptionFile.getPrefix());
  }
}
