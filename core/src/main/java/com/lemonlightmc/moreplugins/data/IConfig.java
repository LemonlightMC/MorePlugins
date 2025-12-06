package com.lemonlightmc.moreplugins.data;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

public interface IConfig extends IConfigSection {
  public Path getPath();

  public String getFileName();

  public File getFile();

  public File getFolder();

  public YamlConfiguration getConfig();

  public void createDefault();

  public void load();

  public void load(String path);

  public void load(Path path);

  public void load(File file);

  public void load(InputStream stream);

  public void load(Reader reader);

  public void loadFromString(String contents);

  public void save();

  public void save(String path);

  public void save(Path path);

  public void save(File file);

  public void save(OutputStream stream);

  public void save(Writer writer);

  public String saveToString();

  public void reload();

  public void delete();

  public void options(String header, String footer, boolean copyDefaults);

  public void options(List<String> header, List<String> footer, boolean copyDefaults);

  public boolean equals(Object obj);

  public String toString();

  public int hashCode();

  public void registerSerializer(ConfigSerializable<?> serializer);

  public void unregisterSerializer(ConfigSerializable<?> serializer);

  public void unregisterAllSerializer();

  public <T extends ConfigSerializable<T>> void setSerialized(String path, T serializer);

  public <T extends ConfigSerializable<T>> T getSerialized(String path, T serializer);

  public static interface ConfigSerializable<V extends ConfigSerializable<V>> {

    public String id();

    public Map<String, Object> serialize();

    public V deserialize(ConfigSection map);
  }
}
