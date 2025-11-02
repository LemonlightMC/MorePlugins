package com.lemonlightmc.moreplugins.messages;

import com.lemonlightmc.moreplugins.base.MorePlugins;
import com.lemonlightmc.moreplugins.utils.StringUtils.Replaceable;

import org.bukkit.Bukkit;

public class Logger {

  public static void info(String msg, final Replaceable... replaceables) {
    msg = MessageFormatter.format(msg, false, true, replaceables);
    if (msg == null)
      return;
    Bukkit.getLogger().info(msg);
  }

  public static void warn(String msg, final Replaceable... replaceables) {
    msg = MessageFormatter.format(msg, false, true, replaceables);
    if (msg == null)
      return;
    Bukkit.getLogger().warning(msg);
  }

  public static void severe(String msg, final Replaceable... replaceables) {
    msg = MessageFormatter.format(msg, false, true, replaceables);
    if (msg == null)
      return;
    Bukkit.getLogger().severe(msg);
  }

  public static void error(
      final Throwable throwable,
      final String description,
      final boolean disable) {
    if (throwable != null) {
      throwable.printStackTrace();
    }

    severe("*-----------------------------------------------------*");
    severe(
        "An error has occurred in " +
            MorePlugins.instance.getDescription().getName() +
            ".");
    severe("Description: " + description);
    severe("Contact the plugin author if you cannot fix this issue.");
    severe("*-----------------------------------------------------*");
    if (disable && Bukkit.getPluginManager().isPluginEnabled(MorePlugins.instance)) {
      Bukkit.getPluginManager().disablePlugin(MorePlugins.instance);
    }
  }
}
