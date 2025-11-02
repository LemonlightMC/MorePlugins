package com.julizey.moreplugins.apis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.entity.Player;

import com.julizey.moreplugins.apis.IBossBarAPI.IBossBar;
import com.julizey.moreplugins.apis.IBossBarAPI.IBossBarListener;
import com.julizey.moreplugins.base.MorePlugins;
import com.julizey.moreplugins.messages.MessageFormatter;

public class BossbarAPI {
  private static Map<String, NamespacedKey> keys = new HashMap<>();
  private static final Map<UUID, HashSet<BaseBossbar>> bossBars = new HashMap<>();
  private static final Map<String, BaseBossbar> registry = new HashMap<>();
  private static HashSet<BarFlag> flagCache = null;

  public static final double MAX_PROGRESS = 1d;
  public static final double MIN_PROGRESS = 0d;
  public static final double MAX_DISTANCE = 30d;

  public static final BarColor DEFAULT_COLOR = BarColor.PURPLE;
  public static final BarStyle DEFAULT_STYLE = BarStyle.SOLID;
  public static final BarFlag[] DEFAULT_FLAGS = new BarFlag[] {};
  public static final boolean DEFAULT_VISIBILITY = true;
  public static final double DEFAULT_PROGRESS = 1d;

  public static BaseBossbar createBar(final String name, final String title, final BarColor color, final BarStyle style,
      final BarFlag... flags) {
    final KeyedBossBar bar = Bukkit.createBossBar(getKey(name), MessageFormatter.format(title, true, false), color,
        style,
        flags);
    final BaseBossbar base = new BaseBossbar(name, bar);
    registry.put(name, base);
    return base;
  }

  public static BaseBossbar getBar(final String key) {
    return registry.get(key);
  }

  public static boolean hasBar(final String key) {
    return registry.containsKey(key);
  }

  public static void removeBar(final String key) {
    registry.remove(key);
    Bukkit.removeBossBar(getKey(key));
    keys.remove(key);
  }

  private static NamespacedKey getKey(final String key) {
    NamespacedKey namespacedkey = keys.get(key);
    if (namespacedkey != null) {
      return namespacedkey;
    }
    namespacedkey = new NamespacedKey(MorePlugins.instance, key);
    keys.put(key, namespacedkey);
    return namespacedkey;
  }

  public static class BaseBossbar implements IBossBar {
    private String name;
    private final KeyedBossBar bar;
    private Set<IBossBarListener> listeners = new HashSet<>();
    private Location location;

    public BaseBossbar(final String name, final KeyedBossBar bar) {
      this.bar = bar;
    }

    public BaseBossbar(final BaseBossbar base) {
      this.bar = base.bar;
      bar.setTitle(base.getTitle());
      bar.setColor(base.getColor());
      bar.setStyle(base.getStyle());
      bar.setVisible(base.getVisible());
      bar.setProgress(base.getProgress());
      for (final BarFlag flag : BarFlag.values()) {
        if (base.hasFlag(flag)) {
          bar.addFlag(flag);
        }
      }
      for (final Player p : base.getPlayers()) {
        bar.addPlayer(p);
      }
      this.listeners = base.getListeners();
      this.location = base.getLocation().clone();
    }

