package com.lemonlightmc.moreplugins.commands;

import com.lemonlightmc.moreplugins.apis.ChatAPI;
import com.lemonlightmc.moreplugins.base.MorePlugins;
import com.lemonlightmc.moreplugins.commands.Senders.AbstractCommandSender;
import com.lemonlightmc.moreplugins.commands.Senders.BukkitCommandSender;
import com.lemonlightmc.moreplugins.commands.argumentsbase.Argument;
import com.lemonlightmc.moreplugins.commands.argumentsbase.CommandArguments;
import com.lemonlightmc.moreplugins.commands.executors.BukkitExecutionInfo;
import com.lemonlightmc.moreplugins.commands.executors.ExecutionInfo;
import com.lemonlightmc.moreplugins.commands.executors.ExecutorType;
import com.lemonlightmc.moreplugins.commands.executors.Executors.*;
import com.lemonlightmc.moreplugins.exceptions.InvalidCommandSyntaxException;
import com.lemonlightmc.moreplugins.exceptions.PlatformException;
import com.lemonlightmc.moreplugins.messages.Logger;
import com.lemonlightmc.moreplugins.version.ServerEnvironment;

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

public class AbstractCommand implements PluginIdentifiableCommand {

  protected List<Argument<?, ?>> arguments = new ArrayList<>();
  protected List<AbstractCommand> subcommands = new ArrayList<>();
  protected Set<String> aliases = new HashSet<String>();
  protected List<NormalExecutor<?, ?>> executors;

  protected Set<String> permissions = new HashSet<String>();
  protected Predicate<CommandSender> requirements = (_) -> {
    return true;
  };
  protected boolean playerOnly = false;

