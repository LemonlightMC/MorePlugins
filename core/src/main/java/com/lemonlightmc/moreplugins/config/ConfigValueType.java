package com.lemonlightmc.moreplugins.config;

public enum ConfigValueType {
  BOOL("boolean", "$b"),
  INT("int", "$i"),
  STRING("string", "$s"),
  DOUBLE("double", "$d"),
  LONG("long", "$l"),
  LIST("list", "$ls"),
  MAP("map", "$m"),
  SECTION("section", "$sec"),
  CUSTOM("custom", "$c");

  private final String id;
  private final String name;

  private ConfigValueType(final String name, final String id) {
    this.id = id;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String getId() {
    return id;
  }

  public String toKey(final String key) {
    return key + id;
  }

  public String toString() {
    return id;
  }
}
