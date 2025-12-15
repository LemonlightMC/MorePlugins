package com.lemonlightmc.moreplugins.wrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public enum EnchantmentCategory {

  ANY() {

    public List<Enchantment> getEnchantments() {
      return new ArrayList<>(anyEnchantments);
    }

  },

  UNKOWN() {

    public List<Enchantment> getEnchantments() {
      return List.of();
    }

  },

  DEFAULT() {

    public List<Enchantment> getEnchantments() {
      return new ArrayList<>(defaultEnchantments);
    }
  },

  ANY_TOOL() {

    public List<Enchantment> getEnchantments() {
      return new ArrayList<>(toolsEnchantments);
    }
  },

  AXE() {

    public List<Enchantment> getEnchantments() {
      return new ArrayList<>(axeEnchantments);
    }

  },

  PICKAXE() {

    public List<Enchantment> getEnchantments() {
      return new ArrayList<>(toolsEnchantments);
    }

  },

  SHOVEL() {

    public List<Enchantment> getEnchantments() {
      return new ArrayList<>(toolsEnchantments);
    }

  },

  HOE() {

    public List<Enchantment> getEnchantments() {
      return new ArrayList<>(toolsEnchantments);
    }

  },

  SHIELD() {

    public List<Enchantment> getEnchantments() {
      return new ArrayList<>(defaultEnchantments);
    }

  },

  BRUSH() {

    public List<Enchantment> getEnchantments() {
      return new ArrayList<>(defaultEnchantments);
    }

  },

  FLINT_STEEL() {

    public List<Enchantment> getEnchantments() {
      return new ArrayList<>(defaultEnchantments);
    }

  },

  SWORD() {

    public List<Enchantment> getEnchantments() {
      return new ArrayList<>(swordEnchantments);
    }

  },

  ANY_ARMOR() {

    public List<Enchantment> getEnchantments() {
      return new ArrayList<>(armorEnchantments);
    }

  },

  HELMET() {

    public List<Enchantment> getEnchantments() {
      return new ArrayList<>(helmetEnchantments);
    }

  },

  CHESTPLATE() {

    public List<Enchantment> getEnchantments() {
      return new ArrayList<>(armorEnchantments);
    }

  },

  LEGGINGS() {

    public List<Enchantment> getEnchantments() {
      return new ArrayList<>(leggingsEnchantments);
    }

  },

  BOOTS() {

    public List<Enchantment> getEnchantments() {
      return new ArrayList<>(bootsEnchantments);
    }

  },

  BOW() {

    public List<Enchantment> getEnchantments() {
      return new ArrayList<>(bowEnchantments);
    }

  },

  CROSSBOW() {

    public List<Enchantment> getEnchantments() {
      return new ArrayList<>(crossbowEnchantments);
    }

  },

  FISHING_ROD() {

    public List<Enchantment> getEnchantments() {
      return new ArrayList<>(fishingRodEnchantments);
    }

  },

  TRIDENT() {

    public List<Enchantment> getEnchantments() {
      return new ArrayList<>(tridentEnchantments);
    }

  },

  CURSES() {

    public List<Enchantment> getEnchantments() {
      return new ArrayList<>(cursesEnchantments);
    }

  };

  private static List<Enchantment> anyEnchantments = org.bukkit.Registry.ENCHANTMENT.stream().toList();
  private static List<Enchantment> cursesEnchantments = List.of(Enchantment.VANISHING_CURSE,
      Enchantment.BINDING_CURSE);
  private static List<Enchantment> defaultEnchantments = List.of(Enchantment.MENDING,
      Enchantment.UNBREAKING);

  private static List<Enchantment> swordEnchantments = merge(Arrays.asList(
      Enchantment.SHARPNESS, Enchantment.BANE_OF_ARTHROPODS, Enchantment.SMITE, Enchantment.SWEEPING_EDGE,
      Enchantment.FIRE_ASPECT, Enchantment.KNOCKBACK, Enchantment.LOOTING), defaultEnchantments);

  private static List<Enchantment> bowEnchantments = merge(Arrays.asList(
      Enchantment.FLAME, Enchantment.INFINITY, Enchantment.POWER, Enchantment.PUNCH), defaultEnchantments);
  private static List<Enchantment> crossbowEnchantments = merge(Arrays.asList(
      Enchantment.MULTISHOT, Enchantment.PIERCING, Enchantment.QUICK_CHARGE), defaultEnchantments);

  private static List<Enchantment> armorEnchantments = merge(Arrays.asList(Enchantment.BLAST_PROTECTION,
      Enchantment.FIRE_PROTECTION, Enchantment.PROJECTILE_PROTECTION, Enchantment.PROTECTION, Enchantment.THORNS),
      defaultEnchantments);
  private static List<Enchantment> helmetEnchantments = merge(Arrays.asList(
      Enchantment.AQUA_AFFINITY, Enchantment.RESPIRATION), armorEnchantments);
  private static List<Enchantment> leggingsEnchantments = merge(Arrays.asList(Enchantment.SWIFT_SNEAK),
      armorEnchantments);;
  private static List<Enchantment> bootsEnchantments = merge(Arrays.asList(
      Enchantment.DEPTH_STRIDER, Enchantment.FROST_WALKER, Enchantment.FEATHER_FALLING, Enchantment.SOUL_SPEED),
      armorEnchantments);

  private static List<Enchantment> fishingRodEnchantments = merge(Arrays.asList(
      Enchantment.LURE, Enchantment.LUCK_OF_THE_SEA), defaultEnchantments);
  private static List<Enchantment> tridentEnchantments = merge(Arrays.asList(
      Enchantment.CHANNELING, Enchantment.IMPALING, Enchantment.LOYALTY,
      Enchantment.RIPTIDE), defaultEnchantments);

  private static List<Enchantment> toolsEnchantments = merge(Arrays.asList(
      Enchantment.FORTUNE, Enchantment.SILK_TOUCH, Enchantment.EFFICIENCY), defaultEnchantments);
  private static List<Enchantment> axeEnchantments = merge(Arrays.asList(
      Enchantment.SMITE, Enchantment.BANE_OF_ARTHROPODS, Enchantment.SHARPNESS), toolsEnchantments);

  public abstract List<Enchantment> getEnchantments();

  public static EnchantmentCategory getByMaterial(final Material material) {
    if (material == null) {
      return null;
    }
    if (material.toString().contains("_HELMET")) {
      return EnchantmentCategory.HELMET;
    } else if (material.toString().contains("_CHESTPLATE")) {
      return EnchantmentCategory.CHESTPLATE;
    } else if (material.toString().contains("_BOOTS")) {
      return EnchantmentCategory.BOOTS;
    } else if (material.toString().contains("_LEGGINGS")) {
      return EnchantmentCategory.LEGGINGS;
    } else if (material.toString().contains("_PICKAXE")) {
      return EnchantmentCategory.PICKAXE;
    } else if (material.toString().contains("_SHOVEL")) {
      return EnchantmentCategory.SHOVEL;
    } else if (material.toString().contains("_AXE")) {
      return EnchantmentCategory.AXE;
    } else if (material.toString().contains("_HOE")) {
      return EnchantmentCategory.HOE;
    } else if (material.toString().contains("_SHIELD")) {
      return EnchantmentCategory.HOE;
    } else if (material.toString().contains("_BRUSH")) {
      return EnchantmentCategory.HOE;
    } else if (material.toString().contains("_FLINT_STEEL")) {
      return EnchantmentCategory.HOE;
    } else if (material.toString().contains("_SWORD")) {
      return EnchantmentCategory.SWORD;
    } else if (material == Material.BOW) {
      return EnchantmentCategory.BOW;
    } else if (material == Material.CROSSBOW) {
      return EnchantmentCategory.CROSSBOW;
    } else if (material == Material.TRIDENT) {
      return EnchantmentCategory.TRIDENT;
    } else if (material == Material.FISHING_ROD) {
      return EnchantmentCategory.FISHING_ROD;
    }
    return EnchantmentCategory.ANY;
  }

  public static EnchantmentCategory getByItemStack(final ItemStack itemStack) {
    if (itemStack == null) {
      return null;
    }
    final Material material = itemStack.getType();
    return getByMaterial(material);
  }

  private static <T> List<T> merge(final List<T> list1, final List<T> list2) {
    for (final T t : list2) {
      if (!list1.contains(t)) {
        list1.add(t);
      }
    }
    return list1;
  }
}
