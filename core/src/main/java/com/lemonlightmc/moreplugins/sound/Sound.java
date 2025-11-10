package com.lemonlightmc.moreplugins.sound;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.SoundCategory;
import org.bukkit.plugin.Plugin;

import com.lemonlightmc.moreplugins.base.MorePlugins;

public class Sound extends Playable {
  private final NamespacedKey key;
  private final org.bukkit.Sound bukkitSound;

  public Sound(final NamespacedKey key) {
    this(key, DEFAULT_SOURCE, DEFAULT_VOLUME, DEFAULT_PITCH, DEFAULT_PANNING, DEFAULT_SEED);
  }

  public Sound(final NamespacedKey key, final SoundCategory source) {
    this(key, source, DEFAULT_VOLUME, DEFAULT_PITCH, DEFAULT_PANNING, DEFAULT_SEED);
  }

  public Sound(final NamespacedKey key, final SoundCategory source, final float volume) {
    this(key, source, volume, DEFAULT_PITCH, DEFAULT_PANNING, DEFAULT_SEED);
  }

  public Sound(final NamespacedKey key, final SoundCategory source, final float volume, final float pitch) {
    this(key, source, volume, pitch, DEFAULT_PANNING, DEFAULT_SEED);
  }

  public Sound(final NamespacedKey key, final SoundCategory source, final float volume, final float pitch,
      final int panning) {
    this(key, source, volume, pitch, panning, DEFAULT_SEED);
  }

  public Sound(final NamespacedKey key, final SoundCategory source, final float volume, final float pitch,
      final int panning,
      final long seed) {
    super(source, volume, pitch, panning, seed);
    this.key = key;
    this.bukkitSound = Registry.SOUNDS.get(key);
  }

  public static Sound minecraft(final String name) {
    return new Sound(NamespacedKey.minecraft(name));
  }

  public static Sound from(final String name) {
    return new Sound(NamespacedKey.fromString(name, MorePlugins.getInstance()));
  }

  public static Sound from(final String name, final Plugin plugin) {
    return new Sound(NamespacedKey.fromString(name, plugin));
  }

  public NamespacedKey getKey() {
    return key;
  }

  public org.bukkit.Sound getBukkitSound() {
    return bukkitSound;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + ((key == null) ? 0 : key.hashCode());
    result = 31 * result + ((bukkitSound == null) ? 0 : bukkitSound.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    final Sound other = (Sound) obj;
    if (key == null) {
      if (other.key != null)
        return false;
    } else if (!key.equals(other.key))
      return false;
    if (bukkitSound == null) {
      if (other.bukkitSound != null)
        return false;
    } else if (!bukkitSound.equals(other.bukkitSound))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Sound [key=" + key + ", bukkitSound=" + bukkitSound + "]";
  }
}
