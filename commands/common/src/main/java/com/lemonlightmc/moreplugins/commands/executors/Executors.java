package com.lemonlightmc.moreplugins.commands.executors;

import com.lemonlightmc.moreplugins.commands.CommandSource;
import com.lemonlightmc.moreplugins.commands.argumentsbase.CommandArguments;
import com.lemonlightmc.moreplugins.commands.exceptions.CommandException;

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

  public interface NormalExecutor<S> {
    void run(CommandSource<S> source, CommandArguments args) throws CommandException;

    default void run(
        final ExecutionInfo<S> info)
        throws CommandException {
      this.run(info.source(), info.args());
    }

    @SuppressWarnings("unchecked")
    default int executeWith(final CommandSource<?> source, final CommandArguments args)
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

  public interface NormalExecutorInfo<S> extends NormalExecutor<S> {
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
  public interface CommandExecutor<S>
      extends
      NormalExecutor<S> {

    void run(CommandSource<S> source, CommandArguments args) throws CommandException;

    @Override
    default ExecutorType getType() {
      return ExecutorType.ALL;
    }
  }

  @FunctionalInterface
  public interface CommandExecutionInfo<S>
      extends
      NormalExecutorInfo<S> {

    void run(ExecutionInfo<S> info) throws CommandException;

    @Override
    default ExecutorType getType() {
      return ExecutorType.ALL;
    }
  }
}
