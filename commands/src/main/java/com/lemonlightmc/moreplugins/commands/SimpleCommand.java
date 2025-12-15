package com.lemonlightmc.moreplugins.commands;

import java.util.List;
import java.util.Optional;

import com.lemonlightmc.moreplugins.commands.exceptions.InvalidCommandNameException;

public class SimpleCommand extends AbstractCommand {

  private final String name;
  private Optional<String> shortDescription = Optional.empty();
  private Optional<String> fullDescription = Optional.empty();
  private Optional<String[]> usageDescription = Optional.empty();
  private Optional<String> helpMessage = Optional.empty();

  public SimpleCommand(final String label) {
    super();
    if (label == null || label.length() == 0 || label.isBlank()) {
      throw new InvalidCommandNameException(label);
    }
    this.name = label.toLowerCase();
    withAliases(name);
  }

  public void register(final String namespace) {
    CommandManager.register(this, namespace);
  }

  public void register() {
    CommandManager.register(this);
  }

  public String getName() {
    return name;
  }

  public String getShortDescription() {
    return shortDescription.orElse(null);
  }

  public AbstractCommand withShortDescription(final String description) {
    shortDescription = Optional.ofNullable(description);
    return this;
  }

  public String getFullDescription() {
    return fullDescription.orElse(null);
  }

  public AbstractCommand withFullDescription(final String description) {
    fullDescription = Optional.ofNullable(description);
    return this;
  }

  public AbstractCommand withUsage(final String... usage) {
    usageDescription = Optional.ofNullable(usage);
    return this;
  }

  public String[] getUsage() {
    return usageDescription.orElse(null);
  }

  public AbstractCommand withHelp(
      final String shortDesc,
      final String fullDesc) {
    shortDescription = Optional.ofNullable(shortDesc);
    fullDescription = Optional.ofNullable(fullDesc);
    return this;
  }

  public AbstractCommand withHelp(final List<String> help) {
    this.helpMessage = Optional.ofNullable(String.join("\n", help));
    return this;
  }

  public List<String> getHelp() {
    return List.of(helpMessage.orElse("").split("\n"));
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + name.hashCode();
    result = 31 * result + ((shortDescription == null) ? 0 : shortDescription.hashCode());
    result = 31 * result + ((fullDescription == null) ? 0 : fullDescription.hashCode());
    result = 31 * result + ((usageDescription == null) ? 0 : usageDescription.hashCode());
    result = 31 * result + ((helpMessage == null) ? 0 : helpMessage.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj) || getClass() != obj.getClass()) {
      return false;
    }
    final SimpleCommand other = (SimpleCommand) obj;
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    if (shortDescription == null) {
      if (other.shortDescription != null) {
        return false;
      }
    } else if (!shortDescription.equals(other.shortDescription)) {
      return false;
    }
    if (fullDescription == null) {
      if (other.fullDescription != null) {
        return false;
      }
    } else if (!fullDescription.equals(other.fullDescription)) {
      return false;
    }
    if (usageDescription == null) {
      if (other.usageDescription != null) {
        return false;
      }
    } else if (!usageDescription.equals(other.usageDescription)) {
      return false;
    }
    if (helpMessage == null) {
      if (other.helpMessage != null) {
        return false;
      }
    } else if (!helpMessage.equals(other.helpMessage)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "SimpleCommand [name=" + name + ", shortDescription=" + shortDescription + ", usageDescription="
        + usageDescription + ", arguments=" + arguments + ", subcommands=" + subcommands + ", aliases=" + aliases
        + ", permissions=" + permissions + "]";
  }

}
