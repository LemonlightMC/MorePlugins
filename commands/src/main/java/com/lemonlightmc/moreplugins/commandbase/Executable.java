package com.lemonlightmc.moreplugins.commandbase;

import com.lemonlightmc.moreplugins.commands.Senders.AbstractCommandSender;
import com.lemonlightmc.moreplugins.commands.executors.Executors.*;
import com.lemonlightmc.moreplugins.commands.executors.NormalExecutor;
import java.util.List;
import org.bukkit.command.CommandSender;

public class Executable {

  public Executable withExecutor(CommandExecutor ex) {
    Executor.getInstance().addNormalExecutor(ex);
    return this;
  }

  public Executable withExecutor(CommandExecutionInfo ex) {
    Executor.getInstance().addNormalExecutor(ex);
    return this;
  }

  public Executable withExecutor(ConsoleCommandExecutor ex) {
    Executor.getInstance().addNormalExecutor(ex);
    return this;
  }

  public Executable withExecutor(ConsoleExecutionInfo ex) {
    Executor.getInstance().addNormalExecutor(ex);
    return this;
  }

  public Executable withExecutor(RemoteConsoleCommandExecutor ex) {
    Executor.getInstance().addNormalExecutor(ex);
    return this;
  }

  public Executable withExecutor(RemoteConsoleExecutionInfo ex) {
    Executor.getInstance().addNormalExecutor(ex);
    return this;
  }

  public Executable withExecutor(CommandBlockExecutor ex) {
    Executor.getInstance().addNormalExecutor(ex);
    return this;
  }

  public Executable withExecutor(CommandBlockExecutionInfo ex) {
    Executor.getInstance().addNormalExecutor(ex);
    return this;
  }

  public Executable withExecutor(EntityCommandExecutor ex) {
    Executor.getInstance().addNormalExecutor(ex);
    return this;
  }

  public Executable withExecutor(EntityExecutionInfo ex) {
    Executor.getInstance().addNormalExecutor(ex);
    return this;
  }

  public Executable withExecutor(PlayerCommandExecutor ex) {
    Executor.getInstance().addNormalExecutor(ex);
    return this;
  }

  public Executable withExecutor(PlayerExecutionInfo ex) {
    Executor.getInstance().addNormalExecutor(ex);
    return this;
  }

  public Executable withExecutor(FeedbackForwardingCommandExecutor ex) {
    Executor.getInstance().addNormalExecutor(ex);
    return this;
  }

  public Executable withExecutor(FeedbackForwardingExecutionInfo ex) {
    Executor.getInstance().addNormalExecutor(ex);
    return this;
  }

  public Executable setExecutor(
      List<NormalExecutor<CommandSender, AbstractCommandSender<? extends CommandSender>>> ex) {
    Executor.getInstance().setNormalExecutors(ex);
    return this;
  }

  public boolean hasExecutors() {
    return Executor.getInstance().hasAnyExecutors();
  }

  public List<NormalExecutor<CommandSender, AbstractCommandSender<? extends CommandSender>>> getExecutors() {
    return Executor.getInstance().getNormalExecutors();
  }

  public void clearExecutors() {
    Executor.getInstance().clearExecutors();
  }
}
