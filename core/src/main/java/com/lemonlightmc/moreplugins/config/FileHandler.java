package com.lemonlightmc.moreplugins.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Map;

import com.lemonlightmc.moreplugins.config.handlers.ConfigHandlerType;
import com.lemonlightmc.moreplugins.config.handlers.FileHandlerOptions;
import com.lemonlightmc.moreplugins.config.schema.SchemaPair;
import com.lemonlightmc.moreplugins.exceptions.ConfigHandlingException;
import com.lemonlightmc.moreplugins.utils.FileUtils;
import com.lemonlightmc.moreplugins.utils.RessourceUtils;

public abstract class FileHandler {
  private FileHandlerOptions options;

  public FileHandler(final FileHandlerOptions options) {
    this.options = options;
  }

  protected abstract String saveToString(final Map<String, SchemaPair<?>> data);

  protected abstract Map<String, Object> loadFromString(final String raw);

  public FileHandlerOptions options() {
    if (options == null) {
      options = new FileHandlerOptions();
    }
    return options;
  }

  public static ConfigHandlerType detect(final File path) {
    return ConfigHandlerType.detect(path);
  }

  public void createIfNotExists(final Path path) {
    if (FileUtils.exists(path)) {
      return;
    }
    final File ressource = RessourceUtils.getResource(path.toString());
    if (ressource == null) {
      return;
    }
    RessourceUtils.saveResource(ressource, path.toFile());
  }

  public ConfigData reload(final ConfigData data) {
    if (data == null) {
      return null;
    }
    load(data);
    return data;
  }

  public void save(final ConfigData data) {
    if (data == null) {
      throw new IllegalArgumentException("ConfigData cannot be null");
    }
    if (data.getFilePath() == null) {
      throw new IllegalArgumentException("ConfigData file path is not set");
    }
    save(data.getFilePath(), data);
  }

  public void save(final Path path, final ConfigData data) {
    FileUtils.mkdirs(path).throwIfFailed(ConfigHandlingException.class);
    OutputStreamWriter writer = null;
    try {
      final String str = saveToString(data.getRawData());
      writer = new OutputStreamWriter(new FileOutputStream(path.toFile()), StandardCharsets.UTF_8);
      writer.write(str);
    } catch (final Exception e) {
      throw new ConfigHandlingException("Failed to save Config to " + path, e);
    } finally {
      if (writer == null) {
        return;
      }
      try {
        writer.close();
      } catch (final Exception e) {
      }
    }
  }

  public void load(final ConfigData data) {
    load(data.getFilePath(), data);
  }

  public void load(final Path path, final ConfigData data) {
    if (path == null) {
      throw new IllegalArgumentException("Path cannot be null");
    }
    if (data == null) {
      throw new IllegalArgumentException("ConfigData cannot be null");
    }
    final Map<String, Object> raw = loadFromString(FileUtils.readString(path));
    data.setRawData(data.getSchema().parse(raw));
  }
}
