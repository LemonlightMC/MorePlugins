package com.lemonlightmc.moreplugins.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;

import com.lemonlightmc.moreplugins.apis.ChatAPI;
import com.lemonlightmc.moreplugins.commands.argumentsbase.Argument;
import com.lemonlightmc.moreplugins.commands.exceptions.InvalidCommandNameException;

public class SimpleCommand extends AbstractCommand<SimpleCommand> {

  private NamespacedKey key = null;
  private Optional<String> shortDescription = Optional.empty();
  private Optional<String> fullDescription = Optional.empty();
  private Optional<String[]> usageDescription = Optional.empty();
  private Optional<String> helpMessage = Optional.empty();

  public SimpleCommand(final NamespacedKey key) {
    super();
    setName(key);
  }

  public SimpleCommand getInstance() {
    return this;
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
    return CommandAPI.isRegistered(this);
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
    if (key == null || key.getKey().length() == 0 || key.getKey().isBlank()
        || Utils.isInvalidNamespace(key.getNamespace())) {
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

  private void build() {
    if (usageDescription.isEmpty()) {
      usageDescription = Optional.of(buildUsageString("/" + key.getKey(), this).toArray(new String[0]));
    }
    if (helpMessage.isEmpty()) {
      final StringBuilder builder = new StringBuilder();
      final ArrayList<String> helpLines = new ArrayList<>();
      if (fullDescription.isPresent()) {
        builder.append(fullDescription.get());
      } else {
        builder.append("\nDescription not available!");
      }
      if (usageDescription.isPresent()) {
        builder.append("\nUsage:");
        for (final String usageLine : usageDescription.get()) {
          builder.append(" ");
          builder.append(usageLine);
        }
      }
      builder.append("\nAliases: " + String.join(", ", aliases));
      helpMessage = Optional.of(helpLines.toString());
    }
  }

  private ArrayList<String> buildUsageString(String str, final AbstractCommand<?> command) {
    final ArrayList<String> usageList = new ArrayList<>();
    for (final SimpleSubCommand subCmd : subcommands) {
      usageList.addAll(buildUsageString(str, subCmd));
    }
    for (final Argument<?, ?> arg : command.arguments) {
      str += " ";
      str += arg.getHelpString();
    }
    usageList.add(str);
    return usageList;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + key.hashCode();
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
    if (key == null) {
      if (other.key != null) {
        return false;
      }
    } else if (!key.equals(other.key)) {
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
    return "SimpleCommand [key=" + key + ", shortDescription=" + shortDescription + ", usageDescription="
        + usageDescription + ", arguments=" + arguments + ", subcommands=" + subcommands + ", aliases=" + aliases
        + "]";
  }

}
