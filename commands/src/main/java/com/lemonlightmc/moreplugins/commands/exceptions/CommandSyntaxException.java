package com.lemonlightmc.moreplugins.commands.exceptions;

import com.lemonlightmc.moreplugins.commands.argumentsbase.StringReader;
import com.lemonlightmc.moreplugins.exceptions.DynamicExceptionFunction;
import com.lemonlightmc.moreplugins.exceptions.DynamicExceptionType;
import com.lemonlightmc.moreplugins.exceptions.SimpleExceptionType;

public class CommandSyntaxException extends Exception {
  public static final int CONTEXT_AMOUNT = 10;
  public static boolean ENABLE_COMMAND_STACK_TRACES = true;

  private final String message;
  private final String input;
  private final int cursor;
  private final SimpleExceptionType<?, StringReader> type;

  public CommandSyntaxException(final SimpleExceptionType<?, StringReader> type, final String message) {
    super(message, null, ENABLE_COMMAND_STACK_TRACES, ENABLE_COMMAND_STACK_TRACES);
    this.type = type;
    this.message = message;
    this.input = null;
    this.cursor = -1;
  }

  public CommandSyntaxException(final SimpleExceptionType<?, StringReader> type, final String message,
      final String input,
      final int cursor) {
    super(message, null, ENABLE_COMMAND_STACK_TRACES, ENABLE_COMMAND_STACK_TRACES);
    this.type = type;
    this.message = message;
    this.input = input;
    this.cursor = cursor;
  }

  public SimpleExceptionType<?, StringReader> getType() {
    return type;
  }

  public String getRawMessage() {
    return message;
  }

  public String getInput() {
    return input;
  }

  public int getCursor() {
    return cursor;
  }

  @Override
  public String getMessage() {
    String message = this.message;
    final String context = getContext();
    if (context != null) {
      message += " at position " + cursor + ": " + context;
    }
    return message;
  }

  public String getContext() {
    if (input == null || cursor < 0) {
      return null;
    }
    final StringBuilder builder = new StringBuilder();
    final int cursor = Math.min(input.length(), this.cursor);

    if (cursor > CONTEXT_AMOUNT) {
      builder.append("...");
    }

    builder.append(input.substring(Math.max(0, cursor - CONTEXT_AMOUNT), cursor));
    builder.append("<--[HERE]");

    return builder.toString();
  }

  public static class DynamicCommandException<T extends DynamicExceptionFunction>
      extends
      DynamicExceptionType<CommandSyntaxException, T, StringReader> {

    public DynamicCommandException(final T function) {
      super(CommandSyntaxException.class, function);
    }
  }

  public static class SimpleCommandException
      extends SimpleExceptionType<CommandSyntaxException, StringReader> {

    public SimpleCommandException(final String message) {
      super(CommandSyntaxException.class, message);
    }
  }
}
