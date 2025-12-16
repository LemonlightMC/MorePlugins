package com.lemonlightmc.moreplugins.commands.executors;

import com.lemonlightmc.moreplugins.commands.CommandSource;
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

  public enum ExecutorType {
    ALL,

    PLAYER,

    ENTITY,

    CONSOLE,

    BLOCK,

    // NativeProxyCommandSender
    PROXY,

    // NativeProxyCommandSender (always)
    NATIVE,

    // RemoteConsoleCommandSender
    REMOTE,

    // Paper's FeedbackForwardingSender
    FEEDBACK_FORWARDING,
  }

  public interface NormalExecutor<S extends CommandSender> {
    void run(CommandSource<S> source, CommandArguments args) throws CommandException;

    default void run(
        final ExecutionInfo<S> info)
        throws CommandException {
      this.run(info.source(), info.args());
    }

    @SuppressWarnings("unchecked")
    default int executeWith(CommandSource<?> source, CommandArguments args)
        throws CommandException {
      this.run((CommandSource<S>) source, args);
      return 1;
    }

    @SuppressWarnings("unchecked")
    default int executeWith(final ExecutionInfo<?> info)
        throws CommandException {
      this.run((ExecutionInfo<S>) info);
      return 1;
    }

    default ExecutorType getType() {
      return ExecutorType.ALL;
    }
  }

  public interface NormalExecutorInfo<S extends CommandSender> extends NormalExecutor<S> {
    void run(ExecutionInfo<S> info) throws CommandException;

    default void run(
        final CommandSource<S> source, final CommandArguments args)
        throws CommandException {
      this.run(new ExecutionInfo<>(source, args));
    }

    default ExecutorType getType() {
      return ExecutorType.ALL;
    }
  }

  @FunctionalInterface
  public interface CommandExecutor
      extends
      NormalExecutor<CommandSender> {

    void run(CommandSource<CommandSender> source, CommandArguments args) throws CommandException;

    @Override
    default ExecutorType getType() {
      return ExecutorType.ALL;
    }
  }

  @FunctionalInterface
  public interface CommandExecutionInfo
      extends
      NormalExecutorInfo<CommandSender> {

    void run(ExecutionInfo<CommandSender> info) throws CommandException;

    @Override
    default ExecutorType getType() {
      return ExecutorType.ALL;
    }
  }

  @FunctionalInterface
  public interface PlayerCommandExecutor
      extends NormalExecutor<Player> {

    @Override
    void run(CommandSource<Player> source, CommandArguments args) throws CommandException;

    @Override
    default ExecutorType getType() {
      return ExecutorType.PLAYER;
    }
  }

  @FunctionalInterface
  public interface PlayerExecutionInfo
      extends NormalExecutorInfo<Player> {

    void run(ExecutionInfo<Player> info) throws CommandException;

    @Override
    default ExecutorType getType() {
      return ExecutorType.PLAYER;
    }
  }

  @FunctionalInterface
  public interface EntityCommandExecutor
      extends NormalExecutor<Entity> {

    void run(CommandSource<Entity> source, CommandArguments args) throws CommandException;

    @Override
    default ExecutorType getType() {
      return ExecutorType.ENTITY;
    }
  }

  @FunctionalInterface
  public interface EntityExecutionInfo
      extends NormalExecutorInfo<Entity> {

    void run(ExecutionInfo<Entity> info) throws CommandException;

    @Override
    default ExecutorType getType() {
      return ExecutorType.ENTITY;
    }
  }

  @FunctionalInterface
  public interface CommandBlockExecutor
      extends NormalExecutor<BlockCommandSender> {
    void run(CommandSource<BlockCommandSender> source, CommandArguments args)
        throws CommandException;

    @Override
    default ExecutorType getType() {
      return ExecutorType.BLOCK;
    }
  }

  @FunctionalInterface
  public interface CommandBlockExecutionInfo
      extends NormalExecutorInfo<BlockCommandSender> {

    void run(ExecutionInfo<BlockCommandSender> info)
        throws CommandException;

    @Override
    default ExecutorType getType() {
      return ExecutorType.BLOCK;
    }
  }

  @FunctionalInterface
  public interface ConsoleCommandExecutor
      extends NormalExecutor<ConsoleCommandSender> {

    abstract void run(CommandSource<ConsoleCommandSender> source, CommandArguments args)
        throws CommandException;

    @Override
    default ExecutorType getType() {
      return ExecutorType.CONSOLE;
    }
  }

  @FunctionalInterface
  public interface ConsoleExecutionInfo
      extends NormalExecutorInfo<ConsoleCommandSender> {

    void run(ExecutionInfo<ConsoleCommandSender> info) throws CommandException;

    @Override
    default ExecutorType getType() {
      return ExecutorType.CONSOLE;
    }
  }

  @FunctionalInterface
  public interface RemoteConsoleCommandExecutor
      extends
      NormalExecutor<RemoteConsoleCommandSender> {

    void run(CommandSource<RemoteConsoleCommandSender> source, CommandArguments args)
        throws CommandException;

    @Override
    default ExecutorType getType() {
      return ExecutorType.REMOTE;
    }
  }

  @FunctionalInterface
  public interface RemoteConsoleExecutionInfo
      extends
      NormalExecutorInfo<RemoteConsoleCommandSender> {

    void run(ExecutionInfo<RemoteConsoleCommandSender> info) throws CommandException;

    @Override
    default ExecutorType getType() {
      return ExecutorType.REMOTE;
    }
  }

  @FunctionalInterface
  public interface FeedbackForwardingExecutionInfo
      extends
      NormalExecutorInfo<CommandSender> {

    void run(final ExecutionInfo<CommandSender> info) throws CommandException;

    @Override
    default ExecutorType getType() {
      return ExecutorType.FEEDBACK_FORWARDING;
    }
  }

  @FunctionalInterface
  public interface FeedbackForwardingExecutor
      extends
      NormalExecutor<CommandSender> {

    void run(CommandSource<CommandSender> source, CommandArguments args);

    @Override
    default ExecutorType getType() {
      return ExecutorType.FEEDBACK_FORWARDING;
    }
  }

  @FunctionalInterface
  public interface ProxyCommandExecutor
      extends NormalExecutor<ProxiedCommandSender> {

    void run(CommandSource<ProxiedCommandSender> source, CommandArguments args) throws CommandException;

    @Override
    default ExecutorType getType() {
      return ExecutorType.PROXY;
    }
  }

  @FunctionalInterface
  public interface ProxyExecutionInfo extends NormalExecutorInfo<ProxiedCommandSender> {

    void run(ExecutionInfo<ProxiedCommandSender> info) throws CommandException;

    @Override
    default ExecutorType getType() {
      return ExecutorType.PROXY;
    }
  }

  @FunctionalInterface
  public interface NativeCommandExecutor
      extends NormalExecutor<ProxiedCommandSender> {

    void run(CommandSource<ProxiedCommandSender> source, CommandArguments args) throws CommandException;

    @Override
    default ExecutorType getType() {
      return ExecutorType.NATIVE;
    }
  }

  @FunctionalInterface
  public interface NativeExecutionInfo
      extends NormalExecutorInfo<ProxiedCommandSender> {

    void run(ExecutionInfo<ProxiedCommandSender> info) throws CommandException;

    @Override
    default ExecutorType getType() {
      return ExecutorType.NATIVE;
    }
  }
}
