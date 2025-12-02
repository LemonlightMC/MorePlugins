package com.lemonlightmc.moreplugins.commands.argumentsbase;

// <Type, ArgType>
public record ParsedArgument(
    String name,
    String raw,
    // Argument<Type, ArgType> type,
    Object value) {
}
