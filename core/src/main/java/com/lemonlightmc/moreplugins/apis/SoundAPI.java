package com.lemonlightmc.moreplugins.apis;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.lemonlightmc.moreplugins.sound.Playable;
import com.lemonlightmc.moreplugins.sound.Sound;
import com.lemonlightmc.moreplugins.sound.mode.ChannelMode;
import com.lemonlightmc.moreplugins.sound.mode.MonoMode;

public class SoundAPI {

  private static ChannelMode channel = new MonoMode();

  public static ChannelMode getMode() {
    return channel;
  }

  public static void setMode(final ChannelMode mode) {
    SoundAPI.channel = mode;
  }

  public static void play(final Player player, final org.bukkit.Sound sound, final Location loc, final double volume,
      final double pitch) {
    player.playSound(loc, sound, (float) volume, (float) pitch);
  }

  public static void play(final Player player, final org.bukkit.Sound sound, final Location loc, final double volume) {
    player.playSound(loc, sound, (float) volume, (float) Playable.DEFAULT_PITCH);
  }

  public static void play(final Player player, final org.bukkit.Sound sound, final Location loc) {
    player.playSound(loc, sound, (float) Playable.DEFAULT_VOLUME, (float) Playable.DEFAULT_PITCH);
  }

  public static void play(final Player player, final org.bukkit.Sound sound, final Entity entity, final double volume,
      final double pitch) {
    player.playSound(entity, sound, (float) volume, (float) pitch);
  }

  public static void play(final Player player, final org.bukkit.Sound sound, final Entity entity, final double volume) {
    player.playSound(entity, sound, (float) volume, (float) Playable.DEFAULT_PITCH);
  }

  public static void play(final Player player, final org.bukkit.Sound sound, final Entity entity) {
    player.playSound(entity, sound, (float) Playable.DEFAULT_VOLUME, (float) Playable.DEFAULT_PITCH);
  }

  public static void play(final Player player, final org.bukkit.Sound sound, final double volume, final double pitch) {
    player.playSound(player.getLocation(), sound, (float) volume, (float) pitch);
  }

  public static void play(final Player player, final org.bukkit.Sound sound, final double volume) {
    player.playSound(player.getLocation(), sound, (float) volume, (float) Playable.DEFAULT_PITCH);
  }

  public static void play(final Player player, final org.bukkit.Sound sound) {
    player.playSound(player.getLocation(), sound, (float) Playable.DEFAULT_VOLUME, (float) Playable.DEFAULT_PITCH);
  }

  public static void play(final Player player, final String sound, final Location loc, final double volume,
      final double pitch) {
    player.playSound(loc, sound, (float) volume, (float) pitch);
  }

  public static void play(final Player player, final String sound, final Location loc, final double volume) {
    player.playSound(loc, sound, (float) volume, (float) Playable.DEFAULT_PITCH);
  }

  public static void play(final Player player, final String sound, final Location loc) {
    player.playSound(loc, sound, (float) Playable.DEFAULT_VOLUME, (float) Playable.DEFAULT_PITCH);
  }

  public static void play(final Player player, final String sound, final Entity entity, final double volume,
      final double pitch) {
    player.playSound(entity, sound, (float) volume, (float) pitch);
  }

  public static void play(final Player player, final String sound, final Entity entity, final double volume) {
    player.playSound(entity, sound, (float) volume, (float) Playable.DEFAULT_PITCH);
  }

  public static void play(final Player player, final String sound, final Entity entity) {
    player.playSound(entity, sound, (float) Playable.DEFAULT_VOLUME, (float) Playable.DEFAULT_PITCH);
  }

  public static void play(final Player player, final String sound, final double volume, final double pitch) {
    player.playSound(player.getLocation(), sound, (float) volume, (float) pitch);
  }

  public static void play(final Player player, final String sound, final double volume) {
    player.playSound(player.getLocation(), sound, (float) volume, (float) Playable.DEFAULT_PITCH);
  }

  public static void play(final Player player, final String sound) {
    player.playSound(player.getLocation(), sound, (float) Playable.DEFAULT_VOLUME, (float) Playable.DEFAULT_PITCH);
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
