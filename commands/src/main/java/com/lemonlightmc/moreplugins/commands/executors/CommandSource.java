package com.lemonlightmc.moreplugins.commands.executors;

import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

public class CommandSource<S extends CommandSender> {

  private final S sender;
  private final Location loc;
  private final Entity entity;

  public CommandSource(final S sender, final Location location, final Entity entity) {
    if (sender == null) {
      throw new IllegalArgumentException("Failed to create CommandSource due empty Sender");
    }
    this.sender = sender;
    this.loc = location;
    this.entity = entity;
  }

  public CommandSource(final S sender) {
    this(sender, getLocation(sender), getEntity(sender));
  }

  public <T extends CommandSender> CommandSource<T> copyFor(T newSender) {
    return new CommandSource<T>(newSender, loc, entity);
  }

  public CommandSource<S> copy() {
    return new CommandSource<>(sender, loc, entity);
  }

  public S sender() {
    return sender;
  }

  public Location location() {
    return loc;
  }

  public Entity entity() {
    return entity;
  }

  public boolean hasPermission(final String perm) {
    return perm == null || perm.length() == 0 || sender.hasPermission(perm);
  }

  public static Location getLocation(final CommandSender sender) {
    return switch (sender) {
      case final Entity entity -> entity.getLocation();
      case final BlockCommandSender block -> block.getBlock().getLocation();
      default -> null;
    };
  }

  public static Entity getEntity(final CommandSender sender) {
    return sender instanceof final Entity entity ? entity : null;
  }
}
