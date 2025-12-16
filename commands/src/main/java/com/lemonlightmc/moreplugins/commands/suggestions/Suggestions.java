package com.lemonlightmc.moreplugins.commands.suggestions;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.ArrayList;
import java.util.function.Function;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

import com.lemonlightmc.moreplugins.messages.StringTooltip;

@FunctionalInterface
public interface Suggestions<S extends CommandSender> {

  abstract Collection<StringTooltip> suggest(SuggestionInfo<S> info) throws CommandException;

  public static Suggestions<CommandSender> empty() {
    return _ -> List.of();
  }

  public static Suggestions<CommandSender> from(final String... strings) {
    final Collection<StringTooltip> suggestions = apply(strings);
    return info -> suggestions;
  }

  public static Suggestions<CommandSender> from(final Collection<String> strings) {
    final Collection<StringTooltip> suggestions = apply(strings);
    return info -> suggestions;
  }

  public static Suggestions<CommandSender> from(
      final Function<SuggestionInfo<CommandSender>, Collection<String>> suggestions) {
    return info -> apply(suggestions.apply(info));
  }

  public static Suggestions<CommandSender> fromAsync(
      final CompletableFuture<Collection<String>> suggestions) {
    return (info) -> {
      try {
        return apply(suggestions.get());
      } catch (final Exception e) {
        return List.of();
      }
    };
  }

  public static Suggestions<CommandSender> fromAsync(
      final Function<SuggestionInfo<CommandSender>, CompletableFuture<Collection<String>>> suggestions) {
    return (info) -> {
      try {
        return apply(suggestions.apply(info).get());
      } catch (final Exception e) {
        return List.of();
      }
    };
  }

  public static Suggestions<CommandSender> fromTooltip(final StringTooltip... suggestions) {
    return _ -> List.of(suggestions);
  }

  public static Suggestions<CommandSender> fromTooltip(final Collection<StringTooltip> suggestions) {
    return _ -> suggestions;
  }

  public static Suggestions<CommandSender> fromTooltip(
      final Function<SuggestionInfo<CommandSender>, Collection<StringTooltip>> suggestions) {
    return info -> suggestions.apply(info);
  }

  public static Suggestions<CommandSender> fromAsyncTooltip(
      final CompletableFuture<Collection<StringTooltip>> suggestions) {
    return (info) -> {
      try {
        return suggestions.get();
      } catch (final Exception e) {
        return List.of();
      }
    };
  }

  public static Suggestions<CommandSender> fromAsyncTooltip(
      final Function<SuggestionInfo<CommandSender>, CompletableFuture<Collection<StringTooltip>>> suggestions) {
    return (info) -> {
      try {
        return suggestions.apply(info).get();
      } catch (final Exception e) {
        return List.of();
      }
    };
  }

  public static Suggestions<CommandSender> fromProvider(
      final SuggestionProvider provider) {
    return info -> applyProvider(provider);
  }

  public static Suggestions<CommandSender> fromProvider(final SuggestionProvider... provider) {
    return info -> applyProvider(provider);
  }

  public static Suggestions<CommandSender> fromProvider(final Collection<SuggestionProvider> providers) {
    return info -> applyProvider(providers);
  }

  public static Suggestions<CommandSender> fromProviderAsync(
      final CompletableFuture<SuggestionProvider> provider) {
    return (info) -> {
      try {
        return applyProvider(provider.get());
      } catch (final Exception e) {
        return List.of();
      }
    };
  }

  private static Collection<StringTooltip> apply(final String[] strings) {
    final ArrayList<StringTooltip> suggestions = new ArrayList<>();
    for (final String string : strings) {
      if (string != null && string.length() != 0) {
        suggestions.add(StringTooltip.of(string));
      }
    }
    return suggestions;
  }

  private static Collection<StringTooltip> apply(final Collection<String> strings) {
    final ArrayList<StringTooltip> suggestions = new ArrayList<>();
    for (final String string : strings) {
      if (string != null && string.length() != 0) {
        suggestions.add(StringTooltip.of(string));
      }
    }
    return suggestions;
  }

  private static Collection<StringTooltip> applyProvider(final SuggestionProvider provider) {
    try {
      return List.of(StringTooltip.of(provider.getSuggestion(), provider.getTooltip()));
    } catch (final Exception e) {
      return List.of();
    }
  }

  private static Collection<StringTooltip> applyProvider(final SuggestionProvider[] providers) {
    final ArrayList<StringTooltip> suggestions = new ArrayList<>();
    for (final SuggestionProvider provider : providers) {
      if (provider == null) {
        continue;
      }
      try {
        final StringTooltip suggestion = StringTooltip.of(provider.getSuggestion(), provider.getTooltip());
        if (suggestion != null) {
          suggestions.add(suggestion);
        }
      } catch (final Exception e) {
        continue;
      }
    }
    return suggestions;
  }

  private static Collection<StringTooltip> applyProvider(final Collection<SuggestionProvider> providers) {
    return applyProvider(providers.toArray(SuggestionProvider[]::new));
  }
}
