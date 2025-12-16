package com.lemonlightmc.moreplugins.commands.exceptions;

public class InvalidCommandNameException extends RuntimeException {

  public InvalidCommandNameException(final String commandName) {
    super(
        "Invalid command with name '" + commandName + "' cannot be registered!");
  }
}
