package com.lemonlightmc.moreplugins.commands;

import com.lemonlightmc.moreplugins.exceptions.InvalidCommandNameException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public final class Metadata<CommandSender> {

  public final String commandName;
  public List<String> aliases = new ArrayList<String>();
  public String permission = "";
  public Predicate<CommandSender> requirements = _ -> true;

  public Optional<String> shortDescription = Optional.empty();
  public Optional<String> fullDescription = Optional.empty();
  public Optional<String[]> usageDescription = Optional.empty();
  public Optional<String> helpMessage = Optional.empty();

  Metadata(final String commandName) {
    if (commandName == null || commandName.isEmpty() || commandName.contains(" ")) {
      throw new InvalidCommandNameException(commandName);
    }

    this.commandName = commandName;
  }

  public Metadata(final Metadata<CommandSender> original) {
    this(original.commandName);
    this.permission = original.permission;
    this.aliases = original.aliases;
    this.requirements = original.requirements;
    this.shortDescription = original.shortDescription.isPresent()
        ? Optional.of(original.shortDescription.get())
        : Optional.empty();
    this.fullDescription = original.fullDescription.isPresent()
        ? Optional.of(original.fullDescription.get())
        : Optional.empty();
    this.usageDescription = original.usageDescription.isPresent()
        ? Optional.of(original.usageDescription.get())
        : Optional.empty();
    this.helpMessage = original.helpMessage.isPresent()
        ? Optional.of(original.helpMessage.get())
        : Optional.empty();
  }
}
