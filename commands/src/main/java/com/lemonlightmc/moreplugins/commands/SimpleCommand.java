package com.lemonlightmc.moreplugins.commands;

import java.util.List;
import java.util.Optional;

public class SimpleCommand extends AbstractCommand {

  private final String label;
  private Optional<String> shortDescription = Optional.empty();
  private Optional<String> fullDescription = Optional.empty();
  private Optional<String[]> usageDescription = Optional.empty();
  private Optional<String> helpMessage = Optional.empty();

  public SimpleCommand(final String label) {
    this.label = label.toLowerCase();
    withAliases(label);
  }

  public void register(final String namespace) {
    CommandManager.register(this, namespace);
  }

  public void register() {
    CommandManager.register(this);
  }

  public String getName() {
    return label;
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
}
