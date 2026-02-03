package com.lemonlightmc.moreplugins.commands.executors;

import com.lemonlightmc.moreplugins.commands.CommandRequirement;
import com.lemonlightmc.moreplugins.commands.CommandSource;
import com.lemonlightmc.moreplugins.commands.SimpleSubCommand;
import com.lemonlightmc.moreplugins.commands.Utils;
import com.lemonlightmc.moreplugins.commands.argumentsbase.Argument;
import com.lemonlightmc.moreplugins.commands.exceptions.OptionalArgumentException;
import com.lemonlightmc.moreplugins.commands.executors.Executors.*;
import com.lemonlightmc.moreplugins.commands.suggestions.SuggestionInfo;
import com.lemonlightmc.moreplugins.commands.suggestions.Suggestions;
import com.lemonlightmc.moreplugins.messages.Logger;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

public abstract class AbstractCommand<T extends AbstractCommand<T>> extends Executable<T> {

  protected List<Argument<?, ?>> arguments = new ArrayList<>();
  protected List<SimpleSubCommand> subcommands = new ArrayList<>();
  protected Set<String> aliases = new HashSet<String>();

  protected List<CommandRequirement<CommandSender>> requirements;
  private boolean hasOptional = false;

  public abstract T getInstance();

  // Aliases
  public T withAliases(final String... aliases) {
    for (final String alias : aliases) {
      this.aliases.add(alias.toLowerCase());
    }
    return getInstance();
  }

  public T setAliases(final Set<String> aliases) {
    this.aliases.clear();
    for (final String alias : aliases) {
      this.aliases.add(alias.toLowerCase());
    }
    return getInstance();
  }

  public Set<String> getAliases() {
    return this.aliases;
  }

  public boolean hasAlias(final String alias) {
    return this.aliases.contains(alias);
  }

  public T removeAlias(final String... aliases) {
    for (final String alias : aliases) {
      this.aliases.remove(alias.toLowerCase());
    }
    return getInstance();
  }

