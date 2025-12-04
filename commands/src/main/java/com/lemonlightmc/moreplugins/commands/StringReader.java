package com.lemonlightmc.moreplugins.commands;

import com.lemonlightmc.moreplugins.commands.exceptions.CommandExceptions;
import com.lemonlightmc.moreplugins.commands.exceptions.CommandSyntaxException;
import com.lemonlightmc.moreplugins.math.ranges.*;

public class StringReader {
  private static final char SYNTAX_ESCAPE = '\\';
  private static final char SYNTAX_DOUBLE_QUOTE = '"';
  private static final char SYNTAX_SINGLE_QUOTE = '\'';

  private final String string;
  private int cursor;
  private int start;

  public StringReader(final StringReader other) {
    this.string = other.string;
    this.cursor = other.cursor;
  }

  public StringReader(final String string) {
    this.string = string;
  }

  public static boolean isNumberStrict(final char c) {
    return c >= '0' && c <= '9';
  }

  public static boolean isNumber(final char c) {
    return c >= '0' && c <= '9' || c == '.' || c == '-';
  }

  public static boolean isAlphabet(final char c) {
    return c >= '0' && c <= '9'
        || c >= 'A' && c <= 'Z'
        || c >= 'a' && c <= 'z';
  }

  public static boolean isUnquotedString(final char c) {
    return c >= '0' && c <= '9'
        || c >= 'A' && c <= 'Z'
        || c >= 'a' && c <= 'z'
        || c == '_' || c == '-'
        || c == '.' || c == '+';
  }

  public static boolean isQuote(final char c) {
    return c == SYNTAX_DOUBLE_QUOTE || c == SYNTAX_SINGLE_QUOTE;
  }

  public String getString() {
    return string;
  }

  public void setCursor(final int cursor) {
    this.cursor = cursor;
  }

  public int getCursor() {
    return cursor;
  }

  public void setStart() {
    this.start = cursor;
  }

  public void setStart(final int start) {
    this.start = start;
  }

  public void resetCursor() {
    this.cursor = start;
  }

  public int getStart() {
    return start;
  }

  public int getRemainingLength() {
    return string.length() - cursor;
  }

  public int getTotalLength() {
    return string.length();
  }

  public String getRead() {
    return string.substring(0, cursor);
  }

  public String getRemaining() {
    return string.substring(cursor);
  }

  public boolean canRead(final int length) {
    return cursor + length <= string.length();
  }

  public boolean canRead() {
    return canRead(1);
  }

  public char peek() {
    return string.charAt(cursor);
  }

  public char peek(final int offset) {
    return string.charAt(cursor + offset);
  }

  public char read() {
    return string.charAt(cursor++);
  }

  public void skip() {
    cursor++;
  }

  public void skipWhitespace() {
    while (canRead() && Character.isWhitespace(peek())) {
      skip();
    }
  }

  public int readInt() throws CommandSyntaxException {
    final int start = cursor;
    while (canRead() && isNumber(peek())) {
      skip();
    }
    final String number = string.substring(start, cursor);
    if (number.isEmpty()) {
      throw CommandExceptions.readerExpectedInt().createWithContext(this);
    }
    try {
      return Integer.parseInt(number);
    } catch (final NumberFormatException ex) {
      cursor = start;
      throw CommandExceptions.readerInvalidInt().createWithContext(this, number);
    }
  }

  public long readLong() throws CommandSyntaxException {
    final int start = cursor;
    while (canRead() && isNumber(peek())) {
      skip();
    }
    final String number = string.substring(start, cursor);
    if (number.isEmpty()) {
      throw CommandExceptions.readerExpectedLong().createWithContext(this);
    }
    try {
      return Long.parseLong(number);
    } catch (final NumberFormatException ex) {
      cursor = start;
      throw CommandExceptions.readerInvalidLong().createWithContext(this, number);
    }
  }

  public double readDouble() throws CommandSyntaxException {
    final int start = cursor;
    while (canRead() && isNumber(peek())) {
      skip();
    }
    final String number = string.substring(start, cursor);
    if (number.isEmpty()) {
      throw CommandExceptions.readerExpectedDouble().createWithContext(this);
    }
    try {
      return Double.parseDouble(number);
    } catch (final NumberFormatException ex) {
      cursor = start;
      throw CommandExceptions.readerInvalidDouble().createWithContext(this, number);
    }
  }

