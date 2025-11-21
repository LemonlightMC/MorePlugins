package com.lemonlightmc.moreplugins.apis;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.lemonlightmc.moreplugins.messages.MessageFormatter;
import com.lemonlightmc.moreplugins.utils.StringUtils.Replaceable;

/**
 * Utility class for sending formatted chat messages to command senders and
 * broadcasting
 * messages to online players.
 *
 * <p>
 * Messages are first processed for placeholders via
 * {@link MessageFormatter#parsePlaceholder},
 * then formatted via {@link MessageFormatter#format}. Color support is enabled
 * for instances of
 * {@link Player} and {@link ConsoleCommandSender} when sending to an individual
 * sender; broadcasts
 * assume color support.
 * </p>
 *
 * <p>
 * All methods are static convenience helpers and do not return values.
 * </p>
 *
 * @see MessageFormatter
 * @see Replaceable
 */
public class ChatAPI {
  /**
   * Sends a single formatted message to the provided {@link CommandSender}.
   *
   * <p>
   * Workflow:
   * <ol>
   * <li>Parse placeholders in the message with
   * {@link MessageFormatter#parsePlaceholder(CommandSender, String)}</li>
   * <li>Determine whether color codes are supported for this sender (players and
   * console)</li>
   * <li>Format the message with
   * {@link MessageFormatter#format(String, boolean, boolean, Replaceable...)}</li>
   * <li>If the resulting message is non-null and non-empty, send it via
   * {@link CommandSender#sendMessage(String)}</li>
   * </ol>
   * </p>
   *
   * @param sender       the recipient of the message; may be a player, console,
   *                     or other command sender
   * @param msg          the raw message to send (may contain placeholders and
   *                     color codes)
   * @param replaceables optional replacement tokens to apply during formatting
   */
  public static void send(
      final CommandSender sender,
      String msg,
      final Replaceable... replaceables) {

    msg = MessageFormatter.parsePlaceholder(sender, msg);
    final boolean hasColor = sender instanceof Player || sender instanceof ConsoleCommandSender;
    msg = MessageFormatter.format(msg, hasColor, true, replaceables);

    if (msg == null || msg.length() == 0)
      return;
    sender.sendMessage(msg);
  }

  /**
   * Sends multiple formatted messages to the provided {@link CommandSender}.
   *
   * <p>
   * Each message in the array is processed with
   * {@link #send(CommandSender, String, Replaceable...)}.
   * </p>
   *
   * @param sender       the recipient of the messages
   * @param messages     an array of raw messages to send
   * @param replaceables optional replacement tokens to apply during formatting
   */
  public static void send(
      final CommandSender sender,
      final String[] messages,
      final Replaceable... replaceables) {
    for (int i = 0; i < messages.length; i++) {
      send(sender, messages[i], replaceables);
    }
  }

  /**
   * Broadcasts a single formatted message to all online players.
   *
   * <p>
   * For each online player the message is:
   * <ol>
   * <li>Parsed for placeholders using that player as the placeholder context</li>
   * <li>Formatted with colors enabled</li>
   * <li>Sent to the player if non-null and non-empty</li>
   * </ol>
   * </p>
   *
   * <p>
   * Note: if the formatted message becomes null or empty for a given player the
   * method returns
   * immediately and will not continue broadcasting to the remaining players (this
   * mirrors the
   * current implementation behavior).
   * </p>
   *
   * @param msg          the raw message to broadcast (may contain placeholders
   *                     and color codes)
   * @param replaceables optional replacement tokens to apply during formatting
   */
  public static void broadcast(String msg, final Replaceable... replaceables) {
    for (final Player p : Bukkit.getOnlinePlayers()) {
      msg = MessageFormatter.parsePlaceholder(p, msg);
      msg = MessageFormatter.format(msg, true, true, replaceables);
      if (msg == null || msg.length() == 0)
        return;
      p.sendMessage(msg);
    }
  }

  /**
   * Broadcasts multiple formatted messages to all online players.
   *
   * <p>
   * Each message in the array is processed with
   * {@link #broadcast(String, Replaceable...)}.
   * </p>
   *
   * @param messages     an array of raw messages to broadcast
   * @param replaceables optional replacement tokens to apply during formatting
   */
  public static void broadcast(
      final String[] messages,
      final Replaceable... replaceables) {
    for (int i = 0; i < messages.length; i++) {
      broadcast(messages[i], replaceables);
    }
  }
}
