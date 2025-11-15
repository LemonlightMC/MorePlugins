package com.lemonlightmc.moreplugins.apis;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffectTypeCategory;

import com.lemonlightmc.moreplugins.apis.IPotionAPI.IPotion;
import com.lemonlightmc.moreplugins.wrapper.Builder;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class PotionAPI {

  public static final Set<Material> potionMaterials = Set.of(Material.POTION, Material.SPLASH_POTION,
      Material.LINGERING_POTION, Material.TIPPED_ARROW);

  public static PotionBuilder builder() {
    return new PotionBuilder();
  }

  public static PotionBuilder effectBuilder() {
    return new PotionBuilder();
  }

  public static void applyPotion(Player p, Potion potion) {
    p.addPotionEffects(potion.getEffects());
  }

  public static void applyPotion(Player p, Collection<Potion> potions) {
    for (Potion potion : potions) {
      p.addPotionEffects(potion.getEffects());
    }
  }

  public static void removePotion(Player p, Potion potion) {
    for (PotionEffect effect : potion.getEffects()) {
      p.removePotionEffect(effect.getType());
    }
  }

  public static void removePotion(Player p, Collection<Potion> potions) {
    for (Potion potion : potions) {
      for (PotionEffect effect : potion.getEffects()) {
        p.removePotionEffect(effect.getType());
      }
    }
  }

  public static boolean hasPotion(Player p, Potion potion) {
    for (PotionEffect effect : potion.getEffects()) {
      if (!p.hasPotionEffect(effect.getType())) {
        return false;
      }
    }
    return true;
  }

  public static void applyEffect(Player p, PotionEffect effect) {
    p.addPotionEffect(effect);
  }

  public static void applyEffect(Player p, Collection<PotionEffect> effect) {
    p.addPotionEffects(effect);
  }

  public static void removeEffect(Player p, PotionEffect effect) {
    p.removePotionEffect(effect.getType());
  }

  public static void removeEffect(Player p, Collection<PotionEffect> effects) {
    for (PotionEffect effect : effects) {
      p.removePotionEffect(effect.getType());
    }
  }

  public static boolean hasEffect(Player p, PotionEffect effect) {
    return p.hasPotionEffect(effect.getType());
  }

  public static Collection<PotionEffect> getEffects(Player p) {
    return p.getActivePotionEffects();
  }

  public static boolean isPotionItem(final ItemStack item) {
    return potionMaterials.contains(item.getType());
  }

  public static class Potion implements IPotionAPI.IPotion {
    private String name;
    private Color color;
    private Material material = Material.POTION;
    private float durationScale = 1.0f;
    private PotionEffectTypeCategory category;
    private boolean isUpgradeable = true;
    private boolean isExtendable = true;
    private List<PotionEffect> effects;

    public Potion() {

    }

    @Override
    public void setName(final String name) {
      this.name = name;
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public void setColor(final Color color) {
      this.color = color;
    }

    @Override
    public void setColor(final java.awt.Color color) {
      this.color = Color.fromRGB(color.getRed(), color.getGreen(), color.getBlue());
    }

    @Override
    public Color getColor() {
      return color;
    }

    @Override
    public void setMaterial(final Material material) {
      this.material = material;
    }

    @Override
    public Material getMaterial() {
      return material;
    }

    @Override
    public void setCategory(final PotionEffectTypeCategory category) {
      this.category = category;
    }

    @Override
    public PotionEffectTypeCategory getCategory() {
      return category;
    }

    @Override
    public boolean isHarmful() {
      return this.category.compareTo(PotionEffectTypeCategory.HARMFUL) == 0;
    }

    @Override
    public boolean isNeutral() {
      return this.category.compareTo(PotionEffectTypeCategory.NEUTRAL) == 0;
    }

    @Override
    public boolean isBeneficial() {
      return this.category.compareTo(PotionEffectTypeCategory.BENEFICIAL) == 0;
    }

    @Override
    public void setDurationScale(final float scale) {
      this.durationScale = scale;
    }

    @Override
    public float getDurationScale() {
      return durationScale;
    }

    @Override
    public boolean isExtendable() {
      return isExtendable;
    }

    @Override
    public boolean isUpgradeable() {
      return isUpgradeable;
    }

    @Override
    public void setExtendable(final boolean value) {
      this.isExtendable = value;
    }

    @Override
    public void setUpgradeable(final boolean value) {
      this.isUpgradeable = value;
    }

    @Override
    public void setEffects(final List<PotionEffect> effects) {
      this.effects = effects;
    }

    @Override
    public void addEffect(final PotionEffect effect) {
      effects.add(effect);
    }

    @Override
    public boolean hasEffect(final PotionEffect effect) {
      return effects.contains(effect);
    }

    @Override
    public void removeEffect(final PotionEffect effect) {
      effects.remove(effect);
    }

    @Override
    public void clearEffects() {
      effects.clear();
    }

    @Override
    public List<PotionEffect> getEffects() {
      return effects;
    }

    @Override
    public ItemStack getItem() {
      return getItem(new ItemStack(this.material));
    }

    @Override
    public ItemStack getItem(final Material material) {
      return getItem(new ItemStack(material));
    }

    @Override
    public ItemStack getItem(final ItemStack item) {
      if (!isPotionItem(item)) {
        throw new IllegalArgumentException("Invalid Potion Item");
      }
      final PotionMeta meta = (PotionMeta) item.getItemMeta();

      meta.setItemName(name);
      meta.setColor(color);
      meta.setDurationScale(durationScale);
      for (final PotionEffect effect : effects) {
        meta.addCustomEffect(effect, false);
      }

      item.setItemMeta(meta);
      return item;
    }

    @Override
    public String toString() {
      return "Potion [name=" + name + ", color=" + color + ", material=" + material + ", durationScale=" + durationScale
          + ", category=" + category + ", isUpgradeable=" + isUpgradeable + ", isExtendable=" + isExtendable
          + ", effects=" + effects + "]";
    }

    @Override
    public int hashCode() {
      int result = 31 + ((name == null) ? 0 : name.hashCode());
      result = 31 * result + ((color == null) ? 0 : color.hashCode());
      result = 31 * result + ((material == null) ? 0 : material.hashCode());
      result = 31 * result + Float.floatToIntBits(durationScale);
      result = 31 * result + ((category == null) ? 0 : category.hashCode());
      result = 31 * result + (isUpgradeable ? 1231 : 1237);
      result = 31 * result + (isExtendable ? 1231 : 1237);
      return 13 * result + ((effects == null) ? 0 : effects.hashCode());
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null || getClass() != obj.getClass()) {
        return false;
      }
      final Potion other = (Potion) obj;
      if (name == null && other.name != null || color == null && other.color != null
          || effects == null && other.effects != null) {
        return false;
      }
      return name.equals(other.name) && color.equals(other.color)
          && Float.floatToIntBits(durationScale) == Float.floatToIntBits(other.durationScale)
          && material == other.material && category == other.category && isUpgradeable == other.isUpgradeable
          && isExtendable == other.isExtendable && effects.equals(other.effects);
    }

    @Override
    public int compareTo(IPotion obj) {
      if (this == obj) {
        return 0;
      }
      if (obj == null || getClass() != obj.getClass()) {
        return -1;
      }
      final Potion other = (Potion) obj;
      return name.compareTo(other.name);
    }
  }

  public static class PotionBuilder implements Builder<Potion> {
    private final Potion potion;

    public PotionBuilder() {
      potion = new Potion();
    }

    public PotionBuilder name(final String name) {
      potion.setName(name);
      return this;
    }

    public PotionBuilder category(final PotionEffectTypeCategory category) {
      potion.setCategory(category);
      return this;
    }

    public PotionBuilder beneficial() {
      potion.setCategory(PotionEffectTypeCategory.BENEFICIAL);
      return this;
    }

    public PotionBuilder harmful() {
      potion.setCategory(PotionEffectTypeCategory.HARMFUL);
      return this;
    }

    public PotionBuilder neutral() {
      potion.setCategory(PotionEffectTypeCategory.NEUTRAL);
      return this;
    }

    public PotionBuilder color(final java.awt.Color color) {
      potion.setColor(Color.fromRGB(color.getRed(), color.getGreen(), color.getBlue()));
      return this;
    }

    public PotionBuilder color(final Color color) {
      potion.setColor(color);
      return this;
    }

    public PotionBuilder upgradable() {
      potion.setUpgradeable(true);
      return this;
    }

    public PotionBuilder upgradable(final boolean isUpgradeable) {
      potion.setUpgradeable(isUpgradeable);
      return this;
    }

    public PotionBuilder extendable() {
      potion.setExtendable(true);
      return this;
    }

    public PotionBuilder extendable(final boolean isExtendable) {
      potion.setExtendable(isExtendable);
      return this;
    }

    public PotionBuilder durationScale(final float duration) {
      potion.setDurationScale(duration);
      return this;
    }

    public PotionBuilder effect(final PotionEffectType type, final int duration, final int amplifier) {
      potion.addEffect(new PotionEffect(type, duration * 20, amplifier));
      return this;
    }

    public PotionBuilder effect(final PotionEffect effect) {
      potion.addEffect(effect);
      return this;
    }

    public Potion build() {
      return potion;
    }

    @Override
    public String toString() {
      return "PotionBuilder [potion=" + potion + "]";
    }

    @Override
    public int hashCode() {
      return 31 + ((potion == null) ? 0 : potion.hashCode());
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null || getClass() != obj.getClass()) {
        return false;
      }
      final PotionBuilder other = (PotionBuilder) obj;
      if (potion == null && other.potion != null) {
        return false;
      }
      return potion.equals(other.potion);
    }

  }

  public static class PotionEffectBuilder {
    private PotionEffectType type;
    private int duration;
    private int amplifier;
    private boolean hasParticales;
    private boolean hasIcon;
    private boolean isAmbient;

    public PotionEffectBuilder() {

    }

    public PotionEffectBuilder type(final PotionEffectType type) {
      this.type = type;
      return this;
    }

    public PotionEffectBuilder duration(final int duration) {
      this.duration = duration;
      return this;
    }

    public PotionEffectBuilder amplifier(final int amplifier) {
      this.amplifier = amplifier;
      return this;
    }

    public PotionEffectBuilder particles() {
      this.hasParticales = true;
      return this;
    }

    public PotionEffectBuilder particles(final boolean hasParticales) {
      this.hasParticales = hasParticales;
      return this;
    }

    public PotionEffectBuilder icon() {
      this.hasIcon = true;
      return this;
    }

    public PotionEffectBuilder icon(final boolean hasIcon) {
      this.hasIcon = hasIcon;
      return this;
    }

    public PotionEffectBuilder ambient() {
      this.isAmbient = true;
      return this;
    }

    public PotionEffectBuilder ambient(final boolean isAmbient) {
      this.isAmbient = isAmbient;
      return this;
    }

    public PotionEffectBuilder infinite() {
      this.duration = PotionEffect.INFINITE_DURATION;
      return this;
    }

    public PotionEffectBuilder infinite(final boolean isInfinite) {
      this.duration = isInfinite ? PotionEffect.INFINITE_DURATION : duration;
      return this;
    }

    public PotionEffect build() {
      return new PotionEffect(type, this.duration, this.amplifier, this.isAmbient, this.hasParticales, this.hasIcon);
    }

    @Override
    public int hashCode() {
      int result = 31 + ((type == null) ? 0 : type.hashCode());
      result = 31 * result + duration;
      result = 31 * result + amplifier;
      result = 31 * result + (hasParticales ? 1231 : 1237);
      result = 31 * result + (hasIcon ? 1231 : 1237);
      return 31 * result + (isAmbient ? 1231 : 1237);
    }

    @Override
    public String toString() {
      return type.getKeyOrNull() + (isAmbient ? ":(" : ":") + duration + "t-x" + amplifier + (isAmbient ? ")" : "")
          + (hasParticales ? ":" : "") + (hasIcon ? ":" : "");
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null || getClass() != obj.getClass()) {
        return false;
      }
      final PotionEffectBuilder other = (PotionEffectBuilder) obj;
      if (type == null && other.type != null) {
        return false;
      }
      return type.equals(other.type) && duration == other.duration && amplifier == other.amplifier
          && hasParticales == other.hasParticales && hasIcon == other.hasIcon && isAmbient == other.isAmbient;
    }

  }
}