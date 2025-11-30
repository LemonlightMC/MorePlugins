package com.lemonlightmc.moreplugins.exceptions;

public class InvalidCommandNameException extends RuntimeException {

  public InvalidCommandNameException(String commandName) {
    super(
        "Invalid command with name '" + commandName + "' cannot be registered!");
  }
}