  public T clearAliases() {
    this.aliases.clear();
    return getInstance();
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
  public T withArguments(final List<Argument<?, ?>> args) {
    if (args == null || args.isEmpty()) {
      return getInstance();
    }
    for (final Argument<?, ?> arg : args) {
      addArgument(arg, false);
    }
    return getInstance();
  }

  public T withArguments(final Argument<?, ?>... args) {
    if (args == null || args.length == 0) {
      return getInstance();
    }
    for (final Argument<?, ?> arg : args) {
      addArgument(arg, false);
    }
    return getInstance();
  }

  public T withArguments(final List<Argument<?, ?>> args, final boolean optional) {
    if (args == null || args.isEmpty()) {
      return getInstance();
    }
    for (final Argument<?, ?> arg : args) {
      addArgument(arg, optional);
    }
    return getInstance();
  }

  public T withArguments(final Argument<?, ?>[] args, final boolean optional) {
    if (args == null || args.length == 0) {
      return getInstance();
    }
    for (final Argument<?, ?> arg : args) {
      addArgument(arg, optional);
    }
    return getInstance();
  }

  public T withOptionalArguments(final List<Argument<?, ?>> args) {
    return withArguments(args, true);
  }

  @SafeVarargs
  public final T withOptionalArguments(final Argument<?, ?>... args) {
    return withArguments(args, true);
  }

  public T setArguments(final List<Argument<?, ?>> args) {
    arguments = args;
    return getInstance();
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

  public T removeArguments(final Argument<?, ?> args) {
    arguments.remove(args);
    return getInstance();
  }

  public T removeArguments(final Argument<?, ?>... args) {
    arguments.removeAll(List.of(args));
    return getInstance();
  }

  public T clearArguments() {
    arguments.clear();
    return getInstance();
  }

  // Subcommands
  public T withSubcommands(final List<SimpleSubCommand> subs) {
    if (subs != null) {
      subcommands.addAll(subs);
    }
    return getInstance();
  }

  @SafeVarargs
  public final T withSubcommands(final SimpleSubCommand... subs) {
    if (subs != null) {
      subcommands.addAll(List.of(subs));
    }
    return getInstance();
  }

  public T setSubcommands(final List<SimpleSubCommand> subs) {
    subcommands = subs;
    return getInstance();
  }

  @SafeVarargs
  public final boolean hasSubcommands(final T... subs) {
    return subs != null && subs.length != 0 && subcommands.containsAll(List.of(subs));
  }

  public boolean hasSubcommands() {
    return !subcommands.isEmpty();
  }

  public final boolean isSubcommand(final String sub) {
    return sub != null && sub.length() != 0
        && subcommands.stream().anyMatch((s) -> s.getAliases().contains(sub));
  }

  public SimpleSubCommand getSubcommand(final String sub) {
    return sub == null || sub.length() == 0 || subcommands.isEmpty() ? null
        : subcommands.stream().filter((s) -> s.getAliases().contains(sub)).findFirst().orElse(null);
  }

  public List<SimpleSubCommand> getSubcommands() {
    return subcommands;
  }

  public T removeSubcommands(final SimpleSubCommand sub) {
    if (sub != null) {
      subcommands.remove(sub);
    }
    return getInstance();
  }

  @SafeVarargs
  public final T removeSubcommands(final SimpleSubCommand... subs) {
    if (subs != null) {
      subcommands.removeAll(List.of(subs));
    }
    return getInstance();
  }

  public T clearSubcommands() {
    subcommands.clear();
    return getInstance();
  }

  // requirements
  public T withRequirement(final CommandRequirement<CommandSender> requirement) {
    if (requirements == null) {
      requirements = new ArrayList<>();
    }
    requirements.add(requirement);
    return getInstance();
  }

  public T withRequirement(final Predicate<CommandSource<CommandSender>> requirement, final String message,
      final boolean hide) {
    return withRequirement(CommandRequirement.from(requirement, message, hide));
  }

  public T withRequirement(final Predicate<CommandSource<CommandSender>> requirement, final boolean hide) {
    return withRequirement(CommandRequirement.from(requirement, hide));
  }

  public T withRequirement(final Predicate<CommandSource<CommandSender>> requirement, final String message) {
    return withRequirement(CommandRequirement.from(requirement, message));
  }

  public T withRequirement(final Predicate<CommandSource<CommandSender>> requirement) {
    return withRequirement(CommandRequirement.from(requirement));
  }

  public T setRequirements(final List<CommandRequirement<CommandSender>> requirements) {
    this.requirements = requirements;
    return getInstance();
  }

  public T withPermission(final String permission, final String message, final boolean hide) {
    return withRequirement(CommandRequirement.permission(permission, message, hide));
  }

  public T withPermission(final String permission, final boolean hide) {
    return withRequirement(CommandRequirement.permission(permission, hide));
  }

  public T withPermission(final String permission, final String message) {
    return withRequirement(CommandRequirement.permission(permission, message));
  }

  public T withPermission(final String permission) {
    return withRequirement(CommandRequirement.permission(permission));
  }

  public boolean hasRequirements() {
    return requirements != null && !requirements.isEmpty();
  }

  public T clearRequirements() {
    if (requirements != null) {
      requirements.clear();
    }
    return getInstance();
  }

  public List<CommandRequirement<CommandSender>> getRequirements() {
    return requirements;
  }

  public boolean checkRequirements(final CommandSource<CommandSender> source) {
    if (requirements == null || requirements.size() == 0) {
      return true;
    }
    for (final CommandRequirement<CommandSender> requirement : requirements) {
      if (!requirement.check(source)) {
        return false;
      }
    }
    return true;
  }

  // execution
  public void run(final ExecutionInfo<CommandSender> info) throws CommandException {
    try {
      if (executors == null || executors.isEmpty()) {
        return;
      }

      final SimpleSubCommand sub = getSubcommand(info.args().getRaw(0));
      if (sub != null) {
        sub.run(info);
        return;
      }

      final ExecutorType[] priorities = Utils.prioritiesForSender(info.source().sender());
      if (priorities == null || priorities.length == 0) {
        return;
      }
      if (!_run(info, priorities)) {
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

  public List<Suggestions<CommandSender>> tabComplete(final SuggestionInfo<CommandSender> info) {
    try {
      if (!checkRequirements(info.source())) {
        return List.of();
      }
      final String subStr = info.args().getRaw(0);
      final SimpleSubCommand sub = getSubcommand(subStr);
      if (sub != null) {
        return sub.tabComplete(info);
      }

      for (final Argument<?, ?> arg : arguments) {
        final Optional<?> opt = info.args().getByArgumentOptional(arg);
        if (opt.isPresent()) {
          return arg.getSuggestions();
        }
      }
      return null;
    } catch (final CommandException e) {
      throw e;
    } catch (final Throwable ex) {
      Logger.warn(
          "Unhandled exception executing '" + info.args().getFullInput() + "'");
      ex.printStackTrace();
      if (ex instanceof Exception) {
        throw ex;
      }
      return null;
    }
  }

  private boolean _run(final ExecutionInfo<CommandSender> info, final ExecutorType... types)
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
    result = 31 * result + ((requirements == null) ? 0 : requirements.hashCode());
    return result;
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj) || getClass() != obj.getClass()) {
      return false;
    }
    final T other = (T) obj;
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
        + ", executors=" + executors + ", requirements=" + requirements + "]";
  }

}