    @Override
    public NamespacedKey getKey() {
      return bar.getKey();
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public IBossBar setName(final String name) {
      this.name = name;
      return this;
    }

    @Override
    public String getTitle() {
      return bar.getTitle();
    }

    @Override
    public IBossBar setTitle(final String title) {
      bar.setTitle(title);
      return this;
    }

    @Override
    public double getProgress() {
      return bar.getProgress();
    }

    @Override
    public IBossBar setProgress(final double progress) {
      bar.setProgress(progress);
      return this;
    }

    @Override
    public BarColor getColor() {
      return bar.getColor();
    }

    @Override
    public IBossBar setColor(final BarColor color) {
      bar.setColor(color);
      return this;
    }

    @Override
    public BarStyle getStyle() {
      return bar.getStyle();
    }

    @Override
    public IBossBar setStyle(final BarStyle style) {
      bar.setStyle(style);
      return this;
    }

    @Override
    public Set<BarFlag> getFlags() {
      if (flagCache != null) {
        return flagCache;
      }
      final HashSet<BarFlag> flags = new HashSet<>();
      for (final BarFlag flag : BarFlag.values()) {
        if (bar.hasFlag(flag)) {
          flags.add(flag);
        }
      }
      return (flagCache = flags);
    }

    @Override
    public IBossBar setFlags(final Set<BarFlag> flags) {
      for (final BarFlag flag : BarFlag.values()) {
        if (flags.contains(flag)) {
          bar.addFlag(flag);
        } else {
          bar.removeFlag(flag);
        }
      }
      flagCache = null;
      return this;
    }

    @Override
    public boolean hasFlag(final BarFlag flag) {
      return bar.hasFlag(flag);
    }

    @Override
    public IBossBar addFlags(final BarFlag... flags) {
      for (final BarFlag barFlag : flags) {
        bar.addFlag(barFlag);
      }
      flagCache = null;
      return this;
    }

    @Override
    public IBossBar addFlags(final Iterable<BarFlag> flags) {
      for (final BarFlag flag : flags) {
        bar.addFlag(flag);
      }
      flagCache = null;
      return this;
    }

    @Override
    public IBossBar removeFlags(final BarFlag... flags) {
      for (final BarFlag barFlag : flags) {
        bar.removeFlag(barFlag);
      }
      flagCache = null;
      return this;
    }

    @Override
    public IBossBar removeFlags(final Iterable<BarFlag> flags) {
      for (final BarFlag flag : flags) {
        bar.removeFlag(flag);
      }
      flagCache = null;
      return this;
    }

    @Override
    public boolean getVisible() {
      return bar.isVisible();
    }

    @Override
    public boolean setVisible(final Player player) {
      return bar.isVisible() && inDistance(player.getLocation());
    }

    @Override
    public IBossBar setVisible(final Player player, final boolean visible) {
      if (visible) {
        bar.addPlayer(player);
      } else {
        bar.removePlayer(player);
      }
      return this;
    }

    @Override
    public IBossBar setVisible(final boolean visible) {
      bar.setVisible(visible);
      return this;
    }

    @Override
    public IBossBar show(final Player player) {
      bar.addPlayer(player);
      return this;
    }

    @Override
    public IBossBar hide(final Player player) {
      bar.removePlayer(player);
      return this;
    }

    @Override
    public IBossBar hide() {
      bar.removeAll();
      return this;
    }

    @Override
    public IBossBar show() {
      for (final Player player : Bukkit.getOnlinePlayers()) {
        bar.addPlayer(player);
      }
      return this;
    }

    @Override
    public Location getLocation() {
      return location;
    }

    @Override
    public IBossBar setLocation(final Location location) {
      this.location = location;
      return this;
    }

    @Override
    public boolean inDistance(final Location location) {
      if (this.location == null || location == null || this.location.getWorld() == location.getWorld()) {
        return false;
      }
      final double dx = this.location.getX() - location.getX();
      final double dy = this.location.getY() - location.getY();
      final double dz = this.location.getZ() - location.getZ();
      return Math.sqrt(dx * dx + dy * dy + dz * dz) <= (MAX_DISTANCE * MAX_DISTANCE);
    }

    @Override
    public Set<IBossBarListener> getListeners() {
      return listeners;
    }

    @Override
    public IBossBar setListeners(final Set<IBossBarListener> listeners) {
      this.listeners = listeners;
      return this;
    }

    @Override
    public boolean hasListener(final IBossBarListener listener) {
      return this.listeners.contains(listener);
    }

    @Override
    public IBossBar addListeners(final IBossBarListener... listener) {
      for (final IBossBarListener l : listener) {
        this.listeners.add(l);
      }
      return this;
    }

    @Override
    public IBossBar addListeners(final Iterable<IBossBarListener> listener) {
      for (final IBossBarListener l : listener) {
        this.listeners.add(l);
      }
      return this;
    }

    @Override
    public IBossBar removeListener(final IBossBarListener... listener) {
      for (final IBossBarListener l : listener) {
        this.listeners.remove(l);
      }
      return this;
    }

    @Override
    public IBossBar removeListener(final Iterable<IBossBarListener> listener) {
      for (final IBossBarListener l : listener) {
        this.listeners.remove(l);
      }
      return this;
    }

    @Override
    public IBossBar clearListener() {
      this.listeners.clear();
      return this;
    }

    @Override
    public List<Player> getPlayers() {
      return bar.getPlayers();
    }

    @Override
    public boolean hasPlayer(final Player player) {
      return bar.getPlayers().contains(player);
    }

    @Override
    public IBossBar addPlayer(final Player player) {
      bar.addPlayer(player);
      return this;
    }

    @Override
    public IBossBar addPlayers(final Iterable<Player> players) {
      for (final Player p : players) {
        bar.addPlayer(p);
      }
      return this;
    }

    @Override
    public IBossBar removePlayer(final Player player) {
      bar.removePlayer(player);
      return this;
    }

    @Override
    public IBossBar removePlayers(final Iterable<Player> players) {
      for (final Player p : players) {
        bar.removePlayer(p);
      }
      return this;
    }

    @Override
    public IBossBar clearPlayers() {
      bar.removeAll();
      return this;
    }

    @Override
    public int compareTo(final IBossBarListener o) {
      return this.getName().compareTo(((IBossBar) o).getName());
    }

    @Override
    public IBossBar clone() {
      return new BaseBossbar(this);
    }

    @Override
    public int hashCode() {
      int result = 31 * (31 + name.hashCode()) + bar.hashCode();
      result = 31 * result + ((listeners == null) ? 0 : listeners.hashCode());
      return 31 * result + ((location == null) ? 0 : location.hashCode());
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null || getClass() != obj.getClass()) {
        return false;
      }
      final BaseBossbar other = (BaseBossbar) obj;
      if (name == null && other.name != null || bar == null && other.bar != null
          || listeners == null && other.listeners != null || location == null && other.location != null) {
        return false;
      }
      return name.equals(other.name) && bar.equals(other.bar) && listeners.equals(other.listeners)
          && location.equals(other.location);
    }

