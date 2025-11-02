package com.lemonlightmc.moreplugins.apis;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectTypeCategory;

public class IPotionAPI {
  public static interface IPotion extends Cloneable, Comparable<IPotion> {

    public void setName(String name);

    public String getName();

    public void setColor(java.awt.Color color);

    public void setColor(Color color);

    public Color getColor();

    public void setMaterial(Material material);

    public Material getMaterial();

    public void setCategory(PotionEffectTypeCategory category);

    public PotionEffectTypeCategory getCategory();

    public boolean isHarmful();

    public boolean isNeutral();

    public boolean isBeneficial();

    public void setDurationScale(float scale);

    public float getDurationScale();

    public boolean isExtendable();

    public boolean isUpgradeable();

    public void setExtendable(boolean value);

    public void setUpgradeable(boolean value);

    public void setEffects(List<PotionEffect> effects);

    public void addEffect(PotionEffect effect);

    public boolean hasEffect(PotionEffect effect);

    public void removeEffect(PotionEffect effect);

    public void clearEffects();

    public List<PotionEffect> getEffects();

    public ItemStack getItem();

    public ItemStack getItem(Material material);

    public ItemStack getItem(ItemStack item);

  }
}
