package com.lemonlightmc.moreplugins.commands.arguments;

import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.advancement.Advancement;
import org.bukkit.block.Biome;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootTable;
import org.bukkit.loot.LootTables;
import org.bukkit.potion.PotionEffectType;

import com.lemonlightmc.moreplugins.commands.CommandSource;
import com.lemonlightmc.moreplugins.commands.Utils;
import com.lemonlightmc.moreplugins.commands.argumentsbase.Argument;
import com.lemonlightmc.moreplugins.commands.argumentsbase.ArgumentType;
import com.lemonlightmc.moreplugins.commands.argumentsbase.CommandArguments;
import com.lemonlightmc.moreplugins.commands.argumentsbase.ParticleData;
import com.lemonlightmc.moreplugins.commands.argumentsbase.StringReader;
import com.lemonlightmc.moreplugins.commands.exceptions.CommandSyntaxException.*;
import com.lemonlightmc.moreplugins.commands.exceptions.CommandSyntaxException;
import com.lemonlightmc.moreplugins.exceptions.DynamicExceptionFunction.Dynamic1ExceptionFunktion;
import com.lemonlightmc.moreplugins.time.DurationParser;

public class MiscArguments {

  public static class BiomeArgument extends Argument<Biome, BiomeArgument> {

    public static final String[] NAMES = Utils.mapRegistry(Registry.BIOME);
    private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_BIOME = new DynamicCommandException<Dynamic1ExceptionFunktion>(
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

    @Override
    public String toString() {
      return "BiomeArgument []";
    }
  }

  public static class WorldArgument extends Argument<World, WorldArgument> {

    public static final String[] NAMES = Bukkit.getWorlds().stream().map((final World w) -> {
      return w == null ? null : w.getName();
    }).toArray(String[]::new);
    private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_WORLD = new DynamicCommandException<Dynamic1ExceptionFunktion>(
        value -> "Invalid World '" + value + "'");

    public WorldArgument(final String name) {
      super(name, World.class, ArgumentType.WORLD);
      withSuggestions(NAMES);
    }

    public WorldArgument getInstance() {
      return this;
    }

    @Override
    public World parseArgument(final CommandSource<CommandSender> source, final StringReader reader, final String key,
        final CommandArguments previousArgs)
        throws CommandSyntaxException {
      final int start = reader.getCursor();
      String value = null;
      try {
        value = reader.readString();
        return Objects.requireNonNull(Bukkit.getWorld(value));
      } catch (final Exception e) {
        reader.setCursor(start);
        throw INVALID_WORLD.createWithContext(reader, value);
      }
    }

    @Override
    public String toString() {
      return "WorldArgument []";
    }
  }

  public static class TimeArgument extends Argument<Duration, TimeArgument> {
    private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_TIME = new DynamicCommandException<Dynamic1ExceptionFunktion>(
        value -> "Invalid Time '" + value + "'");

    public TimeArgument(final String name) {
      super(name, Duration.class, ArgumentType.TIME);
      withSuggestions("true", "false");
    }

    public TimeArgument getInstance() {
      return this;
    }

    @Override
    public Duration parseArgument(final CommandSource<CommandSender> source, final StringReader reader,
        final String key, final CommandArguments previousArgs)
        throws CommandSyntaxException {
      final int start = reader.getCursor();
      String value = null;
      try {
        value = reader.readString();
        return Duration.ofMillis(DurationParser.parse(value));
      } catch (final Exception e) {
        reader.setCursor(start);
        throw INVALID_TIME.createWithContext(reader, value);
      }
    }

    @Override
    public String toString() {
      return "TimeArgument []";
    }
  }

  public static class SoundArgument extends Argument<Sound, SoundArgument> {

    public static final String[] NAMES = Utils.mapRegistry(Registry.SOUNDS);

    private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_SOUND = new DynamicCommandException<Dynamic1ExceptionFunktion>(
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

    @Override
    public String toString() {
      return "SoundArgument []";
    }
  }

  public static class PotionEffectArgument extends Argument<PotionEffectType, PotionEffectArgument> {

    public static final String[] NAMES = Utils.mapRegistry(Registry.EFFECT);

    private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_POTION = new DynamicCommandException<Dynamic1ExceptionFunktion>(
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

    @Override
    public String toString() {
      return "PotionEffectArgument []";
    }
  }

  @SuppressWarnings("rawtypes")
  public static class ParticleArgument extends Argument<ParticleData, ParticleArgument> {

    public static final String[] NAMES = Utils.mapRegistry(Registry.PARTICLE_TYPE);

    private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_PARTICLE = new DynamicCommandException<Dynamic1ExceptionFunktion>(
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

    @Override
    public String toString() {
      return "ParticleArgument []";
    }
  }

  public static class NamespacedKeyArgument extends Argument<NamespacedKey, NamespacedKeyArgument> {
    private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_KEY = new DynamicCommandException<Dynamic1ExceptionFunktion>(
        value -> "Invalid Key '" + value + "'");

    public NamespacedKeyArgument(final String name) {
      super(name, NamespacedKey.class, ArgumentType.NAMESPACED_KEY);
    }

    public NamespacedKeyArgument getInstance() {
      return this;
    }

    @Override
    public NamespacedKey parseArgument(final CommandSource<CommandSender> source, final StringReader reader,
        final String key, final CommandArguments previousArgs)
        throws CommandSyntaxException {
      final int start = reader.getCursor();
      String value = null;
      try {
        value = reader.readString();
        return Objects.requireNonNull(NamespacedKey.fromString(value));
      } catch (final Exception e) {
        reader.setCursor(start);
        throw INVALID_KEY.createWithContext(reader, value);
      }
    }

