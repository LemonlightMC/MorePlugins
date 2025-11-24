package com.lemonlightmc.moreplugins.commandbase;

import com.lemonlightmc.moreplugins.commands.Senders;
import com.lemonlightmc.moreplugins.commands.Senders.AbstractCommandSender;
import com.lemonlightmc.moreplugins.commands.executors.ExecutionInfo;
import com.lemonlightmc.moreplugins.commands.executors.ExecutorType;
import com.lemonlightmc.moreplugins.commands.executors.NormalExecutor;
import com.lemonlightmc.moreplugins.messages.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

public class Executor<S extends CommandSender, W extends AbstractCommandSender<? extends CommandSender>> {

  private List<NormalExecutor<S, W>> executors;

  private static Executor<CommandSender, AbstractCommandSender<? extends CommandSender>> instance;

  public static Executor<CommandSender, AbstractCommandSender<? extends CommandSender>> getInstance() {
    if (instance == null) {
      instance = new Executor<CommandSender, AbstractCommandSender<? extends CommandSender>>();
    }
    return instance;
  }

  public Executor() {
    executors = new ArrayList<>();
  }

  @SuppressWarnings("unchecked")
  public void addExecutor(final NormalExecutor<?, ?> currentExecutor) {
    this.executors.add((NormalExecutor<S, W>) currentExecutor);
  }

  public void setExecutors(final List<NormalExecutor<S, W>> currentExecutors) {
    this.executors = currentExecutors;
  }

  public boolean hasExecutor(final NormalExecutor<S, W> currentExecutor) {
    return this.executors.contains(currentExecutor);
  }

  public void clearExecutors() {
    executors.clear();
  }

  public List<NormalExecutor<S, W>> getExecutors() {
    return executors;
  }

  public boolean hasAnyExecutors() {
    return !executors.isEmpty();
  }

  Executor<S, W> mergeExecutor(final Executor<S, W> currentExecutor) {
    final Executor<S, W> result = new Executor<S, W>();
    result.executors = new ArrayList<NormalExecutor<S, W>>(executors);
    result.executors.addAll(currentExecutor.executors);
    return result;
  }

  public int execute(final ExecutionInfo<S, W> info) throws CommandException {
    // Run normal executor
    try {
      return mapExecutor(executors, info);
    } catch (final CommandException e) {
      throw e;
    } catch (final Throwable ex) {
      Logger.warn(
          "Unhandled exception executing '" + info.args().fullInput() + "'");
      ex.printStackTrace();
      if (ex instanceof Exception) {
        throw ex;
      } else {
        throw new RuntimeException(ex);
      }
    }
  }

  private int mapExecutor(
      final List<? extends NormalExecutor<S, W>> currentExecutors,
      final ExecutionInfo<S, W> info) throws CommandException {
    if (isForceNative()) {
      return execute(currentExecutors, info, ExecutorType.NATIVE);
    } else if (info.sender() instanceof Senders.AbstractPlayer &&
        matches(currentExecutors, ExecutorType.PLAYER)) {
      return execute(currentExecutors, info, ExecutorType.PLAYER);
    } else if (info.sender() instanceof Senders.AbstractEntity &&
        matches(currentExecutors, ExecutorType.ENTITY)) {
      return execute(currentExecutors, info, ExecutorType.ENTITY);
    } else if (info.sender() instanceof Senders.AbstractConsoleCommandSender &&
        matches(currentExecutors, ExecutorType.CONSOLE)) {
      return execute(currentExecutors, info, ExecutorType.CONSOLE);
    } else if (info.sender() instanceof Senders.AbstractBlockCommandSender &&
        matches(currentExecutors, ExecutorType.BLOCK)) {
      return execute(currentExecutors, info, ExecutorType.BLOCK);
    } else if (info.sender() instanceof Senders.AbstractProxiedCommandSender &&
        matches(currentExecutors, ExecutorType.PROXY)) {
      return execute(currentExecutors, info, ExecutorType.PROXY);
    } else if (info.sender() instanceof Senders.AbstractRemoteConsoleCommandSender &&
        matches(currentExecutors, ExecutorType.REMOTE)) {
      return execute(currentExecutors, info, ExecutorType.REMOTE);
    } else if (info.sender() instanceof Senders.AbstractFeedbackForwardingCommandSender &&
        matches(currentExecutors, ExecutorType.FEEDBACK_FORWARDING)) {
      return execute(currentExecutors, info, ExecutorType.FEEDBACK_FORWARDING);
    } else if (matches(currentExecutors, ExecutorType.ALL)) {
      return execute(currentExecutors, info, ExecutorType.ALL);
    } else {
      throw new CommandException(
          "".replace("%s", info.sender().getClass().getSimpleName().toLowerCase())
              .replace("%S", info.sender().getClass().getSimpleName()));
    }
  }

  private int execute(
      final List<? extends NormalExecutor<S, W>> currentExecutors,
      final ExecutionInfo<S, W> info,
      final ExecutorType type) throws CommandException {
    for (final NormalExecutor<S, W> executor : currentExecutors) {
      if (executor.getType() == type) {
        return executor.executeWith(info);
      }
    }
    throw new NoSuchElementException(
        "Executor had no valid executors for type " + type.toString());
  }

  public boolean isForceNative() {
    return (matches(executors, ExecutorType.NATIVE));
  }

  private boolean matches(
      final List<? extends NormalExecutor<S, W>> currentExecutors,
      final ExecutorType type) {
    for (final NormalExecutor<S, W> executor : currentExecutors) {
      if (executor.getType() == type) {
        return true;
      }
    }
    return false;
  }
}
