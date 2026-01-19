package com.lemonlightmc.moreplugins.commands;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

import com.lemonlightmc.moreplugins.commands.exceptions.CommandSyntaxException;

public record CommandSource<S extends CommandSender>(S sender, Location location, Entity entity) {

  public static <T extends CommandSender> CommandSource<T> from(final T sender) {
    return new CommandSource<T>(sender, getLocation(sender), getEntity(sender));
  }

  public <T extends CommandSender> CommandSource<T> copyFor(final T newSender) {
    return new CommandSource<T>(newSender, location, entity);
  }

  public CommandSource<S> copy() {
    return new CommandSource<>(sender, location, entity);
  }

  public S sender() {
    return sender;
  }

  public Location location() {
    return location;
  }

  public Entity entity() {
    return entity;
  }

  public World world() {
    return location != null ? location.getWorld() : null;
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
