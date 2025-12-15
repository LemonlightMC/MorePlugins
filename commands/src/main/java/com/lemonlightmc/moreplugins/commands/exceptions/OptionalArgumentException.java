package com.lemonlightmc.moreplugins.commands.exceptions;

public class OptionalArgumentException extends RuntimeException {
  public OptionalArgumentException(String argName1, String argName2) {
    super(
        "Failed to register required argument " + argName1
            + " because it cannot follow the optional argument " + argName2);
  }

  public OptionalArgumentException(String argName1) {
    super(
        "Failed to register required argument " + argName1
            + " because it cannot follow the optional argument!");
  }
}
