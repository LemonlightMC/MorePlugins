package com.julizey.moreplugins.commands.argumentsbase;

public record ParsedArgument<Type, Impl>(
  String name,
  String raw,
  Argument<Type, Impl> type,
  Type value
) {}
