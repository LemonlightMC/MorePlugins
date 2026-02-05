package com.lemonlightmc.moreplugins.commands.suggestions;

import org.bukkit.command.CommandSender;

import com.lemonlightmc.moreplugins.commands.CommandSource;
import com.lemonlightmc.moreplugins.commands.argumentsbase.CommandArguments;

public record SuggestionInfo<S extends CommandSender>(
                CommandSource<S> source,
                CommandArguments args,
                String currentInput) {
}
