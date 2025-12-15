package com.lemonlightmc.moreplugins.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

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

  static public String getStringLocation(final Location location) {
    if (location == null) {
      return "";
    }
    return location.getWorld().getName() + ":" + location.getBlockX() + ":" + location.getBlockY() + ":"
        + location.getBlockZ();
  }

  public static Location getLocationFromString(final String locationString) {
    if (locationString == null || locationString.trim() == "") {
      return null;
    }
    final String[] parts = locationString.split(":");
    if (parts.length == 4) {
      final World w = Bukkit.getServer().getWorld(parts[0]);
      final int x = Integer.parseInt(parts[1]);
      final int y = Integer.parseInt(parts[2]);
      final int z = Integer.parseInt(parts[3]);
      Location location = new Location(w, x, y, z);
      return location;
    }
    return null;
  }

  public static List<Location> getSphere(Location center, Integer radius) {
    List<Location> sphereLocations = new ArrayList<Location>();
    for (double i = 0; i <= Math.PI; i += Math.PI / 10) {
      double circleRadius = Math.sin(i);
      double y = Math.cos(i);
      for (double a = 0; a < Math.PI * 2; a += Math.PI / 10) {
        double x = Math.cos(a) * circleRadius;
        double z = Math.sin(a) * circleRadius;
        sphereLocations.add(new Location(center.getWorld(), x, y, z));
      }
    }
    return sphereLocations;
  }

  public static List<Block> getBox(Location location, int radius) {
    List<Block> blocks = new ArrayList<>();
    for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
      for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
        for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
          blocks.add(location.getWorld().getBlockAt(x, y, z));
        }
      }
    }
    return blocks;
  }

  public static List<Location> getCircle(Location center, double radius, int amount) {
    double increment = (2 * Math.PI) / amount;
    List<Location> locations = new ArrayList<Location>();
    for (int i = 0; i < amount; i++) {
      double angle = i * increment;
      double x = center.getX() + (radius * Math.cos(angle));
      double z = center.getZ() + (radius * Math.sin(angle));
      locations.add(new Location(center.getWorld(), x, center.getY(), z));
    }
    return locations;
  }

  public static Block getBlockBehindPlayer(Player player, Number distance) {
    Vector inverseDirectionVec = player.getLocation().getDirection().normalize()
        .multiply(distance.doubleValue() * (-1d));
    return player.getLocation().add(inverseDirectionVec).getBlock();
  }

  public static Location getLocationBehindPlayer(Player player, Number distance) {
    Vector inverseDirectionVec = player.getLocation().getDirection().multiply(distance.doubleValue() * (-1d));
    return player.getLocation().add(inverseDirectionVec);
  }

  public static Block getRelativeByFace(Block block, BlockFace blockFace, int offsetX, int offsetY, int offsetZ) {
    if (blockFace == BlockFace.NORTH) {
      return block.getRelative(offsetX, offsetY, -offsetZ);
    } else if (blockFace == BlockFace.SOUTH) {
      return block.getRelative(-offsetX, offsetY, offsetZ);
    } else if (blockFace == BlockFace.EAST) {
      return block.getRelative(offsetZ, offsetY, offsetX);
    } else if (blockFace == BlockFace.WEST) {
      return block.getRelative(-offsetZ, offsetY, -offsetX);
    } else if (blockFace == BlockFace.UP) {
      return block.getRelative(offsetX, offsetZ, offsetY);
    } else if (blockFace == BlockFace.DOWN) {
      return block.getRelative(offsetX, -offsetZ, -offsetY);
    }
    return block.getRelative(offsetX, offsetY, offsetZ);
  }
}
