package com.lemonlightmc.moreplugins.commands.arguments;

import java.util.Arrays;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.advancement.Advancement;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Biome;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.structure.Structure;
import org.bukkit.generator.structure.StructureType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import com.lemonlightmc.moreplugins.commands.CommandSource;
import com.lemonlightmc.moreplugins.commands.argumentsbase.Argument;
import com.lemonlightmc.moreplugins.commands.argumentsbase.ArgumentType;
import com.lemonlightmc.moreplugins.commands.argumentsbase.CommandArguments;
import com.lemonlightmc.moreplugins.commands.argumentsbase.ParticleData;
import com.lemonlightmc.moreplugins.commands.argumentsbase.StringReader;
import com.lemonlightmc.moreplugins.commands.exceptions.CommandSyntaxException;
import com.lemonlightmc.moreplugins.commands.exceptions.CommandSyntaxException.CommandSyntaxExceptionContainer;

public class RegistryArguments {
  public static class BiomeArgument extends Argument<Biome, BiomeArgument> {

    public static final String[] NAMES = _mapRegistry(Registry.BIOME);
    private static final CommandSyntaxExceptionContainer INVALID_BIOME = new CommandSyntaxExceptionContainer(
        value -> "Invalid Biome '" + value + "'");

    public BiomeArgument(final String name) {
      super(name, Biome.class, ArgumentType.BIOME);
      withSuggestions(NAMES);
    }

    public BiomeArgument getInstance() {
      return this;
    }

    @Override
    public Biome parseArgument(final CommandSource<CommandSender> source, final StringReader reader, final String key,
        final CommandArguments previousArgs)
        throws CommandSyntaxException {
      final int start = reader.getCursor();
      String value = null;
      try {
        value = reader.readString();
        return Registry.BIOME.getOrThrow(NamespacedKey.fromString(value));
      } catch (final Exception e) {
        reader.setCursor(start);
        throw INVALID_BIOME.createWithContext(reader, value);
      }
    }
  }

  public static class SoundArgument extends Argument<Sound, SoundArgument> {

    public static final String[] NAMES = _mapRegistry(Registry.SOUNDS);

    private static final CommandSyntaxExceptionContainer INVALID_SOUND = new CommandSyntaxExceptionContainer(
        value -> "Invalid Sound '" + value + "'");

    public SoundArgument(final String name) {
      super(name, Sound.class, ArgumentType.SOUND);
      withSuggestions(NAMES);
    }

    public SoundArgument getInstance() {
      return this;
    }

    @Override
    public Sound parseArgument(final CommandSource<CommandSender> source, final StringReader reader, final String key,
        final CommandArguments previousArgs)
        throws CommandSyntaxException {
      final int start = reader.getCursor();
      String value = null;
      try {
        value = reader.readString();
        return Registry.SOUNDS.getOrThrow(NamespacedKey.fromString(value));
      } catch (final Exception e) {
        reader.setCursor(start);
        throw INVALID_SOUND.createWithContext(reader, value);
      }
    }
  }

  @SuppressWarnings("rawtypes")
  public static class ParticleArgument extends Argument<ParticleData, ParticleArgument> {

    public static final String[] NAMES = _mapRegistry(Registry.PARTICLE_TYPE);

    private static final CommandSyntaxExceptionContainer INVALID_PARTICLE = new CommandSyntaxExceptionContainer(
        value -> "Invalid Particle '" + value + "'");

    public ParticleArgument(final String name) {
      super(name, ParticleData.class, ArgumentType.PARTICLE);
      withSuggestions(NAMES);
    }

    public ParticleArgument getInstance() {
      return this;
    }

    @Override
    public ParticleData<?> parseArgument(final CommandSource<CommandSender> source, final StringReader reader,
        final String key,
        final CommandArguments previousArgs)
        throws CommandSyntaxException {
      final int start = reader.getCursor();
      String value = null;
      try {
        value = reader.readString();
        return new ParticleData<>(Registry.PARTICLE_TYPE.getOrThrow(NamespacedKey.fromString(value)), null);
      } catch (final Exception e) {
        reader.setCursor(start);
        throw INVALID_PARTICLE.createWithContext(reader, value);
      }
    }
  }

