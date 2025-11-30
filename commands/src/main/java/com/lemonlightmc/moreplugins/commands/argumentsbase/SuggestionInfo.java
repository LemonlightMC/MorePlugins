package com.lemonlightmc.moreplugins.commands.argumentsbase;

public record SuggestionInfo<S>(
                S sender,
                CommandArguments previousArgs,
                String currentInput,
                String currentArg) {
}
