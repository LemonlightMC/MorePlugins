package com.lemonlightmc.moreplugins.commands.executors;

import com.lemonlightmc.moreplugins.commands.Senders.AbstractCommandSender;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

public interface NormalExecutor<S extends CommandSender, W extends AbstractCommandSender<? extends CommandSender>> {
  void run(ExecutionInfo<S, W> info) throws CommandException;

  @SuppressWarnings("unchecked")
  default int executeWith(ExecutionInfo<?, ?> info)
      throws CommandException {
    this.run((ExecutionInfo<S, W>) info);
    return 1;
  }

  default ExecutorType getType() {
    return ExecutorType.ALL;
  }
}
