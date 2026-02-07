package com.lemonlightmc.moreplugins.commands.executors;

import com.lemonlightmc.moreplugins.commands.CommandRequirement;
import com.lemonlightmc.moreplugins.commands.CommandSource;
import com.lemonlightmc.moreplugins.commands.SimpleSubCommand;
import com.lemonlightmc.moreplugins.commands.argumentsbase.Argument;
import com.lemonlightmc.moreplugins.commands.exceptions.CommandException;
import com.lemonlightmc.moreplugins.commands.exceptions.DuplicateArgumentException;
import com.lemonlightmc.moreplugins.commands.exceptions.GreedyArgumentException;
import com.lemonlightmc.moreplugins.commands.exceptions.OptionalArgumentException;
import com.lemonlightmc.moreplugins.commands.executors.Executors.ExecutorType;
import com.lemonlightmc.moreplugins.commands.executors.Executors.NormalExecutor;
import com.lemonlightmc.moreplugins.commands.suggestions.SuggestionInfo;
import com.lemonlightmc.moreplugins.commands.suggestions.Suggestions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public abstract class AbstractCommand<T extends AbstractCommand<T, S>, S> extends Executable<T> {

  protected List<Argument<?, ?, S>> arguments = new ArrayList<>();
  protected List<SimpleSubCommand<S>> subcommands = new ArrayList<>();
  protected Set<String> aliases = new HashSet<String>();

  protected List<CommandRequirement<CommandSource<S>>> requirements;
  protected String shortDescription;
  protected String fullDescription;
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

  private void addArgument(final Argument<?, ?, S> arg, final boolean optional) {
    if (arg == null) {
      return;
    }
    if (arguments.getLast().getType().isGreedy()) {
      throw new GreedyArgumentException(arg.getName(), GreedyArgumentException.buildArgsStr(arguments));
    }
    for (final Argument<?, ?, S> tempArg : arguments) {
      if (tempArg.getName().equals(arg.getName())) {
        throw new DuplicateArgumentException(arg.getName());
      }
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
  public T withArguments(final List<Argument<?, ?, S>> args) {
    if (args == null || args.isEmpty()) {
      return getInstance();
    }
    for (final Argument<?, ?, S> arg : args) {
      addArgument(arg, false);
    }
    return getInstance();
  }

  @SuppressWarnings("unchecked")
  public T withArguments(final Argument<?, ?, S>... args) {
    if (args == null || args.length == 0) {
      return getInstance();
    }
    for (final Argument<?, ?, S> arg : args) {
      addArgument(arg, false);
    }
    return getInstance();
  }

  public T withArguments(final List<Argument<?, ?, S>> args, final boolean optional) {
    if (args == null || args.isEmpty()) {
      return getInstance();
    }
    for (final Argument<?, ?, S> arg : args) {
      addArgument(arg, optional);
    }
    return getInstance();
  }

  public T withArguments(final Argument<?, ?, S>[] args, final boolean optional) {
    if (args == null || args.length == 0) {
      return getInstance();
    }
    for (final Argument<?, ?, S> arg : args) {
      addArgument(arg, optional);
    }
    return getInstance();
  }

  public T withOptionalArguments(final List<Argument<?, ?, S>> args) {
    return withArguments(args, true);
  }

  @SafeVarargs
  public final T withOptionalArguments(final Argument<?, ?, S>... args) {
    return withArguments(args, true);
  }

  public T setArguments(final List<Argument<?, ?, S>> args) {
    arguments = args;
    return getInstance();
  }

  @SuppressWarnings("unchecked")
  public boolean hasArguments(final Argument<?, ?, S>... args) {
    return args != null && args.length != 0 && arguments.containsAll(List.of(args));
  }

  public boolean hasArguments() {
    return !arguments.isEmpty();
  }

  public List<Argument<?, ?, S>> getArguments() {
    return arguments;
  }

  public List<Argument<?, ?, S>> getOptionalArguments() {
    return arguments.stream().filter(a -> !a.isOptional()).toList();
  }

  public T removeArguments(final Argument<?, ?, S> args) {
    arguments.remove(args);
    return getInstance();
  }

  public T removeArguments(@SuppressWarnings("unchecked") final Argument<?, ?, S>... args) {
    arguments.removeAll(List.of(args));
    return getInstance();
  }

  public T clearArguments() {
    arguments.clear();
    return getInstance();
  }

  // Subcommands
  public T withSubcommands(final List<SimpleSubCommand<S>> subs) {
    if (subs != null) {
      subcommands.addAll(subs);
    }
    return getInstance();
  }

  @SafeVarargs
  public final T withSubcommands(final SimpleSubCommand<S>... subs) {
    if (subs != null) {
      subcommands.addAll(List.of(subs));
    }
    return getInstance();
  }

  public T setSubcommands(final List<SimpleSubCommand<S>> subs) {
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

  public SimpleSubCommand<S> getSubcommand(final String sub) {
    return sub == null || sub.length() == 0 || subcommands.isEmpty() ? null
        : subcommands.stream().filter((s) -> s.getAliases().contains(sub)).findFirst().orElse(null);
  }

  public List<SimpleSubCommand<S>> getSubcommands() {
    return subcommands;
  }

  public T removeSubcommands(final SimpleSubCommand<S> sub) {
    if (sub != null) {
      subcommands.remove(sub);
    }
    return getInstance();
  }

  @SafeVarargs
  public final T removeSubcommands(final SimpleSubCommand<S>... subs) {
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
  public T withRequirement(final CommandRequirement<CommandSource<S>> requirement) {
    if (requirements == null) {
      requirements = new ArrayList<>();
    }
    requirements.add(requirement);
    return getInstance();
  }

  public T withRequirement(final Predicate<CommandSource<S>> requirement, final String message,
      final boolean hide) {
    return withRequirement(CommandRequirement.from(requirement, message, hide));
  }

  public T withRequirement(final Predicate<CommandSource<S>> requirement, final boolean hide) {
    return withRequirement(CommandRequirement.from(requirement, hide));
  }

  public T withRequirement(final Predicate<CommandSource<S>> requirement, final String message) {
    return withRequirement(CommandRequirement.from(requirement, message));
  }

  public T withRequirement(final Predicate<CommandSource<S>> requirement) {
    return withRequirement(CommandRequirement.from(requirement));
  }

  public T setRequirements(final List<CommandRequirement<CommandSource<S>>> requirements) {
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

  public List<CommandRequirement<CommandSource<S>>> getRequirements() {
    return requirements;
  }

  public boolean checkRequirements(final CommandSource<S> source) {
    if (requirements == null || requirements.size() == 0) {
      return true;
    }
    for (final CommandRequirement<CommandSource<S>> requirement : requirements) {
      if (!requirement.test(source)) {
        return false;
      }
    }
    return true;
  }

  // other
  public String getShortDescription() {
    return shortDescription;
  }

  public T withShortDescription(final String desc) {
    this.shortDescription = desc;
    return getInstance();
  }

  public String getFullDescription() {
    return fullDescription;
  }

  public T withFullDescription(final String desc) {
    this.fullDescription = desc;
    return getInstance();
  }

  protected boolean hasAnyExecutors() {
    if (hasExecutors()) {
      return true;
    }
    for (final SimpleSubCommand<S> sub : subcommands) {
      if (sub.hasAnyExecutors()) {
        return true;
      }
    }
    return false;
  }

  // execution
  protected void run(final ExecutionInfo<S> info, final int idx) throws CommandException {
    final SimpleSubCommand<S> sub = getSubcommand(info.args().getRaw(idx));
    if (sub != null) {
      sub.run(info, idx + 1);
      return;
    }
    if (executors == null || executors.isEmpty()) {
      return;
    }
    NormalExecutor<?>[] ex = getExecutors(info.source().executorType());
    if (ex == null) {
      ex = getExecutors(ExecutorType.NATIVE);
    }
    if (ex == null) {
      ex = getExecutors(ExecutorType.ALL);
    }
    if (ex == null) {
      return;
    }
    for (final NormalExecutor<?> normalExecutor : ex) {
      normalExecutor.executeWith(info);
    }
  }

  public List<Suggestions<S>> tabComplete(final SuggestionInfo<S> info, int idx) {
    if (arguments.isEmpty()) {
      if (subcommands.isEmpty()) {
        return null;
      }
      final List<String> list = new ArrayList<>();
      for (final SimpleSubCommand<S> sub : subcommands) {
        for (final String string : sub.aliases) {
          list.add(string);
        }
      }
      return List.of(Suggestions.from(list));
    }
    if (idx >= 0) {
      final String subStr = info.args().getRaw(idx);
      final SimpleSubCommand<S> sub = getSubcommand(subStr);
      if (sub != null) {
        return sub.tabComplete(info, idx + 1);
      }
      idx = 0 - (info.args().count() - idx);
    }
    if (idx == 0) {
      return null;
    }
    final Argument<?, ?, S> arg = arguments.get(Math.abs(idx + 1));
    return arg == null ? null : arg.getSuggestions();
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
    if (arguments == null && other.arguments != null || subcommands == null && other.subcommands != null
        || aliases == null && other.aliases != null || requirements == null && other.requirements != null) {
      return false;
    }
    return arguments.equals(other.arguments) && subcommands.equals(other.subcommands) && aliases.equals(other.aliases)
        && requirements.equals(other.requirements);
  }

  @Override
  public String toString() {
    return "AbstractCommand [arguments=" + arguments + ", subcommands=" + subcommands + ", aliases=" + aliases
        + ", executors=" + executors + ", requirements=" + requirements + "]";
  }

}
