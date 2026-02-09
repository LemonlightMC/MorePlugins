package com.lemonlightmc.moreplugins.commands.argumentsbase;

public record ParsedArgument(
    String name,
    String raw,
    Object value) {
}
