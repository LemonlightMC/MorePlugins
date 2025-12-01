package com.lemonlightmc.moreplugins.commands.executors;

import com.lemonlightmc.moreplugins.commands.Senders.*;
import com.lemonlightmc.moreplugins.commands.argumentsbase.CommandArguments;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.ProxiedCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Executors {
  public interface NormalExecutor<S extends CommandSender, W extends AbstractCommandSender<? extends CommandSender>> {
    void run(ExecutionInfo<S, W> info) throws CommandException;

    @SuppressWarnings("unchecked")
    default int executeWith(final ExecutionInfo<?, ?> info)
        throws CommandException {
      this.run((ExecutionInfo<S, W>) info);
      return 1;
    }

    default ExecutorType getType() {
      return ExecutorType.ALL;
    }
  }

  @FunctionalInterface
  public interface CommandExecutor
      extends
      NormalExecutor<CommandSender, AbstractCommandSender<? extends CommandSender>> {

    void run(CommandSender sender, CommandArguments args)
        throws CommandException;

    @Override
    default void run(
        final ExecutionInfo<CommandSender, AbstractCommandSender<? extends CommandSender>> info)
        throws CommandException {
      this.run(info.sender(), info.args());
    }

    @Override
    default ExecutorType getType() {
      return ExecutorType.ALL;
    }
  }

  @FunctionalInterface
  public interface CommandExecutionInfo
      extends
      NormalExecutor<CommandSender, AbstractCommandSender<? extends CommandSender>> {

    void run(
        ExecutionInfo<CommandSender, AbstractCommandSender<? extends CommandSender>> info) throws CommandException;

    @Override
    default ExecutorType getType() {
      return ExecutorType.ALL;
    }
  }

  @FunctionalInterface
  public interface PlayerCommandExecutor
      extends NormalExecutor<Player, BukkitPlayer> {

    void run(Player sender, CommandArguments args) throws CommandException;

    @Override
    default void run(final ExecutionInfo<Player, BukkitPlayer> info)
        throws CommandException {
      this.run(info.sender(), info.args());
    }

    @Override
    default ExecutorType getType() {
      return ExecutorType.PLAYER;
    }
  }

  @FunctionalInterface
  public interface PlayerExecutionInfo
      extends NormalExecutor<Player, BukkitPlayer> {

    void run(ExecutionInfo<Player, BukkitPlayer> info) throws CommandException;

    @Override
    default ExecutorType getType() {
      return ExecutorType.PLAYER;
    }
  }

  @FunctionalInterface
  public interface EntityCommandExecutor
      extends NormalExecutor<Entity, BukkitEntity> {

    void run(Entity sender, CommandArguments args) throws CommandException;

    @Override
    default void run(final ExecutionInfo<Entity, BukkitEntity> info)
        throws CommandException {
      this.run(info.sender(), info.args());
    }

    @Override
    default ExecutorType getType() {
      return ExecutorType.ENTITY;
    }
  }

  @FunctionalInterface
  public interface EntityExecutionInfo
      extends NormalExecutor<Entity, BukkitEntity> {

    void run(ExecutionInfo<Entity, BukkitEntity> info) throws CommandException;

    @Override
    default ExecutorType getType() {
      return ExecutorType.ENTITY;
    }
  }

  @FunctionalInterface
  public interface CommandBlockExecutor
      extends NormalExecutor<BlockCommandSender, BukkitBlockCommandSender> {
    void run(BlockCommandSender sender, CommandArguments args)
        throws CommandException;

    @Override
    default void run(
        final ExecutionInfo<BlockCommandSender, BukkitBlockCommandSender> info) throws CommandException {
      this.run(info.sender(), info.args());
    }

    @Override
    default ExecutorType getType() {
      return ExecutorType.BLOCK;
    }
  }

  @FunctionalInterface
  public interface CommandBlockExecutionInfo
      extends NormalExecutor<BlockCommandSender, BukkitBlockCommandSender> {

    void run(ExecutionInfo<BlockCommandSender, BukkitBlockCommandSender> info)
        throws CommandException;

    @Override
    default ExecutorType getType() {
      return ExecutorType.BLOCK;
    }
  }

  @FunctionalInterface
  public interface ConsoleCommandExecutor
      extends NormalExecutor<ConsoleCommandSender, BukkitConsoleCommandSender> {

    abstract void run(ConsoleCommandSender sender, CommandArguments args)
        throws CommandException;

    @Override
    default void run(
        final ExecutionInfo<ConsoleCommandSender, BukkitConsoleCommandSender> info) throws CommandException {
      this.run(info.sender(), info.args());
    }

    @Override
    default ExecutorType getType() {
      return ExecutorType.CONSOLE;
    }
  }

  @FunctionalInterface
  public interface ConsoleExecutionInfo
      extends NormalExecutor<ConsoleCommandSender, BukkitConsoleCommandSender> {

    void run(
        ExecutionInfo<ConsoleCommandSender, BukkitConsoleCommandSender> info) throws CommandException;

    @Override
    default ExecutorType getType() {
      return ExecutorType.CONSOLE;
    }
  }

  @FunctionalInterface
  public interface RemoteConsoleCommandExecutor
      extends
      NormalExecutor<RemoteConsoleCommandSender, BukkitRemoteConsoleCommandSender> {

    void run(RemoteConsoleCommandSender sender, CommandArguments args)
        throws CommandException;

    @Override
    default void run(
        final ExecutionInfo<RemoteConsoleCommandSender, BukkitRemoteConsoleCommandSender> info)
        throws CommandException {
      this.run(info.sender(), info.args());
    }

    @Override
    default ExecutorType getType() {
      return ExecutorType.REMOTE;
    }
  }

  @FunctionalInterface
  public interface RemoteConsoleExecutionInfo
      extends
      NormalExecutor<RemoteConsoleCommandSender, BukkitRemoteConsoleCommandSender> {

    void run(
        ExecutionInfo<RemoteConsoleCommandSender, BukkitRemoteConsoleCommandSender> info) throws CommandException;

    @Override
    default ExecutorType getType() {
      return ExecutorType.REMOTE;
    }
  }

  @FunctionalInterface
  public interface FeedbackForwardingCommandExecutor
      extends
      NormalExecutor<CommandSender, BukkitFeedbackForwardingCommandSender<CommandSender>> {

    void run(final ExecutionInfo<CommandSender, BukkitFeedbackForwardingCommandSender<CommandSender>> info)
        throws CommandException;

    @Override
    default ExecutorType getType() {
      return ExecutorType.FEEDBACK_FORWARDING;
    }
  }

  @FunctionalInterface
  public interface FeedbackForwardingExecutionInfo
      extends
      NormalExecutor<CommandSender, BukkitFeedbackForwardingCommandSender<CommandSender>> {

    void run(CommandSender sender, CommandArguments args);

    @Override
    default void run(final ExecutionInfo<CommandSender, BukkitFeedbackForwardingCommandSender<CommandSender>> info)
        throws CommandException {
      this.run(info.sender(), info.args());
    }

    @Override
    default ExecutorType getType() {
      return ExecutorType.FEEDBACK_FORWARDING;
    }
  }

  @FunctionalInterface
  public interface ProxyCommandExecutor
      extends NormalExecutor<ProxiedCommandSender, BukkitProxiedCommandSender> {

    void run(ProxiedCommandSender sender, CommandArguments args) throws CommandException;

    @Override
    default void run(final ExecutionInfo<ProxiedCommandSender, BukkitProxiedCommandSender> info)
        throws CommandException {
      this.run(info.sender(), info.args());
    }

    @Override
    default ExecutorType getType() {
      return ExecutorType.PROXY;
    }
  }

  @FunctionalInterface
  public interface ProxyExecutionInfo extends NormalExecutor<ProxiedCommandSender, BukkitProxiedCommandSender> {

    void run(ExecutionInfo<ProxiedCommandSender, BukkitProxiedCommandSender> info) throws CommandException;

    @Override
    default ExecutorType getType() {
      return ExecutorType.PROXY;
    }
  }

  @FunctionalInterface
  public interface NativeCommandExecutor
      extends NormalExecutor<ProxiedCommandSender, BukkitNativeProxyCommandSender> {

    void run(ProxiedCommandSender sender, CommandArguments args) throws CommandException;

    @Override
    default void run(final ExecutionInfo<ProxiedCommandSender, BukkitNativeProxyCommandSender> info)
        throws CommandException {
      this.run(info.sender(), info.args());
    }

    @Override
    default ExecutorType getType() {
      return ExecutorType.NATIVE;
    }
  }

  @FunctionalInterface
  public interface NativeExecutionInfo
      extends NormalExecutor<ProxiedCommandSender, BukkitNativeProxyCommandSender> {

    void run(ExecutionInfo<ProxiedCommandSender, BukkitNativeProxyCommandSender> info)
        throws CommandException;

    @Override
    default ExecutorType getType() {
      return ExecutorType.NATIVE;
    }
  }
}
