package com.lemonlightmc.moreplugins.commands.arguments;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.profile.PlayerProfile;

import com.lemonlightmc.moreplugins.commands.CommandSource;
import com.lemonlightmc.moreplugins.commands.argumentsbase.Argument;
import com.lemonlightmc.moreplugins.commands.argumentsbase.ArgumentType;
import com.lemonlightmc.moreplugins.commands.argumentsbase.StringReader;
import com.lemonlightmc.moreplugins.commands.suggestions.Suggestions;
import com.lemonlightmc.moreplugins.commands.exceptions.CommandSyntaxException;

public class EntityArguments {
  public static class PlayerArgument extends Argument<Player, PlayerArgument> {

    public PlayerArgument(final String name) {
      super(name, Player.class, ArgumentType.PLAYER);
      withSuggestions(Suggestions.from((info) -> Bukkit.getOnlinePlayers().stream().map(p -> p.getName()).toList()));

    }

    @Override
    public PlayerArgument getInstance() {
      return this;
    }

    @Override
    public Player parseArgument(final CommandSource<CommandSender> source, final StringReader reader, final String key)
        throws CommandSyntaxException {
      return Bukkit.getPlayerExact(reader.readString());
    }
  }

  public static class PlayerProfileArgument extends Argument<PlayerProfile, PlayerProfileArgument> {
    public PlayerProfileArgument(final String name) {
      super(name, PlayerProfile.class, ArgumentType.PLAYERPROFILE);
      withSuggestions(Suggestions.from((info) -> Bukkit.getOnlinePlayers().stream().map(p -> p.getName()).toList()));
    }

    public PlayerProfileArgument getInstance() {
      return this;
    }

    @Override
    public PlayerProfile parseArgument(final CommandSource<CommandSender> source, final StringReader reader,
        final String key)
        throws CommandSyntaxException {
      return Bukkit.getPlayerExact(reader.readString()).getPlayerProfile();
    }
  }

}
