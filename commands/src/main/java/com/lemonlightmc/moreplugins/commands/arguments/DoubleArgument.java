package com.lemonlightmc.moreplugins.commands.arguments;

import org.bukkit.command.CommandException;

import com.lemonlightmc.moreplugins.commands.argumentsbase.Argument;
import com.lemonlightmc.moreplugins.commands.argumentsbase.ArgumentType;
import com.lemonlightmc.moreplugins.commands.argumentsbase.CommandArguments;

public class DoubleArgument extends Argument<Double, DoubleArgument> {

  public DoubleArgument(String name) {
    super(name, Double.class, ArgumentType.PRIMITIVE_DOUBLE);
  }

  public DoubleArgument getInstance() {
    return this;
  }

  @Override
  public <Source> Double parseArgument(
      Context<Source> cmdCtx,
      String key,
      CommandArguments previousArgs) throws CommandException {
    return null;
  }
}
