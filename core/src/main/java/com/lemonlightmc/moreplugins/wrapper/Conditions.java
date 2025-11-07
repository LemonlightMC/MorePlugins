package com.lemonlightmc.moreplugins.wrapper;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiPredicate;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;

import com.lemonlightmc.moreplugins.base.MorePlugins;
import com.lemonlightmc.moreplugins.time.TimeRange;

public class Conditions {

  public static interface Condition {
    public boolean isEmpty();

    public boolean test(Player player);
  }

  public static enum ConditionValueType {
    WHITELIST(false),
    BLACKLIST(true);

    private boolean negate = false;

    private ConditionValueType(final boolean negate) {
      this.negate = negate;
    }

    public boolean apply(final boolean value) {
      return negate ? !value : value;
    }
  }

  public static enum ConditionTestType {
    AND((v1, v2) -> v1 && v2),
    NAND((v1, v2) -> !(v1 && v2)),
    OR((v1, v2) -> v1 || v2),
    NOR((v1, v2) -> !(v1 || v2)),
    XOR((v1, v2) -> (v1 && !v2) || (!v1 && v2)),
    XNOR((v1, v2) -> (!v1 || v2) && (v1 || !v2));

    private final BiPredicate<Boolean, Boolean> tester;

    private ConditionTestType(final BiPredicate<Boolean, Boolean> tester) {
      this.tester = tester;
    }

    public boolean merge(final boolean v1, final boolean v2) {
      return tester.test(v1, v2);
    }
  }

  public static class ConditionSet implements Condition {
    private Set<Condition> conditions = new HashSet<>();
    private ConditionTestType type;

    public ConditionSet(final Set<Condition> conditions) {
      this.conditions = conditions;
      this.type = ConditionTestType.AND;
    }

    public ConditionSet(final Set<Condition> conditions, final ConditionTestType type) {
      this.conditions = conditions;
      this.type = type;
    }

    public ConditionTestType getTestType() {
      return type;
    }

    public void setTestType(final ConditionTestType type) {
      this.type = type;
    }

    public void add(final Condition condition) {
      conditions.add(condition);
    }

    public boolean has(final Condition condition) {
      return conditions.contains(condition);
    }

    public void remove(final Condition condition) {
      conditions.remove(condition);
    }

    public void clear() {
      conditions.clear();
    }

    public Set<Condition> getConditions() {
      return conditions;
    }

    public boolean isEmpty() {
      return conditions == null || conditions.isEmpty();
    }

    public boolean test(final Player player) {
      if (conditions == null) {
        return true;
      }
      if (player == null) {
        return false;
      }
      boolean value = true;
      for (final Condition condition : conditions) {
        value = type.merge(value, condition.test(player));
      }
      return true;
    }

    @Override
    public int hashCode() {
      return 31 + ((conditions == null) ? 0 : conditions.hashCode());
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null || getClass() != obj.getClass()) {
        return false;
      }
      final ConditionSet other = (ConditionSet) obj;
      if (conditions == null && other.conditions != null) {
        return false;
      }
      return conditions.equals(other.conditions);
    }

    @Override
    public String toString() {
      return "ConditionSet [conditions=" + conditions + "]";
    }

    public ConditionTestType getType() {
      return type;
    }

