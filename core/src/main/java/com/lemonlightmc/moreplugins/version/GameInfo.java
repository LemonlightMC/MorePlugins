package com.lemonlightmc.moreplugins.version;

import org.bukkit.entity.Player;

import com.lemonlightmc.moreplugins.base.PluginBase;

public interface GameInfo {
  public static boolean hasViaVersionSupport = false;

  static GameInfo SERVER = new GameInfo() {
    private final Version version = PluginBase.getInstance().getVersion();

    @Override
    public String getVersion() {
      return version.toString();
    }
  };
  static GameInfo VIA = new GameInfo() {
    private final Version version = PluginBase.getInstance().getVersion();

    @Override
    public String getVersion() {
      return version.toString();
    }
  };

  public String getVersion();

  static GameInfo of() {
    return SERVER;
  }

  static GameInfo of(Player player) {
    if (hasViaVersionSupport) {
      return VIA;
    } else {
      return SERVER;
    }
  }
}