    @Override
    public String toString() {
      return "BaseBossbar [name=" + name + ", getKey()=" + getKey() + ", getTitle()=" + getTitle() + ", getProgress()="
          + getProgress() + ", getColor()=" + getColor() + ", getStyle()=" + getStyle() + ", getFlags()=" + getFlags()
          + ", getVisible()=" + getVisible() + ", getLocation()=" + location + ", getListeners()=" + listeners
          + ", getPlayers()=" + getPlayers() + "]";
    }
  }

  public static class BossBarBuilder {
    private String key;
    private String title;
    private BarColor color = DEFAULT_COLOR;
    private BarStyle style = DEFAULT_STYLE;
    private boolean visible = DEFAULT_VISIBILITY;
    private double progress = DEFAULT_PROGRESS;
    private final ArrayList<BarFlag> flags = new ArrayList<>();

    public BossBarBuilder() {
    }

    public BossBarBuilder key(final String key) {
      this.key = key;
      return this;
    }

    public BossBarBuilder title(final String title) {
      this.title = title;
      return this;
    }

    public BossBarBuilder color(final BarColor color) {
      this.color = color;
      return this;
    }

    public BossBarBuilder style(final BarStyle style) {
      this.style = style;
      return this;
    }

    public BossBarBuilder visible(final boolean visible) {
      this.visible = visible;
      return this;
    }

    public BossBarBuilder progress(final float progress) {
      this.progress = progress;
      return this;
    }

    public BossBarBuilder flag(final BarFlag flag) {
      this.flags.add(flag);
      return this;
    }

    public BossBarBuilder addFlag(final BarFlag flag) {
      this.flags.add(flag);
      return this;
    }

    public BossBarBuilder removeFlag(final BarFlag flag) {
      this.flags.remove(flag);
      return this;
    }

    public BossBarBuilder setFlags(final BarFlag... flags) {
      this.flags.addAll(List.of(flags));
      return this;
    }

    public BossBarBuilder clearFlags() {
      this.flags.clear();
      return this;
    }

    public BossBarBuilder create(final Player player) {
      final BaseBossbar bossBar = createBar(key, title, this.color, this.style, this.flags.toArray(BarFlag[]::new));
      bossBar.setProgress(this.progress);
      bossBar.setVisible(visible);
      bossBar.addPlayer(player);
      return this;
    }

    public BossBarBuilder update(final Player player) {
      final BossBar bossBar = (BossBar) bossBars.get(player.getUniqueId());
      if (bossBar != null) {
        bossBar.setTitle(MessageFormatter.format(this.title, true, false));
        bossBar.setColor(this.color);
        bossBar.setStyle(this.style);
        bossBar.setProgress((double) this.progress);
        bossBar.setVisible(visible);
        bossBar.removeAll();
        bossBar.addPlayer(player);
      }
      return this;
    }
  }
}
