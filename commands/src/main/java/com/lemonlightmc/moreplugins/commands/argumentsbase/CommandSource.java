package com.lemonlightmc.moreplugins.commands.argumentsbase;

import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;;

public record CommandSource(CommandSender sender, Location location, Entity entity) {
  public CommandSource(CommandSender sender) {
    this(sender, getLocation(sender), getEntity(sender));
  }

  private static Location getLocation(CommandSender sender) {
    return switch (sender) {
      case Entity entity -> entity.getLocation();
      case BlockCommandSender block -> block.getBlock().getLocation();
      default -> new Location(null, 0, 0, 0);
    };
  }

  private static Entity getEntity(CommandSender sender) {
    return sender instanceof Entity entity ? entity : null;
  }
}
