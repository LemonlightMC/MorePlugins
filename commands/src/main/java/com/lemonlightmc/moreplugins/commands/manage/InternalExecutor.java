package com.lemonlightmc.moreplugins.commands.manage;

import com.lemonlightmc.moreplugins.base.PluginBase;
import com.lemonlightmc.moreplugins.commands.SimpleCommand;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class InternalExecutor extends Command {

  private final SimpleCommand cmd;
  private final boolean runAsync;

  public InternalExecutor(final SimpleCommand cmd, final boolean runAsync) {
    super(cmd == null ? null : cmd.getKey());
    if (cmd == null) {
      throw new IllegalArgumentException("Command cannot be null");
    }
    this.cmd = cmd;
    super.setAliases(List.copyOf(cmd.getAliases()));
    this.runAsync = runAsync;
  }

  public SimpleCommand getSimpleCommand() {
    return cmd;
  }

  public String getNamespace() {
    return cmd.getNamespace();
  }

  public String getLabel() {
    return super.getName();
  }

  public boolean setLabel(final String label) {
    if (super.setLabel(label)) {
      cmd.setKey(label);
      return true;
    }
    return false;
  }

  @Override
  public boolean execute(final CommandSender sender, final String label0, final String[] args) {
    if (!CommandHandler.shouldHandle(cmd, sender, label0)) {
      return true;
    }
    if (!runAsync) {
      CommandHandler.run(cmd, sender, args);
      return true;
    }
    PluginBase.getInstanceScheduler()
        .runAsync(() -> {
          CommandHandler.run(cmd, sender, args);
        });
    return true;
  }

  @Override
  public List<String> tabComplete(
      final CommandSender sender,
      final String label,
      final String[] args,
      final Location location) {
    if (!CommandHandler.shouldHandle(cmd, sender, label)) {
      return List.of();
    }
    return CommandHandler.tabComplete(cmd, sender, args);
  }

  @Override
  public String toString() {
    return cmd.getName().toString() + "(" + PluginBase.getInstance().getDescription().getFullName() + ")";
  }

  @Override
  public int hashCode() {
    return 31 + cmd.hashCode();
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    return cmd.equals(((InternalExecutor) obj).cmd);
  }
}
