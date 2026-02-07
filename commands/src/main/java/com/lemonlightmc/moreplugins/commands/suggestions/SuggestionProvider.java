package com.lemonlightmc.moreplugins.commands.suggestions;

import java.util.List;

import org.bukkit.command.CommandSender;

public interface SuggestionProvider {

  public List<String> getSuggestions(SuggestionInfo<CommandSender> info);
}
