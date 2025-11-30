package com.lemonlightmc.moreplugins.commands.suggestions;

import com.lemonlightmc.moreplugins.commands.argumentsbase.CommandArguments;

public record SuggestionInfo<S>(
    S sender,
    CommandArguments previousArgs,
    String currentInput,
    String currentArg) {
}
