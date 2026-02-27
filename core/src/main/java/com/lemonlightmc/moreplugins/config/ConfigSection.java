package com.lemonlightmc.moreplugins.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.bukkit.configuration.ConfigurationSection;

public class ConfigSection implements IConfigSection {
  protected HashMap<String, Object> cache;
  protected ConfigurationSection config;

  public ConfigSection(final ConfigurationSection section) {
    config = section;
  }

  @Override
  public Object get(final String path) {
    Object v = cache.get(path);
    if (v != null) {
      return v;
    }
    v = config.get(path);
    cache.put(path, v);
    return v;
  }

  @Override
  public Object get(final String path, final Object def) {
    Object v = cache.get(path);
    if (v != null) {
      return v;
    }
    v = config.get(path, def);
    cache.put(path, v);
    return v;
  }

  @Override
  public String getString(final String path) {
    final Object v = get(ConfigValueType.STRING.toKey(path));
    return v != null ? (String) v : null;
  }

  @Override
  public String getString(final String path, final String def) {
    final Object v = get(ConfigValueType.STRING.toKey(path), def);
    return v != null ? (String) v : null;
  }

  @Override
  public int getInt(final String path) {
    return (int) get(ConfigValueType.INT.toKey(path));
  }

  @Override
  public int getInt(final String path, final int def) {
    return (int) get(ConfigValueType.INT.toKey(path), def);
  }

  @Override
  public boolean getBoolean(final String path) {
    return (boolean) get(ConfigValueType.BOOL.toKey(path));
  }

  @Override
  public boolean getBoolean(final String path, final boolean def) {
    return (boolean) get(ConfigValueType.BOOL.toKey(path), def);
  }

  @Override
  public double getDouble(final String path) {
    return (double) get(ConfigValueType.DOUBLE.toKey(path));
  }

  @Override
  public double getDouble(final String path, final double def) {
    return (double) get(ConfigValueType.DOUBLE.toKey(path), def);
  }

  @Override
  public long getLong(final String path) {
    return (long) get(ConfigValueType.LONG.toKey(path));
  }

  @Override
  public long getLong(final String path, final long def) {
    return (long) get(ConfigValueType.LONG.toKey(path), def);
  }

  @Override
  public List<?> getList(final String path) {
    final Object obj = get(ConfigValueType.LIST.toKey(path));
    return obj != null && obj instanceof List<?> ? (List<?>) obj : null;
  }

  @Override
  public List<?> getList(final String path, final List<?> def) {
    final Object obj = get(ConfigValueType.LIST.toKey(path), def);
    return obj != null && obj instanceof List<?> ? (List<?>) obj : null;
  }

  @Override
  public List<String> getStringList(final String path) {
    return getTypedList(path, null,
        obj -> (obj instanceof String) || isPrimitiveWrapper(obj) ? String.valueOf(obj) : null);
  }

  @Override
  public List<Integer> getIntegerList(final String path) {
    return getTypedList(path, null,
        obj -> {
          if (obj instanceof Integer) {
            return (Integer) obj;
          } else if (obj instanceof String) {
            try {
              return Integer.valueOf((String) obj);
            } catch (final Exception ex) {
            }
          } else if (obj instanceof Character) {
            return (int) ((Character) obj).charValue();
          } else if (obj instanceof Number) {
            return ((Number) obj).intValue();
          }
          return null;
        });
  }

  @Override
  public List<Boolean> getBooleanList(final String path) {
    return getTypedList(path, null,
        obj -> {
          if (obj instanceof Boolean) {
            return (Boolean) obj;
          } else if (obj instanceof String) {
            if (Boolean.TRUE.toString().equals(obj)) {
              return true;
            } else if (Boolean.FALSE.toString().equals(obj)) {
              return false;
            }
          }
          return null;
        });
  }

