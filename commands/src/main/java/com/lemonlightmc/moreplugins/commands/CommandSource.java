package com.lemonlightmc.moreplugins.commands;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

import com.lemonlightmc.moreplugins.commands.exceptions.CommandSyntaxException;

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

  public <T extends CommandSender> CommandSource<T> copyFor(final T newSender) {
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

  public World world() {
    return loc != null ? loc.getWorld() : null;
  }

  public boolean hasPermission(final String perm) {
    return perm == null || perm.length() == 0 || sender.hasPermission(perm);
  }

  public void sendError(final CommandSyntaxException e) {
    sender.sendMessage(e.getMessage());
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

  public static World getWorld(final CommandSender sender) {
    return switch (sender) {
      case final Entity entity -> entity.getWorld();
      case final BlockCommandSender block -> block.getBlock().getWorld();
      default -> null;
    };
  }
}
