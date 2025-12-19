package com.lemonlightmc.moreplugins.commands.arguments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.lemonlightmc.moreplugins.commands.CommandManager;
import com.lemonlightmc.moreplugins.commands.CommandSource;
import com.lemonlightmc.moreplugins.commands.argumentsbase.Argument;
import com.lemonlightmc.moreplugins.commands.argumentsbase.ArgumentType;
import com.lemonlightmc.moreplugins.commands.argumentsbase.CommandArguments;
import com.lemonlightmc.moreplugins.commands.argumentsbase.CommandResult;
import com.lemonlightmc.moreplugins.commands.argumentsbase.StringReader;
import com.lemonlightmc.moreplugins.commands.exceptions.CommandSyntaxException.*;
import com.lemonlightmc.moreplugins.commands.exceptions.CommandSyntaxException;
import com.lemonlightmc.moreplugins.commands.exceptions.InvalidArgumentBranchException;
import com.lemonlightmc.moreplugins.commands.suggestions.SuggestionInfo;
import com.lemonlightmc.moreplugins.exceptions.DynamicExceptionFunction.Dynamic1ExceptionFunktion;

public class SpecialArguments {

  public static record ArgumentBranch(List<Argument<?, ?>> args, ArgumentBranchType type) {
  }

  public static enum ArgumentBranchType {
    ONCE(),
    LOOPING(),
    END();
  }

  public interface LiteralArgumentType {

  }

  public class BranchArgument extends Argument<Boolean, BranchArgument> {

    private final List<ArgumentBranch> branches;

    public BranchArgument(final String name) {
      super(name, null, ArgumentType.BRANCH);
      branches = new ArrayList<>();
    }

    public BranchArgument getInstance() {
      return this;
    }

    public List<ArgumentBranch> getBranches() {
      return branches;
    }

    public BranchArgument clear() {
      branches.clear();
      return this;
    }

    public BranchArgument branch(final Argument<?, ?>... args) {
      createBranch(args, ArgumentBranchType.LOOPING);
      return this;
    }

    public BranchArgument branch(final ArgumentBranchType type, final Argument<?, ?>... args) {
      createBranch(args, type);
      return this;
    }

    public BranchArgument loopingBranch(final Argument<?, ?>... args) {
      createBranch(args, ArgumentBranchType.LOOPING);
      return this;
    }

    public BranchArgument onceBranch(final Argument<?, ?>... args) {
      createBranch(args, ArgumentBranchType.ONCE);
      return this;
    }

    public BranchArgument endBranch(final Argument<?, ?>... args) {
      createBranch(args, ArgumentBranchType.END);
      return this;
    }

    private void createBranch(final Argument<?, ?>[] args, final ArgumentBranchType type) {
      if (args == null || args.length == 0 || args[0] == null) {
        throw new IllegalArgumentException("Supplied Arguments to Branch cant be empty");
      }
      if (type == null) {
        throw new IllegalArgumentException("Invalid Argument Branch Type: " + type);
      }
      if (args[0].getArgumentType().equals(ArgumentType.LITERAL)) {
        throw new InvalidArgumentBranchException("invalid literal", args[0].getName());
      }
      branches.add(new ArgumentBranch(List.of(args), type));
    }

    @Override
    public Boolean parseArgument(final CommandSource<CommandSender> source, final StringReader reader, final String key,
        final CommandArguments previousArgs)
        throws CommandSyntaxException {
      reader.point();
      try {
        return reader.readBoolean();
      } catch (final CommandSyntaxException e) {
        reader.resetCursor();
        throw e;
      } catch (final Exception e) {
        reader.resetCursor();
        throw e;
      }
    }

    @Override
    public int hashCode() {
      return 31 * super.hashCode() + branches.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
      return this == obj && getClass() == obj.getClass() && super.equals(obj)
          && branches.equals(((BranchArgument) obj).branches);
    }

    @Override
    public String toString() {
      return "BranchArgument [branches=" + branches + "]";
    }
  }

  public static class CommandArgument extends Argument<CommandResult, CommandArgument> {
    private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_COMMAND = new DynamicCommandException<Dynamic1ExceptionFunktion>(
        value -> "Invalid Command '" + value);

    public CommandArgument(final String name) {
      super(name, CommandResult.class, ArgumentType.COMMAND);
      withSuggestions((final SuggestionInfo<CommandSender> info) -> {
        final String[] args = info.currentInput().split(" ");
        if (args.length == 0) {
          return List.of();
        }
        final Command cmd = CommandManager.getCommandMap().getCommand(args[0]);
        return cmd == null ? List.of() : cmd.tabComplete(info.source().sender(), cmd.getLabel(), args);
      });
    }

    public CommandArgument getInstance() {
      return this;
    }

    @Override
    public CommandResult parseArgument(final CommandSource<CommandSender> source, final StringReader reader,
        final String key,
        final CommandArguments previousArgs)
        throws CommandSyntaxException {
      reader.point();
      try {
        final String[] args = reader.getRemaining().split(" ");
        if (args.length == 0) {
          return null;
        }
        final Command cmd = CommandManager.getCommandMap().getCommand(args[0]);
        if (cmd == null) {
          throw INVALID_COMMAND.createWithContext(reader, cmd);
        }
        return new CommandResult(cmd, Arrays.copyOfRange(args, 1, args.length));
      } catch (final CommandSyntaxException e) {
        reader.resetCursor();
        throw e;
      } catch (final Exception e) {
        reader.resetCursor();
        throw e;
      }
    }