  public float readFloat() throws CommandSyntaxException {
    final int start = cursor;
    while (canRead() && isNumber(peek())) {
      skip();
    }
    final String number = string.substring(start, cursor);
    if (number.isEmpty()) {
      throw CommandExceptions.readerExpectedFloat().createWithContext(this);
    }
    try {
      return Float.parseFloat(number);
    } catch (final NumberFormatException ex) {
      cursor = start;
      throw CommandExceptions.readerInvalidFloat().createWithContext(this, number);
    }
  }

  private String readRange() throws CommandSyntaxException {
    while (canRead() && isNumber(peek())) {
      skip();
    }
    final String number = string.substring(start, cursor);
    if (number.isEmpty()) {
      throw CommandExceptions.readerExpectedRange().createWithContext(this);
    }
    return number;
  }

  public IntegerRange readIntRange() throws CommandSyntaxException {
    setStart();
    String str = null;
    try {
      str = readRange();
      return IntegerRange.of(str);
    } catch (final CommandSyntaxException ex) {
      resetCursor();
      throw ex;
    } catch (final Exception ex) {
      resetCursor();
      throw CommandExceptions.readerInvalidRange().createWithContext(this, str);
    }
  }

  public LongRange readLongRange() throws CommandSyntaxException {
    setStart();
    String str = null;
    try {
      str = readRange();
      return LongRange.of(str);
    } catch (final CommandSyntaxException ex) {
      resetCursor();
      throw ex;
    } catch (final Exception ex) {
      resetCursor();
      throw CommandExceptions.readerInvalidRange().createWithContext(this, str);
    }
  }

  public FloatRange readFloatRange() throws CommandSyntaxException {
    setStart();
    String str = null;
    try {
      str = readRange();
      return FloatRange.of(str);
    } catch (final CommandSyntaxException ex) {
      resetCursor();
      throw ex;
    } catch (final Exception ex) {
      resetCursor();
      throw CommandExceptions.readerInvalidRange().createWithContext(this, str);
    }
  }

  public DoubleRange readDoubleRange() throws CommandSyntaxException {
    setStart();
    String str = null;
    try {
      str = readRange();
      return DoubleRange.of(str);
    } catch (final CommandSyntaxException ex) {
      resetCursor();
      throw ex;
    } catch (final Exception ex) {
      resetCursor();
      throw CommandExceptions.readerInvalidRange().createWithContext(this, str);
    }
  }

  public String readUnquotedString() {
    final int start = cursor;
    while (canRead() && isUnquotedString(peek())) {
      skip();
    }
    return string.substring(start, cursor);
  }

  public String readQuotedString() throws CommandSyntaxException {
    if (!canRead()) {
      return "";
    }
    final char next = peek();
    if (!isQuote(next)) {
      throw CommandExceptions.readerExpectedStartOfQuote().createWithContext(this);
    }
    skip();
    return readStringUntil(next);
  }

  public String readStringUntil(final char terminator) throws CommandSyntaxException {
    final StringBuilder result = new StringBuilder();
    boolean escaped = false;
    while (canRead()) {
      final char c = read();
      if (escaped) {
        if (c == terminator || c == SYNTAX_ESCAPE) {
          result.append(c);
          escaped = false;
        } else {
          setCursor(getCursor() - 1);
          throw CommandExceptions.readerInvalidEscape().createWithContext(this,
              String.valueOf(c));
        }
      } else if (c == SYNTAX_ESCAPE) {
        escaped = true;
      } else if (c == terminator) {
        return result.toString();
      } else {
        result.append(c);
      }
    }

    throw CommandExceptions.readerExpectedEndOfQuote().createWithContext(this);
  }

  public String readString() throws CommandSyntaxException {
    if (!canRead()) {
      return "";
    }
    final char next = peek();
    if (isQuote(next)) {
      skip();
      return readStringUntil(next);
    }
    return readUnquotedString();
  }

  public boolean readBoolean() throws CommandSyntaxException {
    final int start = cursor;
    final String value = readString();
    if (value.isEmpty()) {
      throw CommandExceptions.readerExpectedBool().createWithContext(this);
    }

    if (value.equals("true")) {
      return true;
    } else if (value.equals("false")) {
      return false;
    } else {
      cursor = start;
      throw CommandExceptions.readerInvalidBool().createWithContext(this, value);
    }
  }

  public void expect(final char c) throws CommandSyntaxException {
    if (!canRead() || peek() != c) {
      throw CommandExceptions.readerExpectedSymbol().createWithContext(this,
          String.valueOf(c));
    }
    skip();
  }
}