  public static class PotionEffectArgument extends Argument<PotionEffectType, PotionEffectArgument> {

    public static final String[] NAMES = _mapRegistry(Registry.EFFECT);

    private static final CommandSyntaxExceptionContainer INVALID_POTION = new CommandSyntaxExceptionContainer(
        value -> "Invalid Potion Effect '" + value + "'");

    public PotionEffectArgument(final String name) {
      super(name, PotionEffectType.class, ArgumentType.POTION_EFFECT);
      withSuggestions(NAMES);
    }

    public PotionEffectArgument getInstance() {
      return this;
    }

    @Override
    public PotionEffectType parseArgument(final CommandSource<CommandSender> source, final StringReader reader,
        final String key,
        final CommandArguments previousArgs)
        throws CommandSyntaxException {
      final int start = reader.getCursor();
      String value = null;
      try {
        value = reader.readString();
        return Registry.EFFECT.getOrThrow(NamespacedKey.fromString(value));
      } catch (final Exception e) {
        reader.setCursor(start);
        throw INVALID_POTION.createWithContext(reader, value);
      }
    }
  }

  public static class EnchantmentArgument extends Argument<Enchantment, EnchantmentArgument> {

    public static final String[] NAMES = _mapRegistry(Registry.ENCHANTMENT);

    private static final CommandSyntaxExceptionContainer INVALID_ENCHANTMENT = new CommandSyntaxExceptionContainer(
        value -> "Invalid Enchantment '" + value + "'");

    public EnchantmentArgument(final String name) {
      super(name, Enchantment.class, ArgumentType.ENCHANTMENT);
      withSuggestions(NAMES);
    }

    public EnchantmentArgument getInstance() {
      return this;
    }

    @Override
    public Enchantment parseArgument(final CommandSource<CommandSender> source, final StringReader reader,
        final String key,
        final CommandArguments previousArgs)
        throws CommandSyntaxException {
      final int start = reader.getCursor();
      String value = null;
      try {
        value = reader.readString();
        return Registry.ENCHANTMENT.getOrThrow(Objects.requireNonNull(NamespacedKey.fromString(value)));
      } catch (final Exception e) {
        reader.setCursor(start);
        throw INVALID_ENCHANTMENT.createWithContext(reader, value);
      }
    }
  }

  public static class AdvancementArgument extends Argument<Advancement, AdvancementArgument> {

    @SuppressWarnings("deprecation")
    public static final String[] NAMES = _mapRegistry(Registry.ADVANCEMENT);

    private static final CommandSyntaxExceptionContainer INVALID_ADVANCEMENT = new CommandSyntaxExceptionContainer(
        value -> "Invalid Advancement '" + value + "'");

    public AdvancementArgument(final String name) {
      super(name, Advancement.class, ArgumentType.ADVANCEMENT);
      withSuggestions(NAMES);
    }

    public AdvancementArgument getInstance() {
      return this;
    }

    @Override
    public Advancement parseArgument(final CommandSource<CommandSender> source, final StringReader reader,
        final String key,
        final CommandArguments previousArgs)
        throws CommandSyntaxException {
      final int start = reader.getCursor();
      String value = null;
      try {
        value = reader.readString();
        return Objects.requireNonNull(Bukkit.getAdvancement(Objects.requireNonNull(NamespacedKey.fromString(value))));
      } catch (final Exception e) {
        reader.setCursor(start);
        throw INVALID_ADVANCEMENT.createWithContext(reader, value);
      }
    }
  }

  public static class BlockDataArgument extends Argument<BlockData, BlockDataArgument> {

    private static final CommandSyntaxExceptionContainer INVALID_BLOCK = new CommandSyntaxExceptionContainer(
        value -> "Invalid BlockData '" + value + "'");
    public static final String[] NAMES = _mapRegistry(Registry.MATERIAL);

