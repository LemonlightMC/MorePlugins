package com.lemonlightmc.moreplugins.commands.exceptions;

public class InvalidCommandNameException extends RuntimeException {

  public InvalidCommandNameException(String commandName) {
    super(
        "Invalid command with name '" + commandName + "' cannot be registered!");
  }
}
