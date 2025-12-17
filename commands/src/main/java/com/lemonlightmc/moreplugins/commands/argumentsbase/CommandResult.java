package com.lemonlightmc.moreplugins.commands.argumentsbase;

import java.util.Arrays;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.lemonlightmc.moreplugins.commands.CommandSource;

public record CommandResult(Command command, String[] args) {

  public boolean execute(final CommandSender sender) {
    return command.execute(sender, command.getLabel(), args);
  }

  public boolean execute(final CommandSource<CommandSender> source) {
    return command.execute(source.sender(), command.getLabel(), args);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final CommandResult that = (CommandResult) o;
    return command.equals(that.command) && Arrays.equals(args, that.args);
  }

  @Override
  public int hashCode() {
    return 31 * command.hashCode() + Arrays.hashCode(args);
  }

  @Override
  public String toString() {
    return "CommandResult [command=" + command + ", args=" + Arrays.toString(args) + "]";
  }
}
