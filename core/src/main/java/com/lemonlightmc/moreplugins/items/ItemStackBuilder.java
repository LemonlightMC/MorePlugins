package com.lemonlightmc.moreplugins.items;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.Tag;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.banner.Pattern;
import org.bukkit.damage.DamageType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntitySnapshot;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.inventory.meta.ShieldMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.bukkit.inventory.meta.components.BlocksAttacksComponent;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.inventory.meta.components.EquippableComponent;
import org.bukkit.inventory.meta.components.FoodComponent;
import org.bukkit.inventory.meta.components.JukeboxPlayableComponent;
import org.bukkit.inventory.meta.components.ToolComponent;
import org.bukkit.inventory.meta.components.UseCooldownComponent;
import org.bukkit.inventory.meta.components.WeaponComponent;
import org.bukkit.inventory.meta.components.consumable.ConsumableComponent;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.profile.PlayerProfile;

import com.lemonlightmc.moreplugins.interfaces.Builder;

public class ItemStackBuilder implements Builder<ItemStack> {
  protected ItemStack item;
  protected ItemMeta meta;

  public ItemStackBuilder(ItemStack item) {
    this.item = item;
    this.meta = item.getItemMeta();
  }

  public ItemStackBuilder(Material material, int amount) {
    this.item = new ItemStack(material, amount);
    this.meta = item.getItemMeta();
  }

  public ItemStackBuilder(Material material) {
    this.item = new ItemStack(material);
    this.meta = item.getItemMeta();
  }

  public static ItemStackBuilder of(Material material) {
    return new ItemStackBuilder(material);
  }

  public static ItemStackBuilder of(Material material, int amount) {
    return new ItemStackBuilder(material, amount);
  }

  public static ItemStackBuilder of(ItemStack item) {
    return new ItemStackBuilder(item);
  }

  public ItemStack build() {
    item.setItemMeta(meta);
    return item;
  }

  public ItemStackBuilder amount(int amount) {
    item.setAmount(amount);
    return this;
  }

  public boolean hasName() {
    return meta != null && this.meta.hasDisplayName();
  }

  public ItemStackBuilder name(String name) {
    meta.setDisplayName(name);
    return this;
  }

  public List<String> description() {
    return meta.getLore() == null ? List.of() : meta.getLore();
  }

  public boolean hasDescription() {
    return meta.getLore() != null;
  }

  public ItemStackBuilder description(String... description) {
    meta.setLore(List.of(description));
    return this;
  }

  public ItemStackBuilder description(List<String> description) {
    meta.setLore(description);
    return this;
  }

  public ItemStackBuilder appendDescription(String... lore) {
    if (meta.hasLore() && meta.getLore() != null) {
      meta.getLore().addAll(List.of(lore));
    } else {
      meta.setLore(List.of(lore));
    }
    return this;
  }

  public ItemStackBuilder description(String description, int index) {
    if (meta.hasLore() && meta.getLore() != null && meta.getLore().size() > index) {
      meta.getLore().set(index, description);
    } else {
      meta.setLore(List.of(description));
    }
    return this;
  }

