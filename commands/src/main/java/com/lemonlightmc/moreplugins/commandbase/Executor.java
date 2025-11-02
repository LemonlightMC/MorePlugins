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

  private List<NormalExecutor<S, W>> normalExecutors;

  private static Executor<CommandSender, AbstractCommandSender<? extends CommandSender>> instance;

  public static Executor<CommandSender, AbstractCommandSender<? extends CommandSender>> getInstance() {
    if (instance == null)
      instance = new Executor<CommandSender, AbstractCommandSender<? extends CommandSender>>();
    return instance;
  }

  public Executor() {
    normalExecutors = new ArrayList<>();
  }

  @SuppressWarnings("unchecked")
  public void addNormalExecutor(NormalExecutor<?, ?> executor) {
    this.normalExecutors.add((NormalExecutor<S, W>) executor);
  }

  public void setNormalExecutors(List<NormalExecutor<S, W>> normalExecutors) {
    this.normalExecutors = normalExecutors;
  }

  public void clearExecutors() {
    normalExecutors.clear();
  }

  public List<NormalExecutor<S, W>> getNormalExecutors() {
    return normalExecutors;
  }

  public boolean hasAnyExecutors() {
    return !normalExecutors.isEmpty();
  }

  Executor<S, W> mergeExecutor(Executor<S, W> executor) {
    Executor<S, W> result = new Executor<S, W>();
    result.normalExecutors = new ArrayList<NormalExecutor<S, W>>(normalExecutors);
    result.normalExecutors.addAll(executor.normalExecutors);
    return result;
  }

  public int execute(ExecutionInfo<S, W> info) throws CommandException {
    // Run normal executor
    try {
      return execute(normalExecutors, info);
    } catch (CommandException e) {
      throw e;
    } catch (Throwable ex) {
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

  private int execute(
      List<? extends NormalExecutor<S, W>> executors,
      ExecutionInfo<S, W> info) throws CommandException {
    if (isForceNative()) {
      return execute(executors, info, ExecutorType.NATIVE);
    } else if (info.sender() instanceof Senders.AbstractPlayer &&
        matches(executors, ExecutorType.PLAYER)) {
      return execute(executors, info, ExecutorType.PLAYER);
    } else if (info.sender() instanceof Senders.AbstractEntity &&
        matches(executors, ExecutorType.ENTITY)) {
      return execute(executors, info, ExecutorType.ENTITY);
    } else if (info.sender() instanceof Senders.AbstractConsoleCommandSender &&
        matches(executors, ExecutorType.CONSOLE)) {
      return execute(executors, info, ExecutorType.CONSOLE);
    } else if (info.sender() instanceof Senders.AbstractBlockCommandSender &&
        matches(executors, ExecutorType.BLOCK)) {
      return execute(executors, info, ExecutorType.BLOCK);
    } else if (info.sender() instanceof Senders.AbstractProxiedCommandSender &&
        matches(executors, ExecutorType.PROXY)) {
      return execute(executors, info, ExecutorType.PROXY);
    } else if (info.sender() instanceof Senders.AbstractRemoteConsoleCommandSender &&
        matches(executors, ExecutorType.REMOTE)) {
      return execute(executors, info, ExecutorType.REMOTE);
    } else if (info.sender() instanceof Senders.AbstractFeedbackForwardingCommandSender &&
        matches(executors, ExecutorType.FEEDBACK_FORWARDING)) {
      return execute(executors, info, ExecutorType.FEEDBACK_FORWARDING);
    } else if (matches(executors, ExecutorType.ALL)) {
      return execute(executors, info, ExecutorType.ALL);
    } else {
      throw new CommandException(
          "".replace("%s", info.sender().getClass().getSimpleName().toLowerCase())
              .replace("%S", info.sender().getClass().getSimpleName()));
    }
  }

  private int execute(
      List<? extends NormalExecutor<S, W>> executors,
      ExecutionInfo<S, W> info,
      ExecutorType type) throws CommandException {
    for (NormalExecutor<S, W> executor : executors) {
      if (executor.getType() == type) {
        return executor.executeWith(info);
      }
    }
    throw new NoSuchElementException(
        "Executor had no valid executors for type " + type.toString());
  }

  public boolean isForceNative() {
    return (matches(normalExecutors, ExecutorType.NATIVE));
  }

  private boolean matches(
      List<? extends NormalExecutor<S, W>> executors,
      ExecutorType type) {
    for (NormalExecutor<S, W> executor : executors) {
      if (executor.getType() == type) {
        return true;
      }
    }
    return false;
  }
}