    @Override
    public String toString() {
      return "NamespacedKeyArgument []";
    }
  }

  public static class MaterialArgument extends Argument<Material, MaterialArgument> {

    private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_MATERIAL = new DynamicCommandException<Dynamic1ExceptionFunktion>(
        value -> "Invalid Material '" + value + "'");
    public static final String[] NAMES = Utils.mapRegistry(Registry.MATERIAL);

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

    @Override
    public String toString() {
      return "MaterialArgument []";
    }
  }

  public static class ItemStackArgument extends Argument<ItemStack, ItemStackArgument> {

    public static final String[] NAMES = Utils.mapRegistry(Registry.ITEM);

    private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_ITEM = new DynamicCommandException<Dynamic1ExceptionFunktion>(
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

    @Override
    public String toString() {
      return "ItemStackArgument []";
    }
  }

  public static class BlockDataArgument extends Argument<BlockData, BlockDataArgument> {

    private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_BLOCK = new DynamicCommandException<Dynamic1ExceptionFunktion>(
        value -> "Invalid BlockData '" + value + "'");
    public static final String[] NAMES = Utils.mapRegistry(Registry.MATERIAL);

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

    @Override
    public String toString() {
      return "BlockDataArgument []";
    }
  }

  public static class BlockStateArgument extends Argument<BlockState, BlockStateArgument> {
    private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_BLOCK = new DynamicCommandException<Dynamic1ExceptionFunktion>(
        value -> "Invalid Block '" + value + "'");

    public BlockStateArgument(final String name) {
      super(name, BlockState.class, ArgumentType.BLOCKSTATE);
    }

    public BlockStateArgument getInstance() {
      return this;
    }

    @Override
    public BlockState parseArgument(final CommandSource<CommandSender> source, final StringReader reader,
        final String key, final CommandArguments previousArgs)
        throws CommandSyntaxException {
      final int start = reader.getCursor();
      String value = null;
      try {
        value = reader.readString();
        return Bukkit.createBlockData(Objects.requireNonNull(Material.getMaterial(value))).createBlockState();
      } catch (final Exception e) {
        reader.setCursor(start);
        throw INVALID_BLOCK.createWithContext(reader, value);
      }
    }

    @Override
    public String toString() {
      return "BlockStateArgument []";
    }
  }

  public static class AdvancementArgument extends Argument<Advancement, AdvancementArgument> {

    @SuppressWarnings("deprecation")
    public static final String[] NAMES = Utils.mapRegistry(Registry.ADVANCEMENT);

    private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_ADVANCEMENT = new DynamicCommandException<Dynamic1ExceptionFunktion>(
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

    @Override
    public String toString() {
      return "AdvancementArgument []";
    }
  }

  public static class EnchantmentArgument extends Argument<Enchantment, EnchantmentArgument> {

    public static final String[] NAMES = Utils.mapRegistry(Registry.ENCHANTMENT);

    private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_ENCHANTMENT = new DynamicCommandException<Dynamic1ExceptionFunktion>(
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

    @Override
    public String toString() {
      return "EnchantmentArgument []";
    }
  }

  public static class LootTableArgument extends Argument<LootTable, LootTableArgument> {

    public static final String[] NAMES = Utils.mapRegistry(LootTables.values());

    private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_LOOTTABLE = new DynamicCommandException<Dynamic1ExceptionFunktion>(
        value -> "Invalid Loot Table '" + value + "'");

    public LootTableArgument(final String name) {
      super(name, LootTable.class, ArgumentType.LOOT_TABLE);
      withSuggestions(NAMES);
    }

    public LootTableArgument getInstance() {
      return this;
    }

    @Override
    public LootTable parseArgument(final CommandSource<CommandSender> source, final StringReader reader,
        final String key,
        final CommandArguments previousArgs)
        throws CommandSyntaxException {
      final int start = reader.getCursor();
      String value = null;
      try {
        value = reader.readString();
        return Objects.requireNonNull(Objects.requireNonNull(
            LootTables.valueOf(Objects.requireNonNull(value))).getLootTable());
      } catch (final Exception e) {
        reader.setCursor(start);
        throw INVALID_LOOTTABLE.createWithContext(reader, value);
      }
    }

    @Override
    public String toString() {
      return "LootTableArgument []";
    }
  }

  public static class EnvironmentArgument extends Argument<Environment, EnvironmentArgument> {

    public static final String[] NAMES = Utils.mapRegistry(Environment.values());

    private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_LOOTTABLE = new DynamicCommandException<Dynamic1ExceptionFunktion>(
        value -> "Invalid Environment '" + value + "'");

    public EnvironmentArgument(final String name) {
      super(name, Environment.class, ArgumentType.ENVIRONMENT);
      withSuggestions(NAMES);
    }

    public EnvironmentArgument getInstance() {
      return this;
    }

    @Override
    public Environment parseArgument(final CommandSource<CommandSender> source, final StringReader reader,
        final String key,
        final CommandArguments previousArgs)
        throws CommandSyntaxException {
      final int start = reader.getCursor();
      String value = null;
      try {
        value = reader.readString();
        return Objects.requireNonNull(Environment.valueOf(Objects.requireNonNull(value)));
      } catch (final Exception e) {
        reader.setCursor(start);
        throw INVALID_LOOTTABLE.createWithContext(reader, value);
      }
    }

    @Override
    public String toString() {
      return "EnvironmentArgument []";
    }
  }
}
