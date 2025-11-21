package com.lemonlightmc.moreplugins.apis;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.lemonlightmc.moreplugins.sound.Sound;
import com.lemonlightmc.moreplugins.sound.mode.ChannelMode;
import com.lemonlightmc.moreplugins.sound.mode.MonoMode;

/**
 * Utility helpers for parsing, resolving and playing sounds.
 *
 * <p>
 * This class centralizes common sound operations such as parsing names into
 * the internal {@link com.lemonlightmc.moreplugins.sound.Sound} wrapper,
 * querying the Bukkit {@code Registry.SOUNDS}, and convenience overloads for
 * playing sounds at locations, entities or players with defaulted volume and
 * pitch. It also exposes a pluggable {@code ChannelMode} used by higher-level
 * sound playback code.
 * </p>
 */
public class SoundAPI {
  public static final float DEFAULT_VOLUME = 1f;
  public static final float MINIMUM_VOLUME = 0f;
  public static final float MAXIMUM_VOLUME = 1f;

  public static final float DEFAULT_PITCH = 1f;
  public static final float MINIMUM_PITCH = 0f;
  public static final float MAXIMUM_PITCH = 1f;

  public static final int DEFAULT_PANNING = 0;
  public static final int MINIMUM_PANNING = 0;
  public static final int MAXIMUM_PANNING = 100;

  public static final long DEFAULT_SEED = 0;
  public static final long MINIMUM_SEED = Long.MIN_VALUE;
  public static final long MAXIMUM_SEED = Long.MAX_VALUE;
  // Assumes that most common tempo is close to 10 tps
  public static final double COMMON_TEMPO = 10d;
  public static final SoundCategory DEFAULT_SOURCE = SoundCategory.MASTER;

  private static ChannelMode channel = new MonoMode();

  public static ChannelMode getMode() {
    return channel;
  }

  /**
   * Get the currently configured sound channel mode used by playback routines.
   *
   * @return configured {@link ChannelMode}
   */

  public static void setMode(final ChannelMode mode) {
    SoundAPI.channel = mode;
  }

  public static void play(final Player player, final org.bukkit.Sound sound, final Location loc, final double volume,
      final double pitch) {
    player.playSound(loc, sound, (float) volume, (float) pitch);
  }

  public static void play(final Player player, final org.bukkit.Sound sound, final Location loc, final double volume) {
    player.playSound(loc, sound, (float) volume, (float) DEFAULT_PITCH);
  }

  public static void play(final Player player, final org.bukkit.Sound sound, final Location loc) {
    player.playSound(loc, sound, (float) DEFAULT_VOLUME, (float) DEFAULT_PITCH);
  }

  public static void play(final Player player, final org.bukkit.Sound sound, final Entity entity, final double volume,
      final double pitch) {
    player.playSound(entity, sound, (float) volume, (float) pitch);
  }

  public static void play(final Player player, final org.bukkit.Sound sound, final Entity entity, final double volume) {
    player.playSound(entity, sound, (float) volume, (float) DEFAULT_PITCH);
  }

  public static void play(final Player player, final org.bukkit.Sound sound, final Entity entity) {
    player.playSound(entity, sound, (float) DEFAULT_VOLUME, (float) DEFAULT_PITCH);
  }

  public static void play(final Player player, final org.bukkit.Sound sound, final double volume, final double pitch) {
    player.playSound(player.getLocation(), sound, (float) volume, (float) pitch);
  }

  public static void play(final Player player, final org.bukkit.Sound sound, final double volume) {
    player.playSound(player.getLocation(), sound, (float) volume, (float) DEFAULT_PITCH);
  }

  public static void play(final Player player, final org.bukkit.Sound sound) {
    player.playSound(player.getLocation(), sound, (float) DEFAULT_VOLUME, (float) DEFAULT_PITCH);
  }

  public static void play(final Player player, final String sound, final Location loc, final double volume,
      final double pitch) {
    player.playSound(loc, sound, (float) volume, (float) pitch);
  }

  public static void play(final Player player, final String sound, final Location loc, final double volume) {
    player.playSound(loc, sound, (float) volume, (float) DEFAULT_PITCH);
  }

  public static void play(final Player player, final String sound, final Location loc) {
    player.playSound(loc, sound, (float) DEFAULT_VOLUME, (float) DEFAULT_PITCH);
  }

