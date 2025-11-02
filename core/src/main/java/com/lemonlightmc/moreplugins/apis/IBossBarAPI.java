package com.lemonlightmc.moreplugins.apis;

import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;

public class IBossBarAPI {
  public static interface IBossBar extends IBossBarListener, Comparable<IBossBarListener>, Cloneable {

    public NamespacedKey getKey();

    public String getName();

    public IBossBar setName(final String name);

    public String getTitle();

    public IBossBar setTitle(final String title);

    public double getProgress();

    public IBossBar setProgress(final double progress);

    public BarColor getColor();

    public IBossBar setColor(final BarColor color);

    public BarStyle getStyle();

    public IBossBar setStyle(final BarStyle style);

    public Set<BarFlag> getFlags();

    public IBossBar setFlags(final Set<BarFlag> flags);

    public boolean hasFlag(final BarFlag flag);

    public IBossBar addFlags(final BarFlag... flags);

    public IBossBar addFlags(final Iterable<BarFlag> flags);

    public IBossBar removeFlags(final BarFlag... flags);

    public IBossBar removeFlags(final Iterable<BarFlag> flags);

    public boolean getVisible();

    public boolean setVisible(Player player);

    public IBossBar setVisible(Player player, boolean visible);

    public IBossBar setVisible(boolean visible);

    public IBossBar show(Player player);

    public IBossBar hide(Player player);

    public IBossBar hide();

    public IBossBar show();

    public Location getLocation();

    public IBossBar setLocation(Location location);

    public boolean inDistance(Location location);

    public Set<IBossBarListener> getListeners();

    public IBossBar setListeners(final Set<IBossBarListener> listener);

    public boolean hasListener(final IBossBarListener listener);

    public IBossBar addListeners(final IBossBarListener... listener);

    public IBossBar addListeners(final Iterable<IBossBarListener> listener);

    public IBossBar removeListener(final IBossBarListener... listener);

    public IBossBar removeListener(final Iterable<IBossBarListener> listener);

    public IBossBar clearListener();

    public List<Player> getPlayers();

    public boolean hasPlayer(Player player);

    public IBossBar addPlayer(Player player);

    public IBossBar addPlayers(Iterable<Player> players);

    public IBossBar removePlayer(Player player);

    public IBossBar removePlayers(Iterable<Player> players);

    public IBossBar clearPlayers();

    public IBossBar clone();

    public int compareTo(final IBossBarListener o);

    public String toString();

    public boolean equals(final Object obj);

    public int hashCode();
  }

  public static interface IBossBarListener {
    default void bossBarNameChanged(final IBossBar bar, final String oldName, final String newName) {
    }

    default void bossBarProgressChanged(final IBossBar bar, final float oldProgress, final float newProgress) {
      this.bossBarPercentChanged(bar, oldProgress, newProgress);
    }

    default void bossBarPercentChanged(final IBossBar bar, final float oldProgress, final float newProgress) {
    }

    default void bossBarColorChanged(final IBossBar bar, final BarColor oldColor,
        final BarColor newColor) {
    }

    default void bossBarOverlayChanged(final IBossBar bar, final BarStyle oldOverlay,
        final BarStyle newOverlay) {
    }

    default void bossBarFlagsChanged(final IBossBar bar, final Set<BarFlag> flagsAdded,
        final Set<BarFlag> flagsRemoved) {
    }
  }
}
