package com.lemonlightmc.moreplugins.data;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IConfigSection {
  public Object get(String path);

  public Object get(String path, Object def);

  public String getString(String path);

  public String getString(String path, String def);

  public int getInt(String path);

  public int getInt(String path, int def);

  public boolean getBoolean(String path);

  public boolean getBoolean(String path, boolean def);

  public double getDouble(String path);

  public double getDouble(String path, double def);

  public long getLong(String path);

  public long getLong(String path, long def);

  public List<?> getList(String path);

  public List<?> getList(String path, List<?> def);

  public List<String> getStringList(String path);

  public List<Integer> getIntegerList(String path);

  public List<Boolean> getBooleanList(String path);

  public List<Double> getDoubleList(String path);

  public List<Float> getFloatList(String path);

  public List<Long> getLongList(String path);

  public List<Byte> getByteList(String path);

  public List<Character> getCharacterList(String path);

  public List<Short> getShortList(String path);

  public List<Map<?, ?>> getMapList(String path);

  public Map<?, ?> getMap(String path);

  public Map<?, ?> getMap(String path, Map<?, ?> def);

  // Setters
  public void set(String path, Object value);

  public void remove(String path);

  public boolean contains(String path);

  public boolean contains(String path, boolean ignoreDefault);

  // Comments
  public void setComments(String path, String comment);

  public void setComments(String path, List<String> comments);

  public List<String> getComments(String path);

  public List<String> getComments(String path, String def);

  public List<String> getComments(String path, List<String> def);

  // Section methods
  public void createSection(String path);

  public void setSection(String path);

  public void setSection(String path, Map<String, Object> values);

  public void setSection(String path, ConfigSection section);

  public void removeSection(String path);

  public IConfigSection getSection(String path);

  public boolean isSection(String path);

  public String getSectionPath();

  public String getParentPath();

  // Utility methods

  public Set<String> getKeys();

  public Set<String> getKeys(boolean deep);

  public Map<String, Object> getValues();

  public Map<String, Object> getValues(boolean deep);

  public void addDefault(String path, Object value);

  public void setDefaults(Map<String, Object> defaults);

  // other

  public boolean equals(Object obj);

  public String toString();

  public int hashCode();
}
