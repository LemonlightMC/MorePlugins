package com.lemonlightmc.moreplugins.commands.arguments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import org.bukkit.ChatColor;

import com.lemonlightmc.moreplugins.commands.StringReader;
import com.lemonlightmc.moreplugins.commands.argumentsbase.Argument;
import com.lemonlightmc.moreplugins.commands.argumentsbase.ArgumentType;
import com.lemonlightmc.moreplugins.commands.argumentsbase.CommandArguments;
import com.lemonlightmc.moreplugins.commands.argumentsbase.GreedyArgument;
import com.lemonlightmc.moreplugins.commands.argumentsbase.Literal;
import com.lemonlightmc.moreplugins.commands.argumentsbase.MultiLiteral;
import com.lemonlightmc.moreplugins.commands.argumentsbase.StringType;
import com.lemonlightmc.moreplugins.commands.exceptions.BadLiteralException;
import com.lemonlightmc.moreplugins.commands.exceptions.CommandSyntaxException;

public class StringArguments {
  public static class StringArgument extends Argument<String, StringArgument> {
    private final StringType type;

    public StringArgument(final String name) {
      this(name, StringType.SINGLE_WORD);
    }

    public StringArgument(final String name, final StringType type) {
      super(name, String.class, ArgumentType.PRIMITIVE_STRING);
      this.type = type;
      if (type == null) {
        throw new IllegalArgumentException("String Type cant be null");
      }
    }

    public static StringArgument word(final String name) {
      return new StringArgument(name, StringType.SINGLE_WORD);
    }

    public static StringArgument string(final String name) {
      return new StringArgument(name, StringType.QUOTABLE_PHRASE);
    }

    public static StringArgument greedyString(final String name) {
      return new StringArgument(name, StringType.GREEDY_PHRASE);
    }

    public StringArgument getInstance() {
      return this;
    }

    public StringType getStringType() {
      return type;
    }

    @Override
    public String parseArgument(final String key, final StringReader reader, final CommandArguments previousArgs)
        throws CommandSyntaxException {
      if (type == StringType.GREEDY_PHRASE) {
        final String text = reader.getRemaining();
        reader.setCursor(reader.getTotalLength());
        return text;
      } else if (type == StringType.SINGLE_WORD) {
        return reader.readUnquotedString();
      } else {
        return reader.readString();
      }
    }

    public static String escapeIfRequired(final String input) {
      for (final char c : input.toCharArray()) {
        if (!StringReader.isUnquotedString(c)) {
          return escape(input);
        }
      }
      return input;
    }

    public static String escape(final String input) {
      final StringBuilder result = new StringBuilder("\"");
      final int len = input.length();
      for (int i = 0; i < len; i++) {
        final char c = input.charAt(i);
        if (c == '\\' || c == '"') {
          result.append('\\');
        }
        result.append(c);
      }
      return result.append("\"").toString();
    }

    @Override
    public int hashCode() {
      return 31 * super.hashCode() + type.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (!super.equals(obj) || getClass() != obj.getClass()) {
        return false;
      }
      final StringArgument other = (StringArgument) obj;
      return type == other.type;
    }

    @Override
    public String toString() {
      return "StringArgument [type=" + type + "]";
    }
  }

  public static class TextArgument extends Argument<String, TextArgument> {

    public TextArgument(final String name) {
      super(name, String.class, ArgumentType.PRIMITIVE_TEXT);
    }

    public TextArgument getInstance() {
      return this;
    }

    @Override
    public String parseArgument(final String key, final StringReader reader, final CommandArguments previousArgs)
        throws CommandSyntaxException {
      return reader.readString();
    }

    @Override
    public int hashCode() {
      return 31 + super.hashCode();
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
      return "TextArgument []";
    }
  }

  public static class GreedyStringArgument extends Argument<String, GreedyStringArgument> implements GreedyArgument {

    public GreedyStringArgument(final String name) {
      super(name, String.class, ArgumentType.PRIMITIVE_GREEDY_STRING);
    }

