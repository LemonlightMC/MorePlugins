package com.lemonlightmc.moreplugins.commands.arguments;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Team;

import com.lemonlightmc.moreplugins.commands.CommandSource;
import com.lemonlightmc.moreplugins.commands.argumentsbase.Argument;
import com.lemonlightmc.moreplugins.commands.argumentsbase.ArgumentType;
import com.lemonlightmc.moreplugins.commands.argumentsbase.CommandArguments;
import com.lemonlightmc.moreplugins.commands.argumentsbase.ScoreboardSlot;
import com.lemonlightmc.moreplugins.commands.argumentsbase.StringReader;
import com.lemonlightmc.moreplugins.commands.exceptions.CommandExceptions.DynamicCommandException;
import com.lemonlightmc.moreplugins.commands.suggestions.Suggestions;
import com.lemonlightmc.moreplugins.commands.exceptions.CommandSyntaxException;
import com.lemonlightmc.moreplugins.exceptions.DynamicExceptionFunction.Dynamic1ExceptionFunktion;

public class ScoreArguments {

  public static class TeamArgument extends Argument<Team, TeamArgument> {
    private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_TEAM = new DynamicCommandException<Dynamic1ExceptionFunktion>(
        value -> "Invalid Team '" + value + "'");

    public TeamArgument(final String name) {
      super(name, Team.class, ArgumentType.TEAM);
      withSuggestions(Suggestions.from((info) -> Bukkit.getScoreboardManager().getMainScoreboard().getTeams().stream()
          .map((v) -> v.getName()).toList()));
    }

    public TeamArgument getInstance() {
      return this;
    }

    @Override
    public Team parseArgument(final CommandSource<CommandSender> source, final StringReader reader, final String key,
        final CommandArguments previousArgs)
        throws CommandSyntaxException {
      reader.point();
      String value = null;
      try {
        value = reader.readString();
        return Objects.requireNonNull(Bukkit.getScoreboardManager().getMainScoreboard().getTeam(value));
      } catch (final CommandSyntaxException ex) {
        reader.resetCursor();
        throw ex;
      } catch (final Exception e) {
        reader.resetCursor();
        throw INVALID_TEAM.createWithContext(reader, value);
      }
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (!super.equals(obj) || getClass() != obj.getClass()) {
        return false;
      }
      return true;
    }

    @Override
    public String toString() {
      return "TeamArgument []";
    }
  }

  public static class ScoreboardSlotArgument extends Argument<ScoreboardSlot, ScoreboardSlotArgument> {
    private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_SLOT = new DynamicCommandException<Dynamic1ExceptionFunktion>(
        value -> "Invalid Scoreboard Slot '" + value + "'");

    public ScoreboardSlotArgument(final String name) {
      super(name, ScoreboardSlot.class, ArgumentType.SCOREBOARD_SLOT);
      withSuggestions(ScoreboardSlot.keys());
    }

    public ScoreboardSlotArgument getInstance() {
      return this;
    }

    @Override
    public ScoreboardSlot parseArgument(final CommandSource<CommandSender> source, final StringReader reader,
        final String key,
        final CommandArguments previousArgs)
        throws CommandSyntaxException {
      reader.point();
      String value = null;
      try {
        value = reader.readString();
        return Objects.requireNonNull(ScoreboardSlot.of(value));
      } catch (final CommandSyntaxException ex) {
        reader.resetCursor();
        throw ex;
      } catch (final Exception e) {
        reader.resetCursor();
        throw INVALID_SLOT.createWithContext(reader, value);
      }
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (!super.equals(obj) || getClass() != obj.getClass()) {
        return false;
      }
      return true;
    }

    @Override
    public String toString() {
      return "ScoreboardSlotArgument []";
    }
  }

  public static class ObjectiveArgument extends Argument<Objective, ObjectiveArgument> {
    private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_OBJECTIVE = new DynamicCommandException<Dynamic1ExceptionFunktion>(
        value -> "Invalid Scoreboard Objective '" + value + "'");

    public ObjectiveArgument(final String name) {
      super(name, Objective.class, ArgumentType.OBJECTIVE);
      withSuggestions(
          Suggestions.from((info) -> Bukkit.getScoreboardManager().getMainScoreboard().getObjectives().stream()
              .map((v) -> v.getName()).toList()));
    }

    public ObjectiveArgument getInstance() {
      return this;
    }

    @Override
    public Objective parseArgument(final CommandSource<CommandSender> source, final StringReader reader,
        final String key,
        final CommandArguments previousArgs)
        throws CommandSyntaxException {
      reader.point();
      String value = null;
      try {
        value = reader.readString();
        Objective objective = Bukkit.getScoreboardManager().getMainScoreboard().getObjective(value);
        if (objective != null) {
          return objective;
        }
        final ScoreboardSlot slot = ScoreboardSlot.of(value);
        if (slot != null) {
          final DisplaySlot slot2 = slot.getDisplaySlot();
          if (slot2 != null) {
            objective = Bukkit.getScoreboardManager().getMainScoreboard().getObjective(slot2);
            if (objective != null) {
              return objective;
            }
          }
        }
        throw INVALID_OBJECTIVE.createWithContext(reader, value);
      } catch (final CommandSyntaxException ex) {
        reader.resetCursor();
        throw ex;
      } catch (final Exception e) {
        reader.resetCursor();
        throw INVALID_OBJECTIVE.createWithContext(reader, value);
      }
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (!super.equals(obj) || getClass() != obj.getClass()) {
        return false;
      }
      return true;
    }

    @Override
    public String toString() {
      return "ObjectiveArgument []";
    }
  }

  public static class CriteriaArgument extends Argument<Criteria, CriteriaArgument> {
    private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_CRITERIA = new DynamicCommandException<Dynamic1ExceptionFunktion>(
        value -> "Invalid Scoreboard Objective '" + value + "'");

    public CriteriaArgument(final String name) {
      super(name, Criteria.class, ArgumentType.CRITERIA);
      withSuggestions(
          Suggestions.from((info) -> Bukkit.getScoreboardManager().getMainScoreboard().getObjectives().stream()
              .map((v) -> v.getTrackedCriteria().getName()).toList()));
    }

    public CriteriaArgument getInstance() {
      return this;
    }

    @Override
    public Criteria parseArgument(final CommandSource<CommandSender> source, final StringReader reader,
        final String key,
        final CommandArguments previousArgs)
        throws CommandSyntaxException {
      reader.point();
      String value = null;
      try {
        value = reader.readString();
        return Objects.requireNonNull(Criteria.create(value));
      } catch (final CommandSyntaxException ex) {
        reader.resetCursor();
        throw ex;
      } catch (final Exception e) {
        reader.resetCursor();
        throw INVALID_CRITERIA.createWithContext(reader, value);
      }
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (!super.equals(obj) || getClass() != obj.getClass()) {
        return false;
      }
      return true;
    }

    @Override
    public String toString() {
      return "CriteriaArgument []";
    }
  }
}