    public BlockDataArgument(final String name) {
      super(name, BlockData.class, ArgumentType.BLOCKDATA);
      withSuggestions(NAMES);
    }

    public BlockDataArgument getInstance() {
      return this;
    }

    @Override
    public BlockData parseArgument(final CommandSource<CommandSender> source, final StringReader reader,
        final String key, final CommandArguments previousArgs)
        throws CommandSyntaxException {
      final int start = reader.getCursor();
      String value = null;
      try {
        value = reader.readString();
        return Bukkit.createBlockData(Objects.requireNonNull(Material.getMaterial(value)));
      } catch (final Exception e) {
        reader.setCursor(start);
        throw INVALID_BLOCK.createWithContext(reader, value);
      }
    }
  }

  public static class ItemStackArgument extends Argument<ItemStack, ItemStackArgument> {

    public static final String[] NAMES = _mapRegistry(Registry.ITEM);

    private static final CommandSyntaxExceptionContainer INVALID_ITEM = new CommandSyntaxExceptionContainer(
        value -> "Invalid ItemStack '" + value + "'");

    public ItemStackArgument(final String name) {
      super(name, ItemStack.class, ArgumentType.ITEMSTACK);
      withSuggestions(NAMES);
    }

    public ItemStackArgument getInstance() {
      return this;
    }

    @Override
    public ItemStack parseArgument(final CommandSource<CommandSender> source, final StringReader reader,
        final String key,
        final CommandArguments previousArgs)
        throws CommandSyntaxException {
      final int start = reader.getCursor();
      String[] value = null;
      try {
        value = reader.readList(':');
        if (value.length == 0) {
          throw new Exception();
        }
        final Material mat = Objects.requireNonNull(Material.getMaterial(value[0]));
        if (value.length > 1) {
          final int amount = Integer.parseInt(value[1]);
          return new ItemStack(Material.getMaterial(value[0]), amount);
        }
        return new ItemStack(mat, 1);
      } catch (final Exception e) {
        reader.setCursor(start);
        throw INVALID_ITEM.createWithContext(reader, Arrays.toString(value));
      }
    }
  }

  public static class MaterialArgument extends Argument<Material, MaterialArgument> {

    private static final CommandSyntaxExceptionContainer INVALID_MATERIAL = new CommandSyntaxExceptionContainer(
        value -> "Invalid Material '" + value + "'");
    public static final String[] NAMES = _mapRegistry(Registry.MATERIAL);

    public MaterialArgument(final String name) {
      super(name, Material.class, ArgumentType.MATERIAL);
      withSuggestions(NAMES);
    }

    public MaterialArgument getInstance() {
      return this;
    }

    @Override
    public Material parseArgument(final CommandSource<CommandSender> source, final StringReader reader,
        final String key, final CommandArguments previousArgs)
        throws CommandSyntaxException {
      final int start = reader.getCursor();
      String value = null;
      try {
        value = reader.readString();
        return Objects.requireNonNull(Material.getMaterial(value));
      } catch (final Exception e) {
        reader.setCursor(start);
        throw INVALID_MATERIAL.createWithContext(reader, value);
      }
    }
  }

  public static class StructureArgument extends Argument<Structure, StructureArgument> {

    public static final String[] NAMES = _mapRegistry(Registry.STRUCTURE);

    private static final CommandSyntaxExceptionContainer INVALID_STRUCTURE = new CommandSyntaxExceptionContainer(
        value -> "Invalid Structure '" + value + "'");

    public StructureArgument(final String name) {
      super(name, Structure.class, ArgumentType.STRUCTURE);
      withSuggestions(NAMES);
    }

    public StructureArgument getInstance() {
      return this;
    }

