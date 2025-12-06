package com.lemonlightmc.moreplugins.commands.argumentsbase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class EntitySelector<E> {
  public static final EntitySelector<Player> ONE_PLAYER = new EntitySelector<Player>("@s",
      (source) -> player(source.sender().getName()));
  public static final EntitySelector<Player> ALL_PLAYERS = new EntitySelector<Player>("@a",
      (source) -> players());
  public static final EntitySelector<Player> NEAREST_PLAYER = new EntitySelector<Player>("@p",
      (source) -> nearestPlayer(source.location()));
  public static final EntitySelector<Player> RANDOM_PLAYER = new EntitySelector<Player>("@r",
      (source) -> randomPlayer());
  public static final EntitySelector<Entity> ALL_ENTITIES = new EntitySelector<Entity>("@e",
      (source) -> entities());
  public static final EntitySelector<Entity> NEAREST_ENTITY = new EntitySelector<Entity>("@n",
      (source) -> nearestEntity(source.location()));

  private final String symbol;
  private final Function<CommandSource, List<E>> parser;

  private EntitySelector(final String symbol, final Function<CommandSource, List<E>> parser) {
    this.symbol = symbol;
    this.parser = parser;
  }

  public String getSymbol() {
    return symbol;
  }

  public String toString() {
    return symbol;
  }

  public Function<CommandSource, List<E>> getParser() {
    return parser;
  }

  public List<E> find(final CommandSource source) {
    return parser.apply(source);
  }

  public static EntitySelector<?> fromString(final String str) {
    if (str == null || str.length() != 2) {
      return null;
    }
    switch (str) {
      case "@s":
        return ONE_PLAYER;
      case "@a":
        return ALL_PLAYERS;
      case "@p":
        return NEAREST_PLAYER;
      case "@r":
        return RANDOM_PLAYER;
      case "@e":
        return ALL_ENTITIES;
      case "@n":
        return NEAREST_ENTITY;
      default:
        return null;
    }
  }

  @FunctionalInterface
  public interface Order {
    void sort(Location location, List<? extends Entity> entities);
  }

  public static final Order ORDER_ARBITRARY = (location, entities) -> {
  };

  public static final Order ORDER_NEAREST = (location, entities) -> entities
      .sort(Comparator.comparingDouble(entity -> entity.getLocation().distanceSquared(location)));

  public static final Order ORDER_FURTHEST = (location, entities) -> entities
      .sort(Comparator.comparingDouble(entity -> -entity.getLocation().distanceSquared(location)));

  public static final Order ORDER_RANDOM = (location, entities) -> Collections.shuffle(entities);

  public static List<Player> player(final String name) {
    if (name == null || name.length() == 0) {
      return null;
    }
    final Player player = Bukkit.getPlayerExact(name);
    return player == null ? List.of() : List.of(player);
  }

  public static List<Player> player(final UUID uuid) {
    if (uuid == null) {
      return null;
    }
    final Player player = Bukkit.getPlayer(uuid);
    return player == null ? List.of() : List.of(player);
  }

  public static List<Player> players() {
    final List<Player> players = new ArrayList<>();
    for (final Player player : Bukkit.getOnlinePlayers()) {
      players.add(player);
    }
    return players;
  }

  public static List<Entity> entity(final UUID uuid) {
    if (uuid == null) {
      return List.of();
    }
    for (final World world : Bukkit.getWorlds()) {
      for (final Entity entity : world.getEntities()) {
        if (entity.getUniqueId().equals(uuid) && !entity.isDead()) {
          return List.of(entity);
        }
      }
    }
    return List.of();
  }

  public static List<Entity> entities() {
    final List<Entity> entities = new ArrayList<>();
    for (final World world : Bukkit.getWorlds()) {
      for (final Entity entity : world.getEntities()) {
        if (!entity.isDead()) {
          entities.add(entity);
        }
      }
    }
    return entities;
  }

  public static List<Entity> entities(final EntityType type) {
    final List<Entity> entities = new ArrayList<>();
    for (final World world : Bukkit.getWorlds()) {
      for (final Entity entity : world.getEntities()) {
        if ((type == null || entity.getType().equals(type)) && !entity.isDead()) {
          entities.add(entity);
        }
      }
    }
    return entities;
  }

  public static List<Entity> randomEntity() {
    final List<Entity> entities = entities();
    final int rand = (int) Math.round(Math.random() * entities.size());
    return List.of(entities.get(rand));
  }

  public static List<Entity> randomEntity(final EntityType type) {
    final List<Entity> entities = entities(type);
    final int rand = (int) Math.round(Math.random() * entities.size());
    return List.of(entities.get(rand));
  }

  public static List<Player> randomPlayer() {
    final List<Player> entities = players();
    final int rand = (int) Math.round(Math.random() * entities.size());
    return List.of(entities.get(rand));
  }

  public static List<Entity> nearestEntity(final Location loc) {
    final List<Entity> entities = entities();
    ORDER_NEAREST.sort(loc, entities);
    return entities;
  }

  public static List<Entity> nearestEntity(final Location loc, final EntityType type) {
    final List<Entity> entities = entities(type);
    ORDER_NEAREST.sort(loc, entities);
    return entities;
  }

  public static List<Player> nearestPlayer(final Location loc) {
    final List<Player> players = players();
    ORDER_NEAREST.sort(loc, players);
    return players;
  }

  public static List<Entity> furthestEntity(final Location loc) {
    final List<Entity> entities = entities();
    ORDER_FURTHEST.sort(loc, entities);
    return entities;
  }

  public static List<Entity> furthestEntity(final Location loc, final EntityType type) {
    final List<Entity> entities = entities(type);
    ORDER_FURTHEST.sort(loc, entities);
    return entities;
  }

  public static List<Player> furthestPlayer(final Location loc) {
    final List<Player> players = players();
    ORDER_FURTHEST.sort(loc, players);
    return players;
  }
}
