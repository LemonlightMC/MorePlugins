package com.lemonlightmc.moreplugins.commands;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.ProxiedCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Senders {

  public static interface AbstractCommandSender<S extends CommandSender> {
    boolean hasPermission(String permissionNode);

    boolean isOp();

    S getSource();
  }

  public static interface AbstractBlockCommandSender<S extends CommandSender>
      extends AbstractCommandSender<S> {
  }

  public static interface AbstractPlayerCommandSender<S extends CommandSender>
      extends AbstractCommandSender<S> {
  }

  public static interface AbstractEntityCommandSender<S extends CommandSender>
      extends AbstractCommandSender<S> {
  }

  public static interface AbstractConsoleCommandSender<S extends CommandSender>
      extends AbstractCommandSender<S> {
  }

  public static interface AbstractRemoteConsoleCommandSender<S extends CommandSender>
      extends AbstractCommandSender<S> {
  }

  public static interface AbstractFeedbackForwardingCommandSender<S extends CommandSender>
      extends AbstractCommandSender<S> {
  }

  public static interface AbstractProxiedCommandSender<S extends CommandSender>
      extends AbstractCommandSender<S> {
  }

  public interface AbstractNativeProxyCommandSender<S extends CommandSender> extends AbstractProxiedCommandSender<S> {
  }

  public static class BukkitBlockCommandSender implements AbstractBlockCommandSender<BlockCommandSender> {

    private final BlockCommandSender commandBlock;

    public BukkitBlockCommandSender(final BlockCommandSender commandBlock) {
      this.commandBlock = commandBlock;
    }

    @Override
    public boolean hasPermission(final String permissionNode) {
      return this.commandBlock.hasPermission(permissionNode);
    }

    @Override
    public boolean isOp() {
      return this.commandBlock.isOp();
    }

    @Override
    public BlockCommandSender getSource() {
      return this.commandBlock;
    }
  }

  public static class BukkitEntityCommandSender implements AbstractEntityCommandSender<Entity> {

    private final Entity entity;

    public BukkitEntityCommandSender(final Entity entity) {
      this.entity = entity;
    }

    @Override
    public boolean hasPermission(final String permissionNode) {
      return this.entity.hasPermission(permissionNode);
    }

    @Override
    public boolean isOp() {
      return this.entity.isOp();
    }

    @Override
    public Entity getSource() {
      return this.entity;
    }
  }

  public static class BukkitPlayerCommandSender implements AbstractPlayerCommandSender<Player> {

    private final Player player;

    public BukkitPlayerCommandSender(final Player player) {
      this.player = player;
    }

    @Override
    public boolean hasPermission(final String permissionNode) {
      return this.player.hasPermission(permissionNode);
    }

    @Override
    public boolean isOp() {
      return this.player.isOp();
    }

    @Override
    public Player getSource() {
      return this.player;
    }
  }

  public static class BukkitConsoleCommandSender implements AbstractConsoleCommandSender<ConsoleCommandSender> {

    private final ConsoleCommandSender sender;

    public BukkitConsoleCommandSender(final ConsoleCommandSender sender) {
      this.sender = sender;
    }

    @Override
    public boolean hasPermission(final String permissionNode) {
      return sender.hasPermission(permissionNode);
    }

    @Override
    public boolean isOp() {
      return sender.isOp();
    }

    @Override
    public ConsoleCommandSender getSource() {
      return sender;
    }
  }

  public static class BukkitRemoteConsoleCommandSender
      implements AbstractRemoteConsoleCommandSender<RemoteConsoleCommandSender> {

    private final RemoteConsoleCommandSender remote;

    public BukkitRemoteConsoleCommandSender(final RemoteConsoleCommandSender remote) {
      this.remote = remote;
    }

    @Override
    public boolean hasPermission(final String permissionNode) {
      return remote.hasPermission(permissionNode);
    }

    @Override
    public boolean isOp() {
      return remote.isOp();
    }

    @Override
    public RemoteConsoleCommandSender getSource() {
      return remote;
    }
  }

  public static class BukkitProxiedCommandSender implements AbstractProxiedCommandSender<ProxiedCommandSender> {

    private final ProxiedCommandSender proxySender;

    public BukkitProxiedCommandSender(final ProxiedCommandSender player) {
      this.proxySender = player;
    }

    @Override
    public boolean hasPermission(final String permissionNode) {
      return this.proxySender.hasPermission(permissionNode);
    }

    @Override
    public boolean isOp() {
      return this.proxySender.isOp();
    }

    @Override
    public ProxiedCommandSender getSource() {
      return this.proxySender;
    }
  }

  public static class BukkitFeedbackForwardingCommandSender<FeedbackForwardingSender extends CommandSender>
      implements AbstractFeedbackForwardingCommandSender<FeedbackForwardingSender> {

    private final FeedbackForwardingSender sender;

    public BukkitFeedbackForwardingCommandSender(
        final FeedbackForwardingSender sender) {
      this.sender = sender;
    }

    @Override
    public boolean hasPermission(final String permissionNode) {
      return this.sender.hasPermission(permissionNode);
    }

    @Override
    public boolean isOp() {
      return this.sender.isOp();
    }

    @Override
    public FeedbackForwardingSender getSource() {
      return this.sender;
    }
  }

  public static class BukkitNativeProxyCommandSender implements AbstractNativeProxyCommandSender<ProxiedCommandSender> {

    private final ProxiedCommandSender proxySender;

    public BukkitNativeProxyCommandSender(ProxiedCommandSender player) {
      this.proxySender = player;
    }

    @Override
    public boolean hasPermission(String permissionNode) {
      return this.proxySender.hasPermission(permissionNode);
    }

    @Override
    public boolean isOp() {
      return this.proxySender.isOp();
    }

    @Override
    public ProxiedCommandSender getSource() {
      return this.proxySender;
    }

  }
}
