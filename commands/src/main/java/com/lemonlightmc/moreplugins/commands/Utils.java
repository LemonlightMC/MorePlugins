package com.lemonlightmc.moreplugins.commands;

import com.lemonlightmc.moreplugins.commands.executors.Executors.ExecutorType;
import com.lemonlightmc.moreplugins.messages.Logger;
import com.lemonlightmc.moreplugins.messages.MessageFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.ProxiedCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.CommandMinecart;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;

public class Utils {

  static final Pattern NAMESPACE_PATTERN = Pattern.compile("[0-9a-z_.-]+");

  public static com.lemonlightmc.moreplugins.commands.executors.Executors.ExecutorType[] prioritiesForSender(
      final CommandSender sender) {
    if (sender == null) {
      return null;
    }
    if (sender instanceof Player) {
      return new ExecutorType[] { ExecutorType.PLAYER, ExecutorType.NATIVE, ExecutorType.ALL };
    } else if (sender instanceof Entity) {
      return new ExecutorType[] { ExecutorType.ENTITY, ExecutorType.NATIVE, ExecutorType.ALL };
    } else if (sender instanceof ConsoleCommandSender) {
      return new ExecutorType[] { ExecutorType.CONSOLE, ExecutorType.NATIVE, ExecutorType.ALL };
    } else if (sender instanceof BlockCommandSender) {
      return new ExecutorType[] { ExecutorType.BLOCK, ExecutorType.NATIVE, ExecutorType.ALL };
    } else if (sender instanceof ProxiedCommandSender) {
      return new ExecutorType[] { ExecutorType.PROXY, ExecutorType.NATIVE, ExecutorType.ALL };
    } else if (sender instanceof RemoteConsoleCommandSender) {
      return new ExecutorType[] { ExecutorType.REMOTE, ExecutorType.NATIVE, ExecutorType.ALL };
    }
    return new ExecutorType[] { ExecutorType.NATIVE, ExecutorType.ALL };
  }

  public static boolean isInvalidNamespace(final String namespace) {
    if (namespace == null || namespace.isEmpty()) {
      Logger.warn(
          "Registering commands using the default namespace because an empty namespace was given!");
      return true;
    }
    if (!NAMESPACE_PATTERN.matcher(namespace).matches()) {
      Logger.warn(
          "Registering comands using the default namespace because an invalid namespace (" +
              namespace +
              ") was given. Only 0-9, a-z, underscores, periods and hyphens are allowed!");
      return true;
    }
    return false;
  }

  private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER = new HashMap<>();

  static {
    PRIMITIVE_TO_WRAPPER.put(boolean.class, Boolean.class);
    PRIMITIVE_TO_WRAPPER.put(byte.class, Byte.class);
    PRIMITIVE_TO_WRAPPER.put(short.class, Short.class);
    PRIMITIVE_TO_WRAPPER.put(char.class, Character.class);
    PRIMITIVE_TO_WRAPPER.put(int.class, Integer.class);
    PRIMITIVE_TO_WRAPPER.put(long.class, Long.class);
    PRIMITIVE_TO_WRAPPER.put(float.class, Float.class);
    PRIMITIVE_TO_WRAPPER.put(double.class, Double.class);
  }

  public void registerPermission(final String string) {
    try {
      Bukkit.getPluginManager().addPermission(new Permission(string));
    } catch (final IllegalArgumentException e) {
      assert true;
    }
  }

  @SuppressWarnings("unchecked")
  public static <V> V primitive2wrapper(final Object source, final Class<V> target) {
    if (PRIMITIVE_TO_WRAPPER.getOrDefault(target, target).isAssignableFrom(source.getClass())) {
      final V v = (V) source;
      return v;
    } else {
      throw new IllegalArgumentException(
          "Classs is defined as " +
              source.getClass().getSimpleName() +
              ", not " +
              target.getSimpleName());
    }
  }

  public static ArrayList<String> copyPartialMatches(
      final String token,
      final Iterable<String> originals) throws UnsupportedOperationException, IllegalArgumentException {
    return copyPartialMatches(token, originals, new ArrayList<>());
  }

  public static ArrayList<String> copyPartialMatches(
      final String token,
      final Iterable<String> originals,
      final ArrayList<String> collection) throws UnsupportedOperationException, IllegalArgumentException {
    if (token == null || token.length() == 0)
      return collection;
    if (collection == null)
      return new ArrayList<String>();
    if (originals == null)
      return collection;
    for (final String string : originals) {
      if (startsWithIgnoreCase(string, token)) {
        collection.add(string);
      }
    }
    return collection;
  }

  public static boolean startsWithIgnoreCase(
      final String string,
      final String prefix) {
    if (string == null || prefix.length() == 0)
      return false;
    if (prefix == null || prefix.length() == 0)
      return true;
    if (string.length() < prefix.length()) {
      return false;
    }
    return string.regionMatches(true, 0, prefix, 0, prefix.length());
  }

  public static void broadcastCommandMessage(final CommandSender source, final String message) {
    broadcastCommandMessage(source, message, true);
  }

  public static void broadcastCommandMessage(final CommandSender source, final String message,
      final boolean sendToSource) {
    final String colored = MessageFormatter.format("$7o[" + source.getName() + ": " + message + "$7o]");

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

        if (target instanceof ConsoleCommandSender) {
          target.sendMessage(colored);
        } else if (target != source) {
          target.sendMessage(colored);
        }
      }
    }
  }

}
