package com.lemonlightmc.moreplugins.config;

import java.io.File;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.lemonlightmc.moreplugins.config.handlers.ConfigHandlerType;
import com.lemonlightmc.moreplugins.config.handlers.FileHandlerOptions;
import com.lemonlightmc.moreplugins.config.handlers.YamlHandler;
import com.lemonlightmc.moreplugins.config.schema.Schema;
import com.lemonlightmc.moreplugins.exceptions.ConfigHandlingException;
import com.lemonlightmc.moreplugins.messages.Logger;
import com.lemonlightmc.moreplugins.utils.FileUtils;

public class Configurate {

  private final File folder;
  private static Map<String, ConfigData> configs = new ConcurrentHashMap<>();
  private static EnumMap<ConfigHandlerType, FileHandler> handlers;

  public Configurate(final File folder) {
    this.folder = folder;
  }

  public File getConfigFolder() {
    return folder;
  }

  public static List<File> getDefaultConfigs() {
    return null;
  }

  public void createDefault() {
    try {
      FileUtils.mkdirs(folder).throwIfFailed(ConfigHandlingException.class);
      for (final ConfigData data : configs.values()) {
        getHandler(data).createIfNotExists(data.filePath);
      }
    } catch (final ConfigHandlingException e) {
      throw e;
    } catch (final Exception e) {
      Logger.warn("Failed to create default Configs");
      e.printStackTrace();
    }
  }

  public static ConfigData getConfig(final String name) {
    return name == null || name.isEmpty() ? null : configs.get(name);
  }

  public static boolean hasConfig(final String name) {
    return name == null || name.isEmpty() ? false : configs.containsKey(name);
  }

  public static ConfigData yaml(final String name, final Schema schema, final FileHandlerOptions options) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Config name cannot be null or empty");
    }
    return yaml(Path.of(name), schema, options);
  }

  public static ConfigData yaml(final Path path, final Schema schema) {
    return yaml(path, schema, null);
  }

  public static ConfigData yaml(final Path path, final Schema schema, final FileHandlerOptions options) {
    if (path == null) {
      throw new IllegalArgumentException("Config path cannot be null");
    }
    if (configs.containsKey(path.getFileName().toString())) {
      throw new IllegalArgumentException("Config with name " + path.getFileName() + " already exists");
    }
    final YamlHandler handler = YamlHandler.from(options);
    handlers.put(ConfigHandlerType.YAML, handler);
    final ConfigData data = new ConfigData(schema, path);
    handler.load(path, data);
    configs.put(path.getFileName().toString(), data);
    return data;
  }

  public static FileHandler getHandler(final String name) {
    if (name == null || name.isEmpty()) {
      return null;
    }
    final ConfigData data = configs.get(name);
    return data == null ? null : getHandler(data.type);
  }

  private static FileHandler getHandler(final ConfigData data) {
    return data == null ? null : getHandler(data.type);
  }

  private static FileHandler getHandler(final ConfigHandlerType type) {
    FileHandler handler = handlers.get(type);
    if (handler == null) {
      handler = type.create();
      handlers.put(type, handler);
    }
    return handler;
  }

  public static void reloadAll() {
    for (final ConfigData data : configs.values()) {
      getHandler(data.type).load(data.filePath, data);
    }
  }

  public static void loadAll() {
    for (final ConfigData data : configs.values()) {
      getHandler(data.type).load(data.filePath, data);
    }
  }

  public static void saveAll() {
    for (final ConfigData data : configs.values()) {
      getHandler(data.type).save(data.filePath, data);
    }
  }

  public static void reload(final String name) {
    final ConfigData data = getConfig(name);
    if (data == null) {
      return;
    }
    getHandler(data.type).reload(data);
  }

  public static void reload(final ConfigData data) {
    if (data == null) {
      return;
    }
    getHandler(data.type).reload(data);
  }

  public static void load(final String name) {
    final ConfigData data = getConfig(name);
    if (data == null) {
      return;
    }
    getHandler(data.type).load(data);
  }

  public static void load(final ConfigData data) {
    if (data == null) {
      return;
    }
    getHandler(data.type).load(data);
  }

  public static void load(final Path path, final ConfigData data) {
    if (data == null) {
      return;
    }
    getHandler(data.type).load(path == null ? data.getFilePath() : path, data);
  }

  public static void save(final String name) {
    final ConfigData data = getConfig(name);
    if (data == null) {
      return;
    }
    getHandler(data.type).save(data);
  }

  public static void save(final ConfigData data) {
    if (data == null) {
      return;
    }
    getHandler(data.type).save(data);
  }

  public static void save(final Path path, final ConfigData data) {
    if (data == null) {
      return;
    }
    getHandler(data.type).save(path == null ? data.getFilePath() : path, data);
  }
}
