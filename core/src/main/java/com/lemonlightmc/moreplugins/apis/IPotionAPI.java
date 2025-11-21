package com.lemonlightmc.moreplugins.apis;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectTypeCategory;

/**
 * Interface describing a lightweight potion abstraction used by the plugin.
 *
 * <p>
 * Implementations provide metadata (name, color, material), category and a
 * set of {@link org.bukkit.potion.PotionEffect}s along with helpers to
 * construct an {@link org.bukkit.inventory.ItemStack} representing the potion
 * in inventory form.
 * </p>
 */
public class IPotionAPI {
  /**
   * Contract for a potion-like object used by the API.
   */
  public static interface IPotion extends Cloneable, Comparable<IPotion> {

    /**
     * Set the potion's display name.
     *
     * @param name display name
     */
    public void setName(String name);

    /**
     * Get the potion's display name.
     *
     * @return display name
     */
    public String getName();

    /**
     * Set the potion color from an AWT color.
     *
     * @param color the AWT color
     */
    public void setColor(java.awt.Color color);

    /**
     * Set the potion color using Bukkit's {@link Color}.
     *
     * @param color the Bukkit color
     */
    public void setColor(Color color);

    /**
     * Get the potion color.
     *
     * @return the Bukkit color or {@code null} if not set
     */
    public Color getColor();

    /**
     * Set the base material of the potion item (e.g. POTION, SPLASH_POTION).
     *
     * @param material the material type
     */
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
