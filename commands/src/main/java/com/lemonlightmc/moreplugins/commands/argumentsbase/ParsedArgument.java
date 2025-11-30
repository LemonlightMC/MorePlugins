package com.lemonlightmc.moreplugins.commands.argumentsbase;

public record ParsedArgument<Type, ArgType>(
                String name,
                String raw,
                Argument<Type, ArgType> type,
                Type value) {
}
