package com.lemonlightmc.moreplugins.commands.executors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.minecart.CommandMinecart;
import org.bukkit.permissions.Permissible;

import com.lemonlightmc.moreplugins.base.PluginBase;
import com.lemonlightmc.moreplugins.commands.CommandSource;
import com.lemonlightmc.moreplugins.commands.SimpleCommand;
import com.lemonlightmc.moreplugins.commands.SimpleSubCommand;
import com.lemonlightmc.moreplugins.commands.arguments.SpecialArguments.LiteralArgument;
import com.lemonlightmc.moreplugins.commands.argumentsbase.Argument;
import com.lemonlightmc.moreplugins.commands.argumentsbase.CommandArguments;
import com.lemonlightmc.moreplugins.commands.argumentsbase.ParsedArgument;
import com.lemonlightmc.moreplugins.commands.argumentsbase.StringReader;
import com.lemonlightmc.moreplugins.commands.exceptions.CommandException;
import com.lemonlightmc.moreplugins.commands.exceptions.CommandSyntaxException;
import com.lemonlightmc.moreplugins.commands.suggestions.SuggestionInfo;
import com.lemonlightmc.moreplugins.commands.suggestions.Suggestions;
import com.lemonlightmc.moreplugins.messages.Logger;
import com.lemonlightmc.moreplugins.messages.MessageFormatter;

public class CommandHandler {
  public static final int MAX_TABCOMPLETIONS = 512;

  public static void run(final SimpleCommand cmd, final CommandSender sender, final String[] args) {
    try {
      final CommandSource<CommandSender> source = CommandSource.from(sender);
      final CommandArguments cmdArgs = parse(cmd, source, args == null ? new String[0] : args);
      if (cmdArgs == null) {
        return;
      }
      final ExecutionInfo<CommandSender> info = new ExecutionInfo<CommandSender>(source, cmdArgs);
      cmd.run(info, 0);
    } catch (final Throwable ex) {
      throw new CommandException(
          "Exception while executing command '" +
              cmd.getName() +
              "' in plugin " +
              PluginBase.getInstance().getFullName(),
          ex);
    }
  }

  public static List<String> tabComplete(
      final SimpleCommand cmd,
      final CommandSender sender,
      String[] args) {
    if (args == null) {
      args = new String[0];
    }
    try {
      final CommandSource<CommandSender> source = CommandSource.from(sender);
      final CommandArguments cmdArgs = parse(cmd, source, args);
      if (cmdArgs == null) {
        return List.of();
      }
      final SuggestionInfo<CommandSender> info = new SuggestionInfo<CommandSender>(source, cmdArgs,
          cmdArgs.getLastRaw());

      final List<Suggestions<CommandSender>> temp = cmd.tabComplete(info, 0);
      if (temp == null) {
        return List.of();
      }
      final List<String> tabCompletions = new ArrayList<>();
      for (final Suggestions<CommandSender> suggestion : temp) {
        if (suggestion != null) {
          final Collection<String> list = suggestion.suggest(info);
          if (list != null && !list.isEmpty()) {
            tabCompletions.addAll(list);
          }
        }
      }
      final String token = args.length > 0 ? args[args.length - 1] : null;
      tabCompletions.removeIf(s -> s == null || s.length() == 0 || token != null && !startsWithIgnoreCase(s, token));
      if (tabCompletions.size() > MAX_TABCOMPLETIONS) {
        return tabCompletions.subList(0, MAX_TABCOMPLETIONS);
      } else {
        return tabCompletions;
      }
    } catch (final Throwable ex) {
      final StringBuilder message = new StringBuilder("Unhandled exception during tab completion for command '/");
      message
          .append(cmd.getName().toString())
          .append(' ')
          .append(String.join(" ", args))
          .append("' in plugin ")
          .append(PluginBase.getInstance().getFullName());
      throw new CommandException(message.toString(), ex);
    }
  }

  private static CommandArguments parse(final SimpleCommand cmd, final CommandSource<CommandSender> source,
      final String[] args) {
    final StringReader reader = new StringReader(String.join(" ", args));
    return _parseArguments(cmd, cmd, reader, source, args);
  }

  private static CommandArguments _parseArguments(final SimpleCommand cmd, AbstractCommand<?> thisCmd,
      final StringReader reader,
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

  private static boolean startsWithIgnoreCase(
      final String string,
      final String prefix) {
    if (prefix == null || prefix.length() == 0)
      return true;
    if (string == null || string.length() == 0)
      return false;
    if (string.length() < prefix.length()) {
      return false;
    }
    return string.regionMatches(true, 0, prefix, 0, prefix.length());
  }

  public static boolean shouldHandle(final SimpleCommand cmd, final CommandSender sender, final String label) {
    if (sender == null) {
      return false;
    }
    if (!PluginBase.getInstance().isEnabled()) {
      Logger.warn(
          "Cannot execute command '" +
              cmd.getName() +
              "' in plugin " +
              PluginBase.getInstance().getDescription().getFullName() +
              " - plugin is disabled.");
      return false;
    }
    if (label == null || label.isBlank() || (!cmd.getKey().equals(label) || !cmd.getAliases().contains(label))) {
      return false;
    }
    return true;
  }

  public static void broadcastCommandMessage(final CommandSender source, final String message,
      final boolean sendToSource) {
    final String colored = MessageFormatter.format("&7&o[" + source.getName() + ": " + message + "&7&o]");

    if (source instanceof final BlockCommandSender blockSender) {
      if (!blockSender.getBlock().getWorld().getGameRuleValue(GameRule.COMMAND_BLOCK_OUTPUT)) {
        Logger.info(colored);
        return;
      }
    } else if (source instanceof final CommandMinecart cartSender) {
      if (!cartSender.getWorld().getGameRuleValue(GameRule.COMMAND_BLOCK_OUTPUT)) {
        Logger.info(colored);
        return;
      }
    }

    if (sendToSource && !(source instanceof ConsoleCommandSender)) {
      source.sendMessage(colored);
    }
    for (final Permissible user : Bukkit.getPluginManager().getPermissionSubscriptions("bukkit.broadcast.admin")) {
      if (user instanceof CommandSender && user.hasPermission("bukkit.broadcast.admin")) {
        final CommandSender target = (CommandSender) user;

        if (target != source || user instanceof ConsoleCommandSender) {
          target.sendMessage(colored);
        }
      }
    }
  }
}
