package com.lemonlightmc.moreplugins.commands.executors;

import com.lemonlightmc.moreplugins.commands.CommandSource;
import com.lemonlightmc.moreplugins.commands.argumentsbase.CommandArguments;
import com.lemonlightmc.moreplugins.commands.executors.Executors.ExecutorType;

import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.ProxiedCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public record ExecutionInfo<S extends CommandSender>(CommandSource<S> source,
    CommandArguments args) {

  public S sender() {
    return source.sender();
  }

  public Location location() {
    return source.location();
  }

  public ExecutorType executorType() {
    return getExecutorType(source.sender());
  }

  public <T extends CommandSender> ExecutionInfo<T> copyFor(final T newSender) {
    return new ExecutionInfo<>(source.copyFor(newSender), args);
  }

  public ExecutionInfo<S> copy() {
    return new ExecutionInfo<>(source, args);
  }

  public static ExecutorType getExecutorType(final CommandSender sender) {
    if (sender == null) {
      return ExecutorType.ALL;
    } else if (sender instanceof Player) {
      return ExecutorType.PLAYER;
    } else if (sender instanceof Entity) {
      return ExecutorType.ENTITY;
    } else if (sender instanceof ConsoleCommandSender) {
      return ExecutorType.CONSOLE;
    } else if (sender instanceof BlockCommandSender) {
      return ExecutorType.BLOCK;
    } else if (sender instanceof ProxiedCommandSender) {
      return ExecutorType.PROXY;
    } else if (sender instanceof RemoteConsoleCommandSender) {
      return ExecutorType.REMOTE;
    }
    return ExecutorType.ALL;
  }
}