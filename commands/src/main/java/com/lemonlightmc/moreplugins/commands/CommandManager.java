package com.lemonlightmc.moreplugins.commands;

import com.lemonlightmc.moreplugins.base.PluginBase;
import com.lemonlightmc.moreplugins.commands.exceptions.MissingCommandExecutorException;
import com.lemonlightmc.moreplugins.commands.executors.InternalExecutor;
import com.lemonlightmc.moreplugins.messages.Logger;

import java.lang.reflect.Field;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;

public class CommandManager {

  private static String namespace = PluginBase.getInstance().getKey();
  private static final Field COMMAND_MAP_FIELD;
  private static final Field KNOWN_COMMANDS_FIELD;
  private static CommandMap commandMap;
  private static Map<String, Command> knownCommandMap;

  protected static String permissionMessage = "&cYou dont have permission to use this command!";
  protected static String requirementsMessage = "&cYou dont fulfill all requirements use this command!";

  static {
    try {
      COMMAND_MAP_FIELD = SimplePluginManager.class.getDeclaredField("commandMap");
      COMMAND_MAP_FIELD.setAccessible(true);
    } catch (final NoSuchFieldException e) {
      throw new RuntimeException(e);
    }

    try {
      KNOWN_COMMANDS_FIELD = SimpleCommandMap.class.getDeclaredField("knownCommands");
      KNOWN_COMMANDS_FIELD.setAccessible(true);
    } catch (final NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
  }

  @SuppressWarnings("unchecked")
  public static void init() {
    try {
      Object obj = COMMAND_MAP_FIELD.get(
          Bukkit.getServer().getPluginManager());
      if (obj == null || !(obj instanceof CommandMap)) {
        throw new NullPointerException("Failed to retrieve Command Map");
      } else {
        CommandManager.commandMap = (CommandMap) obj;
      }

      obj = KNOWN_COMMANDS_FIELD.get(commandMap);
      if (obj == null || obj.getClass().equals(Map.class)) {
        throw new NullPointerException("Failed to retrieve Known Commands Map");
      } else {
        knownCommandMap = (Map<String, Command>) obj;
      }
    } catch (final Exception e) {
      throw new RuntimeException("Could not init CommandManager", e);
    }
  }

  public static CommandMap getCommandMap() {
    if (commandMap == null) {
      init();
    }
    return commandMap;
  }

  public static Map<String, Command> getKnownCommandMap() {
    if (knownCommandMap == null) {
      init();
    }
    return knownCommandMap;
  }

  public static void register(final SimpleCommand command) {
    register(command, namespace);
  }

  public static void register(final SimpleCommand command, final String namespace) {
    if (command.aliases.size() == 0) {
      throw new IllegalArgumentException("At least one alias must be provided");
    }
    if (!command.hasExecutors() && (!command.subcommands.isEmpty() || command.hasArguments())) {
      throw new MissingCommandExecutorException(command.getName());
    }
    final InternalExecutor cmd = new InternalExecutor(command);
    for (final String alias : command.aliases) {
      try {
        cmd.setLabel(alias);
        getCommandMap().register(namespace, cmd);
        getKnownCommandMap().put(namespace + ":" + alias, cmd);
        getKnownCommandMap().put(alias, cmd);
      } catch (final Exception e) {
        Logger.warn("Failed to register a command: " + command.getName());
        e.printStackTrace();
      }
    }
  }

  public static void unregister(final SimpleCommand command) {
    try {
      final Command cmd = getCommandMap().getCommand(namespace);
      if (cmd != null) {
        cmd.unregister(getCommandMap());
      }
      for (final String alias : command.aliases) {
        unregister(alias);
      }
    } catch (final Exception e) {
      Logger.warn("Failed to unregister a command: " + command.getName());
      e.printStackTrace();
    }
  }

  public static void unregister(final String alias) {
    try {
      Command cmd = getKnownCommandMap().get(alias);
      if (cmd != null && cmd instanceof InternalExecutor &&
          ((InternalExecutor) cmd).getAliases().contains(alias)) {
        cmd.unregister(getCommandMap());
        getKnownCommandMap().remove(alias);
      }
      cmd = getKnownCommandMap().get(namespace + ":" + alias);
      if (cmd != null && cmd instanceof InternalExecutor &&
          ((InternalExecutor) cmd).getAliases().contains(alias)) {
        cmd.unregister(getCommandMap());
        getKnownCommandMap().remove(namespace + ":" + alias);
      }
    } catch (final Exception e) {
      Logger.warn("Failed to unregister a command alias: " + alias);
      e.printStackTrace();
    }
  }

  public static void unregisterAll() {
    try {
      getCommandMap().clearCommands();
      for (final String key : getKnownCommandMap().keySet()) {
        unregister(key);
      }
    } catch (final Exception e) {
      Logger.warn("Failed to unregister all command");
      e.printStackTrace();
    }
  }

  public static void setNamespace(final String namespace) {
    if (Utils.isInvalidNamespace(namespace)) {
      return;
    }
    CommandManager.namespace = namespace;
  }

  public static String getNamespace() {
    return namespace;
  }

  public static void setPermissionMessage(final String permissionMessage) {
    CommandManager.permissionMessage = permissionMessage;
  }

  public static String getPermissionMessage() {
    return permissionMessage;
  }

  public static void setRequirementsMessage(final String requirementsMessage) {
    CommandManager.requirementsMessage = requirementsMessage;
  }

  public static String getRequirementsMessage() {
    return requirementsMessage;
  }
}
