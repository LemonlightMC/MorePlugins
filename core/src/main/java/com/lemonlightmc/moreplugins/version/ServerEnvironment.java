package com.lemonlightmc.moreplugins.version;

import com.lemonlightmc.moreplugins.exceptions.PlatformException;

public enum ServerEnvironment {
  PURPUR("org.purpurmc.purpur.PurpurConfig"),
  FOLIA(
      "io.papermc.paper.threadedregions.scheduler.FoliaRegionScheduler",
      "ca.spottedleaf.moonrise.common.util.TickThread"),
  MOCK_BUKKIT(
      "be.seeseemelk.mockbukkit.ServerMock",
      "org.mockbukkit.mockbukkit.ServerMock"),
  PAPER(
      "com.destroystokyo.paper.PaperConfig",
      "io.papermc.paper.ServerBuildInfo"),
  SPIGOT("org.spigotmc.SpigotConfig", "org.bukkit.Server.Spigot"),
  BUKKIT("org.bukkit.Bukkit");

  private boolean isCurrent;

  ServerEnvironment(final String... checkClasses) {
    for (final String checkClass : checkClasses) {
      try {
        Class.forName(checkClass);
        this.isCurrent = true;
        break;
      } catch (final ClassNotFoundException e) {
        this.isCurrent = false;
      }
    }
  }

  public static ServerEnvironment current() {
    for (final ServerEnvironment env : values()) {
      if (env.isCurrent) {
        return env;
      }
    }
    throw new PlatformException("Failed to detect Platform!");
  }

  public static boolean isPurpur() {
    return ServerEnvironment.PURPUR.isCurrent;
  }

  public static boolean isMockBukkit() {
    return ServerEnvironment.MOCK_BUKKIT.isCurrent;
  }

  public static boolean isFolia() {
    return ServerEnvironment.FOLIA.isCurrent;
  }

  public static boolean isPaper() {
    return ServerEnvironment.PAPER.isCurrent;
  }

  public static boolean isSpigot() {
    return ServerEnvironment.SPIGOT.isCurrent;
  }

  public static boolean isBukkit() {
    return ServerEnvironment.BUKKIT.isCurrent;
  }

  public static boolean isPaperFork() {
    return isPaper() || isPurpur() || isFolia();
  }

  public static boolean isMocked() {
    return isMockBukkit();
  }
}
