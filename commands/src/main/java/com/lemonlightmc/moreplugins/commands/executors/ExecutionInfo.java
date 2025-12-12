package com.lemonlightmc.moreplugins.commands.executors;

import com.lemonlightmc.moreplugins.commands.Senders.AbstractCommandSender;
import com.lemonlightmc.moreplugins.commands.argumentsbase.CommandArguments;

import org.bukkit.command.CommandSender;

public record ExecutionInfo<S extends CommandSender>(
    S sender, CommandArguments args) {

    }