package com.lemonlightmc.moreplugins.commands.exceptions;

public class DuplicateArgumentException extends RuntimeException {

  public DuplicateArgumentException(final String argName) {
    super("Failed to register Argument" + argName + " because it is a duplicate!");
  }
}
