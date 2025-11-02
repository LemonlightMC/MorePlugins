package com.julizey.moreplugins.commands.argumentsbase;

/**
 * A class that represents information which you can use to generate
 * suggestions.
 *
 * @param sender       - the CommandSender typing this command
 * @param previousArgs - a {@link CommandArguments} object holding previously
 *                     declared (and parsed) arguments. This can
 *                     be used as if it were arguments in a command executor
 *                     method
 * @param currentInput - a string representing the full current input (including
 *                     /)
 * @param currentArg   - the current partially typed argument. For example
 *                     "/mycmd tes" will return "tes"
 */
public record SuggestionInfo<S>(
    S sender,
    CommandArguments previousArgs,
    String currentInput,
    String currentArg) {
}
