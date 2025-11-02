package com.julizey.moreplugins.apis;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.julizey.moreplugins.base.MorePlugins;
import com.julizey.moreplugins.messages.MessageFormatter;
import com.julizey.moreplugins.utils.StringUtils.Replaceable;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public class ActionbarAPI {
  public static void broadcastActionbar(
      final String msg,
      final Replaceable... replaceables) {
    for (final Player player : Bukkit.getOnlinePlayers()) {
      sendActionbar(player, msg, replaceables);
    }
  }

  public static void sendActionbar(
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

  public static void sendActionbar(
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
