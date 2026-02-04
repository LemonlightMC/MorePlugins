package com.lemonlightmc.moreplugins.commands.executors;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

import com.lemonlightmc.moreplugins.commands.executors.Executors.*;
import com.lemonlightmc.moreplugins.exceptions.PlatformException;
import com.lemonlightmc.moreplugins.version.ServerEnvironment;

public abstract class Executable<T> {
  protected List<NormalExecutor<?>> executors;

  public Executable() {
    executors = new ArrayList<>();
  }

  protected abstract T getInstance();

  public T setExecutors(final List<NormalExecutor<?>> ex) {
    executors = ex;
    return getInstance();
  }

  public boolean hasExecutors() {
    return executors != null && !executors.isEmpty();
  }

  public List<NormalExecutor<?>> getExecutors() {
    return executors;
  }

  public void clearExecutors() {
    if (executors != null) {
      executors.clear();
    }
  }

  public T executes(final CommandExecutor executor, final ExecutorType... types) {
    if (types == null || types.length == 0) {
      executors.add(executor);
    } else {
      for (final ExecutorType type : types) {
        executors.add(new CommandExecutionInfo() {
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
      executors.add(executor);
    } else {
      for (final ExecutorType type : types) {
        executors.add(new CommandExecutionInfo() {
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
    executors.add(executor);
    return getInstance();
  }

  public T executesPlayer(final PlayerExecutionInfo info) {
    executors.add(info);
    return getInstance();
  }

  // Entity command executor
  public T executesEntity(final EntityCommandExecutor executor) {
    executors.add(executor);
    return getInstance();
  }

  public T executesEntity(final EntityExecutionInfo info) {
    executors.add(info);
    return getInstance();
  }

  // Command block command executor
  public T executesCommandBlock(final CommandBlockExecutor executor) {
    executors.add(executor);
    return getInstance();
  }

  public T executesCommandBlock(final CommandBlockExecutionInfo info) {
    executors.add(info);
    return getInstance();
  }

  // Console command executor
  public T executesConsole(final ConsoleCommandExecutor executor) {
    executors.add(executor);
    return getInstance();
  }

  public T executesConsole(final ConsoleExecutionInfo info) {
    executors.add(info);
    return getInstance();
  }

  // RemoteConsole command executor
  public T executesRemoteConsole(final RemoteConsoleCommandExecutor executor) {
    executors.add(executor);
    return getInstance();
  }

  public T executesRemoteConsole(final RemoteConsoleExecutionInfo info) {
    executors.add(info);
    return getInstance();
  }

  // Native command executor
  public T executesNative(final NativeCommandExecutor executor) {
    executors.add(executor);
    return getInstance();
  }

  public T executesNative(final NativeExecutionInfo info) {
    executors.add(info);
    return getInstance();
  }

  // Proxy command executor
  public T executesNative(final ProxyCommandExecutor executor) {
    executors.add(executor);
    return getInstance();
  }

  public T executesNative(final ProxyExecutionInfo info) {
    executors.add(info);
    return getInstance();
  }

  // Feedback-forwarding command executor
  public T executesFeedbackForwarding(final FeedbackForwardingExecutor executor) {
    if (!ServerEnvironment.isPaper()) {
      throw new PlatformException(
          "Attempted to use a FeedbackForwardingCommandExecutor on a non-paper platform ("
              + ServerEnvironment.current().name() + ")!");
    }
    executors.add(executor);
    return getInstance();
  }

  public T executesFeedbackForwarding(final FeedbackForwardingExecutionInfo info) {
    if (!ServerEnvironment.isPaper()) {
      throw new PlatformException(
          "Attempted to use a FeedbackForwardingExecutionInfo on a non-paper platform ("
              + ServerEnvironment.current().name() + ")!");
    }
    executors.add(info);
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
