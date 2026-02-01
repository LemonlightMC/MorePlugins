package com.lemonlightmc.moreplugins.commands.executors;

import com.lemonlightmc.moreplugins.base.MorePlugins;
import com.lemonlightmc.moreplugins.base.PluginBase;
import com.lemonlightmc.moreplugins.commands.AbstractCommand;
import com.lemonlightmc.moreplugins.commands.CommandSource;
import com.lemonlightmc.moreplugins.commands.SimpleCommand;
import com.lemonlightmc.moreplugins.commands.SimpleSubCommand;
import com.lemonlightmc.moreplugins.commands.arguments.SpecialArguments.LiteralArgument;
import com.lemonlightmc.moreplugins.commands.argumentsbase.Argument;
import com.lemonlightmc.moreplugins.commands.argumentsbase.CommandArguments;
import com.lemonlightmc.moreplugins.commands.argumentsbase.ParsedArgument;
import com.lemonlightmc.moreplugins.commands.argumentsbase.StringReader;
import com.lemonlightmc.moreplugins.commands.exceptions.CommandSyntaxException;
import com.lemonlightmc.moreplugins.commands.suggestions.SuggestionInfo;
import com.lemonlightmc.moreplugins.commands.suggestions.Suggestions;
import com.lemonlightmc.moreplugins.messages.StringTooltip;
import com.lemonlightmc.moreplugins.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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
    PluginBase.getInstanceScheduler()
        .runAsync(() -> {
          if (sender == null) {
            return;
          }
          final String label = label0 == null || label0.length() == 0 ? cmd.getName() : label0;
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
            final CommandSource<CommandSender> source = CommandSource.from(sender);
            final CommandArguments cmdArgs = parse(source, args);
            if (cmdArgs == null) {
              return;
            }
            final ExecutionInfo<CommandSender> info = new ExecutionInfo<CommandSender>(source, cmdArgs);
            cmd.run(info);
          } catch (final Throwable ex) {
            throw new CommandException(
                "Exception while executing command '" +
                    label +
                    "' in plugin " +
                    MorePlugins.instance.getFullName(),
                ex);
          }
        });
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

    final List<String> tooltips = new ArrayList<>();
    final String token = args.length > 0 ? args[args.length - 1] : null;

    try {
      final CommandSource<CommandSender> source = CommandSource.from(sender);
      final CommandArguments cmdArgs = parse(source, args);
      if (cmdArgs == null) {
        return tooltips;
      }
      final SuggestionInfo<CommandSender> info = new SuggestionInfo<CommandSender>(source, cmdArgs,
          cmdArgs.getLastRaw());

      final List<Suggestions<CommandSender>> temp = cmd.tabComplete(info);
      if (temp != null) {
        tooltips.addAll(temp.parallelStream()
            .map((final Suggestions<CommandSender> s) -> s == null ? null : s.suggest(info))
            .flatMap(col -> col == null ? Stream.empty() : col.stream())
            .map((final StringTooltip t) -> {
              if (token == null) {
                return t.resolve();
              } else if (t.message().startsWith(token)) {
                return t.resolve();
              }
              return null;
            }).filter(v -> v != null && v.length() > 0).toList());
      }
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
    return tooltips;
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

  private CommandArguments parse(final CommandSource<CommandSender> source, final String[] args) {
    final StringReader reader = new StringReader(StringUtils.join(" ", args));
    return _parseArguments(cmd, reader, source, args);
  }

  private CommandArguments _parseArguments(AbstractCommand<?> thisCmd, final StringReader reader,
      final CommandSource<CommandSender> source, final String[] args) {
    int i = 0;
    boolean hadSubcommand = true;
    while (hadSubcommand && args.length > i && thisCmd.hasSubcommands()) {
      hadSubcommand = false;
      for (final SimpleSubCommand sub : cmd.getSubcommands()) {
        if (_isSubCommand(args[i], sub)) {
          if (!sub.checkRequirements(source)) {
            return null;
          }
          thisCmd = sub;
          i++;
          hadSubcommand = true;
          break;
        }
      }
    }

    final Map<String, ParsedArgument> parsedArgs = new HashMap<>();
    for (final Argument<?, ?> arg : cmd.getArguments()) {
      if (!arg.checkRequirements(source)) {
        return null;
      }
      final Object value = _parseArg(reader, source, arg);
      if (!arg.isListed()) {
        parsedArgs.put(arg.getName(), new ParsedArgument(arg.getName(), reader.getLastRead(), value));
      }
    }
    return new CommandArguments(parsedArgs, reader.getString());
  }

  private static Object _parseArg(final StringReader reader, final CommandSource<CommandSender> source,
      final Argument<?, ?> arg) {
    reader.point();
    try {
      final Object value = arg.parseArgument(source, reader, arg.getName());
      reader.revokePoint();
      return value;
    } catch (final CommandSyntaxException e) {
      source.sendError(e);
      reader.resetCursor();

    } catch (final Exception e) {
      source.sendError(arg.createError(reader, reader.getLastRead()));
      reader.resetCursor();
    }
    return null;
  }

  private static boolean _isSubCommand(final String arg, final SimpleSubCommand sub) {
    final Argument<?, ?> firstArg = sub.getArguments().getFirst();
    if (!firstArg.getType().isLiteral()) {
      return false;
    }
    return ((LiteralArgument) firstArg).getLiteral().equals(arg);
  }
}
