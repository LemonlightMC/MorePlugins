package com.julizey.moreplugins.items;

public interface CustomItemProperty {

  public default String id() {
    return name().replaceAll(" ", "").toLowerCase();
  }

  public String name();

}