  public static void play(final Player player, final String sound, final Entity entity, final double volume,
      final double pitch) {
    player.playSound(entity, sound, (float) volume, (float) pitch);
  }

  public static void play(final Player player, final String sound, final Entity entity, final double volume) {
    player.playSound(entity, sound, (float) volume, (float) DEFAULT_PITCH);
  }

  public static void play(final Player player, final String sound, final Entity entity) {
    player.playSound(entity, sound, (float) DEFAULT_VOLUME, (float) DEFAULT_PITCH);
  }

  public static void play(final Player player, final String sound, final double volume, final double pitch) {
    player.playSound(player.getLocation(), sound, (float) volume, (float) pitch);
  }

  public static void play(final Player player, final String sound, final double volume) {
    player.playSound(player.getLocation(), sound, (float) volume, (float) DEFAULT_PITCH);
  }

  public static void play(final Player player, final String sound) {
    player.playSound(player.getLocation(), sound, (float) DEFAULT_VOLUME, (float) DEFAULT_PITCH);
  }

  public static void playNote(final Player player, final org.bukkit.Instrument instrument, final org.bukkit.Note note) {
    player.playNote(player.getLocation(), instrument, note);
  }

  public static void playNote(final Player player, final org.bukkit.Instrument instrument, final org.bukkit.Note note,
      final Location loc) {
    player.playNote(loc, instrument, note);
  }

  public static void playNote(final Player player, final org.bukkit.Instrument instrument, final org.bukkit.Note note,
      final Entity entity) {
    player.playNote(entity.getLocation(), instrument, note);
  }

  public static Sound parseSound(final String name) {
    return Sound.minecraft(name);
  }

  public static Sound parseSound(final String name, final String def) {
    if (!isInRegistry(name)) {
      return Sound.minecraft(def);
    }
    return Sound.minecraft(name);
  }

  public static Sound parseSound(final String name, final NamespacedKey def) {
    if (!isInRegistry(name)) {
      return Sound.from(def);
    }
    return Sound.minecraft(name);
  }

  public static Sound parseSound(final String name, final Sound def) {
    if (!isInRegistry(name)) {
      return def;
    }
    return Sound.minecraft(name);
  }

  public static Sound parseSound(final NamespacedKey key) {
    return Sound.from(key);
  }

  public static Sound parseSound(final NamespacedKey key, final String def) {
    if (!isInRegistry(key)) {
      return Sound.minecraft(def);
    }
    return Sound.from(key);
  }

  public static Sound parseSound(final NamespacedKey key, final NamespacedKey def) {
    if (!isInRegistry(key)) {
      return Sound.from(def);
    }
    return Sound.from(key);
  }

  public static Sound parseSound(final NamespacedKey key, final Sound def) {
    if (!isInRegistry(key)) {
      return def;
    }
    return Sound.from(key);
  }

  public static boolean isInRegistry(final String name) {
    return getFromRegistry(name) != null;
  }

  public static boolean isInRegistry(final NamespacedKey key) {
    return getFromRegistry(key) != null;
  }

  public static org.bukkit.Sound getFromRegistry(final String name) {
    return Registry.SOUNDS.get(NamespacedKey.minecraft(name));
  }

  public static org.bukkit.Sound getFromRegistry(final NamespacedKey key) {
    return Registry.SOUNDS.get(key);
  }

  public static org.bukkit.Sound getFromRegistry(final Sound sound) {
    return Registry.SOUNDS.get(sound.getKey());
  }

  public static org.bukkit.Sound getFromRegistry(final String name, final Sound def) {
    final org.bukkit.Sound bukkitSound = getFromRegistry(NamespacedKey.minecraft(name));
    return bukkitSound == null ? def.getBukkitSound() : bukkitSound;
  }

  public static org.bukkit.Sound getFromRegistry(final NamespacedKey key, final Sound def) {
    final org.bukkit.Sound bukkitSound = getFromRegistry(key);
    return bukkitSound == null ? def.getBukkitSound() : bukkitSound;
  }

  public static org.bukkit.Sound getFromRegistry(final Sound sound, final Sound def) {
    final org.bukkit.Sound bukkitSound = getFromRegistry(sound.getKey());
    return bukkitSound == null ? def.getBukkitSound() : bukkitSound;
  }
}
