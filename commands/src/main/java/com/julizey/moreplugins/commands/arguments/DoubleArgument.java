package com.julizey.moreplugins.commands.arguments;

import com.julizey.moreplugins.commands.argumentsbase.Argument;
import com.julizey.moreplugins.commands.argumentsbase.ArgumentType;
import com.julizey.moreplugins.commands.argumentsbase.CommandArguments;
import org.bukkit.command.CommandException;

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
    CommandArguments previousArgs
  ) throws CommandException {
    return null;
  }
}
