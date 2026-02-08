package com.lemonlightmc.moreplugins.commands.exceptions;

public class MissingCommandExecutorException extends RuntimeException {

  public MissingCommandExecutorException(final String commandName) {
    super("Cant register a Command " + commandName + " because it does not declare any executors!");
  }
}
