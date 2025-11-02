package com.julizey.moreplugins.commands.executors;

import com.julizey.moreplugins.commands.Senders.AbstractCommandSender;
import com.julizey.moreplugins.commands.argumentsbase.CommandArguments;
import org.bukkit.command.CommandSender;

public interface ExecutionInfo<
  S extends CommandSender, W extends AbstractCommandSender<?>
> {
  S sender();

  W wrapper();

  CommandArguments args();
}
