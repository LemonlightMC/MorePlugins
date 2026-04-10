package com.lemonlightmc.moreplugins.config.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lemonlightmc.moreplugins.exceptions.ConfigParsingException;

public class SchemaSection extends SchemaNode {

  private List<SchemaNode> nodes = new ArrayList<>();

  public SchemaSection(final String path, final String commet, final SchemaNode... nodes) {
    super(path, commet);
    if (path == null || path.isEmpty()) {
      throw new IllegalArgumentException("SchemaSection Path cannot be null or empty");
    }
    addNodes(nodes);
  }

  public SchemaSection(final String path, final String commet) {
    super(path);
    if (path == null || path.isEmpty()) {
      throw new IllegalArgumentException("SchemaSection Path cannot be null or empty");
    }
  }

  public static SchemaSection create(final String name, final String comment) {
    return new SchemaSection(name, comment);
  }

  public static SchemaSection create(final String name) {
    return new SchemaSection(name, null);
  }

  @SuppressWarnings("unchecked")
  public Map<String, SchemaPair<?>> parse(final Map<String, Object> data) {
    if (data == null || data.isEmpty()) {
      return Map.of();
    }
    final Map<String, SchemaPair<?>> schemaPairs = new HashMap<>();
    for (final SchemaNode schemaNode : nodes) {
      final Object obj = data.get(schemaNode.path);
      if (schemaNode instanceof final SchemaPair<?> pair) {
        pair.fillValue(obj);
        schemaPairs.put(pair.path, pair);
      } else if (schemaNode instanceof final SchemaSection section) {
        if (!(obj instanceof final Map<?, ?> map)) {
          throw new ConfigParsingException("Invalid Config! Object at '" + section.path + "' is not a Config Section!");
        }
        schemaPairs.putAll(section.parse((Map<String, Object>) map));
      }
    }
    return schemaPairs;
  }

  public SchemaSection addNodes(final SchemaNode... nodes) {
    if (nodes == null || nodes.length == 0) {
      return this;
    }
    for (final SchemaNode schemaNode : nodes) {
      if (schemaNode != null) {
        this.nodes.add(schemaNode);
      }
    }
    return this;
  }

  public SchemaSection removeNodes(final List<String> paths) {
    if (paths == null || paths.isEmpty()) {
      return this;
    }
    this.nodes.removeIf(n -> paths.contains(n.path));
    return this;
  }

  public SchemaSection removeNodes(final SchemaNode... nodes) {
    if (nodes == null || nodes.length == 0) {
      return this;
    }
    for (final SchemaNode schemaNode : nodes) {
      if (schemaNode != null) {
        this.nodes.remove(schemaNode);
      }
    }
    return this;
  }

  public SchemaSection setNodes(final List<SchemaNode> nodes) {
    this.nodes = nodes;
    return this;
  }

  public boolean hasNodes() {
    return nodes != null && !nodes.isEmpty();
  }

  public SchemaSection clearNodes() {
    nodes.clear();
    return this;
  }

  public SchemaSection setComment(final String comment) {
    super.setComment(comment);
    return this;
  }
}
