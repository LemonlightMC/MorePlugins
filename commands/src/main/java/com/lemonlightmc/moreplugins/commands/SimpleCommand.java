package com.lemonlightmc.moreplugins.commands;

import com.lemonlightmc.moreplugins.base.PluginBase;

import java.util.List;
import java.util.Optional;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

//import org.bukkit.command.Command;

public class SimpleCommand extends AbstractCommand {

  private final String label;
  public Optional<String> shortDescription = Optional.empty();
  public Optional<String> fullDescription = Optional.empty();
  public Optional<String[]> usageDescription = Optional.empty();
  public Optional<String> helpMessage = Optional.empty();

  public SimpleCommand(final String label) {
    this.label = label;
  }

  public boolean execute(
      final CommandSender sender,
      final String commandLabel,
      final String[] args) {
    throw new UnsupportedOperationException("Unimplemented method 'execute'");
  }

  public List<String> tabComplete(
      final CommandSender sender,
      final String alias,
      final String[] args) {
    return List.of();
  }

  public List<String> onTabComplete(
      final CommandSender sender,
      final Command command,
      final String label,
      final String[] args) {
    return tabComplete(sender, label, args);
  }

  public boolean onCommand(
      final CommandSender sender,
      final Command command,
      final String label,
      final String[] args) {
    PluginBase.getInstanceScheduler()
        .runAsync(
            () -> {
              execute(sender, label, args);
            });
    return true;
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
