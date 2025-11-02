package com.lemonlightmc.moreplugins.utils;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PlayerUtils {

  public static OfflinePlayer getOfflinePlayer(String name) {
    if (name == null || name.length() == 0) {
      return null;
    }
    return Bukkit.getOfflinePlayer(UUIDUtils.toUUID(name));
  }

  public static OfflinePlayer getOfflinePlayer(UUID uuid) {
    if (uuid == null) {
      return null;
    }
    return Bukkit.getOfflinePlayer(uuid);
  }

  public static Player getPlayer(String name) {
    if (name == null || name.length() == 0) {
      return null;
    }
    return Bukkit.getPlayerExact(name);
  }

  public static OfflinePlayer getPlayer(String name, boolean allowOffline) {
    if (name == null || name.length() == 0) {
      return null;
    }
    Player p = Bukkit.getPlayerExact(name);
    if (p != null || !allowOffline) {
      return p;
    }
    return Bukkit.getOfflinePlayer(UUIDUtils.toUUID(name));
  }

  public static Player[] getOnlinePlayers() {
    return Bukkit.getOnlinePlayers().toArray(Player[]::new);
  }

  public static Player[] getPlayersInRadius(Player player, int size) {
    List<Entity> players = player.getNearbyEntities(size, size, size);
    players.removeIf(e -> !(e instanceof Player));
    return players.toArray(Player[]::new);
  }

  public static OfflinePlayer[] getOfflinePlayers() {
    return Bukkit.getOfflinePlayers();
  }

  public static boolean isValidPlayer(final OfflinePlayer player) {
    if (player == null || player.getName() == null || player.getName().length() == 0) {
      return false;
    }
    final String name = UUIDUtils.fetchName(player.getUniqueId());
    return name != null && name.length() != 0 && name == player.getName();
  }

  public static boolean isValidPlayerName(final String playerName) {
    return UUIDUtils.toName(playerName) != null;
  }

  public static boolean isValidPlayerUUID(final String uuidStr) {
    return UUIDUtils.isUUID(uuidStr);
  }

  public static boolean isValidPlayerUUID(final UUID uuid) {
    return UUIDUtils.isUUID(uuid);
  }

  public static Locale getPlayerLocale(final Player player) {
    if (player == null) {
      return null;
    }
    return StringUtils.parseLocale(player.getLocale());
  }

}
