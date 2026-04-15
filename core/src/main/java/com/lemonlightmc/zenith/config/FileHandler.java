package com.lemonlightmc.zenith.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Map;

import com.lemonlightmc.zenith.config.handlers.ConfigHandlerType;
import com.lemonlightmc.zenith.config.handlers.FileHandlerOptions;
import com.lemonlightmc.zenith.config.schema.SchemaPair;
import com.lemonlightmc.zenith.exceptions.ConfigHandlingException;
import com.lemonlightmc.zenith.utils.FileUtils;
import com.lemonlightmc.zenith.utils.ResourceUtils;

public abstract class FileHandler {
  private FileHandlerOptions options;

  public FileHandler(final FileHandlerOptions options) {
    this.options = options;
  }

  public abstract String saveToString(final Map<String, SchemaPair<?>> data);

  public abstract Map<String, Object> loadFromString(final String raw);

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
    final File ressource = ResourceUtils.getResourceFile(path.toString());
    if (ressource == null) {
      return;
    }
    ResourceUtils.saveResource(ressource, path.toFile());
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
