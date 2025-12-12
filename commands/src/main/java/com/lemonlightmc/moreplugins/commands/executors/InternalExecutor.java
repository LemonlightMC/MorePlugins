package com.lemonlightmc.moreplugins.commands.executors;

import com.lemonlightmc.moreplugins.base.MorePlugins;
import com.lemonlightmc.moreplugins.base.PluginBase;
import com.lemonlightmc.moreplugins.commands.SimpleCommand;
import com.lemonlightmc.moreplugins.commands.StringReader;
import com.lemonlightmc.moreplugins.commands.Utils;
import com.lemonlightmc.moreplugins.commands.argumentsbase.Argument;
import com.lemonlightmc.moreplugins.commands.argumentsbase.CommandArguments;
import com.lemonlightmc.moreplugins.commands.argumentsbase.ParsedArgument;
import com.lemonlightmc.moreplugins.commands.exceptions.CommandSyntaxException;
import com.lemonlightmc.moreplugins.messages.Logger;
import com.lemonlightmc.moreplugins.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            final CommandSource<CommandSender> source = Utils.toSource(sender);
            final CommandArguments cmdArgs = parse(source, args);
            final ExecutionInfo<CommandSender> info = Utils.toInfo(source, cmdArgs);

            cmd.execute(info);
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
      final String label0,
      final String[] args0,
      final Location location) {
    if (sender == null) {
      return List.of();
    }
    final String label = label0 == null ? this.getLabel() : label0;
    final String[] args = args0 == null ? new String[0] : args0;

    List<String> completions = null;
    try {
      final CommandSource<CommandSender> source = Utils.toSource(sender);
      final CommandArguments cmdArgs = parse(source, args);
      final ExecutionInfo<CommandSender> info = Utils.toInfo(source, cmdArgs);

      completions = cmd.tabComplete(info);
    } catch (final Throwable ex) {
      final StringBuilder message = new StringBuilder();
      message
          .append("Unhandled exception during tab completion for command '/")
          .append(label)
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

  public CommandArguments parse(CommandSource<CommandSender> source, final String[] args) {
    final String fullInput = StringUtils.join(" ", args);
    final ParserCommandArguments cmd_args = new ParserCommandArguments(null, null, fullInput);
    final StringReader reader = new StringReader(fullInput);

    for (final Argument<?, ?> arg : cmd.getArguments()) {
      if (!arg.isListed()) {
        continue;
      }

      try {
        final Object value = arg.parseArgument(source, reader, arg.getName(), cmd_args);
        cmd_args.add(arg.getName(), new ParsedArgument(arg.getName(), null, value));
      } catch (final CommandSyntaxException e) {
        Logger.warn("Failed to parse Argument " + arg.getName());
        e.printStackTrace();
      }
    }

    return cmd_args.finish();
  }

  private static class ParserCommandArguments extends CommandArguments {

    public ParserCommandArguments(final String fullInput) {
      super(new ParsedArgument[] {}, new HashMap<>(), fullInput);
    }

    public ParserCommandArguments(final ParsedArgument[] args, final Map<String, ParsedArgument> argsMap,
        final String fullInput) {
      super(args, argsMap, fullInput);
    }

    public void add(final String key, final ParsedArgument arg) {
      argsMap.put(key, arg);
      args = argsMap.values().toArray(ParsedArgument[]::new);
    }

    public CommandArguments finish() {
      return new CommandArguments(args, argsMap, fullInput);
    }
  }
}