    public GreedyStringArgument getInstance() {
      return this;
    }

    @Override
    public String parseArgument(final String key, final StringReader reader, final CommandArguments previousArgs)
        throws CommandSyntaxException {
      final String text = reader.getRemaining();
      reader.setCursor(reader.getTotalLength());
      return text;
    }

    @Override
    public int hashCode() {
      return 31 + super.hashCode();
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
      return "GreedyStringArgument []";
    }
  }

  public static class ChatColorArgument extends Argument<ChatColor, ChatColorArgument> implements GreedyArgument {

    public ChatColorArgument(final String name) {
      super(name, ChatColor.class, ArgumentType.CHATCOLOR);
      final ArrayList<String> list = new ArrayList<>();
      for (final ChatColor color : ChatColor.values()) {
        list.add(color.name());
      }
      withSuggestions(list);
    }

    public ChatColorArgument getInstance() {
      return this;
    }

    @Override
    public ChatColor parseArgument(final String key, final StringReader reader, final CommandArguments previousArgs)
        throws CommandSyntaxException {
      return ChatColor.valueOf(reader.readUnquotedString());
    }

    @Override
    public int hashCode() {
      return 31 + super.hashCode();
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
      return "ChatColorArgument []";
    }
  }

  public static class ChatArgument extends Argument<String, ChatArgument> implements GreedyArgument {

    public ChatArgument(final String name) {
      super(name, String.class, ArgumentType.CHAT);
    }

    public ChatArgument getInstance() {
      return this;
    }

    @Override
    public String parseArgument(final String key, final StringReader reader, final CommandArguments previousArgs)
        throws CommandSyntaxException {
      final String text = reader.getRemaining();
      reader.setCursor(reader.getTotalLength());
      return text;
    }

    @Override
    public int hashCode() {
      return 31 + super.hashCode();
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
      return "ChatArgument []";
    }
  }

  public static class LiteralArgument extends Argument<String, LiteralArgument> implements Literal {

    private final String literal;

    public LiteralArgument(final String literal) {
      this(literal, literal);
    }

    public LiteralArgument(final String nodeName, final String literal) {
      super(nodeName, String.class, ArgumentType.LITERAL);

      if (literal == null) {
        throw new BadLiteralException(true);
      }
      if (literal.isEmpty()) {
        throw new BadLiteralException(false);
      }
      this.literal = literal;
      this.setListed(false);
    }

    public static LiteralArgument of(final String literal) {
      return new LiteralArgument(literal);
    }

    public static LiteralArgument of(final String nodeName, final String literal) {
      return new LiteralArgument(nodeName, literal);
    }

    public static LiteralArgument literal(final String literal) {
      return new LiteralArgument(literal);
    }

    public static LiteralArgument literal(final String nodeName, final String literal) {
      return new LiteralArgument(nodeName, literal);
    }

    @Override
    public LiteralArgument getInstance() {
      return this;
    }

    @Override
    public String getLiteral() {
      return literal;
    }

    @Override
    public String getHelpString() {
      return literal;
    }

    @Override
    public String parseArgument(final String key, final StringReader reader, final CommandArguments previousArgs)
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

  public static class MultiLiteralArgument extends Argument<String, MultiLiteralArgument> implements MultiLiteral {

    private final String[] literals;

    public MultiLiteralArgument(final String nodeName, final Set<String> literals) {
      this(nodeName, literals.toArray(String[]::new));
    }

    public MultiLiteralArgument(final String nodeName, final String... literals) {
      super(nodeName, String.class, ArgumentType.MULTI_LITERAL);

      if (literals == null) {
        throw new BadLiteralException(true);
      }
      if (literals.length == 0) {
        throw new BadLiteralException(false);
      }
      this.literals = literals;
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

    @Override
    public String[] getLiterals() {
      return literals;
    }

    @Override
    public String parseArgument(final String key, final StringReader reader, final CommandArguments previousArgs)
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
}
