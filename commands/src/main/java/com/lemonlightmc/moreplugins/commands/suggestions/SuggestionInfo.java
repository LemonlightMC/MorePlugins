package com.lemonlightmc.moreplugins.commands.suggestions;

import com.lemonlightmc.moreplugins.commands.CommandSource;
import com.lemonlightmc.moreplugins.commands.argumentsbase.CommandArguments;

public record SuggestionInfo<S>(
    CommandSource<S> source,
    CommandArguments args,
    String currentInput) {
}
