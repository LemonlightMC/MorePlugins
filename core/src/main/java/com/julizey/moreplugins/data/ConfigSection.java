package com.julizey.moreplugins.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
  public String getString(String path) {
    path = path + "$s";
    Object v = cache.get(path);
    if (v != null) {
      return (String) v;
    }
    v = config.getString(path);
    cache.put(path, v);
    return (String) v;
  }

  @Override
  public String getString(String path, final String def) {
    path = path + "$s";
    Object v = cache.get(path);
    if (v != null) {
      return (String) v;
    }
    v = config.getString(path, def);
    cache.put(path, v);
    return (String) v;
  }

  @Override
  public int getInt(String path) {
    path = path + "$i";
    Object v = cache.get(path);
    if (v != null) {
      return (int) v;
    }
    v = config.getInt(path);
    cache.put(path, v);
    return (int) v;
  }

  @Override
  public int getInt(String path, final int def) {
    path = path + "$i";
    Object v = cache.get(path);
    if (v != null) {
      return (int) v;
    }
    v = config.getInt(path, def);
    cache.put(path, v);
    return (int) v;
  }

  @Override
  public boolean getBoolean(String path) {
    path = path + "$b";
    Object v = cache.get(path);
    if (v != null) {
      return (boolean) v;
    }
    v = config.getBoolean(path);
    cache.put(path, v);
    return (boolean) v;
  }

  @Override
  public boolean getBoolean(String path, final boolean def) {
    path = path + "$b";
    Object v = cache.get(path);
    if (v != null) {
      return (boolean) v;
    }
    v = config.getBoolean(path, def);
    cache.put(path, v);
    return (boolean) v;
  }

  @Override
  public double getDouble(String path) {
    path = path + "$d";
    Object v = cache.get(path);
    if (v != null) {
      return (double) v;
    }
    v = config.getDouble(path);
    cache.put(path, v);
    return (double) v;
  }

  @Override
  public double getDouble(String path, final double def) {
    path = path + "$d";
    Object v = cache.get(path);
    if (v != null) {
      return (double) v;
    }
    v = config.getDouble(path, def);
    cache.put(path, v);
    return (double) v;
  }

  @Override
  public long getLong(String path) {
    path = path + "$l";
    Object v = cache.get(path);
    if (v != null) {
      return (long) v;
    }
    v = config.getLong(path);
    cache.put(path, v);
    return (long) v;
  }

  @Override
  public long getLong(String path, final long def) {
    path = path + "$l";
    Object v = cache.get(path);
    if (v != null) {
      return (long) v;
    }
    v = config.getLong(path, def);
    cache.put(path, v);
    return (long) v;
  }

  @Override
  public List<?> getList(String path) {
    path = path + "$lt";
    List<?> v = (List<?>) cache.get(path);
    if (v != null) {
      return v;
    }
    v = config.getList(path);
    cache.put(path, v);
    return v;
  }

  @Override
  public List<?> getList(String path, final List<?> def) {
    path = path + "$lt";
    List<?> v = (List<?>) cache.get(path);
    if (v != null) {
      return v;
    }
    v = config.getList(path, def);
    cache.put(path, v);
    return v;
  }

  @Override
  public List<String> getStringList(String path) {
    path = path + "$lst";
    List<?> list = (List<?>) cache.get(path);
    if (list == null) {
      list = config.getList(path);
    }
    List<String> result;
    if (list == null) {
      result = new ArrayList<String>(0);
      cache.put(path, result);
      return result;
    }

    result = new ArrayList<String>();
    for (final Object object : list) {
      if ((object instanceof String) || (isPrimitiveWrapper(object))) {
        result.add(String.valueOf(object));
      }
    }
    return result;
  }

  @Override
  public List<Integer> getIntegerList(String path) {
    path = path + "$li";
    List<?> list = (List<?>) cache.get(path);
    if (list == null) {
      list = config.getList(path);
    }
    List<Integer> result;
    if (list == null) {
      result = new ArrayList<Integer>(0);
      cache.put(path, result);
      return result;
    }

    result = new ArrayList<Integer>();
    for (final Object object : list) {
      if (object instanceof Integer) {
        result.add((Integer) object);
      } else if (object instanceof String) {
        try {
          result.add(Integer.valueOf((String) object));
        } catch (final Exception ex) {
        }
      } else if (object instanceof Character) {
        result.add((int) ((Character) object).charValue());
      } else if (object instanceof Number) {
        result.add(((Number) object).intValue());
      }
    }
    return result;
  }

  @Override
  public List<Boolean> getBooleanList(String path) {
    path = path + "$lbo";
    List<?> list = (List<?>) cache.get(path);
    if (list == null) {
      list = config.getList(path);
    }
    List<Boolean> result;
    if (list == null) {
      result = new ArrayList<Boolean>(0);
      cache.put(path, result);
      return result;
    }

    result = new ArrayList<Boolean>();
    for (final Object object : list) {
      if (object instanceof Boolean) {
        result.add((Boolean) object);
      } else if (object instanceof String) {
        if (Boolean.TRUE.toString().equals(object)) {
          result.add(true);
        } else if (Boolean.FALSE.toString().equals(object)) {
          result.add(false);
        }
      }
    }
    return result;
  }

  @Override
  public List<Double> getDoubleList(String path) {
    path = path + "$ld";
    List<?> list = (List<?>) cache.get(path);
    if (list == null) {
      list = config.getList(path);
    }
    List<Double> result;
    if (list == null) {
      result = new ArrayList<Double>(0);
      cache.put(path, result);
      return result;
    }

    result = new ArrayList<Double>();
    for (final Object object : list) {
      if (object instanceof Double) {
        result.add((Double) object);
      } else if (object instanceof String) {
        try {
          result.add(Double.valueOf((String) object));
        } catch (final Exception ex) {
        }
      } else if (object instanceof Character) {
        result.add((double) ((Character) object).charValue());
      } else if (object instanceof Number) {
        result.add(((Number) object).doubleValue());
      }
    }
    return result;
  }

  @Override
  public List<Float> getFloatList(String path) {
    path = path + "$lf";
    List<?> list = (List<?>) cache.get(path);
    if (list == null) {
      list = config.getList(path);
    }
    List<Float> result;
    if (list == null) {
      result = new ArrayList<Float>(0);
      cache.put(path, result);
      return result;
    }

    result = new ArrayList<Float>();
    for (final Object object : list) {
      if (object instanceof Float) {
        result.add((Float) object);
      } else if (object instanceof String) {
        try {
          result.add(Float.valueOf((String) object));
        } catch (final Exception ex) {
        }
      } else if (object instanceof Character) {
        result.add((float) ((Character) object).charValue());
      } else if (object instanceof Number) {
        result.add(((Number) object).floatValue());
      }
    }
    return result;
  }

  @Override
  public List<Long> getLongList(String path) {
    path = path + "$ll";
    List<?> list = (List<?>) cache.get(path);
    if (list == null) {
      list = config.getList(path);
    }
    List<Long> result;
    if (list == null) {
      result = new ArrayList<Long>(0);
      cache.put(path, result);
      return result;
    }

    result = new ArrayList<Long>();
    for (final Object object : list) {
      if (object instanceof Long) {
        result.add((Long) object);
      } else if (object instanceof String) {
        try {
          result.add(Long.valueOf((String) object));
        } catch (final Exception ex) {
        }
      } else if (object instanceof Character) {
        result.add((long) ((Character) object).charValue());
      } else if (object instanceof Number) {
        result.add(((Number) object).longValue());
      }
    }
    return result;
  }

  @Override
  public List<Byte> getByteList(String path) {
    path = path + "$lb";
    List<?> list = (List<?>) cache.get(path);
    if (list == null) {
      list = config.getList(path);
    }
    List<Byte> result;
    if (list == null) {
      result = new ArrayList<Byte>(0);
      cache.put(path, result);
      return result;
    }

    result = new ArrayList<Byte>();
    for (final Object object : list) {
      if (object instanceof Byte) {
        result.add((Byte) object);
      } else if (object instanceof String) {
        try {
          result.add(Byte.valueOf((String) object));
        } catch (final Exception ex) {
        }
      } else if (object instanceof Character) {
        result.add((byte) ((Character) object).charValue());
      } else if (object instanceof Number) {
        result.add(((Number) object).byteValue());
      }
    }
    return result;
  }

  @Override
  public List<Character> getCharacterList(String path) {
    path = path + "$lc";
    List<?> list = (List<?>) cache.get(path);
    if (list == null) {
      list = config.getList(path);
    }
    List<Character> result;
    if (list == null) {
      result = new ArrayList<Character>(0);
      cache.put(path, result);
      return result;
    }

    result = new ArrayList<Character>();
    for (final Object object : list) {
      if (object instanceof Character) {
        result.add((Character) object);
      } else if (object instanceof String) {
        final String str = (String) object;

        if (str.length() == 1) {
          result.add(str.charAt(0));
        }
      } else if (object instanceof Number) {
        result.add((char) ((Number) object).intValue());
      }
    }
    return result;
  }

  @Override
  public List<Short> getShortList(String path) {
    path = path + "$ls";
    List<?> list = (List<?>) cache.get(path);
    if (list == null) {
      list = config.getList(path);
    }
    List<Short> result;
    if (list == null) {
      result = new ArrayList<Short>(0);
      cache.put(path, result);
      return result;
    }

    result = new ArrayList<Short>();
    for (final Object object : list) {
      if (object instanceof Short) {
        result.add((Short) object);
      } else if (object instanceof String) {
        try {
          result.add(Short.valueOf((String) object));
        } catch (final Exception ex) {
        }
      } else if (object instanceof Character) {
        result.add((short) ((Character) object).charValue());
      } else if (object instanceof Number) {
        result.add(((Number) object).shortValue());
      }
    }
    return result;
  }

  @Override
  public List<Map<?, ?>> getMapList(String path) {
    path = path + "$lm";
    List<?> list = (List<?>) cache.get(path);
    if (list == null) {
      list = config.getList(path);
    }
    List<Map<?, ?>> result;
    if (list == null) {
      result = new ArrayList<Map<?, ?>>(0);
      cache.put(path, result);
      return result;
    }

    result = new ArrayList<Map<?, ?>>();
    for (final Object object : list) {
      if (object instanceof Map) {
        result.add((Map<?, ?>) object);
      }
    }

    return result;
  }

  @Override
  public Map<?, ?> getMap(String path) {
    path = path + "$map";
    Object v = cache.get(path);
    if (v != null) {
      return (Map<?, ?>) v;
    }
    final ConfigurationSection section = config.getConfigurationSection(path);
    v = section.getValues(false);
    cache.put(path, v);
    return (Map<?, ?>) v;
  }

  @Override
  public Map<?, ?> getMap(String path, final Map<?, ?> def) {
    path = path + "$map";
    Object v = cache.get(path);
    if (v != null) {
      return (Map<?, ?>) v;
    }
    final ConfigurationSection section = config.getConfigurationSection(path);
    v = section == null ? def : section.getValues(false);
    cache.put(path, v);
    return (Map<?, ?>) v;
  }

  private boolean isPrimitiveWrapper(final Object input) {
    return input instanceof Integer || input instanceof Boolean
        || input instanceof Character || input instanceof Byte
        || input instanceof Short || input instanceof Double
        || input instanceof Long || input instanceof Float;
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
  public String getSectionPath() {
    return "";
  }

  @Override
  public String getParentPath() {
    final String path = getSectionPath();
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
  public void addDefault(final String path, final Object value) {
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

}
