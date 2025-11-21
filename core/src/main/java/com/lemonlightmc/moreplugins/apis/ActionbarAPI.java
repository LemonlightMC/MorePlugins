package com.lemonlightmc.moreplugins.apis;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.lemonlightmc.moreplugins.base.MorePlugins;
import com.lemonlightmc.moreplugins.messages.MessageFormatter;
import com.lemonlightmc.moreplugins.utils.StringUtils.Replaceable;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * Utility for sending action-bar messages to players.
 *
 * <p>
 * Action-bar messages are short messages shown above the player's hotbar. This
 * class provides convenience static helpers to send one-off action-bar
 * messages or timed messages to an individual player or to all online
 * players. Messages passed into the methods of this class are expected to be
 * processed by {@link com.lemonlightmc.moreplugins.messages.MessageFormatter}
 * when placeholders or formatting are required.
 * </p>
 *
 * <p>
 * The implementation uses BungeeCord's chat components to send messages and
 * manages scheduled tasks for timed messages in {@link #send(Player, String,
 * int, com.lemonlightmc.moreplugins.utils.StringUtils.Replaceable...)}. If a
 * timed action-bar is already pending for a player it will be cancelled and
 * replaced by the new one.
 * </p>
 */
public class ActionbarAPI {
  /**
   * Broadcasts an action-bar message to all online players.
   *
   * @param msg          the message to send (may contain placeholders and color
   *                     codes)
   * @param replaceables optional replacement tokens used by the message
   *                     formatter
   */
  public static void broadcast(
      final String msg,
      final Replaceable... replaceables) {
    for (final Player player : Bukkit.getOnlinePlayers()) {
      send(player, msg, replaceables);
    }
  }

  /**
   * Send an immediate (single tick) action-bar message to a player.
   *
   * @param p            the recipient player
   * @param msg          the raw message to send (placeholders should be formatted
   *                     by
   *                     caller if needed)
   * @param replaceables optional replacement tokens (currently forwarded to
   *                     message formatting utilities)
   */
  public static void send(
      final Player p,
      final String msg,
      final Replaceable... replaceables) {

    p
        .spigot()
        .sendMessage(
            ChatMessageType.ACTION_BAR,
            TextComponent.fromLegacy(msg));
  }

  private final static Map<UUID, BukkitTask> PENDING_MESSAGES = new HashMap<>();

  public static void send(
      final Player p,
      String msg,
      final int duration,
      final Replaceable... replaceables) {
    if (PENDING_MESSAGES.containsKey(p.getUniqueId())) {
      PENDING_MESSAGES
          .get(p.getUniqueId())
          .cancel();
    }
    msg = MessageFormatter.parsePlaceholder(p, msg);
    msg = MessageFormatter.format(msg, true, true, replaceables);
    if (msg == null || msg.length() == 0) {
      return;
    }
    final BaseComponent component = TextComponent.fromLegacy(msg);
    if (duration == -1) {
      // Send once and do not schedule repeating updates
      p
          .spigot()
          .sendMessage(
              ChatMessageType.ACTION_BAR,
              component);
      return;
    }

    final BukkitTask messageTask = new BukkitRunnable() {
      private int count = 0;

      @Override
      public void run() {
        if (count >= (duration - 3)) {
          this.cancel();
        }
        p
            .spigot()
            .sendMessage(
                ChatMessageType.ACTION_BAR,
                component);
        count++;
      }
    }
        .runTaskTimer(MorePlugins.instance, 0L, 20L);
    PENDING_MESSAGES.put(p.getUniqueId(), messageTask);
  }
}
