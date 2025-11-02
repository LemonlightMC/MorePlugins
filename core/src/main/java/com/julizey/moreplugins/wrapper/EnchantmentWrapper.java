package com.julizey.moreplugins.wrapper;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.julizey.moreplugins.base.MorePlugins;
import com.julizey.moreplugins.messages.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EnchantmentWrapper extends Enchantment {

  private final String name;
  private final NamespacedKey key;
  private final int maxLevel;
  private final ArrayList<Enchantment> conflicts;
  private final boolean treasure;
  private final boolean cursed;
  private final EnchantmentTarget target;
  private static final Enchantment[] values = values();
  private static final NamespacedKey[] keys = keys();

  public EnchantmentWrapper(final String key, final String name, final int maxLevel, final boolean treasure,
      final boolean cursed,
      final EnchantmentTarget target, final Enchantment... conflicts) {
    super();
    this.key = new NamespacedKey(MorePlugins.instance, key);
    this.name = name;
    this.maxLevel = maxLevel;
    this.treasure = treasure;
    this.cursed = cursed;
    this.target = target;
    this.conflicts = new ArrayList<Enchantment>(List.of(conflicts));
  }

  public EnchantmentWrapper(final Plugin plugin, final String key, final String name, final int maxLevel,
      final boolean treasure, final boolean cursed,
      final EnchantmentTarget target, final Enchantment... conflicts) {
    super();
    this.key = new NamespacedKey(plugin, key);
    this.name = name;
    this.maxLevel = maxLevel;
    this.treasure = treasure;
    this.cursed = cursed;
    this.target = target;
    this.conflicts = new ArrayList<Enchantment>(List.of(conflicts));

  }

  public static Enchantment createEnchantment(final String key, final String name, final int maxLevel,
      final boolean treasure, final boolean cursed,
      final EnchantmentTarget target, final Enchantment... conflicts) {
    return new EnchantmentWrapper(key, name, maxLevel, treasure, cursed, target, conflicts);
  }

  public static Enchantment createEnchantment(final Plugin plugin, final String key, final String name,
      final int maxLevel, final boolean treasure,
      final boolean cursed, final EnchantmentTarget target, final Enchantment... conflicts) {
    return new EnchantmentWrapper(plugin, key, name, maxLevel, treasure, cursed, target, conflicts);
  }

  public void register() {
    try {

    } catch (final Exception e) {
      Logger.warn("Failed to register the Enchantment: " + name);
      e.printStackTrace();
    }
    throw new UnsupportedOperationException("Currently registering enchantments is too difficult to do!");
  }

  public static Enchantment getByKey(NamespacedKey key) {
    if (key == null) {
      return null;
    }
    return Registry.ENCHANTMENT.get(key);
  }

  public static Enchantment getByName(String name) {
    if (name == null) {
      return null;
    }
    return getByKey(NamespacedKey.fromString(name.toLowerCase(Locale.ROOT)));
  }

  public static Enchantment[] values() {
    if (values != null) {
      return values;
    }
    List<Enchantment> arr = new ArrayList<>();
    for (Enchantment enchantment : Registry.ENCHANTMENT) {
      arr.add(enchantment);
    }
    return arr.toArray(new Enchantment[0]);
  }

  public static NamespacedKey[] keys() {
    if (keys != null) {
      return keys;
    }
    List<NamespacedKey> arr = new ArrayList<>();
    for (Enchantment enchantment : Registry.ENCHANTMENT) {
      arr.add(enchantment.getKeyOrNull());
    }
    return arr.toArray(new NamespacedKey[0]);
  }

  @Override
  public String getName() {
    return name;
  }

  public Enchantment getEnchantment() {
    return Registry.ENCHANTMENT.get(getKey());
  }

  @Override
  public NamespacedKey getKey() {
    return key;
  }

  @Override
  public NamespacedKey getKeyOrThrow() {
    if (key == null) {
      throw new IllegalArgumentException("Enchantment key is null");
    }
    return key;
  }

  @Override
  public NamespacedKey getKeyOrNull() {
    if (key == null) {
      return null;
    }
    return key;
  }

  @Override
  public int getMaxLevel() {
    return maxLevel;
  }

  @Override
  public int getStartLevel() {
    return 0;
  }

  @Override
  public EnchantmentTarget getItemTarget() {
    return target;
  }

  @Override
  public boolean isTreasure() {
    return treasure;
  }

  @Override
  public boolean isCursed() {
    return cursed;
  }

  @Override
  public boolean conflictsWith(final Enchantment other) {
    return conflicts.contains(other);
  }

  @Override
  public boolean canEnchantItem(final ItemStack item) {
    return target.includes(item);
  }

  @Override
  public String getTranslationKey() {
    throw new UnsupportedOperationException("Unimplemented method 'getTranslationKey'");
  }

  @Override
  public boolean isRegistered() {
    throw new UnsupportedOperationException("Unimplemented method 'isRegistered'");
  }
}