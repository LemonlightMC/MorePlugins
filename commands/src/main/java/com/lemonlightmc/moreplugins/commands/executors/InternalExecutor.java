package com.lemonlightmc.moreplugins.commands.executors;

import com.lemonlightmc.moreplugins.base.MorePlugins;
import com.lemonlightmc.moreplugins.commandbase.AbstractCommand;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InternalExecutor extends Command {

  public InternalExecutor(String name) {
    super(name);
  }

  private void executeInternal(
      CommandSender sender,
      String commandLabel,
      String[] args) {
  }

  private List<String> tabCompleteInternal(
      CommandSender sender,
      String alias,
      String[] args,
      Location location) {
    return List.of();
  }

  @Override
  public boolean execute(CommandSender sender, String label0, String[] args0) {
    if (sender == null)
      throw new IllegalArgumentException("Sender is null");
    String label = label0 == null ? this.getLabel() : label0;
    String[] args = args0 == null ? new String[0] : args0;
    boolean success = false;

    if (!MorePlugins.instance.isEnabled()) {
      throw new CommandException(
          "Cannot execute command '" +
              label +
              "' in plugin " +
              MorePlugins.instance.getDescription().getFullName() +
              " - plugin is disabled.");
    }

    if (!testPermission(sender)) {
      return true;
    }

    try {
      Bukkit
          .getScheduler()
          .runTaskAsynchronously(
              MorePlugins.instance,
              () -> {
                executeInternal(sender, label, args);
              });
    } catch (Throwable ex) {
      throw new CommandException(
          "Unhandled exception executing command '" +
              label +
              "' in plugin " +
              MorePlugins.instance.getDescription().getFullName(),
          ex);
    }

    if (!success && usageMessage.length() > 0) {
      for (String line : usageMessage.replace("<command>", label).split("\n")) {
        sender.sendMessage(line);
      }
    }

    return success;
  }

  @Override
  public List<String> tabComplete(
      CommandSender sender,
      String alias0,
      String[] args0,
      Location location) {
    if (sender == null)
      throw new IllegalArgumentException("Sender is null");
    String alias = alias0 == null ? this.getLabel() : alias0;
    String[] args = args0 == null ? new String[0] : args0;

    List<String> completions = null;
    try {
      Bukkit
          .getScheduler()
          .runTaskAsynchronously(
              MorePlugins.instance,
              () -> {
                tabCompleteInternal(sender, alias, args, location);
              });
    } catch (Throwable ex) {
      StringBuilder message = new StringBuilder();
      message
          .append("Unhandled exception during tab completion for command '/")
          .append(alias)
          .append(' ');
      for (String arg : args) {
        message.append(arg).append(' ');
      }
      message
          .deleteCharAt(message.length() - 1)
          .append("' in plugin ")
          .append(MorePlugins.instance.getDescription().getFullName());
      throw new CommandException(message.toString(), ex);
    }

    String lastWord = args[args.length - 1];
    if (completions == null) {
      if (args.length == 0)
        completions = List.of();
      Player senderPlayer = sender instanceof Player ? (Player) sender : null;
      ArrayList<String> matchedPlayers = new ArrayList<>();
      for (Player player : sender.getServer().getOnlinePlayers()) {
        String name = player.getName();
        if ((senderPlayer == null || senderPlayer.canSee(player))) {
          matchedPlayers.add(name);
        }
      }
    }
    completions = AbstractCommand.copyPartialMatches(
        lastWord,
        completions,
        new ArrayList<>());
    completions.sort(String.CASE_INSENSITIVE_ORDER);
    return completions;
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder(super.toString());
    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
    stringBuilder
        .append(", ")
        .append(MorePlugins.instance.getDescription().getFullName())
        .append(')');
    return stringBuilder.toString();
  }
}
