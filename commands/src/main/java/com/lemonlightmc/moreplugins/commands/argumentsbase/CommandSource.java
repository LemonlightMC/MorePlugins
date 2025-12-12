package com.lemonlightmc.moreplugins.commands.argumentsbase;

import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

public class CommandSource<S extends CommandSender> {
  
  private final T sender;
  private final Location loc;
  private final Entity entity;

  public CommandSource(T sender, Location location, Entity entity) {
    this.sender = sender;
    this.loc = location;
    this.entity = entity;
  }

  public CommandSource(T sender) {
    this(sender, getLocation(sender), getEntity(sender));
  }

  public T getSender() {
    return sender;
  }

  public Location getLocation() {
    return loc;
  }
  
  public Entity getEntity() {
    return entity;
  }
  
  public static Location getLocation(CommandSender sender) {
    return switch (sender) {
      case Entity entity -> entity.getLocation();
      case BlockCommandSender block -> block.getBlock().getLocation();
      default -> null;
    };
  }

  public static Entity getEntity(CommandSender sender) {
    return sender instanceof Entity entity ? entity : null;
  }
}
