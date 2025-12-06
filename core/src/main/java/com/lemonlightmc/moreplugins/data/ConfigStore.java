package com.lemonlightmc.moreplugins.data;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigStore {
  private final Map<String, Config> configs;

  public ConfigStore() {
    this.configs = new ConcurrentHashMap<>(4);
  }

  public int size() {
    return configs.size();
  }

  public boolean isEmpty() {
    return configs.isEmpty();
  }

  public void add(final String name, final Config config) {
    if (config == null) {
      throw new IllegalArgumentException("Config to store cant be null!");
    }
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name of Config to store cant be empty!");
    }
    configs.put(name, config);
  }

  public void add(final Config config) {
    if (config == null) {
      throw new IllegalArgumentException("Config to store cant be null!");
    }
    add(config.getFileName(), config);
  }

  public void addAll(final Map<String, Config> map) {
    if (map == null || map.isEmpty()) {
      return;
    }
    for (final Map.Entry<String, Config> entry : map.entrySet()) {
      add(entry.getKey(), entry.getValue());
    }
  }

  public void addAll(final Collection<Config> list) {
    if (list == null || list.isEmpty()) {
      return;
    }
    for (final Config entry : list) {
      add(entry);
    }
  }

  public void remove(final String key) {
    if (key == null) {
      return;
    }
    configs.remove(key);
  }

  public void removeAll(final Collection<String> keys) {
    if (keys == null || keys.isEmpty()) {
      return;
    }
    for (final String key : keys) {
      if (key == null || key.isBlank()) {
        return;
      }
      configs.remove(key);
    }
  }

  public boolean contains(final String key) {
    if (key == null || key.isBlank()) {
      return false;
    }
    return configs.containsKey(key);
  }

  public void clear() {
    configs.clear();
  }

  public Set<String> keys() {
    return configs.keySet();
  }

  public Collection<Config> values() {
    return configs.values();
  }

  public Map<String, Config> map() {
    return configs;
  }

  @Override
  public int hashCode() {
    return 31 + ((configs == null) ? 0 : configs.hashCode());
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final ConfigStore other = (ConfigStore) obj;
    if (configs == null && other.configs != null) {
      return false;
    }
    return configs.equals(other.configs);
  }

  @Override
  public String toString() {
    return "ConfigStore [configs=" + configs + "]";
  }
}
