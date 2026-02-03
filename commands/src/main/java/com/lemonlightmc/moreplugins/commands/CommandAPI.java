package com.lemonlightmc.moreplugins.commands;

import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;

import com.lemonlightmc.moreplugins.commands.exceptions.MissingCommandExecutorException;
import com.lemonlightmc.moreplugins.commands.executors.InternalExecutor;
import com.lemonlightmc.moreplugins.messages.Logger;

public class CommandAPI {
  private static final Field COMMAND_MAP_FIELD;
  private static final Field KNOWN_COMMANDS_FIELD;
  private static CommandMap commandMap;
  private static Map<String, Command> knownCommandMap;

  static {
    try {
      COMMAND_MAP_FIELD = SimplePluginManager.class.getDeclaredField("commandMap");
      COMMAND_MAP_FIELD.setAccessible(true);
    } catch (final NoSuchFieldException | InaccessibleObjectException e) {
      throw new RuntimeException(e);
    }

    try {
      KNOWN_COMMANDS_FIELD = SimpleCommandMap.class.getDeclaredField("knownCommands");
      KNOWN_COMMANDS_FIELD.setAccessible(true);
    } catch (final NoSuchFieldException | InaccessibleObjectException e) {
      throw new RuntimeException(e);
    }
  }

  public static CommandMap getCommandMap() {
    if (commandMap == null) {
      try {
        final Object obj = COMMAND_MAP_FIELD.get(
            Bukkit.getServer().getPluginManager());
        if (obj == null || !(obj instanceof CommandMap)) {
          throw new IllegalStateException("CommandMap is not available");
        }
        CommandAPI.commandMap = (CommandMap) obj;
      } catch (final Exception e) {
        throw new IllegalStateException("Failed to retrieve CommandMap", e);
      }
    }
    return commandMap;
  }

  @SuppressWarnings("unchecked")
  public static Map<String, Command> getKnownCommandMap() {
    if (knownCommandMap == null) {
      try {
        final Object obj = KNOWN_COMMANDS_FIELD.get(commandMap);
        if (obj == null || !Map.class.isAssignableFrom(obj.getClass())) {
          throw new IllegalStateException("knownCommandsMap is not available");
        }
        CommandAPI.knownCommandMap = (Map<String, Command>) obj;
      } catch (final Exception e) {
        throw new IllegalStateException("Failed to retrieve knownCommandsMap", e);
      }
    }
    return knownCommandMap;
  }

  public static boolean isRegistered(final NamespacedKey key) {
    return key == null ? false : getKnownCommandMap().containsKey(key.toString());
  }

  public static boolean isRegistered(final String key) {
    if (key == null || key.isEmpty()) {
      return false;
    }
    return getKnownCommandMap().containsKey(key);
  }

  public static boolean isRegistered(final SimpleCommand command) {
    return command == null ? false : getKnownCommandMap().containsKey(command.getName().toString());
  }

  public static void register(final SimpleCommand command) {
    if (command == null) {
      throw new IllegalArgumentException("Command cannot be null");
    }
    if (command.aliases.size() == 0) {
      throw new IllegalArgumentException("At least one alias must be provided");
    }
    if (!command.hasExecutors() && (!command.subcommands.isEmpty() || command.hasArguments())) {
      throw new MissingCommandExecutorException(command.getName().toString());
    }
    final InternalExecutor cmd = new InternalExecutor(command);
    for (final String alias : command.aliases) {
      try {
        cmd.setLabel(alias);
        if (getKnownCommandMap().containsKey(alias) && getKnownCommandMap().get(alias) != null) {
          Logger.warn("Overriding existing command: " + getKnownCommandMap().get(alias).getName());
        }
        getCommandMap().register(command.getNamespace(), cmd);
        getKnownCommandMap().put(command.getName().toString(), cmd);
        getKnownCommandMap().put(alias, cmd);
      } catch (final Exception e) {
        Logger.warn("Failed to register a command: " + command.getName());
        e.printStackTrace();
      }
    }
  }

  public static void unregister(final SimpleCommand command) {
    if (command == null) {
      return;
    }
    for (final String alias : command.aliases) {
      unregister(alias);
      unregister(command.getNamespace() + ":" + alias);
    }
  }

  public static void unregister(final String key) {
    if (key == null || key.isEmpty()) {
      return;
    }
    try {
      Command cmd = getKnownCommandMap().get(key);
      if (cmd != null) {
        cmd.unregister(getCommandMap());
        getKnownCommandMap().remove(key);
      }
    } catch (final Exception e) {
      Logger.warn("Failed to unregister a command: " + key);
      e.printStackTrace();
    }
  }

  public static void unregisterAll(String namespace) {
    if (namespace == null || namespace.isEmpty()) {
      return;
    }
    for (String key : getKnownCommandMap().keySet()) {
      if (key.startsWith(namespace + ":")) {
        CommandAPI.unregister(key);
      }
    }
  }
}
