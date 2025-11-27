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

  public String getName() {
    return meta.commandName;
  }

  public AbstractCommand withAliases(final String... aliases) {
    meta.aliases = List.of(aliases);
    return this;
  }

  public String[] getAliases() {
    return meta.aliases.toArray(new String[0]);
  }

  public boolean hasAlias(final String alias) {
    return meta.aliases.contains(alias);
  }

  public void setAliases(final String... aliases) {
    meta.aliases = meta.aliases;
  }

  public AbstractCommand setAliases(final List<String> aliases) {
    meta.aliases = aliases;
    return this;
  }

  public AbstractCommand addAlias(final String alias) {
    meta.aliases.add(alias);
    return this;
  }

  public AbstractCommand removeAlias(final String alias) {
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

  public AbstractCommand withArguments(final List<Argument<?, ?>> args) {
    arguments.addAll(args);
    return this;
  }

  public AbstractCommand withArguments(final Argument<?, ?>... args) {
    arguments.addAll(List.of(args));
    return this;
  }

  public AbstractCommand withOptionalArguments(final List<Argument<?, ?>> args) {
    for (final Argument<?, ?> argument : args) {
      argument.setOptional(true);
      this.arguments.add(argument);
    }
    return this;
  }

  @SafeVarargs
  public final AbstractCommand withOptionalArguments(final Argument<?, ?>... args) {
    for (final Argument<?, ?> argument : args) {
      argument.setOptional(true);
      this.arguments.add(argument);
    }
    return this;
  }

  public AbstractCommand setArguments(final List<Argument<?, ?>> args) {
    arguments = args;
    return this;
  }

  public boolean hasArguments(final Argument<?, ?>... args) {
    return arguments.containsAll(List.of(args));
  }

  public List<Argument<?, ?>> getArguments() {
    return arguments;
  }

  public AbstractCommand removeArguments(final Argument<?, ?> args) {
    arguments.remove(args);
    return this;
  }

  public AbstractCommand removeArguments(final Argument<?, ?>... args) {
    arguments.removeAll(List.of(args));
    return this;
  }

  public AbstractCommand clearArguments() {
    arguments.clear();
    return this;
  }

  public AbstractCommand withSubCommands(final List<AbstractCommand> subs) {
    subcommands.addAll(subs);
    return this;
  }

  public final AbstractCommand withSubCommands(final AbstractCommand... subs) {
    subcommands.addAll(List.of(subs));
    return this;
  }

  public AbstractCommand setSubCommands(final List<AbstractCommand> subs) {
    subcommands = subs;
    return this;
  }

  public boolean hasSubCommands(final AbstractCommand... subs) {
    return subcommands.containsAll(List.of(subs));
  }

  public List<AbstractCommand> getSubCommands() {
    return subcommands;
  }

  public AbstractCommand removeSubCommands(final AbstractCommand subs) {
    subcommands.remove(subs);
    return this;
  }

  public AbstractCommand removeSubCommands(final AbstractCommand... subs) {
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

  public AbstractCommand withShortDescription(final String description) {
    meta.shortDescription = Optional.ofNullable(description);
    return this;
  }

  public String getFullDescription() {
    return meta.fullDescription.orElse(null);
  }

  public AbstractCommand withFullDescription(final String description) {
    meta.fullDescription = Optional.ofNullable(description);
    return this;
  }

  public AbstractCommand withUsage(final String... usage) {
    meta.usageDescription = Optional.ofNullable(usage);
    return this;
  }

  public String[] getUsage() {
    return meta.usageDescription.orElse(null);
  }

  public AbstractCommand withHelp(
      final String shortDescription,
      final String fullDescription) {
    meta.shortDescription = Optional.ofNullable(shortDescription);
    meta.fullDescription = Optional.ofNullable(fullDescription);
    return this;
  }

  public AbstractCommand setPlayerOnly(final boolean value) {
    this.playerOnly = value;
    return this;
  }

  public AbstractCommand withPermission(final Permission permission) {
    meta.permission = permission;
    return this;
  }

  public AbstractCommand withPermission(final String permission) {
    meta.permission = Permission.fromString(permission);
    return this;
  }

  public AbstractCommand withoutPermission(final Permission permission) {
    meta.permission = permission.negate();
    return this;
  }

  public AbstractCommand withoutPermission(final String permission) {
    meta.permission = Permission.fromString(permission).negate();
    return this;
  }

  public AbstractCommand withRequirement(final Predicate<CommandSender> requirement) {
    meta.requirements = meta.requirements.and(requirement);
    return this;
  }

  public Permission getPermission() {
    return meta.permission;
  }

  public void setPermission(final Permission permission) {
    meta.permission = permission;
  }

  public Predicate<CommandSender> getRequirements() {
    return meta.requirements;
  }

  public void setRequirements(final Predicate<CommandSender> requirements) {
    meta.requirements = requirements;
  }

  public boolean isPlayerOnly() {
    return playerOnly;
  }

  public AbstractCommand setPermissionMessage(final String permissionMessage) {
    this.permissionMessage = permissionMessage;
    return this;
  }

  public String getPermissionMessage() {
    return permissionMessage;
  }

  public AbstractCommand setPlayerOnlyMessage(final String permissionMessage) {
    this.permissionMessage = permissionMessage;
    return this;
  }

  public String getPlayerOnlyMessage() {
    return permissionMessage;
  }

  // utils
  public boolean hasPermission(final CommandSender sender, final String perm) {
    if (perm == null || perm.length() == 0)
      return false;
    return sender.hasPermission(perm);
  }

  public boolean checkPermission(final CommandSender sender, final String perm) {
    if (perm == null || perm.length() == 0 || !sender.hasPermission(perm)) {
      ChatAPI.send(sender, permissionMessage);
      return false;
    }
    return true;
  }

  public boolean isPlayer(final CommandSender sender) {
    return sender != null && sender instanceof Player;
  }

  public boolean checkPlayer(final CommandSender sender) {
    if (sender == null || !(sender instanceof Player)) {
      ChatAPI.send(sender, playerOnlyMessage);
      return false;
    }
    return true;
  }

  public void sendHelpMessage(final CommandSender sender) {
    ChatAPI.send(sender, helpMessage);
  }

  public void sendPermissionMessage(final CommandSender sender) {
    ChatAPI.send(sender, permissionMessage);
  }

  public void sendPlayerOnlyMessage(final CommandSender sender) {
    ChatAPI.send(sender, playerOnlyMessage);
  }
}
