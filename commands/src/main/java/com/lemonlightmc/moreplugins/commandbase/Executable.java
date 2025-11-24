package com.lemonlightmc.moreplugins.commandbase;

import com.lemonlightmc.moreplugins.commands.Senders;
import com.lemonlightmc.moreplugins.commands.Senders.AbstractCommandSender;
import com.lemonlightmc.moreplugins.commands.executors.Executors.*;
import com.lemonlightmc.moreplugins.commands.executors.NormalExecutor;
import java.util.List;
import org.bukkit.command.CommandSender;

public class Executable {

  public Executable withExecutor(final CommandExecutor ex) {
    Executor.getInstance().addExecutor(ex);
    return this;
  }

  public Executable withExecutor(final CommandExecutionInfo ex) {
    Executor.getInstance().addExecutor(ex);
    return this;
  }

  public Executable withExecutor(final ConsoleCommandExecutor ex) {
    Executor.getInstance().addExecutor(ex);
    return this;
  }

  public Executable withExecutor(final ConsoleExecutionInfo ex) {
    Executor.getInstance().addExecutor(ex);
    return this;
  }

  public Executable withExecutor(final RemoteConsoleCommandExecutor ex) {
    Executor.getInstance().addExecutor(ex);
    return this;
  }

  public Executable withExecutor(final RemoteConsoleExecutionInfo ex) {
    Executor.getInstance().addExecutor(ex);
    return this;
  }

  public Executable withExecutor(final CommandBlockExecutor ex) {
    Executor.getInstance().addExecutor(ex);
    return this;
  }

  public Executable withExecutor(final CommandBlockExecutionInfo ex) {
    Executor.getInstance().addExecutor(ex);
    return this;
  }

  public Executable withExecutor(final EntityCommandExecutor ex) {
    Executor.getInstance().addExecutor(ex);
    return this;
  }

  public Executable withExecutor(final EntityExecutionInfo ex) {
    Executor.getInstance().addExecutor(ex);
    return this;
  }

  public Executable withExecutor(final PlayerCommandExecutor ex) {
    Executor.getInstance().addExecutor(ex);
    return this;
  }

  public Executable withExecutor(final PlayerExecutionInfo ex) {
    Executor.getInstance().addExecutor(ex);
    return this;
  }

  public Executable withExecutor(final FeedbackForwardingCommandExecutor ex) {
    Executor.getInstance().addExecutor(ex);
    return this;
  }

  public Executable withExecutor(final FeedbackForwardingExecutionInfo ex) {
    Executor.getInstance().addExecutor(ex);
    return this;
  }

  public Executable setExecutors(
      final List<NormalExecutor<CommandSender, AbstractCommandSender<? extends CommandSender>>> ex) {
    Executor.getInstance().setExecutors(ex);
    return this;
  }

  public boolean hasExecutor(
      final NormalExecutor<CommandSender, Senders.AbstractCommandSender<? extends CommandSender>> executor) {
    return Executor.getInstance().hasExecutor(executor);
  }

  public boolean hasAnyExecutors() {
    return Executor.getInstance().hasAnyExecutors();
  }

  public List<NormalExecutor<CommandSender, AbstractCommandSender<? extends CommandSender>>> getExecutors() {
    return Executor.getInstance().getExecutors();
  }

  public void clearExecutors() {
    Executor.getInstance().clearExecutors();
  }
}
