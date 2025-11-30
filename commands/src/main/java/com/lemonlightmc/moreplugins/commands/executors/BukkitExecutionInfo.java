package com.lemonlightmc.moreplugins.commands.executors;

import com.lemonlightmc.moreplugins.commands.Senders.BukkitCommandSender;
import com.lemonlightmc.moreplugins.commands.argumentsbase.CommandArguments;

import org.bukkit.command.CommandSender;

public record BukkitExecutionInfo<S extends CommandSender, W extends BukkitCommandSender<? extends S>>(S sender,
                W wrapper, CommandArguments args)
                implements ExecutionInfo<S, BukkitCommandSender<? extends S>> {
}
