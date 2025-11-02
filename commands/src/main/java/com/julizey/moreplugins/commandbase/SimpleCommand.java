package com.julizey.moreplugins.commandbase;

import com.julizey.moreplugins.apis.ChatAPI;
import com.julizey.moreplugins.base.MorePlugins;
import com.julizey.moreplugins.commands.CommandManager;
import java.util.List;
import java.util.Optional;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.help.HelpTopic;

//import org.bukkit.command.Command;

public class SimpleCommand extends AbstractCommand {

  private String label;
  private String description;
  private String permission;

  public SimpleCommand(
      String label,
      String description,
      String usageMessage,
      String permission,
      List<String> aliases) {
    this.label = label;
    this.description = description;
    this.usageMessage = usageMessage == null ? "/" + label : usageMessage;
    this.permission = permission;
    this.permissionMessage = "&cYou do not have permission to use this command!";
    this.aliases = aliases == null ? List.of() : aliases;
  }

  public SimpleCommand(
      String label,
      String description,
      String usageMessage,
      String permission) {
    this(label, description, usageMessage, permission, null);
  }

  public SimpleCommand(String label, String description, String usageMessage) {
    this(label, description, usageMessage, null, null);
  }

  public SimpleCommand(String label, String description) {
    this(label, description, null, null, null);
  }

  public SimpleCommand(String label) {
    this(label, "", null, "", null);
  }

  public SimpleCommand() {
    this(null, "", null, "", null);
  }

  public boolean execute(
      CommandSender sender,
      String commandLabel,
      String[] args) {
    throw new UnsupportedOperationException("Unimplemented method 'execute'");
  }

  public List<String> tabComplete(
      CommandSender sender,
      String alias,
      String[] args) {
    return List.of();
  }

  public SimpleCommand getInstance() {
    return this;
  }

  public String getDescription() {
    return description;
  }

  public SimpleCommand setDescription(String description) {
    this.description = description;
    return this;
  }

  public String getLabel() {
    return label;
  }

  public SimpleCommand setLabel(String label) {
    this.label = label;
    return this;
  }

  public String getUsageMessage() {
    return usageMessage;
  }

  public SimpleCommand setUsageMessage(String message) {
    this.usageMessage = message;
    return this;
  }

  public AbstractCommand setHelp(List<String> help) {
    this.helpMessage = String.join("\n", help);
    return this;
  }

  public List<String> getHelp() {
    return List.of(helpMessage.split("\n"));
  }

  public SimpleCommand withHelp(HelpTopic helpTopic) {
    this.meta.helpTopic = Optional.of(helpTopic);
    return getInstance();
  }

  public void register(String namespace) {
    CommandManager.setNamespace(namespace);
    CommandManager.register(this);
  }

  public void register() {
    CommandManager.register(this);
  }

  public List<String> onTabComplete(
      CommandSender sender,
      Command command,
      String label,
      String[] args) {
    return tabComplete(sender, label, args);
  }

  public boolean onCommand(
      CommandSender sender,
      Command command,
      String label,
      String[] args) {
    if (playerOnly && !(sender instanceof org.bukkit.entity.Player)) {
      ChatAPI.send(sender, "&cOnly players can use this command!");
      return true;
    }
    if (permission != null && sender.hasPermission(permission)) {
      ChatAPI.send(sender, permissionMessage);
      return true;
    }
    Bukkit
        .getScheduler()
        .runTaskAsynchronously(
            MorePlugins.instance,
            () -> {
              execute(sender, label, args);
            });
    return true;
  }
}
