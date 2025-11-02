package com.lemonlightmc.moreplugins.commands;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.ProxiedCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Senders {

  public static interface AbstractCommandSender<Source> {
    /**
     * Tests if this CommandSender has permission to use the given permission node
     *
     * @param permissionNode The node to check for
     * @return True if this CommandSender holds the permission node, and false
     *         otherwise
     */
    boolean hasPermission(String permissionNode);

    /**
     * Tests if this CommandSender has `operator` status, or the ability to run any
     * command
     *
     * @return True if this CommandSender is an operator, and false otherwise
     */
    boolean isOp();

    /**
     * @return The underlying CommandSender object
     */
    Source getSource();
  }

  public static interface BukkitCommandSender<Source extends CommandSender>
      extends AbstractCommandSender<Source> {
  }

  public static interface AbstractConsoleCommandSender<Source>
      extends AbstractCommandSender<Source> {
  }

  public static interface AbstractBlockCommandSender<Source>
      extends AbstractCommandSender<Source> {
  }

  public static interface AbstractPlayer<Source>
      extends AbstractCommandSender<Source> {
  }

  public static interface AbstractEntity<Source>
      extends AbstractCommandSender<Source> {
  }

  public static interface AbstractFeedbackForwardingCommandSender<Source>
      extends AbstractCommandSender<Source> {
  }

  public static interface AbstractProxiedCommandSender<Source>
      extends AbstractCommandSender<Source> {
  }

  public static interface AbstractRemoteConsoleCommandSender<Source>
      extends AbstractCommandSender<Source> {
  }

  public static class BukkitBlockCommandSender
      implements
      AbstractBlockCommandSender<BlockCommandSender>,
      BukkitCommandSender<BlockCommandSender> {

    private final BlockCommandSender commandBlock;

    public BukkitBlockCommandSender(BlockCommandSender commandBlock) {
      this.commandBlock = commandBlock;
    }

    @Override
    public boolean hasPermission(String permissionNode) {
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

  public static class BukkitConsoleCommandSender
      implements
      AbstractConsoleCommandSender<ConsoleCommandSender>,
      BukkitCommandSender<ConsoleCommandSender> {

    private final ConsoleCommandSender sender;

    public BukkitConsoleCommandSender(ConsoleCommandSender sender) {
      this.sender = sender;
    }

    @Override
    public boolean hasPermission(String permissionNode) {
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

  public static class BukkitEntity
      implements AbstractEntity<Entity>, BukkitCommandSender<Entity> {

    private final Entity entity;

    public BukkitEntity(Entity entity) {
      this.entity = entity;
    }

    @Override
    public boolean hasPermission(String permissionNode) {
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

  public static class BukkitFeedbackForwardingCommandSender<FeedbackForwardingSender extends CommandSender>
      implements
      AbstractFeedbackForwardingCommandSender<FeedbackForwardingSender>,
      BukkitCommandSender<FeedbackForwardingSender> {

    private final FeedbackForwardingSender sender;

    public BukkitFeedbackForwardingCommandSender(
        FeedbackForwardingSender sender) {
      this.sender = sender;
    }

    @Override
    public boolean hasPermission(String permissionNode) {
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

  public static class BukkitPlayer
      implements AbstractPlayer<Player>, BukkitCommandSender<Player> {

    private final Player player;

    public BukkitPlayer(Player player) {
      this.player = player;
    }

    @Override
    public boolean hasPermission(String permissionNode) {
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

  public static class BukkitProxiedCommandSender
      implements
      AbstractProxiedCommandSender<ProxiedCommandSender>,
      BukkitCommandSender<ProxiedCommandSender> {

    private final ProxiedCommandSender proxySender;

    public BukkitProxiedCommandSender(ProxiedCommandSender player) {
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

  public static class BukkitRemoteConsoleCommandSender
      implements
      AbstractRemoteConsoleCommandSender<RemoteConsoleCommandSender>,
      BukkitCommandSender<RemoteConsoleCommandSender> {

    private final RemoteConsoleCommandSender remote;

    public BukkitRemoteConsoleCommandSender(RemoteConsoleCommandSender remote) {
      this.remote = remote;
    }

    @Override
    public boolean hasPermission(String permissionNode) {
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
}
