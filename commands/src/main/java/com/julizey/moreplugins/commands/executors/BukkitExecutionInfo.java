package com.julizey.moreplugins.commands.executors;

import com.julizey.moreplugins.commands.Senders.BukkitCommandSender;
import com.julizey.moreplugins.commands.argumentsbase.CommandArguments;
import org.bukkit.command.CommandSender;

public record BukkitExecutionInfo<
  S extends CommandSender, W extends BukkitCommandSender<? extends S>
>(S sender, W wrapper, CommandArguments args)
  implements ExecutionInfo<S, BukkitCommandSender<? extends S>> {}
