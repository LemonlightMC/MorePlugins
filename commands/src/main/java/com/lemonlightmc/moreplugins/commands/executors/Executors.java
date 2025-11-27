package com.lemonlightmc.moreplugins.commands.executors;

import com.lemonlightmc.moreplugins.commands.Senders.*;
import com.lemonlightmc.moreplugins.commands.argumentsbase.CommandArguments;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Executors {

  /**
   * A normal command executor for a BlockCommandSender
   */
  @FunctionalInterface
  public interface CommandBlockExecutor
      extends NormalExecutor<BlockCommandSender, BukkitBlockCommandSender> {
    /**
     * The code to run when this command is performed
     *
     * @param sender The sender of this command (a player, the console etc.)
     * @param args   The arguments given to this command.
     */
    void run(BlockCommandSender sender, CommandArguments args)
        throws CommandException;

    /**
     * The code to run when this command is performed
     *
     * @param info The ExecutionInfo for this command
     */
    @Override
    default void run(
        final ExecutionInfo<BlockCommandSender, BukkitBlockCommandSender> info) throws CommandException {
      this.run(info.sender(), info.args());
    }

    /**
     * Returns the type of the sender of the current executor.
     * 
     * @return the type of the sender of the current executor
     */
    @Override
    default ExecutorType getType() {
      return ExecutorType.BLOCK;
    }
  }

  @FunctionalInterface
  public interface CommandBlockExecutionInfo
      extends NormalExecutor<BlockCommandSender, BukkitBlockCommandSender> {
    /**
     * Executes the command.
     *
     * @param info The ExecutionInfo for this command
     * @throws CommandException if an error occurs during the execution of this
     *                          command
     */
    void run(ExecutionInfo<BlockCommandSender, BukkitBlockCommandSender> info)
        throws CommandException;

    /**
     * Returns the type of the sender of the current executor.
     *
     * @return the type of the sender of the current executor
     */
    @Override
    default ExecutorType getType() {
      return ExecutorType.BLOCK;
    }
  }

  @FunctionalInterface
  public interface CommandExecutionInfo
      extends
      NormalExecutor<CommandSender, BukkitCommandSender<? extends CommandSender>> {
    /**
     * Executes the command.
     *
     * @param info The ExecutionInfo for this command
     * @throws CommandException if an error occurs during the execution of this
     *                          command
     */
    void run(
        ExecutionInfo<CommandSender, BukkitCommandSender<? extends CommandSender>> info) throws CommandException;

    /**
     * Returns the type of the sender of the current executor.
     *
     * @return the type of the sender of the current executor
     */
    @Override
    default ExecutorType getType() {
      return ExecutorType.ALL;
    }
  }

  @FunctionalInterface
  public interface CommandExecutor
      extends
      NormalExecutor<CommandSender, BukkitCommandSender<? extends CommandSender>> {
    /**
     * The code to run when this command is performed
     *
     * @param sender The sender of this command (a player, the console etc.)
     * @param args   The arguments given to this command.
     */
    void run(CommandSender sender, CommandArguments args)
        throws CommandException;

    /**
     * The code to run when this command is performed
     *
     * @param info The ExecutionInfo for this command
     */
    @Override
    default void run(
        final ExecutionInfo<CommandSender, BukkitCommandSender<? extends CommandSender>> info) throws CommandException {
      this.run(info.sender(), info.args());
    }

    /**
     * Returns the type of the sender of the current executor.
     * 
     * @return the type of the sender of the current executor
     */
    @Override
    default ExecutorType getType() {
      return ExecutorType.ALL;
    }
  }

  @FunctionalInterface
  public interface ConsoleCommandExecutor
      extends NormalExecutor<ConsoleCommandSender, BukkitConsoleCommandSender> {
    /**
     * The code to run when this command is performed
     *
     * @param sender The sender of this command (a player, the console etc.)
     * @param args   The arguments given to this command.
     */
    abstract void run(ConsoleCommandSender sender, CommandArguments args)
        throws CommandException;

    /**
     * The code to run when this command is performed
     *
     * @param info The ExecutionInfo for this command
     */
    @Override
    default void run(
        final ExecutionInfo<ConsoleCommandSender, BukkitConsoleCommandSender> info) throws CommandException {
      this.run(info.sender(), info.args());
    }

    /**
     * Returns the type of the sender of the current executor.
     * 
     * @return the type of the sender of the current executor
     */
    @Override
    default ExecutorType getType() {
      return ExecutorType.CONSOLE;
    }
  }

  @FunctionalInterface
  public interface ConsoleExecutionInfo
      extends NormalExecutor<ConsoleCommandSender, BukkitConsoleCommandSender> {
    /**
     * Executes the command.
     *
     * @param info The ExecutionInfo for this command
     * @throws CommandException if an error occurs during the execution of this
     *                          command
     */
    void run(
        ExecutionInfo<ConsoleCommandSender, BukkitConsoleCommandSender> info) throws CommandException;

    /**
     * Returns the type of the sender of the current executor.
     *
     * @return the type of the sender of the current executor
     */
    @Override
    default ExecutorType getType() {
      return ExecutorType.CONSOLE;
    }
  }

  @FunctionalInterface
  public interface RemoteConsoleCommandExecutor
      extends
      NormalExecutor<RemoteConsoleCommandSender, BukkitRemoteConsoleCommandSender> {
    /**
     * The code to run when this command is performed
     *
     * @param sender The sender of this command (a player, the console etc.)
     * @param args   The arguments given to this command.
     */
    void run(RemoteConsoleCommandSender sender, CommandArguments args)
        throws CommandException;

    /**
     * Executes the command.
     *
     * @param info The ExecutionInfo for this command
     * @throws CommandException if an error occurs during the execution of this
     *                          command
     */
    @Override
    default void run(
        final ExecutionInfo<RemoteConsoleCommandSender, BukkitRemoteConsoleCommandSender> info)
        throws CommandException {
      this.run(info.sender(), info.args());
    }

    /**
     * Returns the type of the sender of the current executor.
     *
     * @return the type of the sender of the current executor
     */
    @Override
    default ExecutorType getType() {
      return ExecutorType.REMOTE;
    }
  }

  @FunctionalInterface
  public interface RemoteConsoleExecutionInfo
      extends
      NormalExecutor<RemoteConsoleCommandSender, BukkitRemoteConsoleCommandSender> {
    /**
     * Executes the command.
     *
     * @param info The ExecutionInfo for this command
     * @throws CommandException if an error occurs during the execution of this
     *                          command
     */
    void run(
        ExecutionInfo<RemoteConsoleCommandSender, BukkitRemoteConsoleCommandSender> info) throws CommandException;

    /**
     * Returns the type of the sender of the current executor.
     *
     * @return the type of the sender of the current executor
     */
    @Override
    default ExecutorType getType() {
      return ExecutorType.REMOTE;
    }
  }

  @FunctionalInterface
  public interface EntityCommandExecutor
      extends NormalExecutor<Entity, BukkitEntity> {
    /**
     * The code to run when this command is performed
     *
     * @param sender The sender of this command (a player, the console etc.)
     * @param args   The arguments given to this command.
     */
    void run(Entity sender, CommandArguments args) throws CommandException;

    /**
     * Executes the command.
     *
     * @param info The ExecutionInfo for this command
     * @throws CommandException if an error occurs during the execution of this
     *                          command
     */
    @Override
    default void run(final ExecutionInfo<Entity, BukkitEntity> info)
        throws CommandException {
      this.run(info.sender(), info.args());
    }

    /**
     * Returns the type of the sender of the current executor.
     * 
     * @return the type of the sender of the current executor
     */
    @Override
    default ExecutorType getType() {
      return ExecutorType.ENTITY;
    }
  }

  @FunctionalInterface
  public interface EntityExecutionInfo
      extends NormalExecutor<Entity, BukkitEntity> {
    /**
     * Executes the command.
     *
     * @param info The ExecutionInfo for this command
     * @throws CommandException if an error occurs during the execution of this
     *                          command
     */
    void run(ExecutionInfo<Entity, BukkitEntity> info) throws CommandException;

    /**
     * Returns the type of the sender of the current executor.
     *
     * @return the type of the sender of the current executor
     */
    @Override
    default ExecutorType getType() {
      return ExecutorType.ENTITY;
    }
  }

  @FunctionalInterface
  public interface FeedbackForwardingCommandExecutor
      extends
      NormalExecutor<CommandSender, BukkitFeedbackForwardingCommandSender<CommandSender>> {
    /**
     * The code to run when this command is performed
     *
     * @param sender The sender of this command (a player, the console etc.)
     * @param args   The arguments given to this command.
     */
    void run(CommandSender sender, CommandArguments args)
        throws CommandException;

    /**
     * The code to run when this command is performed
     *
     * @param info The ExecutionInfo for this command
     */
    @Override
    default void run(
        final ExecutionInfo<CommandSender, BukkitFeedbackForwardingCommandSender<CommandSender>> info)
        throws CommandException {
      this.run(info.sender(), info.args());
    }

    /**
     * Returns the type of the sender of the current executor.
     * 
     * @return the type of the sender of the current executor
     */
    @Override
    default ExecutorType getType() {
      return ExecutorType.FEEDBACK_FORWARDING;
    }
  }

  @FunctionalInterface
  public interface FeedbackForwardingExecutionInfo
      extends
      NormalExecutor<CommandSender, BukkitFeedbackForwardingCommandSender<CommandSender>> {
    /**
     * Executes the command.
     *
     * @param info The ExecutionInfo for this command
     * @throws CommandException if an error occurs during the execution of this
     *                          command
     */
    void run(
        ExecutionInfo<CommandSender, BukkitFeedbackForwardingCommandSender<CommandSender>> info)
        throws CommandException;

    /**
     * Returns the type of the sender of the current executor.
     *
     * @return the type of the sender of the current executor
     */
    @Override
    default ExecutorType getType() {
      return ExecutorType.FEEDBACK_FORWARDING;
    }
  }

  @FunctionalInterface
  public interface PlayerCommandExecutor
      extends NormalExecutor<Player, BukkitPlayer> {
    /**
     * The code to run when this command is performed
     *
     * @param sender The sender of this command (a player, the console etc.)
     * @param args   The arguments given to this command.
     */
    void run(Player sender, CommandArguments args) throws CommandException;

    /**
     * The code to run when this command is performed
     *
     * @param info The ExecutionInfo for this command
     */
    @Override
    default void run(final ExecutionInfo<Player, BukkitPlayer> info)
        throws CommandException {
      this.run(info.sender(), info.args());
    }

    /**
     * Returns the type of the sender of the current executor.
     * 
     * @return the type of the sender of the current executor
     */
    @Override
    default ExecutorType getType() {
      return ExecutorType.PLAYER;
    }
  }

  @FunctionalInterface
  public interface PlayerExecutionInfo
      extends NormalExecutor<Player, BukkitPlayer> {
    /**
     * Executes the command.
     *
     * @param info The ExecutionInfo for this command
     * @throws CommandException if an error occurs during the execution of this
     *                          command
     */
    void run(ExecutionInfo<Player, BukkitPlayer> info) throws CommandException;

    /**
     * Returns the type of the sender of the current executor.
     *
     * @return the type of the sender of the current executor
     */
    @Override
    default ExecutorType getType() {
      return ExecutorType.PLAYER;
    }
  }
}
