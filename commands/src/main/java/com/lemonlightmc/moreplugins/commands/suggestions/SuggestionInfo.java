package com.lemonlightmc.moreplugins.commands.suggestions;

import org.bukkit.command.CommandSender;

import com.lemonlightmc.moreplugins.commands.argumentsbase.CommandArguments;
import com.lemonlightmc.moreplugins.commands.executors.CommandSource;

public record SuggestionInfo<S extends CommandSender>(
        CommandSource<S> source,
        CommandArguments previousArgs,
        String currentInput,
        String currentArg) {
}
