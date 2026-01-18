package com.lemonlightmc.moreplugins.commands.arguments;

import java.time.Duration;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.BlockState;
import org.bukkit.command.CommandSender;
import org.bukkit.loot.LootTable;
import org.bukkit.loot.LootTables;
import com.lemonlightmc.moreplugins.commands.CommandSource;
import com.lemonlightmc.moreplugins.commands.argumentsbase.Argument;
import com.lemonlightmc.moreplugins.commands.argumentsbase.ArgumentType;
import com.lemonlightmc.moreplugins.commands.argumentsbase.CommandArguments;
import com.lemonlightmc.moreplugins.commands.argumentsbase.LookAnchor;
import com.lemonlightmc.moreplugins.commands.argumentsbase.StringReader;
import com.lemonlightmc.moreplugins.commands.exceptions.CommandSyntaxException.*;
import com.lemonlightmc.moreplugins.commands.exceptions.CommandSyntaxException;
import com.lemonlightmc.moreplugins.exceptions.DynamicExceptionFunction.Dynamic1ExceptionFunktion;
import com.lemonlightmc.moreplugins.math.MathOperation;
import com.lemonlightmc.moreplugins.time.DurationParser;

public class MiscArguments {

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

  public static class LootTableArgument extends Argument<LootTable, LootTableArgument> {

    public static final String[] NAMES = _mapArray(LootTables.values());

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

    public static final String[] NAMES = _mapArray(Environment.values());

    private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_ENVIRONMENT = new DynamicCommandException<Dynamic1ExceptionFunktion>(
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
        throw INVALID_ENVIRONMENT.createWithContext(reader, value);
      }
    }

    @Override
    public String toString() {
      return "EnvironmentArgument []";
    }
  }

  public static class GameModeArgument extends Argument<GameMode, GameModeArgument> {

    public static final String[] NAMES = _mapArray(GameMode.values());

    private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_LOOKANCHOR = new DynamicCommandException<Dynamic1ExceptionFunktion>(
        value -> "Invalid GameMode '" + value + "'");

    public GameModeArgument(final String name) {
      super(name, GameMode.class, ArgumentType.GAME_MODE);
      withSuggestions(NAMES);
    }

    public GameModeArgument getInstance() {
      return this;
    }

    @Override
    public GameMode parseArgument(final CommandSource<CommandSender> source, final StringReader reader,
        final String key,
        final CommandArguments previousArgs)
        throws CommandSyntaxException {
      final int start = reader.getCursor();
      String value = null;
      try {
        value = reader.readString();
        return Objects.requireNonNull(GameMode.valueOf(Objects.requireNonNull(value)));
      } catch (final Exception e) {
        reader.setCursor(start);
        throw INVALID_LOOKANCHOR.createWithContext(reader, value);
      }
    }

    @Override
    public String toString() {
      return "GameModeArgument []";
    }
  }

  public static class LookAnchorArgument extends Argument<LookAnchor, LookAnchorArgument> {

    public static final String[] NAMES = _mapArray(LookAnchor.values());

    private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_LOOKANCHOR = new DynamicCommandException<Dynamic1ExceptionFunktion>(
        value -> "Invalid LookAnchor '" + value + "'");

    public LookAnchorArgument(final String name) {
      super(name, LookAnchor.class, ArgumentType.LOOK_ANCHOR);
      withSuggestions(NAMES);
    }

    public LookAnchorArgument getInstance() {
      return this;
    }

    @Override
    public LookAnchor parseArgument(final CommandSource<CommandSender> source, final StringReader reader,
        final String key,
        final CommandArguments previousArgs)
        throws CommandSyntaxException {
      final int start = reader.getCursor();
      String value = null;
      try {
        value = reader.readString();
        return Objects.requireNonNull(LookAnchor.valueOf(Objects.requireNonNull(value)));
      } catch (final Exception e) {
        reader.setCursor(start);
        throw INVALID_LOOKANCHOR.createWithContext(reader, value);
      }
    }

    @Override
    public String toString() {
      return "LookAnchorArgument []";
    }
  }

  public static class MathOperationArgument extends Argument<MathOperation, MathOperationArgument> {
    public static final String[] NAMES = _mapArray(MathOperation.values());

    private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_OPERATION = new DynamicCommandException<Dynamic1ExceptionFunktion>(
        value -> "Invalid MathOperation '" + value + "'");

    public MathOperationArgument(final String name) {
      super(name, MathOperation.class, ArgumentType.MATH_OPERATION);
      withSuggestions("true", "false");
    }

    public MathOperationArgument getInstance() {
      return this;
    }

    @Override
    public MathOperation parseArgument(final CommandSource<CommandSender> source, final StringReader reader,
        final String key, final CommandArguments previousArgs)
        throws CommandSyntaxException {
      reader.point();
      String value = null;
      try {
        value = reader.readString();
        return Objects.requireNonNull(MathOperation.fromString(value));
      } catch (final Exception e) {
        reader.resetCursor();
        throw INVALID_OPERATION.createWithContext(reader, value);
      }
    }

    @Override
    public String toString() {
      return "MathOperationArgument []";
    }
  }

  public static <T> String[] _mapArray(final T[] registry) {
    final String[] arr = new String[registry.length];
    for (int i = 0; i < registry.length; i++) {
      final T t = registry[i];
      if (t != null) {
        arr[i] = t.toString();
      }
    }
    return arr;
  }
}
