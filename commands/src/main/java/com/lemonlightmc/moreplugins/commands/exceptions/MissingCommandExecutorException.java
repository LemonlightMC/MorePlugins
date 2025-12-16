package com.lemonlightmc.moreplugins.commands.exceptions;

public class MissingCommandExecutorException extends RuntimeException {

  public MissingCommandExecutorException(final String commandName) {
    super("Command " + commandName + " does not declare any executors or executable subcommands!");
  }
}
