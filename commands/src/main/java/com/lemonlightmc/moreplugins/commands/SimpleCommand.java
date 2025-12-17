package com.lemonlightmc.moreplugins.commands;

import java.util.List;
import java.util.Optional;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

import com.lemonlightmc.moreplugins.apis.ChatAPI;
import com.lemonlightmc.moreplugins.base.PluginBase;
import com.lemonlightmc.moreplugins.commands.exceptions.InvalidCommandNameException;
import com.lemonlightmc.moreplugins.commands.executors.ExecutionInfo;

public class SimpleCommand extends AbstractCommand<SimpleCommand> {

  private final String name;
  private Optional<String> shortDescription = Optional.empty();
  private Optional<String> fullDescription = Optional.empty();
  private Optional<String[]> usageDescription = Optional.empty();
  private Optional<String> helpMessage = Optional.empty();
  private boolean withDefaultSubCommands = true;

  public SimpleCommand(final String label) {
    super();
    if (label == null || label.length() == 0 || label.isBlank()) {
      throw new InvalidCommandNameException(label);
    }
    this.name = label.toLowerCase();
    withAliases(name);
  }

  public SimpleCommand instance() {
    return this;
  }

  public void register(final String namespace) {
    CommandManager.register(this, namespace);
  }

  public void register() {
    CommandManager.register(this);
  }

  public boolean runDefault(final ExecutionInfo<CommandSender> info, final String subStr) throws CommandException {
    if (!withDefaultSubCommands) {
      return false;
    }
    if (subStr.equalsIgnoreCase("help") && helpMessage.isPresent()) {
      sendHelp(info.sender());
      return true;
    } else if (subStr.equalsIgnoreCase("reload")) {
      PluginBase.getInstance().reloadConfig();
      ChatAPI.send(info.sender(), "messages.reload");
      return true;
    }
    return false;
  }

  public String getName() {
    return name;
  }

  public String getShortDescription() {
    return shortDescription.orElse(null);
  }

  public SimpleCommand withShortDescription(final String description) {
    shortDescription = Optional.ofNullable(description);
    return this;
  }

  public String getFullDescription() {
    return fullDescription.orElse(null);
  }

  public SimpleCommand withFullDescription(final String description) {
    fullDescription = Optional.ofNullable(description);
    return this;
  }

  public SimpleCommand withUsage(final String... usage) {
    usageDescription = Optional.ofNullable(usage);
    return this;
  }

  public String[] getUsage() {
    return usageDescription.orElse(null);
  }

  public SimpleCommand withHelp(
      final String shortDesc,
      final String fullDesc) {
    shortDescription = Optional.ofNullable(shortDesc);
    fullDescription = Optional.ofNullable(fullDesc);
    return this;
  }

  public SimpleCommand withHelp(final List<String> help) {
    this.helpMessage = Optional.ofNullable(String.join("\n", help));
    return this;
  }

  public List<String> getHelp() {
    return List.of(helpMessage.orElse("").split("\n"));
  }

  public void sendHelp(final CommandSender sender) {
    ChatAPI.send(sender, helpMessage.orElse(null));
  }

  public boolean withDefaultSubCommands() {
    return withDefaultSubCommands;
  }

  public boolean withDefaultSubCommands(final boolean value) {
    withDefaultSubCommands = value;
    return withDefaultSubCommands;
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
