package com.lemonlightmc.moreplugins.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;

import com.lemonlightmc.moreplugins.commands.argumentsbase.Argument;
import com.lemonlightmc.moreplugins.commands.exceptions.InvalidCommandNameException;
import com.lemonlightmc.moreplugins.commands.exceptions.MissingCommandExecutorException;
import com.lemonlightmc.moreplugins.commands.executors.AbstractCommand;

public class SimpleCommand extends AbstractCommand<SimpleCommand, CommandSender> {

  private NamespacedKey key = null;
  private String[] usageDescription;
  private String helpMessage;

  public SimpleCommand(final NamespacedKey key) {
    super();
    setName(key);
  }

  public SimpleCommand getInstance() {
    return this;
  }

  public static SimpleCommand create(final NamespacedKey key) {
    return new SimpleCommand(key);
  }

  public static SimpleSubCommand<CommandSender> subCommand(final String... aliases) {
    return new SimpleSubCommand<CommandSender>(aliases);
  }

  public SimpleCommand register() {
    build();
    CommandAPI.register(this);
    return this;
  }

  public SimpleCommand unregister() {
    CommandAPI.unregister(this);
    return this;
  }

  public boolean isRegistered() {
    return CommandAPI.isRegistered(key.toString());
  }

  public String getNamespace() {
    return key.getNamespace();
  }

  public String getKey() {
    return key.getKey();
  }

  public NamespacedKey getName() {
    return key;
  }

  public SimpleCommand setName(final NamespacedKey key) {
    if (this.key != null && this.key.equals(key)) {
      return this;
    }
    if (key == null || key.getKey().length() == 0 || key.getKey().isBlank() || key.getNamespace().length() == 0
        || key.getNamespace().isBlank()) {
      throw new InvalidCommandNameException(key == null ? "null" : key.toString());
    }
    if (isRegistered()) {
      throw new IllegalStateException("Cannot change the name of a registered command!");
    }
    if (this.key != null) {
      removeAlias(this.key.getKey());
    }
    this.key = key;
    withAliases(this.key.getKey());
    return this;
  }

  public SimpleCommand setKey(final String key) {
    setName(new NamespacedKey(getNamespace(), key));
    return this;
  }

  public SimpleCommand setNamespacey(final String namespace) {
    setName(new NamespacedKey(namespace, getKey()));
    return this;
  }

  public SimpleCommand withUsage(final String... usage) {
    this.usageDescription = usage;
    return this;
  }

  public String[] getUsage() {
    return usageDescription;
  }

  public SimpleCommand withHelp(
      final String shortDesc,
      final String fullDesc) {
    this.shortDescription = shortDesc;
    this.fullDescription = fullDesc;
    return this;
  }

  public SimpleCommand withHelp(final List<String> help) {
    this.helpMessage = String.join("\n", help);
    return this;
  }

  public SimpleCommand withHelp(final String help) {
    this.helpMessage = help;
    return this;
  }

  public List<String> getHelp() {
    return helpMessage == null ? null : List.of(helpMessage.split("\n"));
  }

  void build() {
    if (!hasAnyExecutors()) {
      throw new MissingCommandExecutorException(getName().toString());
    }
    if (fullDescription == null) {
      fullDescription = "The " + getKey() + " Command from " + getNamespace() + " (No Description)";
    }
    if (shortDescription == null) {
      shortDescription = "The " + getKey() + " Command from " + getNamespace() + " (No Description)";
    }
    if (usageDescription == null) {
      usageDescription = buildUsageString("/", this).toArray(new String[0]);
    }
    if (helpMessage == null) {
      final StringBuilder builder = new StringBuilder(shortDescription);
      builder.append("Description: ");
      builder.append(fullDescription);
      if (usageDescription != null) {
        builder.append("\nUsage:");
        for (final String usageLine : usageDescription) {
          builder.append(" ");
          builder.append(usageLine);
        }
      }
      builder.append("\nAliases: " + String.join(", ", aliases));
      helpMessage = builder.toString();
    }
  }

  private List<String> buildUsageString(final String str, final AbstractCommand<?, CommandSender> command) {
    final ArrayList<String> usageList = new ArrayList<>();
    String str2 = str + " <" + String.join("|", aliases) + ">";
    for (final SimpleSubCommand<CommandSender> subCmd : subcommands) {
      usageList.addAll(buildUsageString(str2, subCmd));
    }
    for (final Argument<?, ?, CommandSender> arg : command.getArguments()) {
      str2 += " ";
      str2 += arg.getHelpString();
    }
    usageList.add(str2);
    return usageList;
  }

  @Override
  public int hashCode() {
    int result = 31 * super.hashCode() + key.hashCode();
    result = 31 * result + Arrays.hashCode(usageDescription);
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
    if (helpMessage == null && other.helpMessage != null) {
      return false;
    }
    return key.equals(other.key) && Arrays.equals(usageDescription, other.usageDescription)
        && helpMessage.equals(other.helpMessage);
  }

  @Override
  public String toString() {
    return "SimpleCommand [key=" + key + ", shortDescription=" + shortDescription + ", usageDescription="
        + usageDescription + ", arguments=" + arguments + ", subcommands=" + subcommands + ", aliases=" + aliases
        + "]";
  }

}
