package com.lemonlightmc.moreplugins.commands;

import com.lemonlightmc.moreplugins.commandbase.SimpleCommand;
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

  private static String namespace;
  private static final Field COMMAND_MAP_FIELD;
  private static final Field KNOWN_COMMANDS_FIELD;

  static {
    try {
      COMMAND_MAP_FIELD = SimplePluginManager.class.getDeclaredField("commandMap");
      COMMAND_MAP_FIELD.setAccessible(true);
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    }

    try {
      KNOWN_COMMANDS_FIELD = SimpleCommandMap.class.getDeclaredField("knownCommands");
      KNOWN_COMMANDS_FIELD.setAccessible(true);
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
  }

  public static CommandMap getCommandMap() {
    try {
      return (CommandMap) COMMAND_MAP_FIELD.get(
          Bukkit.getServer().getPluginManager());
    } catch (Exception e) {
      throw new RuntimeException("Could not get CommandMap", e);
    }
  }

  @SuppressWarnings("unchecked")
  public static Map<String, Command> getKnownCommandMap() {
    try {
      return (Map<String, Command>) KNOWN_COMMANDS_FIELD.get(getCommandMap());
    } catch (Exception e) {
      throw new RuntimeException("Could not get known commands map", e);
    }
  }

  public static void register(SimpleCommand command) {
    if (command.getAliases().length == 0) {
      throw new IllegalArgumentException("At least one alias must be provided");
    }
    for (String alias : command.getAliases()) {
      try {
        Command cmd = new InternalExecutor(alias);
        cmd.setLabel(alias.toLowerCase());

        getCommandMap().register(namespace, cmd);
        getKnownCommandMap().put(namespace + ":" + alias.toLowerCase(), cmd);
        getKnownCommandMap().put(alias.toLowerCase(), cmd);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public static void unregister(SimpleCommand command) {
    CommandMap map = getCommandMap();
    try {
      Iterator<Command> iterator = getKnownCommandMap().values().iterator();
      while (iterator.hasNext()) {
        Command cmd = iterator.next();
        if (cmd instanceof InternalExecutor &&
            command.getName() == ((InternalExecutor) cmd).getName()) {
          cmd.unregister(map);
          iterator.remove();
        }
      }
    } catch (Exception e) {
      throw new RuntimeException("Could not unregister command", e);
    }
  }

  public static void setNamespace(String namespace) {
    if (Utils.isInvalidNamespace(namespace)) {
      return;
    }
    CommandManager.namespace = namespace;
  }

  public static String getNamespace() {
    return namespace;
  }
}
