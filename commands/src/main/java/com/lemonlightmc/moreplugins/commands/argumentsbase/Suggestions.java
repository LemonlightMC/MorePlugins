package com.lemonlightmc.moreplugins.commands.argumentsbase;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

public interface Suggestions<S> {

  abstract List<String> suggest(SuggestionInfo<S> info) throws CommandException;

  public static <S> Suggestions<S> empty() {
    return _ -> new ArrayList<>(Arrays.asList());
  }

  public static Suggestions<CommandSender> strings(String... suggestions) {
    return _ -> new ArrayList<>(Arrays.asList(suggestions));
  }

  static <S> Suggestions<S> strings(Collection<String> suggestions) {
    return _ -> new ArrayList<>(suggestions);
  }

  static <S> Suggestions<S> strings(
      Function<SuggestionInfo<S>, String[]> suggestions) {
    return info -> new ArrayList<>(Arrays.asList(suggestions.apply(info)));
  }

  static <S> Suggestions<S> stringCollection(
      Function<SuggestionInfo<S>, Collection<String>> suggestions) {
    return info -> new ArrayList<>((suggestions.apply(info)));
  }
}
