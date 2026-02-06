package com.lemonlightmc.moreplugins.commands.executors;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import org.bukkit.command.CommandSender;

import com.lemonlightmc.moreplugins.commands.exceptions.CommandException;
import com.lemonlightmc.moreplugins.commands.executors.Executors.*;
import com.lemonlightmc.moreplugins.exceptions.PlatformException;
import com.lemonlightmc.moreplugins.version.ServerEnvironment;

public abstract class Executable<T> {
  protected EnumMap<ExecutorType, List<NormalExecutor<?>>> executors;

  public Executable() {
    executors = new EnumMap<>(ExecutorType.class);
  }

  protected abstract T getInstance();

  public boolean hasExecutors() {
    return executors != null && !executors.isEmpty();
  }

  public List<NormalExecutor<?>> getExecutors(final ExecutorType type) {
    return executors.get(type);
  }

  public EnumMap<ExecutorType, List<NormalExecutor<?>>> getExecutors() {
    return executors;
  }

  public void clearExecutors() {
    if (executors != null) {
      executors.clear();
    }
  }

  private void add(final ExecutorType type, final NormalExecutor<?> executor) {
    executors.computeIfAbsent(type, k -> new ArrayList<>()).add(executor);
  }

  public T executes(final CommandExecutor executor, final ExecutorType... types) {
    if (types == null || types.length == 0) {
      add(ExecutorType.ALL, executor);
    } else {
      for (final ExecutorType type : types) {
        add(type, new CommandExecutionInfo() {
          @Override
          public ExecutorType getType() {
            return type;
          }

          @Override
          public void run(final ExecutionInfo<CommandSender> info) throws CommandException {
            executor.run(info);
          }
        });
      }
    }
    return getInstance();
  }

  public T executes(final CommandExecutionInfo executor, final ExecutorType... types) {
    if (types == null || types.length == 0) {
      add(ExecutorType.ALL, executor);
    } else {
      for (final ExecutorType type : types) {
        add(type, new CommandExecutionInfo() {

          @Override
          public ExecutorType getType() {
            return type;
          }

          @Override
          public void run(final ExecutionInfo<CommandSender> info) throws CommandException {
            executor.run(info);
          }
        });
      }
    }
    return getInstance();
  }

  // Player command executor
  public T executesPlayer(final PlayerCommandExecutor executor) {
    add(ExecutorType.PLAYER, executor);
    return getInstance();
  }

  public T executesPlayer(final PlayerExecutionInfo info) {
    add(ExecutorType.PLAYER, info);
    return getInstance();
  }

  // Entity command executor
  public T executesEntity(final EntityCommandExecutor executor) {
    add(ExecutorType.ENTITY, executor);
    return getInstance();
  }

  public T executesEntity(final EntityExecutionInfo info) {
    add(ExecutorType.ENTITY, info);
    return getInstance();
  }

  // Command block command executor
  public T executesCommandBlock(final CommandBlockExecutor executor) {
    add(ExecutorType.BLOCK, executor);
    return getInstance();
  }

  public T executesCommandBlock(final CommandBlockExecutionInfo info) {
    add(ExecutorType.BLOCK, info);
    return getInstance();
  }

  // Console command executor
  public T executesConsole(final ConsoleCommandExecutor executor) {
    add(ExecutorType.CONSOLE, executor);
    return getInstance();
  }

  public T executesConsole(final ConsoleExecutionInfo info) {
    add(ExecutorType.CONSOLE, info);
    return getInstance();
  }

  // RemoteConsole command executor
  public T executesRemoteConsole(final RemoteConsoleCommandExecutor executor) {
    add(ExecutorType.REMOTE, executor);
    return getInstance();
  }

  public T executesRemoteConsole(final RemoteConsoleExecutionInfo info) {
    add(ExecutorType.REMOTE, info);
    return getInstance();
  }

  // Native command executor
  public T executesNative(final NativeCommandExecutor executor) {
    add(ExecutorType.NATIVE, executor);
    return getInstance();
  }

  public T executesNative(final NativeExecutionInfo info) {
    add(ExecutorType.NATIVE, info);
    return getInstance();
  }

  // Proxy command executor
  public T executesNative(final ProxyCommandExecutor executor) {
    add(ExecutorType.PROXY, executor);
    return getInstance();
  }

  public T executesNative(final ProxyExecutionInfo info) {
    add(ExecutorType.PROXY, info);
    return getInstance();
  }

  // Feedback-forwarding command executor
  public T executesFeedbackForwarding(final FeedbackForwardingExecutor executor) {
    if (!ServerEnvironment.isPaper()) {
      throw new PlatformException(
          "Attempted to use a FeedbackForwardingCommandExecutor on a non-paper platform ("
              + ServerEnvironment.current().name() + ")!");
    }
    add(ExecutorType.FEEDBACK_FORWARDING, executor);
    return getInstance();
  }

  public T executesFeedbackForwarding(final FeedbackForwardingExecutionInfo info) {
    if (!ServerEnvironment.isPaper()) {
      throw new PlatformException(
          "Attempted to use a FeedbackForwardingExecutionInfo on a non-paper platform ("
              + ServerEnvironment.current().name() + ")!");
    }
    add(ExecutorType.FEEDBACK_FORWARDING, info);
    return getInstance();
  }

  @Override
  public int hashCode() {
    return 31 + ((executors == null) ? 0 : executors.hashCode());
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final Executable<?> other = (Executable<?>) obj;
    if (executors == null && other.executors != null) {
      return false;
    }
    return executors.equals(other.executors);
  }

  @Override
  public String toString() {
    return "Executable [executors=" + executors + "]";
  }

}
