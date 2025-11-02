package com.lemonlightmc.moreplugins.commandbase;

import com.lemonlightmc.moreplugins.apis.ChatAPI;
import com.lemonlightmc.moreplugins.base.MorePlugins;
import com.lemonlightmc.moreplugins.commands.Metadata;
import com.lemonlightmc.moreplugins.commands.Permission;
import com.lemonlightmc.moreplugins.commands.argumentsbase.Argument;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class AbstractCommand
    extends Executable
    implements PluginIdentifiableCommand {

  protected String usageMessage;
  protected String permissionMessage;
  protected String helpMessage;
  protected String playerOnlyMessage;
  protected boolean playerOnly = false;

  protected Metadata<CommandSender> meta;
  protected List<Argument<?, ?>> arguments = new ArrayList<>();
  protected List<AbstractCommand> subcommands = new ArrayList<>();
  protected List<String> aliases = new ArrayList<>();

  public Plugin getPlugin() {
    return MorePlugins.instance;
  }

  public AbstractCommand getInstance() {
    return this;
  }

  public String getName() {
    return meta.commandName;
  }

  public AbstractCommand withAliases(String... aliases) {
    meta.aliases = List.of(aliases);
    return getInstance();
  }

  public String[] getAliases() {
    return meta.aliases.toArray(new String[0]);
  }

  public boolean hasAlias(String alias) {
    return meta.aliases.contains(alias);
  }

  public void setAliases(String... aliases) {
    meta.aliases = meta.aliases;
  }

  public AbstractCommand setAliases(List<String> aliases) {
    meta.aliases = aliases;
    return this;
  }

  public AbstractCommand addAlias(String alias) {
    meta.aliases.add(alias);
    return this;
  }

  public AbstractCommand removeAlias(String alias) {
    meta.aliases.remove(alias);
    return this;
  }

  public AbstractCommand clearAliases() {
    meta.aliases.clear();
    return this;
  }

  public boolean hasAliases() {
    return !meta.aliases.isEmpty();
  }

  public AbstractCommand withArguments(List<Argument<?, ?>> args) {
    arguments.addAll(args);
    return this;
  }

  public AbstractCommand withArguments(Argument<?, ?>... args) {
    arguments.addAll(List.of(args));
    return this;
  }

  public AbstractCommand withOptionalArguments(List<Argument<?, ?>> args) {
    for (Argument<?, ?> argument : args) {
      argument.setOptional(true);
      this.arguments.add(argument);
    }
    return getInstance();
  }

  @SafeVarargs
  public final AbstractCommand withOptionalArguments(Argument<?, ?>... args) {
    for (Argument<?, ?> argument : args) {
      argument.setOptional(true);
      this.arguments.add(argument);
    }
    return getInstance();
  }

  public AbstractCommand setArguments(List<Argument<?, ?>> args) {
    arguments = args;
    return this;
  }

  public boolean hasArguments(Argument<?, ?>... args) {
    return arguments.containsAll(List.of(args));
  }

  public List<Argument<?, ?>> getArguments() {
    return arguments;
  }

  public AbstractCommand removeArguments(Argument<?, ?> args) {
    arguments.remove(args);
    return this;
  }

  public AbstractCommand removeArguments(Argument<?, ?>... args) {
    arguments.removeAll(List.of(args));
    return this;
  }

  public AbstractCommand clearArguments() {
    arguments.clear();
    return this;
  }

  public AbstractCommand withSubCommands(List<AbstractCommand> subs) {
    subcommands.addAll(subs);
    return this;
  }

  public final AbstractCommand withSubCommands(AbstractCommand... subs) {
    subcommands.addAll(List.of(subs));
    return this;
  }

  public AbstractCommand setSubCommands(List<AbstractCommand> subs) {
    subcommands = subs;
    return this;
  }

  public boolean hasSubCommands(AbstractCommand... subs) {
    return subcommands.containsAll(List.of(subs));
  }

  public List<AbstractCommand> getSubCommands() {
    return subcommands;
  }

  public AbstractCommand removeSubCommands(AbstractCommand subs) {
    subcommands.remove(subs);
    return this;
  }

  public AbstractCommand removeSubCommands(AbstractCommand... subs) {
    subcommands.removeAll(List.of(subs));
    return this;
  }

  public AbstractCommand clearSubCommands() {
    subcommands.clear();
    return this;
  }

  // lesser setter & getters
  public String getShortDescription() {
    return meta.shortDescription.orElse(null);
  }

  public AbstractCommand withShortDescription(String description) {
    meta.shortDescription = Optional.ofNullable(description);
    return getInstance();
  }

  public String getFullDescription() {
    return meta.fullDescription.orElse(null);
  }

  public AbstractCommand withFullDescription(String description) {
    meta.fullDescription = Optional.ofNullable(description);
    return getInstance();
  }

  public AbstractCommand withUsage(String... usage) {
    meta.usageDescription = Optional.ofNullable(usage);
    return getInstance();
  }

  public String[] getUsage() {
    return meta.usageDescription.orElse(null);
  }

  public AbstractCommand withHelp(
      String shortDescription,
      String fullDescription) {
    meta.shortDescription = Optional.ofNullable(shortDescription);
    meta.fullDescription = Optional.ofNullable(fullDescription);
    return getInstance();
  }

  public AbstractCommand setPlayerOnly(boolean value) {
    this.playerOnly = value;
    return this;
  }

  public AbstractCommand withPermission(Permission permission) {
    meta.permission = permission;
    return getInstance();
  }

  public AbstractCommand withPermission(String permission) {
    meta.permission = Permission.fromString(permission);
    return getInstance();
  }

  public AbstractCommand withoutPermission(Permission permission) {
    meta.permission = permission.negate();
    return getInstance();
  }

  public AbstractCommand withoutPermission(String permission) {
    meta.permission = Permission.fromString(permission).negate();
    return getInstance();
  }

  public AbstractCommand withRequirement(Predicate<CommandSender> requirement) {
    meta.requirements = meta.requirements.and(requirement);
    return getInstance();
  }

  public Permission getPermission() {
    return meta.permission;
  }

  public void setPermission(Permission permission) {
    meta.permission = permission;
  }

  public Predicate<CommandSender> getRequirements() {
    return meta.requirements;
  }

  public void setRequirements(Predicate<CommandSender> requirements) {
    meta.requirements = requirements;
  }

  public boolean isPlayerOnly() {
    return playerOnly;
  }

  public AbstractCommand setPermissionMessage(String permissionMessage) {
    this.permissionMessage = permissionMessage;
    return this;
  }

  public String getPermissionMessage() {
    return permissionMessage;
  }

  public AbstractCommand setPlayerOnlyMessage(String permissionMessage) {
    this.permissionMessage = permissionMessage;
    return this;
  }

  public String getPlayerOnlyMessage() {
    return permissionMessage;
  }

  // utils
  public boolean hasPermission(CommandSender sender, String perm) {
    if (perm == null || perm.length() == 0)
      return false;
    return sender.hasPermission(perm);
  }

  public boolean checkPermission(CommandSender sender, String perm) {
    if (perm == null || perm.length() == 0 || !sender.hasPermission(perm)) {
      ChatAPI.send(sender, permissionMessage);
      return false;
    }
    return true;
  }

  public boolean isPlayer(CommandSender sender) {
    return sender != null && sender instanceof Player;
  }

  public boolean checkPlayer(CommandSender sender) {
    if (sender == null || !(sender instanceof Player)) {
      ChatAPI.send(sender, playerOnlyMessage);
      return false;
    }
    return true;
  }

  public void sendHelpMessage(CommandSender sender) {
    ChatAPI.send(sender, helpMessage);
  }

  public void sendPermissionMessage(CommandSender sender) {
    ChatAPI.send(sender, permissionMessage);
  }

  public void sendPlayerOnlyMessage(CommandSender sender) {
    ChatAPI.send(sender, playerOnlyMessage);
  }

  public static ArrayList<String> copyPartialMatches(
      final String token,
      final Iterable<String> originals) throws UnsupportedOperationException, IllegalArgumentException {
    return copyPartialMatches(token, originals, new ArrayList<>());
  }

  public static ArrayList<String> copyPartialMatches(
      final String token,
      final Iterable<String> originals,
      final ArrayList<String> collection) throws UnsupportedOperationException, IllegalArgumentException {
    if (token == null || token.length() == 0)
      return collection;
    if (collection == null)
      return new ArrayList<String>();
    if (originals == null)
      return collection;
    for (String string : originals) {
      if (startsWithIgnoreCase(string, token)) {
        collection.add(string);
      }
    }
    return collection;
  }

  public static boolean startsWithIgnoreCase(
      final String string,
      final String prefix) {
    if (string == null || prefix.length() == 0)
      return false;
    if (prefix == null || prefix.length() == 0)
      return true;
    if (string.length() < prefix.length()) {
      return false;
    }
    return string.regionMatches(true, 0, prefix, 0, prefix.length());
  }
}
