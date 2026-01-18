package com.lemonlightmc.moreplugins.commands.arguments;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.profile.PlayerProfile;

import com.lemonlightmc.moreplugins.commands.CommandSource;
import com.lemonlightmc.moreplugins.commands.argumentsbase.Argument;
import com.lemonlightmc.moreplugins.commands.argumentsbase.ArgumentType;
import com.lemonlightmc.moreplugins.commands.argumentsbase.CommandArguments;
import com.lemonlightmc.moreplugins.commands.argumentsbase.StringReader;
import com.lemonlightmc.moreplugins.commands.suggestions.Suggestions;
import com.lemonlightmc.moreplugins.commands.exceptions.CommandSyntaxException;
import com.lemonlightmc.moreplugins.commands.exceptions.CommandSyntaxException.CommandSyntaxExceptionContainer;

public class EntityArguments {
  public static class PlayerArgument extends Argument<Player, PlayerArgument> {
    private static final CommandSyntaxExceptionContainer INVALID_PLAYER = new CommandSyntaxExceptionContainer(
        (value) -> "Invalid Player '" + value + "'");

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
  }

  public static class PlayerProfileArgument extends Argument<PlayerProfile, PlayerProfileArgument> {
    private static final CommandSyntaxExceptionContainer INVALID_PLAYER = new CommandSyntaxExceptionContainer(
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
  }

}
