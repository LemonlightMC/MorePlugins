package com.julizey.moreplugins.commands;

import com.julizey.moreplugins.commands.Senders.AbstractCommandSender;
import com.julizey.moreplugins.exceptions.InvalidCommandNameException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * This class stores metadata about a command
 */
public final class Metadata<CommandSender> {

  /**
   * The command's name
   */
  public final String commandName;

  /**
   * The command's permission
   */
  public Permission permission = Permission.NONE;

  /**
   * The command's aliases
   */
  public List<String> aliases = new ArrayList<String>();

  /**
   * A predicate that a {@link AbstractCommandSender} must pass in order to
   * execute the command
   */
  public Predicate<CommandSender> requirements = _ -> true;

  /**
   * An optional short description for the command
   */
  public Optional<String> shortDescription = Optional.empty();

  /**
   * An optional full description for the command
   */
  public Optional<String> fullDescription = Optional.empty();

  /**
   * An optional usage description for the command
   */
  public Optional<String[]> usageDescription = Optional.empty();

  /**
   * An optional HelpTopic object for the command (for Bukkit)
   */
  public Optional<Object> helpTopic = Optional.empty();

  /**
   * Create command metadata
   * 
   * @param commandName The command's name
   *
   * @throws InvalidCommandNameException if the command name is not valid
   */
  Metadata(final String commandName) {
    if (commandName == null || commandName.isEmpty() || commandName.contains(" ")) {
      throw new InvalidCommandNameException(commandName);
    }

    this.commandName = commandName;
  }

  public Metadata(Metadata<CommandSender> original) {
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
    this.helpTopic = original.helpTopic.isPresent()
        ? Optional.of(original.helpTopic.get())
        : Optional.empty();
  }
}