  public Plugin getPlugin() {
    return MorePlugins.instance;
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

  // Arguments
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

  // Subcommands
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

  // Executors
  public AbstractCommand setExecutors(final List<NormalExecutor<?, ?>> ex) {
    executors = ex;
    return this;
  }

  public boolean hasExecutor(final NormalExecutor<?, ?> executor) {
    return executors != null && executors.contains(executor);
  }

  public boolean hasAnyExecutors() {
    return executors != null && !executors.isEmpty();
  }

  public List<NormalExecutor<?, ?>> getExecutors() {
    return executors;
  }

  public void clearExecutors() {
    if (executors != null) {
      executors.clear();
    }
  }

  public AbstractCommand executes(final CommandExecutor executor, final ExecutorType... types) {
    if (types == null || types.length == 0) {
      executors.add(executor);
    } else {
      for (final ExecutorType type : types) {
        executors.add(new CommandExecutor() {
          @Override
          public void run(final CommandSender sender, final CommandArguments args)
              throws InvalidCommandSyntaxException {
            executor
                .executeWith(new BukkitExecutionInfo<>(sender, Utils.wrapCommandSender(sender), args));
          }

          @Override
          public ExecutorType getType() {
            return type;
          }
        });
      }
    }
    return this;
  }

  public AbstractCommand executes(final CommandExecutionInfo executor, final ExecutorType... types) {
    if (types == null || types.length == 0) {
      executors.add(executor);
    } else {
      for (final ExecutorType type : types) {
        executors.add(new CommandExecutionInfo() {

          @Override
          public void run(final ExecutionInfo<CommandSender, BukkitCommandSender<? extends CommandSender>> info)
              throws InvalidCommandSyntaxException {
            executor.executeWith(info);
          }

          @Override
          public ExecutorType getType() {
            return type;
          }
        });
      }
    }
    return this;
  }

  // Player command executor
  public AbstractCommand executesPlayer(final PlayerCommandExecutor executor) {
    executors.add(executor);
    return this;
  }

  public AbstractCommand executesPlayer(final PlayerExecutionInfo info) {
    executors.add(info);
    return this;
  }

  // Entity command executor
  public AbstractCommand executesEntity(final EntityCommandExecutor executor) {
    executors.add(executor);
    return this;
  }

  public AbstractCommand executesEntity(final EntityExecutionInfo info) {
    executors.add(info);
    return this;
  }

  // Command block command executor
  public AbstractCommand executesCommandBlock(final CommandBlockExecutor executor) {
    executors.add(executor);
    return this;
  }

  public AbstractCommand executesCommandBlock(final CommandBlockExecutionInfo info) {
    executors.add(info);
    return this;
  }

  // Console command executor
  public AbstractCommand executesConsole(final ConsoleCommandExecutor executor) {
    executors.add(executor);
    return this;
  }

  public AbstractCommand executesConsole(final ConsoleExecutionInfo info) {
    executors.add(info);
    return this;
  }

  // RemoteConsole command executor
  public AbstractCommand executesRemoteConsole(final RemoteConsoleCommandExecutor executor) {
    executors.add(executor);
    return this;
  }

  public AbstractCommand executesRemoteConsole(final RemoteConsoleExecutionInfo info) {
    executors.add(info);
    return this;
  }

  // Feedback-forwarding command executor
  public AbstractCommand executesFeedbackForwarding(final FeedbackForwardingCommandExecutor executor) {
    if (!ServerEnvironment.isPaper()) {
      throw new PlatformException(
          "Attempted to use a FeedbackForwardingCommandExecutor on a non-paper platform ("
              + ServerEnvironment.current().name() + ")!");
    }
    executors.add(executor);
    return this;
  }

  public AbstractCommand executesFeedbackForwarding(final FeedbackForwardingExecutionInfo info) {
    if (!ServerEnvironment.isPaper()) {
      throw new PlatformException(
          "Attempted to use a FeedbackForwardingExecutionInfo on a non-paper platform ("
              + ServerEnvironment.current().name() + ")!");
    }
    executors.add(info);
    return this;
  }

  // lesser setter & getters
  public AbstractCommand withPermission(final String permission) {
    permissions.add(permission);
    return this;
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

  public AbstractCommand setPlayerOnly(final boolean value) {
    this.playerOnly = value;
    return this;
  }

  public boolean isPlayerOnly() {
    return playerOnly;
  }

  // utils
  public boolean hasPermission(final CommandSender sender, final String perm) {
    if (perm == null || perm.length() == 0)
      return false;
    return sender.hasPermission(perm);
  }

  public boolean checkPermission(final CommandSender sender, final Set<String> perms) {
    if (perms == null || perms.size() == 0) {
      return true;
    }
    for (final String perm : perms) {
      if (!sender.hasPermission(perm)) {
        ChatAPI.send(sender, CommandManager.getPermissionMessage());
        return false;
      }
    }
    return true;
  }

  public boolean checkRequirements(final CommandSender sender, final Predicate<CommandSender> requirements) {
    if (requirements == null) {
      return true;
    }
    if (requirements == null || !requirements.test(sender)) {
      ChatAPI.send(sender, CommandManager.getRequirementsMessage());
      return false;
    }
    return true;
  }

  public boolean isPlayer(final CommandSender sender) {
    return sender != null && sender instanceof Player;
  }

  public boolean checkPlayer(final CommandSender sender) {
    if (sender == null || !(sender instanceof Player)) {
      ChatAPI.send(sender, CommandManager.getPlayerOnlyMessage());
      return false;
    }
    return true;
  }

  public void sendPermissionMessage(final CommandSender sender) {
    ChatAPI.send(sender, CommandManager.getPermissionMessage());
  }

  public void sendPlayerOnlyMessage(final CommandSender sender) {
    ChatAPI.send(sender, CommandManager.getPlayerOnlyMessage());
  }

  // execution
  public void execute(final ExecutionInfo<?, ?> info) throws CommandException {
    if (info == null) {
      return;
    }
    try {
      if (!checkPermission(info.sender(), permissions) || checkRequirements(info.sender(), requirements)) {
        return;
      }
      if (executors == null || executors.isEmpty()) {
        return;
      }
      final ExecutorType[] priorities = prioritiesForSender(info.wrapper());
      if (priorities == null || priorities.length == 0) {
        return;
      }
      if (!_execute(info, priorities)) {
        Logger.warn("No valid Executor for " + info.sender().getClass().getSimpleName().toLowerCase());
      }
    } catch (final CommandException e) {
      throw e;
    } catch (final Throwable ex) {
      Logger.warn(
          "Unhandled exception executing '" + info.args().fullInput() + "'");
      ex.printStackTrace();
      if (ex instanceof Exception) {
        throw ex;
      }
    }
  }

  private boolean _execute(final ExecutionInfo<?, ?> info, final ExecutorType... types)
      throws CommandException {
    final Map<ExecutorType, Integer> priorityIndex = new EnumMap<>(ExecutorType.class);
    for (int i = 0; i < types.length; i++) {
      priorityIndex.put(types[i], i);
    }

    NormalExecutor<?, ?> best = null;
    int bestPriority = Integer.MAX_VALUE;

    for (final NormalExecutor<?, ?> executor : executors) {
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

  private ExecutorType[] prioritiesForSender(final AbstractCommandSender<?> sender) {
    if (sender == null) {
      return null;
    }
    if (sender instanceof Senders.AbstractPlayer) {
      return new ExecutorType[] { ExecutorType.PLAYER, ExecutorType.NATIVE, ExecutorType.ALL };
    } else if (sender instanceof Senders.AbstractEntity) {
      return new ExecutorType[] { ExecutorType.ENTITY, ExecutorType.NATIVE, ExecutorType.ALL };
    } else if (sender instanceof Senders.AbstractConsoleCommandSender) {
      return new ExecutorType[] { ExecutorType.CONSOLE, ExecutorType.NATIVE, ExecutorType.ALL };
    } else if (sender instanceof Senders.AbstractBlockCommandSender) {
      return new ExecutorType[] { ExecutorType.BLOCK, ExecutorType.NATIVE, ExecutorType.ALL };
    } else if (sender instanceof Senders.AbstractProxiedCommandSender) {
      return new ExecutorType[] { ExecutorType.PROXY, ExecutorType.NATIVE, ExecutorType.ALL };
    } else if (sender instanceof Senders.AbstractRemoteConsoleCommandSender) {
      return new ExecutorType[] { ExecutorType.REMOTE, ExecutorType.NATIVE, ExecutorType.ALL };
    } else if (sender instanceof Senders.AbstractFeedbackForwardingCommandSender) {
      return new ExecutorType[] { ExecutorType.FEEDBACK_FORWARDING, ExecutorType.NATIVE, ExecutorType.ALL };
    }
    return new ExecutorType[] { ExecutorType.NATIVE, ExecutorType.ALL };
  }
}
