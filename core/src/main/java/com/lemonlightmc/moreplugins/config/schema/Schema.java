package com.lemonlightmc.moreplugins.config.schema;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import com.lemonlightmc.moreplugins.config.FileHandler;
import com.lemonlightmc.moreplugins.config.handlers.ConfigHandlerType;
import com.lemonlightmc.moreplugins.utils.FileUtils;

public class Schema extends SchemaSection {
  public Schema(final String header, final SchemaNode... nodes) {
    super("", header, nodes);
  }

  public Schema(final String header, final Collection<SchemaNode> nodes) {
    super("", header, nodes);
  }

  public Schema(final String header) {
    super("", header);
  }

  public Schema() {
    super("", null);
  }

  public static Schema create(final String header, final SchemaNode... nodes) {
    return new Schema(header, nodes);
  }

  public static Schema create(final String header, final Collection<SchemaNode> nodes) {
    return new Schema(header, nodes);
  }

  public static Schema create(final String header) {
    return new Schema(header);
  }

  public static Schema create() {
    return new Schema();
  }

  public static SchemaSection section(final String name, final String comment, final SchemaNode... nodes) {
    return new SchemaSection(name, comment, nodes);
  }

  public static SchemaSection section(final String name, final SchemaNode... nodes) {
    return new SchemaSection(name, null, nodes);
  }

  public static SchemaSection section(final String name, final String comment) {
    return new SchemaSection(name, comment);
  }

  public static SchemaSection section(final String name) {
    return new SchemaSection(name, null);
  }

  public static <T> SchemaPair<T> pair(final String name, final SchemaType<T> type, final T def,
      final String comment, final Predicate<T> validator) {
    return SchemaPair.create(name, type, def, comment, validator);
  }

  public static <T> SchemaPair<T> pair(final String name, final SchemaType<T> type, final T def,
      final String comment) {
    return SchemaPair.create(name, type, def, comment, null);
  }

  public static <T> SchemaPair<T> pair(final String name, final SchemaType<T> type, final T def) {
    return SchemaPair.create(name, type, def, null, null);
  }

  public static <T> SchemaPair<T> pair(final String name, final SchemaType<T> type) {
    return SchemaPair.create(name, type, null, null, null);
  }

  public Schema addNodes(final SchemaNode... nodes) {
    super.addNodes(nodes);
    return this;
  }

  public Schema removeNodes(final List<String> paths) {
    super.removeNodes(paths);
    return this;
  }

  public Schema removeNodes(final SchemaNode... nodes) {
    super.removeNodes(nodes);
    return this;
  }

  public Schema setNodes(final List<SchemaNode> nodes) {
    super.setNodes(nodes);
    return this;
  }

  public Schema clearNodes() {
    super.clearNodes();
    return this;
  }

  public Schema setHeader(final String comment) {
    super.setComment(comment);
    return this;
  }

  public void save(final Path path) {
    if (path == null) {
      throw new IllegalArgumentException("Path cannot be null");
    }
    try {
      if (path.getParent() != null) {
        Files.createDirectories(path.getParent());
      }
      Files.writeString(path, SchemaFactory.saveToString(this), StandardCharsets.UTF_8);
    } catch (final Exception e) {
      throw new IllegalArgumentException("Failed to save Schema to " + path, e);
    }
  }

  public static Schema load(final Path path) {
    if (path == null) {
      throw new IllegalArgumentException("Path cannot be null");
    }
    final String raw = FileUtils.readString(path);
    return SchemaFactory.loadFromString(raw);
  }

  public static Schema generate(final Path path) {
    if (path == null) {
      throw new IllegalArgumentException("Path cannot be null");
    }
    if (!FileUtils.isReadable(path)) {
      throw new IllegalArgumentException("Path must point to an existing readable file: " + path);
    }

    final FileHandler handler = ConfigHandlerType.create(path.toFile());
    if (handler == null) {
      throw new IllegalArgumentException("Unsupported config file type: " + path);
    }

    final String raw = FileUtils.readString(path);
    if (raw == null || raw.isBlank()) {
      return Schema.create();
    }

    final Map<String, Object> root = handler.loadFromString(raw);
    if (root == null || root.isEmpty()) {
      return Schema.create();
    }

    final Schema schema = Schema.create();
    for (final Map.Entry<String, Object> entry : root.entrySet()) {
      final SchemaNode node = SchemaFactory.createSchemaNode(entry.getKey(), entry.getValue());
      if (node != null) {
        schema.addNodes(node);
      }
    }
    return schema;
  }

}