  public ItemStackBuilder tag(NamespacedKey key, String value) {
    meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, value);
    return this;
  }

  public ItemStackBuilder tag(NamespacedKey key, int value) {
    meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, value);
    return this;
  }

  public ItemStackBuilder tag(NamespacedKey key, double value) {
    meta.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, value);
    return this;
  }

  public ItemStackBuilder tag(NamespacedKey key, long value) {
    meta.getPersistentDataContainer().set(key, PersistentDataType.LONG, value);
    return this;
  }

  public <T, Z> ItemStackBuilder tag(NamespacedKey key,
      PersistentDataType<T, Z> type, Z data) {
    meta.getPersistentDataContainer().set(key, type, data);
    return this;
  }

  public ItemStackBuilder flags(ItemFlag... flags) {
    meta.addItemFlags(flags);
    return this;
  }

  public ItemStackBuilder damage(double damage) {
    return setAttribute(Attribute.ATTACK_DAMAGE, damage);
  }

  public ItemStackBuilder health(double health) {
    return setAttribute(Attribute.MAX_HEALTH, health);
  }

  public ItemStackBuilder knockbackResistance(double knockback) {
    return setAttribute(Attribute.KNOCKBACK_RESISTANCE, knockback);
  }

  public ItemStackBuilder moveSpeed(double speed) {
    return setAttribute(Attribute.MOVEMENT_SPEED, speed);
  }

  public ItemStackBuilder attackSpeed(double speed) {
    return setAttribute(Attribute.ATTACK_SPEED, speed);
  }

  public ItemStackBuilder attackKnockback(double knockback) {
    return setAttribute(Attribute.ATTACK_SPEED, knockback);
  }

  public ItemStackBuilder setAttribute(Attribute attribute, double value) {
    meta.removeAttributeModifier(attribute);
    meta.addAttributeModifier(attribute, new AttributeModifier(attribute.getKeyOrThrow(), value - 1,
        AttributeModifier.Operation.ADD_NUMBER, item.getType().getEquipmentSlot().getGroup()));
    return this;
  }

  public ItemStackBuilder setAttribute(Attribute attribute, double value, EquipmentSlotGroup slot) {
    meta.removeAttributeModifier(attribute);
    meta.addAttributeModifier(attribute, new AttributeModifier(attribute.getKeyOrThrow(), value - 1,
        AttributeModifier.Operation.ADD_NUMBER, slot));
    return this;
  }

  public ItemStackBuilder multiplyAttribute(Attribute attribute, double value) {
    meta.addAttributeModifier(attribute, new AttributeModifier(attribute.getKeyOrThrow(), value,
        AttributeModifier.Operation.MULTIPLY_SCALAR_1, item.getType().getEquipmentSlot().getGroup()));
    return this;
  }

  public ItemStackBuilder multiplyAttribute(Attribute attribute, double value, EquipmentSlotGroup slot) {
    meta.addAttributeModifier(attribute, new AttributeModifier(attribute.getKeyOrThrow(), value,
        AttributeModifier.Operation.MULTIPLY_SCALAR_1, slot));
    return this;
  }

  public ItemStackBuilder enchantable(int enchantable) {
    meta.setEnchantable(enchantable);
    return this;
  }

  public ItemStackBuilder enchantmentGlint(boolean override) {
    meta.setEnchantmentGlintOverride(override);
    return this;
  }

  public ItemStackBuilder enchant(Enchantment enchantment, int level) {
    item.addEnchantment(enchantment, level);
    return this;
  }

  public ItemStackBuilder enchant(Enchantment enchantment) {
    item.addEnchantment(enchantment, 1);
    return this;
  }

  public ItemStackBuilder enchantUnsafe(Enchantment enchantment, int level) {
    item.addUnsafeEnchantment(enchantment, level);
    return this;
  }

  public ItemStackBuilder enchantUnsafe(Enchantment enchantment) {
    item.addUnsafeEnchantment(enchantment, 1);
    return this;
  }

  public ItemStackBuilder enchant(Map<Enchantment, Integer> enchantments) {
    item.addEnchantments(enchantments);
    return this;
  }

  public ItemStackBuilder enchant(List<Enchantment> enchantments) {
    item.addEnchantments(enchantments.stream().collect(Collectors.toMap(e -> e, _ -> 1)));
    return this;
  }

  public ItemStackBuilder enchantUnsafe(Map<Enchantment, Integer> enchantments) {
    item.addUnsafeEnchantments(enchantments);
    return this;
  }

  public ItemStackBuilder enchantUnsafe(List<Enchantment> enchantments) {
    item.addUnsafeEnchantments(enchantments.stream().collect(Collectors.toMap(e -> e, _ -> 1)));
    return this;
  }

  public ItemStackBuilder removeEnchant(Enchantment enchantment) {
    item.removeEnchantment(enchantment);
    return this;
  }

  public ItemStackBuilder clearEnchants() {
    item.getEnchantments().forEach((enchantment, _) -> item.removeEnchantment(enchantment));
    return this;
  }

  public ItemStackBuilder customEffect(PotionEffectType effect, int duration, int level) {
    return this.customEffect(new PotionEffect(effect, duration, level));
  }

  public ItemStackBuilder customEffect(PotionEffect effect) {
    if (isPotionMeta()) {
      ((PotionMeta) meta).addCustomEffect(effect, true);
    }
    return this;
  }

  public boolean isPotionMeta() {
    return meta instanceof PotionMeta;
  }

  public ItemStackBuilder type(Material material) {
    item.setType(material);
    return this;
  }

  public ItemStackBuilder model(CustomModelDataComponent customModelData) {
    meta.setCustomModelDataComponent(customModelData);
    return this;
  }

  public ItemStackBuilder unbreakable() {
    return unbreakable(true);
  }

  public ItemStackBuilder unbreakable(boolean value) {
    meta.setUnbreakable(value);
    return this;
  }

  public ItemStackBuilder breakSound(Sound sound) {
    meta.setBreakSound(sound);
    return this;
  }

  public ItemStackBuilder consumable(ConsumableComponent component) {
    meta.setConsumable(component);
    return this;
  }

  public ItemStackBuilder food(FoodComponent component) {
    meta.setFood(component);
    return this;
  }

  public ItemStackBuilder equippable(EquippableComponent component) {
    meta.setEquippable(component);
    return this;
  }

  public ItemStackBuilder damageResistant(Tag<DamageType> tag) {
    meta.setDamageResistant(tag);
    return this;
  }

  public ItemStackBuilder glider(boolean isGlider) {
    meta.setGlider(isGlider);
    return this;
  }

  public ItemStackBuilder jukebox(JukeboxPlayableComponent component) {
    meta.setJukeboxPlayable(component);
    return this;
  }

  public ItemStackBuilder maxStackSize(int size) {
    meta.setMaxStackSize(size);
    return this;
  }

  public ItemStackBuilder rarity(ItemRarity rarity) {
    meta.setRarity(rarity);
    return this;
  }

  public ItemStackBuilder tool(ToolComponent component) {
    meta.setTool(component);
    return this;
  }

  public ItemStackBuilder weapon(WeaponComponent component) {
    meta.setWeapon(component);
    return this;
  }

  public ItemStackBuilder useCooldown(UseCooldownComponent component) {
    meta.setUseCooldown(component);
    return this;
  }

  public ItemStackBuilder useCooldown(ItemStack item) {
    meta.setUseRemainder(item);
    return this;
  }

  public ItemStackBuilder tooltip(NamespacedKey tooltipStyle) {
    meta.setTooltipStyle(tooltipStyle);
    return this;
  }

  public ItemStackBuilder hideTooltip(boolean hide) {
    meta.setHideTooltip(hide);
    return this;
  }

  public ItemStackBuilder blocksAttacks(BlocksAttacksComponent component) {
    meta.setBlocksAttacks(component);
    return this;
  }

  public ItemStackBuilder setSkull(PlayerProfile profile) {
    if (isSkull()) {
      ((SkullMeta) meta).setOwnerProfile(profile);
    }
    return this;
  }

  public boolean isSkull() {
    return meta instanceof SkullMeta;
  }

  public boolean isDurable() {
    return meta instanceof Damageable;
  }

  public ItemStackBuilder durability(int durability) {
    if (isDurable()) {
      ((Damageable) meta).setDamage(durability);
    }
    return this;
  }

  public boolean isRepairable() {
    return meta instanceof Repairable;
  }

  public ItemStackBuilder repairable(int cost) {
    if (isRepairable()) {
      ((Repairable) meta).setRepairCost(cost);
      ;
    }
    return this;
  }

  public boolean isTrimable() {
    return meta instanceof Repairable;
  }

  public ItemStackBuilder trim(ArmorTrim trim) {
    if (isTrimable()) {
      ((ArmorMeta) meta).setTrim(trim);
    }
    return this;
  }

  public boolean isSpawnable() {
    return meta instanceof SpawnEggMeta;
  }

  public ItemStackBuilder spawnable(EntitySnapshot trim) {
    if (isSpawnable()) {
      ((SpawnEggMeta) meta).setSpawnedEntity(null);
    }
    return this;
  }

  public boolean isShield() {
    return meta instanceof ShieldMeta;
  }

  public ItemStackBuilder shieldDye(DyeColor color) {
    if (isShield()) {
      ((ShieldMeta) meta).setBaseColor(color);
    }
    return this;
  }

  public boolean isBanner() {
    return meta instanceof BannerMeta;
  }

  public ItemStackBuilder pattern(Pattern pattern, int idx) {
    if (isBanner()) {
      ((BannerMeta) meta).setPattern(idx, pattern);
    }
    return this;
  }

  public ItemStackBuilder pattern(Pattern pattern) {
    if (isBanner()) {
      ((BannerMeta) meta).addPattern(pattern);
    }
    return this;
  }

  public ItemStackBuilder patterns(List<Pattern> pattern) {
    if (isBanner()) {
      ((BannerMeta) meta).setPatterns(pattern);
    }
    return this;
  }

  public boolean isFirework() {
    return meta instanceof FireworkMeta;
  }

  public ItemStackBuilder fireworkPower(int power) {
    if (isFirework()) {
      ((FireworkMeta) meta).setPower(0);
    }
    return this;
  }

  public ItemStackBuilder fireworkEffect(FireworkEffect effect) {
    if (isFirework()) {
      ((FireworkMeta) meta).addEffect(effect);
    }
    return this;
  }

  public ItemStackBuilder fireworkEffect(FireworkEffect... effect) {
    if (isFirework()) {
      ((FireworkMeta) meta).addEffects(effect);
    }
    return this;
  }

}