    public void setConditions(final Set<Condition> conditions) {
      this.conditions = conditions;
    }
  }

  public static class WorldConditions implements Condition {

    private final Set<String> worlds;
    private final ConditionValueType type;

    public WorldConditions(final Set<String> worlds, final ConditionValueType type) {
      this.worlds = worlds;
      this.type = type;
    }

    public static WorldConditions allowed(final Set<String> allowed_worlds) {
      return new WorldConditions(allowed_worlds, ConditionValueType.WHITELIST);
    }

    public static WorldConditions denied(final Set<String> denied_worlds) {
      return new WorldConditions(denied_worlds, ConditionValueType.BLACKLIST);
    }

    public static WorldConditions from(final Set<String> denied_worlds, final ConditionValueType type) {
      return new WorldConditions(denied_worlds, type);
    }

    public boolean isEmpty() {
      return worlds == null || worlds.isEmpty();
    }

    public boolean test(final Player player) {
      final String w = player.getWorld().getName().toLowerCase();
      return type.apply(worlds.contains(w));
    }
  }

  public static class BiomConditions implements Condition {

    private final Set<String> bioms;
    private final ConditionValueType type;

    public BiomConditions(final Set<String> bioms, final ConditionValueType type) {
      this.bioms = bioms;
      this.type = type;
    }

    public static BiomConditions allowed(final Set<String> allowed_bioms) {
      return new BiomConditions(allowed_bioms, ConditionValueType.WHITELIST);
    }

    public static BiomConditions denied(final Set<String> denied_bioms) {
      return new BiomConditions(denied_bioms, ConditionValueType.BLACKLIST);
    }

    public static BiomConditions from(final Set<String> denied_bioms, final ConditionValueType type) {
      return new BiomConditions(denied_bioms, type);
    }

    public boolean isEmpty() {
      return bioms == null || bioms.isEmpty();
    }

    public boolean test(final Player player) {
      final String b = player.getWorld().getBiome(player.getLocation()).toString().toLowerCase();
      return type.apply(bioms.contains(b));
    }
  }

  public static class WeatherConditions implements Condition {

    private final Set<String> weathers;
    private final ConditionValueType type;

    public WeatherConditions(final Set<String> weathers, final ConditionValueType type) {
      this.weathers = weathers;
      this.type = type;
    }

    public static BiomConditions allowed(final Set<String> allowed_worlds) {
      return new BiomConditions(allowed_worlds, ConditionValueType.WHITELIST);
    }

    public static BiomConditions denied(final Set<String> denied_worlds) {
      return new BiomConditions(denied_worlds, ConditionValueType.BLACKLIST);
    }

    public static BiomConditions from(final Set<String> denied_worlds, final ConditionValueType type) {
      return new BiomConditions(denied_worlds, type);
    }

    public boolean isEmpty() {
      return weathers == null || weathers.isEmpty();
    }

    public boolean test(final Player player) {
      final String now = player.getWorld().isThundering() ? "thunder"
          : (player.getWorld().hasStorm() ? "rain" : "clear");
      return type.apply(weathers.contains(now));
    }
  }

  public static class MoonConditions implements Condition {

    private final Set<Integer> moonPhases;
    private final ConditionValueType type;

    public MoonConditions(final Set<Integer> moonPhases, final ConditionValueType type) {
      this.moonPhases = moonPhases;
      this.type = type;
    }

    public static MoonConditions allowed(final Set<Integer> moonPhases) {
      return new MoonConditions(moonPhases, ConditionValueType.WHITELIST);
    }

    public static MoonConditions denied(final Set<Integer> moonPhases) {
      return new MoonConditions(moonPhases, ConditionValueType.BLACKLIST);
    }

    public static MoonConditions from(final Set<Integer> moonPhases, final ConditionValueType type) {
      return new MoonConditions(moonPhases, type);
    }

    public boolean isEmpty() {
      return moonPhases == null || moonPhases.isEmpty();
    }

    public boolean test(final Player player) {
      final int value = Integer.valueOf(getMoonPhase(player));
      return type.apply(moonPhases.contains(value));
    }

    private static int getMoonPhase(final Player player) {
      final long day = player.getWorld().getFullTime() / 24000L; // Get the in-game day
      return (int) (day % 8); // Calculate the moon phase (0-7)
    }
  }

  public static class TimeRangeConditions implements Condition {

    private final Set<TimeRange> timeRanges;
    private final ConditionValueType type;

    public TimeRangeConditions(final Set<TimeRange> timeRanges, final ConditionValueType type) {
      this.timeRanges = timeRanges;
      this.type = type;
    }

    public static TimeRangeConditions allowed(final Set<TimeRange> timeRanges) {
      return new TimeRangeConditions(timeRanges, ConditionValueType.WHITELIST);
    }

    public static TimeRangeConditions denied(final Set<TimeRange> timeRanges) {
      return new TimeRangeConditions(timeRanges, ConditionValueType.BLACKLIST);
    }

    public static TimeRangeConditions from(final Set<TimeRange> timeRanges, final ConditionValueType type) {
      return new TimeRangeConditions(timeRanges, type);
    }

    public boolean isEmpty() {
      return timeRanges == null;
    }

    public boolean test(final Player player) {
      boolean value = true;
      final int t = (int) (player.getWorld().getFullTime() % 24000);
      for (final TimeRange timeRange : timeRanges) {
        if (timeRange == null) {
          continue;
        }
        value = value && type.apply(timeRange.contains(t));
      }
      return value;
    }
  }

  public static class AdvancementConditions implements Condition {

    private final Set<String> advancements;
    private final ConditionValueType type;

    public AdvancementConditions(final Set<String> advancements, final ConditionValueType type) {
      this.advancements = advancements;
      this.type = type;
    }

    public static AdvancementConditions allowed(final Set<String> advancements) {
      return new AdvancementConditions(advancements, ConditionValueType.WHITELIST);
    }

    public static AdvancementConditions denied(final Set<String> advancements) {
      return new AdvancementConditions(advancements, ConditionValueType.BLACKLIST);
    }

    public static AdvancementConditions from(final Set<String> advancements, final ConditionValueType type) {
      return new AdvancementConditions(advancements, type);
    }

    public boolean isEmpty() {
      return advancements == null || advancements.isEmpty();
    }

    public boolean test(final Player player) {
      boolean value = true;
      for (final String adv : advancements) {
        if (adv == null || adv.isEmpty())
          continue;
        final NamespacedKey key = adv.indexOf(':') >= 0 ? NamespacedKey.fromString(adv)
            : NamespacedKey.minecraft(adv);
        if (key == null)
          continue;
        final Advancement a = Bukkit.getAdvancement(key);
        if (a == null)
          continue;
        final AdvancementProgress prog = player.getAdvancementProgress(a);
        value = value && type.apply(prog.isDone());
      }
      return value;
    }
  }

  public static class RecipeConditions implements Condition {

    private final Set<String> recipes;
    private final ConditionValueType type;

    public RecipeConditions(final Set<String> recipes, final ConditionValueType type) {
      this.recipes = recipes;
      this.type = type;
    }

    public static RecipeConditions allowed(final Set<String> recipes) {
      return new RecipeConditions(recipes, ConditionValueType.WHITELIST);
    }

    public static RecipeConditions denied(final Set<String> recipes) {
      return new RecipeConditions(recipes, ConditionValueType.BLACKLIST);
    }

    public static RecipeConditions from(final Set<String> recipes, final ConditionValueType type) {
      return new RecipeConditions(recipes, type);
    }

    public boolean isEmpty() {
      return recipes == null || recipes.isEmpty();
    }

    public boolean test(final Player player) {
      boolean value = true;
      for (final String recipe : recipes) {
        if (recipe == null || recipe.isEmpty())
          continue;
        final NamespacedKey key = recipe.indexOf(':') >= 0 ? NamespacedKey.fromString(recipe)
            : NamespacedKey.minecraft(recipe);
        if (key == null)
          continue;
        value = value && type.apply(player.hasDiscoveredRecipe(key));
      }
      return value;
    }
  }

  public static class PluginConditions implements Condition {

    private final Set<String> plugins;
    private final ConditionValueType type;

    public PluginConditions(final Set<String> plugins, final ConditionValueType type) {
      this.plugins = plugins;
      this.type = type;
    }

    public static PluginConditions allowed(final Set<String> plugins) {
      return new PluginConditions(plugins, ConditionValueType.WHITELIST);
    }

    public static PluginConditions denied(final Set<String> plugins) {
      return new PluginConditions(plugins, ConditionValueType.BLACKLIST);
    }

    public static PluginConditions from(final Set<String> plugins, final ConditionValueType type) {
      return new PluginConditions(plugins, type);
    }

    public boolean isEmpty() {
      return plugins == null || plugins.isEmpty();
    }

    public boolean test(final Player player) {
      boolean value = true;
      for (final String plugin : plugins) {
        if (plugin == null || plugin.isEmpty())
          continue;
        final boolean isEnabled = MorePlugins.getInstance().getPluginManager().isPluginEnabled(plugin);
        value = value && type.apply(isEnabled);
      }
      return value;
    }
  }
}