  @Override
  public List<Double> getDoubleList(final String path) {
    return getTypedList(path, null,
        obj -> {
          if (obj instanceof Double) {
            return (Double) obj;
          } else if (obj instanceof String) {
            try {
              return Double.valueOf((String) obj);
            } catch (final Exception ex) {
            }
          } else if (obj instanceof Character) {
            return (double) ((Character) obj).charValue();
          } else if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
          }
          return null;
        });
  }

  @Override
  public List<Float> getFloatList(final String path) {
    return getTypedList(path, null,
        obj -> {
          if (obj instanceof Float) {
            return (Float) obj;
          } else if (obj instanceof String) {
            try {
              return Float.valueOf((String) obj);
            } catch (final Exception ex) {
            }
          } else if (obj instanceof Character) {
            return (float) ((Character) obj).charValue();
          } else if (obj instanceof Number) {
            return ((Number) obj).floatValue();
          }
          return null;
        });
  }

  @Override
  public List<Long> getLongList(final String path) {
    return getTypedList(path, null,
        obj -> {
          if (obj instanceof Long) {
            return (Long) obj;
          } else if (obj instanceof String) {
            try {
              return Long.valueOf((String) obj);
            } catch (final Exception ex) {
            }
          } else if (obj instanceof Character) {
            return (long) ((Character) obj).charValue();
          } else if (obj instanceof Number) {
            return ((Number) obj).longValue();
          }
          return null;
        });
  }

  @Override
  public List<Byte> getByteList(final String path) {
    return getTypedList(path, null,
        obj -> {
          if (obj instanceof Byte) {
            return (Byte) obj;
          } else if (obj instanceof String) {
            try {
              return Byte.valueOf((String) obj);
            } catch (final Exception ex) {
            }
          } else if (obj instanceof Character) {
            return (byte) ((Character) obj).charValue();
          } else if (obj instanceof Number) {
            return ((Number) obj).byteValue();
          }
          return null;
        });
  }

  @Override
  public List<Character> getCharacterList(final String path) {
    return getTypedList(path, null,
        obj -> {
          if (obj instanceof Character) {
            return (Character) obj;
          } else if (obj instanceof String) {
            final String str = (String) obj;

            if (str.length() == 1) {
              return str.charAt(0);
            }
          } else if (obj instanceof Number) {
            return (char) ((Number) obj).intValue();
          }
          return null;
        });
  }

  @Override
  public List<Short> getShortList(final String path) {
    return getTypedList(path, null,
        obj -> {
          if (obj instanceof Short) {
            return (Short) obj;
          } else if (obj instanceof String) {
            try {
              return Short.valueOf((String) obj);
            } catch (final Exception ex) {
            }
          } else if (obj instanceof Character) {
            return (short) ((Character) obj).charValue();
          } else if (obj instanceof Number) {
            return ((Number) obj).shortValue();
          }
          return null;
        });
  }

  @Override
  public List<Map<?, ?>> getMapList(final String path) {
    final List<?> list = getList(path);
    if (list == null) {
      return List.of();
    }
    final List<Map<?, ?>> result = new ArrayList<Map<?, ?>>();
    for (final Object obj : list) {
      if (obj instanceof Map) {
        result.add((Map<?, ?>) obj);
      }
    }
    return result;
  }

  @Override
  public Map<?, ?> getMap(final String path) {
    final Object obj = get(ConfigValueType.MAP.toKey(path));
    return obj != null && obj instanceof Map<?, ?> ? (Map<?, ?>) obj : null;
  }

  @Override
  public Map<?, ?> getMap(final String path, final Map<?, ?> def) {
    final Object obj = get(ConfigValueType.MAP.toKey(path), def);
    return obj != null && obj instanceof Map<?, ?> ? (Map<?, ?>) obj : null;
  }

  @Override
  public void set(final String path, final Object value) {
    config.set(path, value);
  }

  @Override
  public boolean contains(final String path) {
    return get(path) != null;
  }

  @Override
  public boolean contains(final String path, final boolean ignoreDefault) {
    return (ignoreDefault ? get(path, null) : get(path)) != null;
  }

  @Override
  public void remove(final String path) {
    config.set(path, null);
  }

  @Override
  public void setComments(final String path, final String comment) {
    config.setComments(path, List.of(comment));
  }

  @Override
  public void setComments(final String path, final List<String> comments) {
    config.setComments(path, comments);
  }

  @Override
  public List<String> getComments(final String path) {
    return config.getComments(path);
  }

  @Override
  public List<String> getComments(final String path, final String def) {
    final List<String> list = config.getComments(path);
    return list == null ? List.of(def) : list;
  }

  @Override
  public List<String> getComments(final String path, final List<String> def) {
    final List<String> list = config.getComments(path);
    return list == null ? def : list;
  }

  @Override
  public ConfigSection getSection(final String path) {
    final ConfigurationSection section = config.getConfigurationSection(path);
    if (section == null) {
      return null;
    }
    return new ConfigSection(section);
  }

  @Override
  public void createSection(final String path) {
    config.createSection(path);
  }

  @Override
  public void setSection(final String path) {
    config.createSection(path);
  }

  @Override
  public void setSection(final String path, final Map<String, Object> values) {
    config.createSection(path, values);
  }

  @Override
  public void setSection(final String path, final ConfigSection section) {
    final Map<String, Object> values = config.getValues(false);
    if (values == null) {
      return;
    }
    config.createSection(path, values);
  }

  @Override
  public void removeSection(final String path) {
    config.set(path, null);
  }

  @Override
  public boolean isSection(final String path) {
    final Object o = config.get(path);
    return o != null && o instanceof ConfigurationSection;
  }

  @Override
  public String getCurrentPath() {
    return config.getCurrentPath();
  }

  @Override
  public String getParentPath() {
    final String path = getCurrentPath();
    return path.contains(".") ? path.substring(0, path.lastIndexOf('.')) : "";
  }

  @Override
  public Set<String> getKeys(final boolean deep) {
    return config.getKeys(deep);
  }

  @Override
  public Map<String, Object> getValues(final boolean deep) {
    return config.getValues(deep);
  }

  @Override
  public Set<String> getKeys() {
    return config.getKeys(false);
  }

  @Override
  public Map<String, Object> getValues() {
    return config.getValues(false);
  }

  @Override
  public void setDefaults(final String path, final Object value) {
    config.addDefault(path, value);
  }

  @Override
  public void setDefaults(final Map<String, Object> defaults) {
    for (final Map.Entry<String, Object> entry : defaults.entrySet()) {
      config.addDefault(entry.getKey(), entry.getValue());
    }
  }

  @Override
  public int hashCode() {
    return 31 + ((config == null) ? 0 : config.hashCode());
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final ConfigSection other = (ConfigSection) obj;
    return (config == null && other.config == null || config != null && other.config != null)
        || config.equals(other.config);
  }

  @Override
  public String toString() {
    return "ConfigSection [config=" + config + "]";
  }

  private boolean isPrimitiveWrapper(final Object input) {
    return input instanceof Integer || input instanceof Boolean
        || input instanceof Character || input instanceof Byte
        || input instanceof Short || input instanceof Double
        || input instanceof Long || input instanceof Float;
  }

  private <T> List<T> getTypedList(final String path, final List<T> def, final Function<Object, T> mapper) {
    final Object obj = get(ConfigValueType.LIST.toKey(path), def);
    if (obj == null || !(obj instanceof final List<?> list)) {
      return List.<T>of();
    }
    final List<T> result = new ArrayList<T>();
    T v;
    for (final Object tempObj : list) {
      v = mapper.apply(tempObj);
      if (v != null) {
        result.add(v);
      }
    }
    return result;
  }

}
