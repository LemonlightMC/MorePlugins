package com.lemonlightmc.moreplugins.commands;

import com.lemonlightmc.moreplugins.base.PluginBase;
import com.lemonlightmc.moreplugins.commands.executors.InternalExecutor;
import java.lang.reflect.Field;
import java.util.Iterator;
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
  protected static String playerOnlyMessage;
  protected static String notPlayerMessage;

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
    if (command.getAliases().length == 0) {
      throw new IllegalArgumentException("At least one alias must be provided");
    }
    for (final String alias : command.getAliases()) {
      try {
        final Command cmd = new InternalExecutor(alias);
        cmd.setLabel(alias.toLowerCase());

        getCommandMap().register(namespace, cmd);
        getKnownCommandMap().put(namespace + ":" + alias.toLowerCase(), cmd);
        getKnownCommandMap().put(alias.toLowerCase(), cmd);
      } catch (final Exception e) {
        e.printStackTrace();
      }
    }
  }

  public static void unregister(final SimpleCommand command) {
    final CommandMap map = getCommandMap();
    try {
      final Iterator<Command> iterator = getKnownCommandMap().values().iterator();
      while (iterator.hasNext()) {
        final Command cmd = iterator.next();
        if (cmd instanceof InternalExecutor &&
            command.getName() == ((InternalExecutor) cmd).getName()) {
          cmd.unregister(map);
          iterator.remove();
        }
      }
    } catch (final Exception e) {
      throw new RuntimeException("Could not unregister command", e);
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

  public static void setPlayerOnlyMessage(final String playerOnlyMessage) {
    CommandManager.playerOnlyMessage = playerOnlyMessage;
  }

  public static String getPlayerOnlyMessage() {
    return playerOnlyMessage;
  }

  public static void setNotPlayerMessage(final String notPlayerMessage) {
    CommandManager.notPlayerMessage = notPlayerMessage;
  }

  public static String getNotPlayerMessage() {
    return notPlayerMessage;
  }
}
