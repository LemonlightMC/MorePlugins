package com.julizey.moreplugins.data;

import com.julizey.moreplugins.base.MorePlugins;
import com.julizey.moreplugins.messages.Logger;
import com.julizey.moreplugins.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config extends ConfigSection implements IConfig {

  public Path filePath;
  public File configFile;
  public File folder;
  public HashSet<ConfigSerializable<?>> serializers = new HashSet<>();

  public Config(final YamlConfiguration yaml, final File file) {
    super(yaml);
    yaml.options().copyDefaults();
    this.filePath = file.toPath();
    this.configFile = file;
    this.folder = file.getParentFile();
    reload();
  }

  public static Config from(final String path) {
    final File file = Path.of(path).toFile();
    return new Config(YamlConfiguration.loadConfiguration(file), file);
  }

  public static Config from(final Path path) {
    final File file = path.toFile();
    return new Config(YamlConfiguration.loadConfiguration(file), file);
  }

  public static Config from(final File file) {
    return new Config(YamlConfiguration.loadConfiguration(file), file);
  }

  @Override
  public Path getPath() {
    return filePath;
  }

  @Override
  public String getStringPath() {
    return filePath.toString();
  }

  @Override
  public File getFile() {
    return configFile;
  }

  @Override
  public File getFolder() {
    return folder;
  }

  @Override
  public void createDefault() {
    try {
      if (!folder.exists()) {
        folder.mkdirs();
      }
      if (!configFile.exists()) {
        MorePlugins.instance.saveResource("config.yml", true);
      }
    } catch (final Exception e) {
      Logger.warn("Failed to create default Config");
      e.printStackTrace();
    }
  }

  @Override
  public YamlConfiguration getConfig() {
    if (config == null) {
      reload();
    }
    return ((YamlConfiguration) config);
  }

  @Override
  public void load() {
    try {
      createDefault();
      getConfig().load(configFile);
    } catch (final Exception ex) {
      Logger.warn("Could not load Config from Path: " + configFile.getPath());
      ex.printStackTrace();
    }
  }

  @Override
  public void load(final String path) {
    try {
      configFile = new File(folder, path);
      FileUtils.mkdirs(configFile);
      getConfig().load(path);
    } catch (final Exception ex) {
      Logger.warn("Could not load Config from Path: " + configFile.getPath());
      ex.printStackTrace();
    }
  }

  @Override
  public void load(final Path path) {
    try {
      configFile = new File(folder, path.toString());
      getConfig().load(path.toFile());
    } catch (final Exception ex) {
      Logger.warn("Could not load Config from Path: " + configFile.getPath());
      ex.printStackTrace();
    }
  }

  @Override
  public void load(final File newFile) {
    try {
      configFile = newFile;
      getConfig().load(newFile);
    } catch (final Exception ex) {
      Logger.warn("Could not load Config from File: " + configFile.getPath());
      ex.printStackTrace();
    }
  }

  @Override
  public void load(final InputStream stream) {
    load(new InputStreamReader(stream, StandardCharsets.UTF_8));
  }

  @Override
  public void load(final Reader reader) {
    try {
      getConfig().load(reader);
    } catch (final Exception e) {
      Logger.warn("Failed to load Config from reader");
      e.printStackTrace();
    }
  }

  @Override
  public void loadFromString(final String contents) {
    try {
      getConfig().loadFromString(contents);
    } catch (final Exception ex) {
      Logger.warn("Could not load Config from String");
      ex.printStackTrace();
    }
  }

  @Override
  public void save() {
    try {
      FileUtils.mkdirs(configFile);
      getConfig().save(configFile);
    } catch (final IOException ex) {
      Logger.warn("Could not save Config to File: " + configFile);
      ex.printStackTrace();
    }
  }

  @Override
  public void save(final String path) {
    try {
      final File file = new File(path);
      FileUtils.mkdirs(file);
      getConfig().save(file);
    } catch (final IOException ex) {
      Logger.warn("Could not save Config to Path: " + path);
      ex.printStackTrace();
    }
  }

  @Override
  public void save(final Path path) {
    try {
      final File file = path.toFile();
      FileUtils.mkdirs(file);
      getConfig().save(file);
    } catch (final IOException ex) {
      Logger.warn("Could not save Config to Path: " + path.toString());
      ex.printStackTrace();
    }
  }

  @Override
  public void save(final File file) {
    try {
      FileUtils.mkdirs(file);
      getConfig().save(file);
    } catch (final IOException ex) {
      Logger.warn("Could not save Config to File: " + file.getAbsolutePath());
      ex.printStackTrace();
    }
  }

  @Override
  public void save(final OutputStream stream) {
    try {
      final Writer writer = new OutputStreamWriter(stream, StandardCharsets.UTF_8);
      writer.write(getConfig().saveToString());
      writer.close();
    } catch (final IOException ex) {
      Logger.warn("Could not save Config to Stream: " + stream.toString());
      ex.printStackTrace();
    }
  }

  @Override
  public void save(final Writer writer) {
    try {
      writer.write(getConfig().saveToString());
      writer.close();
    } catch (final Exception ex) {
      Logger.warn("Could not save Config to Writer: " + writer.toString());
      ex.printStackTrace();
    }
  }

  @Override
  public String saveToString() {
    try {
      return getConfig().saveToString();
    } catch (final Exception ex) {
      Logger.warn("Could not save Config to String");
      ex.printStackTrace();
      return null;
    }
  }

  @Override
  public void reload() {
    try {
      createDefault();
      final YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);

      final InputStream defConfigStream = MorePlugins.instance.getResource(
          "config.yml");
      if (defConfigStream == null) {
        return;
      }
      final YamlConfiguration defConfig = new YamlConfiguration();
      defConfig.load(new InputStreamReader(defConfigStream));
      ((YamlConfiguration) config).setDefaults(defConfig);
    } catch (final Exception e) {
      Logger.warn("Failed to reload config");
      e.printStackTrace();
    }
  }

  @Override
  public void delete() {
    if (configFile.exists()) {
      configFile.delete();
    }
  }

  @Override
  public void options(final String header, final String footer, final boolean copyDefaults) {
    getConfig().options().copyDefaults(copyDefaults);
    getConfig().options().setHeader(header != null ? List.of(header.split("\n")) : null);
    getConfig().options().setFooter(footer != null ? List.of(footer.split("\n")) : null);
  }

  @Override
  public void options(final List<String> header, final List<String> footer, final boolean copyDefaults) {
    getConfig().options().copyDefaults(copyDefaults);
    getConfig().options().setHeader(header);
    getConfig().options().setFooter(footer);
  }

  public String[] getComment(final String key) {
    final ArrayList<String> comments = new ArrayList<>(getConfig().getComments(key));
    comments.removeIf(o -> o == null);
    return comments.toArray(new String[0]);
  }

  public void rebuild() {
    final String[] configStrings = getConfig().saveToString().split("\n");
    final StringBuilder configBuilder = new StringBuilder();
    for (final String configString : configStrings) {
      configBuilder.append(configString).append("\n");
      if (!configString.contains("#")) {
        configBuilder.append("\n");
      }
    }
    try {
      getConfig().loadFromString(configBuilder.toString());
    } catch (final InvalidConfigurationException e) {
      e.printStackTrace(System.err);
    }
  }

  public void stripComments() {
    final String[] configStrings = getConfig().saveToString().split("\n");
    final StringBuilder configBuilder = new StringBuilder();
    for (final String configString : configStrings) {
      if (!configString.contains("#")) {
        configBuilder.append(configString).append("\n");
      }
    }
    try {
      getConfig().loadFromString(configBuilder.toString());
    } catch (final InvalidConfigurationException e) {
      e.printStackTrace(System.err);
    }
  }

  @Override
  public int hashCode() {
    int result = 31 * super.hashCode() + ((configFile == null) ? 0 : configFile.hashCode());
    result = 31 * result + getConfig().hashCode();
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj) || getClass() != obj.getClass()) {
      return false;
    }
    final Config other = (Config) obj;
    if (configFile == null && other.configFile != null || config == null && other.config != null) {
      return false;
    }
    return getConfig().equals(other.config) && configFile.equals(other.configFile);
  }

  @Override
  public String toString() {
    return "Config [configFile=" + configFile + ", config=" + config + "]";
  }

  @Override
  public void registerSerializer(final ConfigSerializable<?> serializer) {
    if (serializer == null) {
      throw new NullPointerException("Serializer is null");
    }
    serializers.add(serializer);
  }

  @Override
  public void unregisterSerializer(final ConfigSerializable<?> serializer) {
    serializers.remove(serializer);
  }

  @Override
  public void unregisterAllSerializer() {
    serializers.clear();
  }

  @Override
  public <T extends ConfigSerializable<T>> T getSerialized(final String path, final T serializer) {
    final ConfigSection section = getSection(path);
    if (section == null) {
      return null;
    }
    return serializer.deserialize(section);
  }

  @Override
  public <T extends ConfigSerializable<T>> void setSerialized(final String path, final T serializer) {
    final Map<String, Object> map = serializer.serialize();
    if (map == null) {
      return;
    }
    setSection(path, map);
  }
}
