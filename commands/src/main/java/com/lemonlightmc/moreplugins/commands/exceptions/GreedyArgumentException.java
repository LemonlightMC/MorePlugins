package com.lemonlightmc.moreplugins.commands.exceptions;

import java.util.List;

import com.lemonlightmc.moreplugins.commands.argumentsbase.Argument;

public class GreedyArgumentException extends RuntimeException {

  public GreedyArgumentException(final String argName, final List<Argument<?, ?>> arguments) {
    super("Argument " + argName
        + " does not satisfies GreedyStringArgument requirements! Only one GreedyStringArgument can be declared, at the end of a List"
        + "Found arguments: " + buildArgsStr(arguments));
  }

  private static String buildArgsStr(final List<Argument<?, ?>> arguments) {
    final StringBuilder builder = new StringBuilder();
    for (final Argument<?, ?> arg : arguments) {
      builder.append(arg.getName()).append("<").append(arg.getClass().getSimpleName()).append("> ");
    }
    return builder.toString();
  }
}
