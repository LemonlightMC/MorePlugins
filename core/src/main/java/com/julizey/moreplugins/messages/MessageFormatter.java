package com.julizey.moreplugins.messages;

import com.julizey.moreplugins.base.MorePlugins;
import com.julizey.moreplugins.utils.StringUtils;
import com.julizey.moreplugins.utils.StringUtils.Replaceable;

import me.clip.placeholderapi.PlaceholderAPI;

import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageFormatter {

  public static String prefix;
  public static boolean hasPlaceholderAPI = false;

  public static enum MessageTypes {
    CHAT,
    ACTIONBAR,
    TITLE,
    SUBTITLE,
  }

  public static String format(String msg) {
    msg = getMessage(msg, null);
    if (msg == null) {
      return null;
    }
    return TextStyling.convert(msg);
  }

  public static String format(String msg, final Player p) {
    msg = getMessage(msg, null);
    if (msg == null) {
      return null;
    }
    return TextStyling.convert(msg);
  }

  public static String format(
      String msg,
      final Replaceable... replaceables) {
    msg = getMessage(msg, null);
    if (msg == null) {
      return null;
    }

    msg = StringUtils.applyReplacements(msg, replaceables);
    return TextStyling.convert(msg);
  }

  public static String format(
      String msg,
      final boolean withPrefix,
      final Replaceable... replaceables) {
    msg = getMessage(msg, null);
    if (msg == null) {
      return null;
    }

    msg = StringUtils.applyReplacements(msg, replaceables);
    if (withPrefix) {
      msg = (prefix + " " + msg).replace("\n", "\n" + prefix + " ");
    }
    return TextStyling.convert(msg);
  }

  public static String format(
      String msg,
      final boolean withPrefix,
      final boolean withColor,
      final Replaceable... replaceables) {
    msg = getMessage(msg, null);
    if (msg == null) {
      return null;
    }

    for (final Replaceable replaceable : replaceables) {
      msg = msg.replaceAll(replaceable.placeholder(), replaceable.value());
    }

    if (withPrefix) {
      msg = (prefix + " " + msg).replace("\n", "\n" + prefix + " ");
    }
    return withColor ? TextStyling.convert(msg) : TextStyling.strip(msg);
  }

  public static String getMessage(String msg, final Locale locale) {
    if (msg == null || msg.length() == 0) {
      return null;
    }
    if (msg.startsWith("messages.")) {
      msg = MorePlugins.instance.getMessageProvider().getMessage(msg.substring(9), locale);
    }
    return msg == null || msg.length() == 0 ? null : msg;
  }

  public static String parsePlaceholder(final CommandSender sender, final String str) {
    if (sender instanceof Player && hasPlaceholderAPI) {
      return PlaceholderAPI.setPlaceholders((Player) sender, str);
    }
    return str;
  }

  public static String parsePlaceholder(final Player p, final String str) {
    if (hasPlaceholderAPI) {
      return PlaceholderAPI.setPlaceholders(p, str);
    }
    return str;
  }

  public static void setPrefix(String newPrefix) {
    if (!prefix.endsWith("&r"))
      newPrefix += "&r";
    prefix = newPrefix;
  }

  public static String getPrefix() {
    return prefix;
  }

  public static void reload() {
    prefix = MorePlugins.instance.getConfig().getString("prefix") + "&r";
    hasPlaceholderAPI = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
  }
}
