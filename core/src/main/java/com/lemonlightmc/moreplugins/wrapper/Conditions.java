package com.lemonlightmc.moreplugins.wrapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;

import com.lemonlightmc.moreplugins.time.TimeRange;

public class Conditions {

  public static interface Condition {
    public boolean isEmpty();

    public boolean test(Player player);
  }

  public static class ConditionSet implements Condition {
    private Set<Condition> conditions = new HashSet<>();

    public ConditionSet(final Set<Condition> conditions) {
      this.conditions = conditions;
    }

    public void addCondition(final Condition condition) {
      conditions.add(condition);
    }

    public boolean hasCondition(final Condition condition) {
      return conditions.contains(condition);
    }

    public void removeCondition(final Condition condition) {
      conditions.remove(condition);
    }

    public void clearConditions() {
      conditions.clear();
    }

    public Set<Condition> getConditions() {
      return conditions;
    }

    public boolean isEmpty() {
      return conditions.isEmpty();
    }

    public boolean test(final Player player) {
      for (final Condition condition : conditions) {
        if (!condition.test(player)) {
          return false;
        }
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

  }

  public static class WorldConditions implements Condition {

    private final Set<String> allowed_worlds;
    private final Set<String> denied_worlds;

    public WorldConditions(final Set<String> allowed_worlds, final Set<String> denied_worlds) {
      this.allowed_worlds = allowed_worlds;
      this.denied_worlds = denied_worlds;
    }

    public WorldConditions(final List<String> allowed_worlds, final List<String> denied_worlds) {
      this(new HashSet<>(allowed_worlds), new HashSet<>(denied_worlds));
    }

    public boolean isEmpty() {
      return allowed_worlds == null && denied_worlds == null || allowed_worlds.isEmpty() && denied_worlds.isEmpty();
    }

    public boolean test(final Player player) {
      final String w = player.getWorld().getName().toLowerCase();
      return allowed_worlds.contains(w) && !denied_worlds.contains(w);
    }
  }

  public static class BiomConditions implements Condition {

    private final Set<String> allowed_bioms;
    private final Set<String> denied_bioms;

    public BiomConditions(final Set<String> allowed_bioms, final Set<String> denied_bioms) {
      this.allowed_bioms = allowed_bioms;
      this.denied_bioms = denied_bioms;
    }

    public BiomConditions(final List<String> allowed_bioms, final List<String> denied_bioms) {
      this(new HashSet<>(allowed_bioms), new HashSet<>(denied_bioms));
    }

    public boolean isEmpty() {
      return allowed_bioms == null && denied_bioms == null || allowed_bioms.isEmpty() && denied_bioms.isEmpty();
    }

    public boolean test(final Player player) {
      final String b = player.getWorld().getBiome(player.getLocation()).toString().toLowerCase();
      return allowed_bioms.contains(b) && !denied_bioms.contains(b);
    }
  }

  public static class WeatherConditions implements Condition {

    private final Set<String> weathers;

    public WeatherConditions(final Set<String> weathers) {
      this.weathers = weathers;
    }

    public WeatherConditions(final List<String> weathers) {
      this(new HashSet<>(weathers));
    }

    public boolean isEmpty() {
      return weathers == null || weathers.isEmpty();
    }

    public boolean test(final Player player) {
      final boolean rain = player.getWorld().hasStorm();
      final boolean thun = player.getWorld().isThundering();
      final String now = thun ? "thunder" : (rain ? "rain" : "clear");
      return weathers.contains(now);
    }
  }

  public static class MoonConditions implements Condition {

    private final Set<Integer> moonPhases;

    public MoonConditions(final Set<Integer> moonPhases) {
      this.moonPhases = moonPhases;
    }

    public MoonConditions(final List<Integer> moonPhases) {
      this(new HashSet<>(moonPhases));
    }

    public boolean isEmpty() {
      return moonPhases == null || moonPhases.isEmpty();
    }

    public boolean test(final Player player) {
      return moonPhases.contains(Integer.valueOf(getMoonPhase(player.getWorld())));
    }

    private static int getMoonPhase(final World world) {
      final long day = world.getFullTime() / 24000L; // Get the in-game day
      return (int) (day % 8); // Calculate the moon phase (0-7)
    }
  }

  public static class TimeConditions implements Condition {

    private final Set<TimeRange> timeRanges;

    public TimeConditions(final Set<TimeRange> timeRanges) {
      this.timeRanges = timeRanges;
    }

    public TimeConditions(final List<TimeRange> timeRanges) {
      this(new HashSet<>(timeRanges));
    }

    public boolean isEmpty() {
      return timeRanges == null || timeRanges.isEmpty();
    }

    public boolean test(final Player player) {
      final int t = (int) (player.getWorld().getFullTime() % 24000);
      for (final TimeRange timeRange : timeRanges) {
        if (timeRange != null && timeRange.contains(t)) {
          return true;
        }
      }
      return false;
    }
  }

  public static class AdvancementConditions implements Condition {

    private final Set<String> advancements;

    public AdvancementConditions(final Set<String> advancements) {
      this.advancements = advancements;
    }

    public AdvancementConditions(final List<String> advancements) {
      this(new HashSet<>(advancements));
    }

    public boolean isEmpty() {
      return advancements == null || advancements.isEmpty();
    }

    public boolean test(final Player player) {
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
        if (!prog.isDone()) {
          return false;
        }
      }
      return true;
    }
  }

  public static class RecipeConditions implements Condition {

    private final Set<String> recipes;

    public RecipeConditions(final Set<String> recipes) {
      this.recipes = recipes;
    }

    public RecipeConditions(final List<String> recipes) {
      this(new HashSet<>(recipes));
    }

    public boolean isEmpty() {
      return recipes == null || recipes.isEmpty();
    }

    public boolean test(final Player player) {
      for (final String recipe : recipes) {
        if (recipe == null || recipe.isEmpty())
          continue;
        final NamespacedKey key = recipe.indexOf(':') >= 0 ? NamespacedKey.fromString(recipe)
            : NamespacedKey.minecraft(recipe);
        if (key == null)
          continue;
        if (!player.hasDiscoveredRecipe(null)) {
          return false;
        }
      }
      return true;
    }
  }
}
