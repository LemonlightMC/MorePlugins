package com.julizey.moreplugins.utils;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class WorldUtils {
  public static World[] getWorlds() {
    return Bukkit.getWorlds().toArray(World[]::new);
  }

  public static World getWorld(final UUID uid) {
    if (uid == null) {
      return null;
    }
    return Bukkit.getWorld(uid);
  }

  public static World getWorld(final String str) {
    if (str == null || str.length() == 0) {
      return null;
    }
    return Bukkit.getWorld(str);
  }

  public static String[] getWorldNames() {
    final ArrayList<String> names = new ArrayList<>(Bukkit.getWorlds().size());
    for (final World w : Bukkit.getWorlds()) {
      if (w == null) {
        continue;
      }
      names.add(w.getName());
    }
    return names.toArray(String[]::new);
  }

  public static World getMainWorld() {
    return Bukkit.getWorlds().get(0);
  }
}
