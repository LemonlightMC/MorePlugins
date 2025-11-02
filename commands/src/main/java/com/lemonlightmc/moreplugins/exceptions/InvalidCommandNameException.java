package com.lemonlightmc.moreplugins.exceptions;

public class InvalidCommandNameException extends RuntimeException {

  /**
   * Creates an InvalidCommandNameException
   * 
   * @param commandName the invalid command name
   */
  public InvalidCommandNameException(String commandName) {
    super(
        "Invalid command with name '" + commandName + "' cannot be registered!");
  }
}