    @Override
    public boolean equals(final Object obj) {
      return this == obj && getClass() == obj.getClass() && super.equals(obj);
    }

    @Override
    public String toString() {
      return "CommandArgument []";
    }
  }

  public static class LiteralArgument extends Argument<String, LiteralArgument> implements LiteralArgumentType {

    private final String literal;

    public LiteralArgument(final String literal) {
      this(literal, literal);
    }

    public LiteralArgument(final String nodeName, final String literal) {
      super(nodeName, String.class, ArgumentType.LITERAL);

      if (literal == null || literal.isBlank()) {
        throw new IllegalArgumentException("The literal cant be empty");
      }
      this.literal = literal;
      this.setListed(false);
      withSuggestions(literal);
    }

    public static LiteralArgument of(final String literal) {
      return new LiteralArgument(literal);
    }

    public static LiteralArgument of(final String nodeName, final String literal) {
      return new LiteralArgument(nodeName, literal);
    }

    @Override
    public LiteralArgument getInstance() {
      return this;
    }

    public String getLiteral() {
      return literal;
    }

    @Override
    public String getHelpString() {
      return literal;
    }

    @Override
    public String parseArgument(final CommandSource<CommandSender> source, final StringReader reader, final String key,
        final CommandArguments previousArgs)
        throws CommandSyntaxException {
      return literal;
    }

    @Override
    public int hashCode() {
      return 31 * super.hashCode() + literal.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (!super.equals(obj) || getClass() != obj.getClass()) {
        return false;
      }
      final LiteralArgument other = (LiteralArgument) obj;
      if (literal == null && other.literal != null) {
        return false;
      }
      return literal.equals(other.literal);
    }

    @Override
    public String toString() {
      return "LiteralArgument [literal=" + literal + "]";
    }
  }

  public static class MultiLiteralArgument extends Argument<String, MultiLiteralArgument>
      implements LiteralArgumentType {

    private final String[] literals;

    public MultiLiteralArgument(final String nodeName, final Set<String> literals) {
      this(nodeName, literals.toArray(String[]::new));
    }

    public MultiLiteralArgument(final String nodeName, final String... literals) {
      super(nodeName, String.class, ArgumentType.MULTI_LITERAL);

      if (literals == null || literals.length == 0) {
        throw new IllegalArgumentException("The literals cant be empty");
      }
      this.literals = literals;
      withSuggestions(literals);
    }

    public static MultiLiteralArgument of(final String literal) {
      return new MultiLiteralArgument(literal);
    }

    public static MultiLiteralArgument of(final String nodeName, final String literal) {
      return new MultiLiteralArgument(nodeName, literal);
    }

    public static MultiLiteralArgument literal(final String literal) {
      return new MultiLiteralArgument(literal);
    }

    public static MultiLiteralArgument literal(final String nodeName, final String literal) {
      return new MultiLiteralArgument(nodeName, literal);
    }

    @Override
    public MultiLiteralArgument getInstance() {
      return this;
    }

    public String[] getLiterals() {
      return literals;
    }

    @Override
    public String parseArgument(final CommandSource<CommandSender> source, final StringReader reader, final String key,
        final CommandArguments previousArgs)
        throws CommandSyntaxException {
      throw new UnsupportedOperationException("Cant parse MultiLiteral");
    }

    @Override
    public int hashCode() {
      return 31 * super.hashCode() + Arrays.hashCode(literals);
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (!super.equals(obj) || getClass() != obj.getClass()) {
        return false;
      }
      final MultiLiteralArgument other = (MultiLiteralArgument) obj;
      return Arrays.equals(literals, other.literals);
    }

    @Override
    public String toString() {
      return "MultiLiteralArgument [literals=" + Arrays.toString(literals) + "]";
    }
  }

  public static class DynamicMultiLiteralArgument extends Argument<String, DynamicMultiLiteralArgument>
      implements LiteralArgumentType {

    private final Function<CommandSource<CommandSender>, Collection<String>> literals;

    public DynamicMultiLiteralArgument(final String nodeName,
        final Function<CommandSource<CommandSender>, Collection<String>> literals) {
      super(nodeName, String.class, ArgumentType.MULTI_LITERAL);

      if (literals == null) {
        throw new IllegalArgumentException("The literals Supplier cant be empty");
      }
      this.literals = literals;
      withSuggestions((final SuggestionInfo<CommandSender> info) -> {
        return literals.apply(info.source());
      });
    }

    public static DynamicMultiLiteralArgument of(final String nodeName,
        final Function<CommandSource<CommandSender>, Collection<String>> literal) {
      return new DynamicMultiLiteralArgument(nodeName, literal);
    }

    @Override
    public DynamicMultiLiteralArgument getInstance() {
      return this;
    }

    @Override
    public String parseArgument(final CommandSource<CommandSender> source, final StringReader reader, final String key,
        final CommandArguments previousArgs)
        throws CommandSyntaxException {
      throw new UnsupportedOperationException("Cant parse MultiLiteral");
    }

    @Override
    public int hashCode() {
      return 31 * super.hashCode() + literals.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (!super.equals(obj) || getClass() != obj.getClass()) {
        return false;
      }
      final DynamicMultiLiteralArgument other = (DynamicMultiLiteralArgument) obj;
      return literals.equals(other.literals);
    }

    @Override
    public String toString() {
      return "DynamicMultiLiteralArgument [literals=" + literals + "]";
    }

  }
}
