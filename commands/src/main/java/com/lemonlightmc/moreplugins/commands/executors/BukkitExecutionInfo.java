package com.lemonlightmc.moreplugins.commands.executors;

import com.lemonlightmc.moreplugins.commands.Senders.AbstractCommandSender;
import com.lemonlightmc.moreplugins.commands.argumentsbase.CommandArguments;

import org.bukkit.command.CommandSender;

public record BukkitExecutionInfo<S extends CommandSender, W extends AbstractCommandSender<S>>(
    S sender,
    W wrapper, CommandArguments args)
    implements ExecutionInfo<S, AbstractCommandSender<S>> {
}
