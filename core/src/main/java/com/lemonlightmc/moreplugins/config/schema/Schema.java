package com.lemonlightmc.moreplugins.config.schema;

import java.util.List;
import java.util.function.Predicate;

public class Schema extends SchemaSection {

  public Schema() {
    super("", null);
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

  public Schema setComment(final String comment) {
    super.setComment(comment);
    return this;
  }
}