    @Override
    public Structure parseArgument(final CommandSource<CommandSender> source, final StringReader reader,
        final String key,
        final CommandArguments previousArgs)
        throws CommandSyntaxException {
      final int start = reader.getCursor();
      String value = null;
      try {
        value = reader.readString();
        return Registry.STRUCTURE.getOrThrow(NamespacedKey.fromString(value));
      } catch (final Exception e) {
        reader.setCursor(start);
        throw INVALID_STRUCTURE.createWithContext(reader, value);
      }
    }
  }

  public static class AttributeArgument extends Argument<Attribute, AttributeArgument> {

    public static final String[] NAMES = _mapRegistry(Registry.ATTRIBUTE);

    private static final CommandSyntaxExceptionContainer INVALID_STRUCTURE = new CommandSyntaxExceptionContainer(
        value -> "Invalid Attribute '" + value + "'");

    public AttributeArgument(final String name) {
      super(name, Attribute.class, ArgumentType.ATTRIBUTE);
      withSuggestions(NAMES);
    }

    public AttributeArgument getInstance() {
      return this;
    }

    @Override
    public Attribute parseArgument(final CommandSource<CommandSender> source, final StringReader reader,
        final String key,
        final CommandArguments previousArgs)
        throws CommandSyntaxException {
      final int start = reader.getCursor();
      String value = null;
      try {
        value = reader.readString();
        return Registry.ATTRIBUTE.getOrThrow(NamespacedKey.fromString(value));
      } catch (final Exception e) {
        reader.setCursor(start);
        throw INVALID_STRUCTURE.createWithContext(reader, value);
      }
    }
  }

  public static class StructureTypeArgument extends Argument<StructureType, StructureTypeArgument> {

    public static final String[] NAMES = _mapRegistry(Registry.STRUCTURE_TYPE);

    private static final CommandSyntaxExceptionContainer INVALID_STRUCTURE = new CommandSyntaxExceptionContainer(
        value -> "Invalid StructureType '" + value + "'");

    public StructureTypeArgument(final String name) {
      super(name, StructureType.class, ArgumentType.STRUCTURE_TYPE);
      withSuggestions(NAMES);
    }

    public StructureTypeArgument getInstance() {
      return this;
    }

    @Override
    public StructureType parseArgument(final CommandSource<CommandSender> source, final StringReader reader,
        final String key,
        final CommandArguments previousArgs)
        throws CommandSyntaxException {
      final int start = reader.getCursor();
      String value = null;
      try {
        value = reader.readString();
        return Registry.STRUCTURE_TYPE.getOrThrow(NamespacedKey.fromString(value));
      } catch (final Exception e) {
        reader.setCursor(start);
        throw INVALID_STRUCTURE.createWithContext(reader, value);
      }
    }
  }

  public static class EntityTypeArgument extends Argument<EntityType, EntityTypeArgument> {
    private static final CommandSyntaxExceptionContainer INVALID_PLAYER = new CommandSyntaxExceptionContainer(
        value -> "Invalid EntityType '" + value + "'");
    public static final String[] NAMES = _mapRegistry(Registry.ENTITY_TYPE);

    public EntityTypeArgument(final String name) {
      super(name, EntityType.class, ArgumentType.ENTITY_TYPE);
      withSuggestions(NAMES);
    }

    public EntityTypeArgument getInstance() {
      return this;
    }

    @Override
    public EntityType parseArgument(final CommandSource<CommandSender> source, final StringReader reader,
        final String key, final CommandArguments previousArgs)
        throws CommandSyntaxException {
      reader.point();
      String value = null;
      try {
        value = reader.readString();
        return Objects.requireNonNull(EntityType.valueOf(value));
      } catch (final Exception e) {
        reader.resetCursor();
        throw INVALID_PLAYER.createWithContext(reader, value);
      }
    }
  }

  public static <T extends Keyed> String[] _mapRegistry(final Registry<T> registry) {
    return registry.stream().map((final T b) -> {
      final NamespacedKey key = b.getKey();
      return key == null ? null : key.toString();
    }).filter((s) -> s != null).toArray(String[]::new);
  }
}
