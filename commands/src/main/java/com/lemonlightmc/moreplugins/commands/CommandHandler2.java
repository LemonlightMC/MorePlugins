package com.lemonlightmc.moreplugins.commands;

/* 
import com.lemonlightmc.moreplugins.base.MorePlugins;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkit;
import dev.jorel.commandapi.CommandAPIHandler;
import dev.jorel.commandapi.RegisteredCommand;
import dev.jorel.commandapi.arguments.AbstractArgument;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.help.HelpTopic;
*/

public class CommandHandler2 {

  public void onLoad() {
  }

  public void onEnable() {
  }

  public void onDisable() {
  }
  /*
   * private void generateHelpUsage(StringBuilder sb, RegisteredCommand command) {
   * // Generate usages
   * String[] usages = getUsageList(command);
   * 
   * if (usages.length == 0) {
   * // Might happen if the developer calls `.withUsage()` with no parameters
   * // They didn't give any usage, so we won't put any there
   * return;
   * }
   * 
   * sb.append(ChatColor.GOLD).append("Usage: ").append(ChatColor.WHITE);
   * // If 1 usage, put it on the same line, otherwise format like a list
   * if (usages.length == 1) {
   * sb.append(usages[0]);
   * } else {
   * for (String usage : usages) {
   * sb.append("\n- ").append(usage);
   * }
   * }
   * }
   * 
   * private String[] getUsageList(RegisteredCommand currentCommand) {
   * List<RegisteredCommand> commandsWithIdenticalNames = new ArrayList<>();
   * 
   * // Collect every command with the same name
   * for (RegisteredCommand registeredCommand : CommandAPIHandler.getInstance()
   * .registeredCommands) {
   * if (
   * registeredCommand.commandName().equals(currentCommand.commandName())
   * ) {
   * commandsWithIdenticalNames.add(registeredCommand);
   * }
   * }
   * 
   * // Generate command usage or fill it with a user provided one
   * final String[] usages;
   * final Optional<String[]> usageDescription =
   * currentCommand.usageDescription();
   * if (usageDescription.isPresent()) {
   * usages = usageDescription.get();
   * } else {
   * // TODO: Figure out if default usage generation should be updated
   * final int numCommandsWithIdenticalNames = commandsWithIdenticalNames.size();
   * usages = new String[numCommandsWithIdenticalNames];
   * for (int i = 0; i < numCommandsWithIdenticalNames; i++) {
   * final RegisteredCommand command = commandsWithIdenticalNames.get(i);
   * StringBuilder usageString = new StringBuilder();
   * usageString.append("/").append(command.commandName()).append(" ");
   * for (AbstractArgument<?, ?, ?, ?> arg : command.arguments()) {
   * usageString.append(arg.getHelpString()).append(" ");
   * }
   * usages[i] = usageString.toString().trim();
   * }
   * }
   * return usages;
   * }
   * 
   * void updateHelpForCommands(List<RegisteredCommand> commands) {
   * Map<String, HelpTopic> helpTopicsToAdd = new HashMap<>();
   * 
   * for (RegisteredCommand command : commands) {
   * // Don't override the plugin help topic
   * String commandPrefix = generateCommandHelpPrefix(command.commandName());
   * 
   * StringBuilder aliasSb = new StringBuilder();
   * final String shortDescription;
   * 
   * // Must be empty string, not null as defined by OBC::CustomHelpTopic
   * final String permission = command.permission().getPermission().orElse("");
   * 
   * HelpTopic helpTopic;
   * final Optional<Object> commandHelpTopic = command.helpTopic();
   * if (commandHelpTopic.isPresent()) {
   * helpTopic = (HelpTopic) commandHelpTopic.get();
   * shortDescription = "";
   * } else {
   * // Generate short description
   * final Optional<String> shortDescriptionOptional = command.shortDescription();
   * final Optional<String> fullDescriptionOptional = command.fullDescription();
   * if (shortDescriptionOptional.isPresent()) {
   * shortDescription = shortDescriptionOptional.get();
   * } else if (fullDescriptionOptional.isPresent()) {
   * shortDescription = fullDescriptionOptional.get();
   * } else {
   * shortDescription =
   * "A command by the " + MorePlugins.instance.getName() + " plugin";
   * }
   * 
   * // Generate full description
   * StringBuilder sb = new StringBuilder();
   * if (fullDescriptionOptional.isPresent()) {
   * sb
   * .append(ChatColor.GOLD)
   * .append("Description: ")
   * .append(ChatColor.WHITE)
   * .append(fullDescriptionOptional.get())
   * .append("\n");
   * }
   * 
   * generateHelpUsage(sb, command);
   * sb.append("\n");
   * 
   * // Generate aliases. We make a copy of the StringBuilder because we
   * // want to change the output when we register aliases
   * aliasSb = new StringBuilder(sb.toString());
   * if (command.aliases().length > 0) {
   * sb
   * .append(ChatColor.GOLD)
   * .append("Aliases: ")
   * .append(ChatColor.WHITE)
   * .append(String.join(", ", command.aliases()));
   * }
   * 
   * helpTopic =
   * generateHelpTopic(
   * commandPrefix,
   * shortDescription,
   * sb.toString().trim(),
   * permission
   * );
   * }
   * helpTopicsToAdd.put(commandPrefix, helpTopic);
   * 
   * for (String alias : command.aliases()) {
   * if (commandHelpTopic.isPresent()) {
   * helpTopic = (HelpTopic) commandHelpTopic.get();
   * } else {
   * StringBuilder currentAliasSb = new StringBuilder(aliasSb.toString());
   * currentAliasSb
   * .append(ChatColor.GOLD)
   * .append("Aliases: ")
   * .append(ChatColor.WHITE);
   * 
   * // We want to get all aliases (including the original command name),
   * // except for the current alias
   * List<String> aliases = new ArrayList<>(
   * Arrays.asList(command.aliases())
   * );
   * aliases.add(command.commandName());
   * aliases.remove(alias);
   * 
   * currentAliasSb.append(String.join(", ", aliases));
   * 
   * // Don't override the plugin help topic
   * commandPrefix = generateCommandHelpPrefix(alias);
   * helpTopic =
   * generateHelpTopic(
   * commandPrefix,
   * shortDescription,
   * currentAliasSb.toString().trim(),
   * permission
   * );
   * }
   * helpTopicsToAdd.put(commandPrefix, helpTopic);
   * }
   * }
   * 
   * // We have to use helpTopics.put (instead of .addTopic) because we're
   * overwriting an existing help topic, not adding a new help topic
   * getHelpMap().putAll(helpTopicsToAdd);
   * }
   * 
   * private String generateCommandHelpPrefix(String command) {
   * return (
   * (Bukkit.getPluginCommand(command) == null ? "/" : "/minecraft:") + command
   * );
   * }
   * 
   * private String generateCommandHelpPrefix(String command, String namespace) {
   * return (
   * (
   * Bukkit.getPluginCommand(command) == null
   * ? "/" + namespace + ":"
   * : "/minecraft:"
   * ) +
   * command
   * );
   * }
   */
}
