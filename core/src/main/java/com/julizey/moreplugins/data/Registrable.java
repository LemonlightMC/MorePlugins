package com.julizey.moreplugins.data;

public interface Registrable {
  public String getID();

  public default void onRegister() {
  }

  public default void onRemove() {
  }
}
