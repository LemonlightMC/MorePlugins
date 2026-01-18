package com.lemonlightmc.moreplugins.commands.exceptions;

import com.lemonlightmc.moreplugins.commands.argumentsbase.StringReader;
import com.lemonlightmc.moreplugins.exceptions.ExceptionContainer;

public class CommandSyntaxException extends Exception {
  public static final int CONTEXT_AMOUNT = 10;
  public static boolean ENABLE_COMMAND_STACK_TRACES = true;

  private final String message;
  private final StringReader reader;

  public CommandSyntaxException(final StringReader reader, final String message) {
    super(message, null, ENABLE_COMMAND_STACK_TRACES, ENABLE_COMMAND_STACK_TRACES);
    this.message = message;
    this.reader = reader;
  }

  public String getRawMessage() {
    return message;
  }

  public String getInput() {
    return reader.getString();
  }

  public int getCursor() {
    return reader.getCursor();
  }

  public StringReader getReader() {
    return reader;
  }

  @Override
  public String getMessage() {
    String message = this.message;
    final String context = getContext();
    if (context != null) {
      message += " at position " + getCursor() + ": " + context;
    }
    return message;
  }

  public String getContext() {
    if (getInput() == null || getCursor() < 0) {
      return null;
    }
    final StringBuilder builder = new StringBuilder();
    final int cursor = Math.min(getInput().length(), this.getCursor());

    if (cursor > CONTEXT_AMOUNT) {
      builder.append("...");
    }

    builder.append(getInput().substring(Math.max(0, cursor - CONTEXT_AMOUNT), cursor));
    builder.append("<--[HERE]");

    return builder.toString();
  }

  public static class CommandSyntaxExceptionContainer extends ExceptionContainer<CommandSyntaxException> {
    public CommandSyntaxExceptionContainer(final ExceptionContainerFunction function) {
      super(CommandSyntaxException.class, function);
    }

    public CommandSyntaxExceptionContainer(final String message) {
      super(CommandSyntaxException.class, message);
    }
  }
}
