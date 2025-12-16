package com.lemonlightmc.moreplugins.commands.exceptions;

import com.lemonlightmc.moreplugins.commands.argumentsbase.StringReader;
import com.lemonlightmc.moreplugins.exceptions.*;
import com.lemonlightmc.moreplugins.exceptions.DynamicExceptionFunction.*;

public class CommandExceptions {
  public static class DynamicCommandException<T extends DynamicExceptionFunction>
      extends
      DynamicExceptionType<CommandSyntaxException, T, StringReader> {

    public DynamicCommandException(final T function) {
      super(CommandSyntaxException.class, function);
    }
  }

  public static class SimpleCommandException
      extends SimpleExceptionType<CommandSyntaxException, StringReader> {

    public SimpleCommandException(final String message) {
      super(CommandSyntaxException.class, message);
    }
  }

  private static final SimpleCommandException READER_EXPECTED_START_OF_QUOTE = new SimpleCommandException(
      "Expected quote to start a string");

  private static final SimpleCommandException READER_EXPECTED_END_OF_QUOTE = new SimpleCommandException(
      "Unclosed quoted string");

  private static final DynamicCommandException<Dynamic1ExceptionFunktion> READER_INVALID_ESCAPE = new DynamicCommandException<Dynamic1ExceptionFunktion>(
      character -> "Invalid escape sequence '" + character + "' in quoted string");

  private static final DynamicCommandException<Dynamic1ExceptionFunktion> READER_INVALID_BOOL = new DynamicCommandException<Dynamic1ExceptionFunktion>(
      value -> "Invalid bool, expected true or false but found '" + value + "'");

  private static final DynamicCommandException<Dynamic1ExceptionFunktion> READER_INVALID_INT = new DynamicCommandException<Dynamic1ExceptionFunktion>(
      value -> "Invalid integer '" + value + "'");

  private static final SimpleCommandException READER_EXPECTED_INT = new SimpleCommandException(
      "Expected integer");

  private static final DynamicCommandException<Dynamic1ExceptionFunktion> READER_INVALID_LONG = new DynamicCommandException<Dynamic1ExceptionFunktion>(
      value -> "Invalid long '" + value + "'");

  private static final SimpleCommandException READER_EXPECTED_LONG = new SimpleCommandException(
      ("Expected long"));

  private static final DynamicCommandException<Dynamic1ExceptionFunktion> READER_INVALID_DOUBLE = new DynamicCommandException<Dynamic1ExceptionFunktion>(
      value -> "Invalid double '" + value + "'");

  private static final SimpleCommandException READER_EXPECTED_DOUBLE = new SimpleCommandException(
      "Expected double");

  private static final DynamicCommandException<Dynamic1ExceptionFunktion> READER_INVALID_FLOAT = new DynamicCommandException<Dynamic1ExceptionFunktion>(
      value -> "Invalid float '" + value + "'");

  private static final SimpleCommandException READER_EXPECTED_FLOAT = new SimpleCommandException(
      "Expected float");

  private static final SimpleCommandException READER_EXPECTED_BOOL = new SimpleCommandException(
      "Expected bool");

  private static final SimpleCommandException READER_EXPECTED_RANGE = new SimpleCommandException(
      "Expected Range");

  private static final DynamicCommandException<Dynamic1ExceptionFunktion> READER_EXPECTED_SYMBOL = new DynamicCommandException<Dynamic1ExceptionFunktion>(
      symbol -> "Expected '" + symbol + "'");

  public static SimpleCommandException readerExpectedStartOfQuote() {
    return READER_EXPECTED_START_OF_QUOTE;
  }

  public static SimpleCommandException readerExpectedEndOfQuote() {
    return READER_EXPECTED_END_OF_QUOTE;
  }

  public static DynamicCommandException<Dynamic1ExceptionFunktion> readerInvalidEscape() {
    return READER_INVALID_ESCAPE;
  }

  public static DynamicCommandException<Dynamic1ExceptionFunktion> readerInvalidBool() {
    return READER_INVALID_BOOL;
  }

  public static DynamicCommandException<Dynamic1ExceptionFunktion> readerInvalidInt() {
    return READER_INVALID_INT;
  }

  public static SimpleCommandException readerExpectedInt() {
    return READER_EXPECTED_INT;
  }

  public static DynamicCommandException<Dynamic1ExceptionFunktion> readerInvalidLong() {
    return READER_INVALID_LONG;
  }

  public static SimpleCommandException readerExpectedLong() {
    return READER_EXPECTED_LONG;
  }

  public static DynamicCommandException<Dynamic1ExceptionFunktion> readerInvalidDouble() {
    return READER_INVALID_DOUBLE;
  }

  public static SimpleCommandException readerExpectedDouble() {
    return READER_EXPECTED_DOUBLE;
  }

  public static DynamicCommandException<Dynamic1ExceptionFunktion> readerInvalidFloat() {
    return READER_INVALID_FLOAT;
  }

  public static SimpleCommandException readerExpectedFloat() {
    return READER_EXPECTED_FLOAT;
  }

  public static SimpleCommandException readerExpectedBool() {
    return READER_EXPECTED_BOOL;
  }

  public static SimpleCommandException readerExpectedRange() {
    return READER_EXPECTED_RANGE;
  }

  public static DynamicCommandException<Dynamic1ExceptionFunktion> readerExpectedSymbol() {
    return READER_EXPECTED_SYMBOL;
  }
}
