package com.lemonlightmc.moreplugins.sound;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;

public enum NoteSounds {

  NOTE_PIANO(0, "minecraft:block.note_block.harp", "NOTE_PIANO", "BLOCK_NOTE_HARP", "BLOCK_NOTE_BLOCK_HARP"),
  NOTE_BASS(1, "minecraft:block.note_block.bass", "NOTE_BASS", "BLOCK_NOTE_BASS", "BLOCK_NOTE_BLOCK_BASS"),
  NOTE_BASS_DRUM(2, "minecraft:block.note_block.basedrum", "NOTE_BASS_DRUM", "BLOCK_NOTE_BASEDRUM",
      "BLOCK_NOTE_BLOCK_BASEDRUM"),
  NOTE_SNARE_DRUM(3, "minecraft:block.note_block.snare", "NOTE_SNARE_DRUM", "BLOCK_NOTE_SNARE",
      "BLOCK_NOTE_BLOCK_SNARE"),
  NOTE_STICKS(4, "minecraft:block.note_block.hat", "NOTE_STICKS", "BLOCK_NOTE_HAT", "BLOCK_NOTE_BLOCK_HAT"),
  NOTE_BASS_GUITAR(5, "minecraft:block.note_block.guitar", "NOTE_BASS_GUITAR", "BLOCK_NOTE_GUITAR",
      "BLOCK_NOTE_BLOCK_GUITAR"),
  NOTE_FLUTE(6, "minecraft:block.note_block.flute", "NOTE_FLUTE", "BLOCK_NOTE_FLUTE", "BLOCK_NOTE_BLOCK_FLUTE"),
  NOTE_BELL(7, "minecraft:block.note_block.bell", "NOTE_BELL", "BLOCK_NOTE_BELL", "BLOCK_NOTE_BLOCK_BELL"),
  NOTE_CHIME(8, "minecraft:block.note_block.chime", "NOTE_CHIME", "BLOCK_NOTE_CHIME", "BLOCK_NOTE_BLOCK_CHIME"),
  NOTE_XYLOPHONE(9, "minecraft:block.note_block.xylophone", "NOTE_XYLOPHONE", "BLOCK_NOTE_XYLOPHONE",
      "BLOCK_NOTE_BLOCK_XYLOPHONE"),
  NOTE_PLING(15, "minecraft:block.note_block.pling", "NOTE_PLING", "BLOCK_NOTE_PLING", "BLOCK_NOTE_BLOCK_PLING"),
  NOTE_IRON_XYLOPHONE(10, "minecraft:block.note_block.iron_xylophone", "BLOCK_NOTE_BLOCK_IRON_XYLOPHONE"),
  NOTE_COW_BELL(11, "minecraft:block.note_block.cow_bell", "BLOCK_NOTE_BLOCK_COW_BELL"),
  NOTE_DIDGERIDOO(12, "minecraft:block.note_block.didgeridoo", "BLOCK_NOTE_BLOCK_DIDGERIDOO"),
  NOTE_BIT(13, "minecraft:block.note_block.bit", "BLOCK_NOTE_BLOCK_BIT"),
  NOTE_BANJO(14, "minecraft:block.note_block.banjo", "BLOCK_NOTE_BLOCK_BANJO");

  private static final Map<String, NoteSounds> soundsByName = new HashMap<>();
  private static final NoteSounds[] soundsByIndex = new NoteSounds[values().length];

  private final int instrumentIndex;
  private final String resourcePackName;
  private final String[] versionDependentNames;
  private NamespacedKey key;
  private org.bukkit.Sound cached = null;

  NoteSounds(final int instrumentIndex, final String resourcePackName, final String... versionDependentNames) {
    this.instrumentIndex = instrumentIndex;
    this.resourcePackName = resourcePackName;
    this.versionDependentNames = versionDependentNames;
    cacheSound();
  }

