package com.lemonlightmc.moreplugins.commands.executors;

import com.lemonlightmc.moreplugins.base.MorePlugins;
import com.lemonlightmc.moreplugins.base.PluginBase;
import com.lemonlightmc.moreplugins.commands.SimpleCommand;
import com.lemonlightmc.moreplugins.commands.Utils;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

public class InternalExecutor extends Command {

  private final SimpleCommand cmd;

  public InternalExecutor(final SimpleCommand cmd) {
    super(cmd.getName());
    this.cmd = cmd;
  }

  @Override
  public boolean execute(final CommandSender sender, final String label0, final String[] args0) {
    if (sender == null) {
      return true;
    }
    final String label = label0 == null ? this.getLabel() : label0;
    final String[] args = args0 == null ? new String[0] : args0;
    if (!MorePlugins.instance.isEnabled()) {
      throw new CommandException(
          "Cannot execute command '" +
              label +
              "' in plugin " +
              MorePlugins.instance.getDescription().getFullName() +
              " - plugin is disabled.");
    }

    try {
      PluginBase.getInstanceScheduler()
          .runAsync(() -> {
            cmd.execute(sender, label, args);
          });
    } catch (final Throwable ex) {
      throw new CommandException(
          "Unhandled exception executing command '" +
              label +
              "' in plugin " +
              MorePlugins.instance.getFullName(),
          ex);
    }
    return true;
  }

  @Override
  public List<String> tabComplete(
      final CommandSender sender,
      final String alias0,
      final String[] args0,
      final Location location) {
    if (sender == null) {
      return List.of();
    }
    final String alias = alias0 == null ? this.getLabel() : alias0;
    final String[] args = args0 == null ? new String[0] : args0;

    List<String> completions = null;
    try {
      completions = cmd.tabComplete(sender, alias, args);
    } catch (final Throwable ex) {
      final StringBuilder message = new StringBuilder();
      message
          .append("Unhandled exception during tab completion for command '/")
          .append(alias)
          .append(' ');
      for (final String arg : args) {
        message.append(arg).append(' ');
      }
      message
          .deleteCharAt(message.length() - 1)
          .append("' in plugin ")
          .append(MorePlugins.instance.getFullName());
      throw new CommandException(message.toString(), ex);
    }

    if (completions == null) {
      completions = List.of();
    }
    if (args.length == 0) {
      return completions;
    }
    completions = Utils.copyPartialMatches(
        args[args.length - 1],
        completions,
        new ArrayList<>());
    completions.sort(String.CASE_INSENSITIVE_ORDER);
    return completions;
  }

  @Override
  public String toString() {
    final StringBuilder stringBuilder = new StringBuilder(super.toString());
    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
    stringBuilder
        .append(", ")
        .append(MorePlugins.instance.getDescription().getFullName())
        .append(')');
    return stringBuilder.toString();
  }
}
