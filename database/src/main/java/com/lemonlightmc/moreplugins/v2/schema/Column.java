package com.lemonlightmc.moreplugins.v2.schema;

public interface Column<T> extends Attribute {
  T get(Object object);

  String getName();

  String getSqlType();

  boolean isNullable();
}