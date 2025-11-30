package com.lemonlightmc.moreplugins.commands.suggestions;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.ArrayList;
import java.util.function.Function;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

public interface Suggestions<S> {

  abstract List<SuggestionRecord> suggest(SuggestionInfo<S> info) throws CommandException;

  public static Suggestions<CommandSender> empty() {
    return _ -> List.of();
  }

  public static Suggestions<CommandSender> from(final String... suggestions) {
    return _ -> apply(suggestions);
  }

  public static Suggestions<CommandSender> from(final Collection<String> suggestions) {
    return _ -> apply(suggestions);
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

  public static Suggestions<CommandSender> fromTooltip(final SuggestionTooltip... suggestions) {
    return _ -> applyTooltip(suggestions);
  }

  public static Suggestions<CommandSender> fromTooltip(final Collection<SuggestionTooltip> suggestions) {
    return _ -> applyTooltip(suggestions);
  }

  public static Suggestions<CommandSender> fromTooltip(
      final Function<SuggestionInfo<CommandSender>, Collection<SuggestionTooltip>> suggestions) {
    return info -> applyTooltip(suggestions.apply(info));
  }

  public static Suggestions<CommandSender> fromAsyncTooltip(
      final CompletableFuture<Collection<SuggestionTooltip>> suggestions) {
    return (info) -> {
      try {
        return applyTooltip(suggestions.get());
      } catch (final Exception e) {
        return List.of();
      }
    };
  }

  public static Suggestions<CommandSender> fromAsyncTooltip(
      final Function<SuggestionInfo<CommandSender>, CompletableFuture<Collection<SuggestionTooltip>>> suggestions) {
    return (info) -> {
      try {
        return applyTooltip(suggestions.apply(info).get());
      } catch (final Exception e) {
        return List.of();
      }
    };
  }

  private static List<SuggestionRecord> apply(final String[] strings) {
    final ArrayList<SuggestionRecord> suggestions = new ArrayList<>();
    for (final String string : strings) {
      if (string != null && string.length() != 0) {
        suggestions.add(new SuggestionRecord(string));
      }
    }
    return suggestions;
  }

  private static List<SuggestionRecord> apply(final Collection<String> strings) {
    final ArrayList<SuggestionRecord> suggestions = new ArrayList<>();
    for (final String string : strings) {
      if (string != null && string.length() != 0) {
        suggestions.add(new SuggestionRecord(string));
      }
    }
    return suggestions;
  }

  private static List<SuggestionRecord> applyTooltip(final SuggestionTooltip[] strings) {
    final ArrayList<SuggestionRecord> suggestions = new ArrayList<>();
    for (final SuggestionTooltip string : strings) {
      if (string != null && string.getSuggestion() != null && string.getSuggestion().length() != 0) {
        suggestions.add(new SuggestionRecord(string.getSuggestion(), string.getTooltip()));
      }
    }
    return suggestions;
  }

  private static List<SuggestionRecord> applyTooltip(final Collection<SuggestionTooltip> strings) {
    final ArrayList<SuggestionRecord> suggestions = new ArrayList<>();
    for (final SuggestionTooltip string : strings) {
      if (string != null && string.getSuggestion() != null && string.getSuggestion().length() != 0) {
        suggestions.add(new SuggestionRecord(string.getSuggestion(), string.getTooltip()));
      }
    }
    return suggestions;
  }

  public static class SuggestionRecord {
    private final String text;
    private final String tooltip;

    public SuggestionRecord(final String text, final String tooltip) {
      this.text = text;
      this.tooltip = tooltip;
    }

    public SuggestionRecord(final String text) {
      this.text = text;
      this.tooltip = null;
    }

    public String getText() {
      return text;
    }

    public String getTooltip() {
      return tooltip;
    }

    @Override
    public int hashCode() {
      int result = 31 + ((text == null) ? 0 : text.hashCode());
      result = 31 * result + ((tooltip == null) ? 0 : tooltip.hashCode());
      return result;
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null || getClass() != obj.getClass()) {
        return false;
      }
      final SuggestionRecord other = (SuggestionRecord) obj;
      if (text == null) {
        if (other.text != null)
          return false;
      } else if (!text.equals(other.text))
        return false;
      if (tooltip == null) {
        if (other.tooltip != null)
          return false;
      } else if (!tooltip.equals(other.tooltip))
        return false;
      return true;
    }

    @Override
    public String toString() {
      return "SuggestionRecord [text=" + text + ", tooltip=" + tooltip + "]";
    }
  }

}
