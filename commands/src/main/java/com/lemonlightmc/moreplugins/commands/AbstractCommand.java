package com.lemonlightmc.moreplugins.commands;

import com.lemonlightmc.moreplugins.apis.ChatAPI;
import com.lemonlightmc.moreplugins.base.MorePlugins;
import com.lemonlightmc.moreplugins.commands.argumentsbase.Argument;
import com.lemonlightmc.moreplugins.commands.exceptions.OptionalArgumentException;
import com.lemonlightmc.moreplugins.commands.executors.CommandSource;
import com.lemonlightmc.moreplugins.commands.executors.Executable;
import com.lemonlightmc.moreplugins.commands.executors.ExecutionInfo;
import com.lemonlightmc.moreplugins.commands.executors.Executors.*;
import com.lemonlightmc.moreplugins.messages.Logger;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class AbstractCommand extends Executable<AbstractCommand> implements PluginIdentifiableCommand {

  protected List<Argument<?, ?>> arguments = new ArrayList<>();
  protected List<AbstractCommand> subcommands = new ArrayList<>();
  protected Set<String> aliases = new HashSet<String>();

  protected Set<String> permissions = new HashSet<String>();
  protected Predicate<CommandSender> requirements = (_) -> {
    return true;
  };
  private boolean hasOptional = false;

  public Plugin getPlugin() {
    return MorePlugins.instance;
  }

  protected AbstractCommand instance() {
    return this;
  }

  // Aliases
  public AbstractCommand withAliases(final String... aliases) {
    for (final String alias : aliases) {
      this.aliases.add(alias.toLowerCase());
    }
    return this;
  }

  public AbstractCommand setAliases(final Set<String> aliases) {
    this.aliases.clear();
    for (final String alias : aliases) {
      this.aliases.add(alias.toLowerCase());
    }
    return this;
  }

  public Set<String> getAliases() {
    return this.aliases;
  }

  public boolean hasAlias(final String alias) {
    return this.aliases.contains(alias);
  }

  public AbstractCommand removeAlias(final String... aliases) {
    for (final String alias : aliases) {
      this.aliases.remove(alias.toLowerCase());
    }
    return this;
  }

  public AbstractCommand clearAliases() {
    this.aliases.clear();
    return this;
  }

  public void addArgument(final Argument<?, ?> arg, final boolean optional) {
    if (arg == null) {
      return;
    }
    arg.setOptional(optional);
    if (arg.isOptional()) {
      hasOptional = true;
    } else if (!hasOptional) {
      throw new OptionalArgumentException(arg.getName(), arguments.getLast().getName());
    }
    arguments.add(arg);
  }

  // Arguments
  public AbstractCommand withArguments(final List<Argument<?, ?>> args) {
    if (args == null || args.isEmpty()) {
      return this;
    }
    for (final Argument<?, ?> arg : args) {
      addArgument(arg, false);
    }
    return this;
  }

  public AbstractCommand withArguments(final Argument<?, ?>... args) {
    if (args == null || args.length == 0) {
      return this;
    }
    for (final Argument<?, ?> arg : args) {
      addArgument(arg, false);
    }
    return this;
  }

  public AbstractCommand withArguments(final List<Argument<?, ?>> args, final boolean optional) {
    if (args == null || args.isEmpty()) {
      return this;
    }
    for (final Argument<?, ?> arg : args) {
      addArgument(arg, optional);
    }
    return this;
  }

  public AbstractCommand withArguments(final Argument<?, ?>[] args, final boolean optional) {
    if (args == null || args.length == 0) {
      return this;
    }
    for (final Argument<?, ?> arg : args) {
      addArgument(arg, optional);
    }
    return this;
  }

  public AbstractCommand withOptionalArguments(final List<Argument<?, ?>> args) {
    return withArguments(args, true);
  }

  @SafeVarargs
  public final AbstractCommand withOptionalArguments(final Argument<?, ?>... args) {
    return withArguments(args, true);
  }

  public AbstractCommand setArguments(final List<Argument<?, ?>> args) {
    arguments = args;
    return this;
  }

  public boolean hasArguments(final Argument<?, ?>... args) {
    return args != null && args.length != 0 && arguments.containsAll(List.of(args));
  }

  public boolean hasArguments() {
    return !arguments.isEmpty();
  }

  public List<Argument<?, ?>> getArguments() {
    return arguments;
  }

  public List<Argument<?, ?>> getOptionalArguments() {
    return arguments.stream().filter(a -> !a.isOptional()).toList();
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

  // Subcommands
  public AbstractCommand withSubcommands(final List<AbstractCommand> subs) {
    subcommands.addAll(subs);
    return this;
  }

  public final AbstractCommand withSubcommands(final AbstractCommand... subs) {
    subcommands.addAll(List.of(subs));
    return this;
  }

  public AbstractCommand setSubcommands(final List<AbstractCommand> subs) {
    subcommands = subs;
    return this;
  }

  public boolean hasSubcommands(final AbstractCommand... subs) {
    return subs != null && subs.length != 0 && subcommands.containsAll(List.of(subs));
  }

  public boolean hasSubcommands() {
    return !subcommands.isEmpty();
  }

  public List<AbstractCommand> getSubcommands() {
    return subcommands;
  }

  public AbstractCommand removeSubcommands(final AbstractCommand subs) {
    subcommands.remove(subs);
    return this;
  }

  public AbstractCommand removeSubcommands(final AbstractCommand... subs) {
    subcommands.removeAll(List.of(subs));
    return this;
  }

  public AbstractCommand clearSubcommands() {
    subcommands.clear();
    return this;
  }

  // lesser setter & getters
  public AbstractCommand withPermission(final String permission) {
    permissions.add(permission);
    return this;
  }

  public void setPermissions(final Set<String> permissions) {
    this.permissions = permissions;
  }

  public Set<String> getPermissions() {
    return permissions;
  }

  public AbstractCommand withRequirement(final Predicate<CommandSender> requirement) {
    requirements = requirements.and(requirement);
    return this;
  }

  public void setRequirements(final Predicate<CommandSender> requirements) {
    this.requirements = requirements;
  }

  public Predicate<CommandSender> getRequirements() {
    return requirements;
  }

  // utils

  public boolean checkPermission(final CommandSource<CommandSender> source, final Set<String> perms) {
    if (perms == null || perms.size() == 0) {
      return true;
    }
    for (final String perm : perms) {
      if (!source.hasPermission(perm)) {
        ChatAPI.send(source.sender(), CommandManager.getPermissionMessage());
        return false;
      }
    }
    return true;
  }

  public boolean checkRequirements(final CommandSource<CommandSender> sender,
      final Predicate<CommandSender> requirements) {
    if (requirements == null) {
      return true;
    }
    if (requirements == null || !requirements.test(sender.sender())) {
      ChatAPI.send(sender.sender(), CommandManager.getRequirementsMessage());
      return false;
    }
    return true;
  }

  public boolean isPlayer(final CommandSender sender) {
    return sender != null && sender instanceof Player;
  }

  public void sendPermissionMessage(final CommandSender sender) {
    ChatAPI.send(sender, CommandManager.getPermissionMessage());
  }

  // execution
  public void execute(final ExecutionInfo<CommandSender> info) throws CommandException {
    if (info == null) {
      return;
    }
    try {
      if (!checkPermission(info.source(), permissions) || checkRequirements(info.source(), requirements)) {
        return;
      }
      if (executors == null || executors.isEmpty()) {
        return;
      }
      final ExecutorType[] priorities = Utils.prioritiesForSender(info.source().sender());
      if (priorities == null || priorities.length == 0) {
        return;
      }
      if (!_execute(info, priorities)) {
        Logger.warn("No valid Executor for " + info.source().getClass().getSimpleName().toLowerCase());
      }
    } catch (final CommandException e) {
      throw e;
    } catch (final Throwable ex) {
      Logger.warn(
          "Unhandled exception executing '" + info.args().getFullInput() + "'");
      ex.printStackTrace();
      if (ex instanceof Exception) {
        throw ex;
      }
    }
  }

  public List<String> tabComplete(final ExecutionInfo<CommandSender> info) {
    return null;
  }

  private boolean _execute(final ExecutionInfo<CommandSender> info, final ExecutorType... types)
      throws CommandException {
    final Map<ExecutorType, Integer> priorityIndex = new EnumMap<>(ExecutorType.class);
    for (int i = 0; i < types.length; i++) {
      priorityIndex.put(types[i], i);
    }

    NormalExecutor<?> best = null;
    int bestPriority = Integer.MAX_VALUE;

    for (final NormalExecutor<?> executor : executors) {
      final ExecutorType t = executor.getType();
      final Integer idx = priorityIndex.get(t);
      if (idx != null && idx < bestPriority) {
        bestPriority = idx;
        best = executor;
        if (bestPriority == 0) {
          break;
        }
      }
    }
    if (best == null) {
      return false;
    }

    best.executeWith(info);
    return true;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + ((arguments == null) ? 0 : arguments.hashCode());
    result = 31 * result + ((subcommands == null) ? 0 : subcommands.hashCode());
    result = 31 * result + ((aliases == null) ? 0 : aliases.hashCode());
    result = 31 * result + ((permissions == null) ? 0 : permissions.hashCode());
    result = 31 * result + ((requirements == null) ? 0 : requirements.hashCode());
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
    final AbstractCommand other = (AbstractCommand) obj;
    if (arguments == null) {
      if (other.arguments != null)
        return false;
    } else if (!arguments.equals(other.arguments))
      return false;
    if (subcommands == null) {
      if (other.subcommands != null)
        return false;
    } else if (!subcommands.equals(other.subcommands))
      return false;
    if (aliases == null) {
      if (other.aliases != null)
        return false;
    } else if (!aliases.equals(other.aliases))
      return false;
    if (permissions == null) {
      if (other.permissions != null)
        return false;
    } else if (!permissions.equals(other.permissions))
      return false;
    if (requirements == null) {
      if (other.requirements != null)
        return false;
    } else if (!requirements.equals(other.requirements))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "AbstractCommand [arguments=" + arguments + ", subcommands=" + subcommands + ", aliases=" + aliases
        + ", executors=" + executors + ", permissions=" + permissions + ", requirements=" + requirements + "]";
  }

}
