package com.lemonlightmc.moreplugins.commands.arguments;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.Registry;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.profile.PlayerProfile;

import com.lemonlightmc.moreplugins.commands.CommandSource;
import com.lemonlightmc.moreplugins.commands.Utils;
import com.lemonlightmc.moreplugins.commands.argumentsbase.Argument;
import com.lemonlightmc.moreplugins.commands.argumentsbase.ArgumentType;
import com.lemonlightmc.moreplugins.commands.argumentsbase.CommandArguments;
import com.lemonlightmc.moreplugins.commands.argumentsbase.StringReader;
import com.lemonlightmc.moreplugins.commands.exceptions.CommandExceptions.DynamicCommandException;
import com.lemonlightmc.moreplugins.commands.suggestions.Suggestions;
import com.lemonlightmc.moreplugins.commands.exceptions.CommandSyntaxException;
import com.lemonlightmc.moreplugins.exceptions.DynamicExceptionFunction.Dynamic1ExceptionFunktion;

public class EntityArguments {
  public static class PlayerArgument extends Argument<Player, PlayerArgument> {
    private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_PLAYER = new DynamicCommandException<Dynamic1ExceptionFunktion>(
        value -> "Invalid Player '" + value + "'");

    public PlayerArgument(final String name) {
      super(name, Player.class, ArgumentType.PLAYER);
      withSuggestions(Suggestions.from((info) -> Bukkit.getOnlinePlayers().stream().map(p -> p.getName()).toList()));

    }

    public PlayerArgument getInstance() {
      return this;
    }

    @Override
    public Player parseArgument(final CommandSource<CommandSender> source, final StringReader reader, final String key,
        final CommandArguments previousArgs)
        throws CommandSyntaxException {
      reader.point();
      String value = null;
      try {
        value = reader.readString();
        return Bukkit.getPlayerExact(value);
      } catch (final Exception e) {
        reader.resetCursor();
        throw INVALID_PLAYER.createWithContext(reader, value);
      }
    }

    @Override
    public boolean equals(final Object obj) {
      return this == obj && getClass() == obj.getClass() && super.equals(obj);
    }

    @Override
    public String toString() {
      return "PlayerArgument []";
    }
  }

  public static class PlayerProfileArgument extends Argument<PlayerProfile, PlayerProfileArgument> {
    private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_PLAYER = new DynamicCommandException<Dynamic1ExceptionFunktion>(
        value -> "Invalid Player '" + value + "'");

    public PlayerProfileArgument(final String name) {
      super(name, PlayerProfile.class, ArgumentType.PLAYERPROFILE);
      withSuggestions(Suggestions.from((info) -> Bukkit.getOnlinePlayers().stream().map(p -> p.getName()).toList()));
    }

    public PlayerProfileArgument getInstance() {
      return this;
    }

    @Override
    public PlayerProfile parseArgument(final CommandSource<CommandSender> source, final StringReader reader,
        final String key, final CommandArguments previousArgs)
        throws CommandSyntaxException {
      reader.point();
      String value = null;
      try {
        value = reader.readString();
        return Bukkit.getPlayerExact(value).getPlayerProfile();
      } catch (final Exception e) {
        reader.resetCursor();
        throw INVALID_PLAYER.createWithContext(reader, value);
      }
    }

    @Override
    public boolean equals(final Object obj) {
      return this == obj && getClass() == obj.getClass() && super.equals(obj);
    }

    @Override
    public String toString() {
      return "PlayerProfileArgument []";
    }
  }

  public static class EntityTypeArgument extends Argument<EntityType, EntityTypeArgument> {
    private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_PLAYER = new DynamicCommandException<Dynamic1ExceptionFunktion>(
        value -> "Invalid EntityType '" + value + "'");
    public static final String[] NAMES = Utils.mapRegistry(Registry.ENTITY_TYPE);

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

    @Override
    public boolean equals(final Object obj) {
      return this == obj && getClass() == obj.getClass() && super.equals(obj);
    }

    @Override
    public String toString() {
      return "EntityTypeArgument []";
    }
  }
}