  private void cacheSound() {
    NoteSounds.soundsByIndex[instrumentIndex] = this;
    for (final String name : versionDependentNames) {
      NoteSounds.soundsByName.put(name, this);
      if (cached == null) {
        key = NamespacedKey.minecraft(name);
        cached = Registry.SOUNDS.get(key);
      }
    }
  }

  public static NoteSounds getByName(final String name) {
    return soundsByName.get(name.toUpperCase());
  }

  public static NoteSounds getByName(final String name, final NoteSounds def) {
    final NoteSounds sound = soundsByName.get(name.toUpperCase());
    return sound == null ? def : sound;
  }

  public static org.bukkit.Sound getSoundbyName(final String name) {
    final NoteSounds sound = soundsByName.get(name.toUpperCase());
    if (sound != null)
      return sound.getBukkitSound();

    return getSoundFromRegistry(name);
  }

  public static org.bukkit.Sound getSoundbyName(final String name, final NoteSounds def) {
    final NoteSounds sound = soundsByName.get(name.toUpperCase());
    if (sound != null)
      return sound.getBukkitSound();

    return getSoundFromRegistry(name, def);
  }

  public static NoteSounds getByIndex(final int index) {
    if (index < 0 || index >= soundsByIndex.length)
      return null;
    return soundsByIndex[index];
  }

  public static NoteSounds getByIndex(final int index, final NoteSounds def) {
    if (index < 0 || index >= soundsByIndex.length)
      return def;
    return soundsByIndex[index] == null ? def : soundsByIndex[index];
  }

  public static org.bukkit.Sound getSoundByIndex(final int index) {
    if (index < 0 || index >= soundsByIndex.length)
      return null;
    final NoteSounds sound = soundsByIndex[index];
    return sound == null ? null : sound.getBukkitSound();
  }

  public static org.bukkit.Sound getSoundByIndex(final int index, final NoteSounds def) {
    if (index < 0 || index >= soundsByIndex.length)
      return def.getBukkitSound();
    final NoteSounds sound = soundsByIndex[index];
    return sound == null ? def.getBukkitSound() : sound.getBukkitSound();
  }

  public static org.bukkit.Sound getSoundFromRegistry(final String name) {
    return Registry.SOUNDS.getOrThrow(NamespacedKey.minecraft(name));
  }

  public static org.bukkit.Sound getSoundFromRegistry(final NamespacedKey key) {
    return Registry.SOUNDS.getOrThrow(key);
  }

  public static org.bukkit.Sound getSoundFromRegistry(final NoteSounds sound) {
    return Registry.SOUNDS.getOrThrow(sound.key);
  }

  public static org.bukkit.Sound getSoundFromRegistry(final String name, final NoteSounds def) {
    final org.bukkit.Sound bukkitSound = Registry.SOUNDS.get(NamespacedKey.minecraft(name));
    return bukkitSound == null ? def.getBukkitSound() : bukkitSound;
  }

  public static org.bukkit.Sound getSoundFromRegistry(final NamespacedKey key, final NoteSounds def) {
    final org.bukkit.Sound bukkitSound = Registry.SOUNDS.get(key);
    return bukkitSound == null ? def.getBukkitSound() : bukkitSound;
  }

  public static org.bukkit.Sound getSoundFromRegistry(final NoteSounds sound, final NoteSounds def) {
    final org.bukkit.Sound bukkitSound = Registry.SOUNDS.get(sound.key);
    return bukkitSound == null ? def.getBukkitSound() : bukkitSound;
  }

  public org.bukkit.Sound getBukkitSound() {
    if (cached != null) {
      return cached;
    }
    throw new IllegalArgumentException("Found no valid sound name for " + this.name());
  }

  public NamespacedKey getKey() {
    return key;
  }

  public int getInstrumentIndex() {
    return instrumentIndex;
  }

  public String getResourcePackName() {
    return resourcePackName;
  }
}