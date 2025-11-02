package com.julizey.moreplugins.commands;

import com.julizey.moreplugins.commands.Senders.*;
import com.julizey.moreplugins.messages.Logger;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.ProxiedCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class Utils {

  static final Pattern NAMESPACE_PATTERN = Pattern.compile("[0-9a-z_.-]+");

  public static void registerImpl(String namespace) {}

  public static void unregisterImpl(String namespace) {}

  public static BukkitCommandSender<? extends CommandSender> wrapCommandSender(
    CommandSender sender
  ) {
    if (sender instanceof BlockCommandSender block) {
      return new BukkitBlockCommandSender(block);
    }
    if (sender instanceof ConsoleCommandSender console) {
      return new BukkitConsoleCommandSender(console);
    }
    if (sender instanceof Player player) {
      return new BukkitPlayer(player);
    }
    if (sender instanceof org.bukkit.entity.Entity entity) {
      return new BukkitEntity(entity);
    }
    if (sender instanceof ProxiedCommandSender proxy) {
      return new BukkitProxiedCommandSender(proxy);
    }
    if (sender instanceof RemoteConsoleCommandSender remote) {
      return new BukkitRemoteConsoleCommandSender(remote);
    }
    throw new RuntimeException(
      "Failed to wrap CommandSender " +
      sender +
      " to a compatible BukkitCommandSender"
    );
  }

  public static boolean isInvalidNamespace(String namespace) {
    if (namespace == null || namespace.isEmpty()) {
      Logger.warn(
        "Registering commands using the default namespace because an empty namespace was given!"
      );
      return true;
    }
    if (!NAMESPACE_PATTERN.matcher(namespace).matches()) {
      Logger.warn(
        "Registering comands using the default namespace because an invalid namespace (" +
        namespace +
        ") was given. Only 0-9, a-z, underscores, periods and hyphens are allowed!"
      );
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

  public void registerPermission(String string) {
    try {
      Bukkit.getPluginManager().addPermission(new Permission(string));
    } catch (IllegalArgumentException e) {
      assert true;
    }
  }

  public <V> V primitive2wrapper(Class<?> source, Class<V> target) {
    if (
      PRIMITIVE_TO_WRAPPER.getOrDefault(target, target).isAssignableFrom(source)
    ) {
      @SuppressWarnings("unchecked")
      V v = (V) target;
      return v;
    } else {
      throw new IllegalArgumentException(
        "Classs is defined as " +
        source.getSimpleName() +
        ", not " +
        target.getSimpleName()
      );
    }
  }
}
