package com.lemonlightmc.moreplugins.apis;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.lemonlightmc.moreplugins.sound.Playable;

public class SoundAPI {

  public static void play(final Player player, final Sound sound, final Location loc, final double volume,
      final double pitch) {
    player.playSound(loc, sound, (float) volume, (float) pitch);
  }

  public static void play(final Player player, final Sound sound, final Location loc, final double volume) {
    player.playSound(loc, sound, (float) volume, (float) Playable.DEFAULT_PITCH);
  }

  public static void play(final Player player, final Sound sound, final Location loc) {
    player.playSound(loc, sound, (float) Playable.DEFAULT_VOLUME, (float) Playable.DEFAULT_PITCH);
  }

  public static void play(final Player player, final Sound sound, final Entity entity, final double volume,
      final double pitch) {
    player.playSound(entity, sound, (float) volume, (float) pitch);
  }

  public static void play(final Player player, final Sound sound, final Entity entity, final double volume) {
    player.playSound(entity, sound, (float) volume, (float) Playable.DEFAULT_PITCH);
  }

  public static void play(final Player player, final Sound sound, final Entity entity) {
    player.playSound(entity, sound, (float) Playable.DEFAULT_VOLUME, (float) Playable.DEFAULT_PITCH);
  }

  public static void play(final Player player, final Sound sound, final double volume, final double pitch) {
    player.playSound(player.getLocation(), sound, (float) volume, (float) pitch);
  }

  public static void play(final Player player, final Sound sound, final double volume) {
    player.playSound(player.getLocation(), sound, (float) volume, (float) Playable.DEFAULT_PITCH);
  }

  public static void play(final Player player, final Sound sound) {
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
}
