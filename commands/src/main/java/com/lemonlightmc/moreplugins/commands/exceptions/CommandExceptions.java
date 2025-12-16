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

  private static final DynamicCommandException<Dynamic2ExceptionFunktion> DOUBLE_TOO_SMALL = new DynamicCommandException<Dynamic2ExceptionFunktion>(
      (final Object found, final Object min) -> "Double must not be less than " + min + ", found " + found);

  private static final DynamicCommandException<Dynamic2ExceptionFunktion> DOUBLE_TOO_BIG = new DynamicCommandException<Dynamic2ExceptionFunktion>(
      (found, max) -> "Double must not be more than " + max + ", found " + found);

  private static final DynamicCommandException<Dynamic2ExceptionFunktion> FLOAT_TOO_SMALL = new DynamicCommandException<Dynamic2ExceptionFunktion>(
      (found, min) -> "Float must not be less than " + min + ", found " + found);

  private static final DynamicCommandException<Dynamic2ExceptionFunktion> FLOAT_TOO_BIG = new DynamicCommandException<Dynamic2ExceptionFunktion>(
      (found, max) -> "Float must not be more than " + max + ", found " + found);

  private static final DynamicCommandException<Dynamic2ExceptionFunktion> INTEGER_TOO_SMALL = new DynamicCommandException<Dynamic2ExceptionFunktion>(
      (found, min) -> "Integer must not be less than " + min + ", found " + found);

  private static final DynamicCommandException<Dynamic2ExceptionFunktion> INTEGER_TOO_BIG = new DynamicCommandException<Dynamic2ExceptionFunktion>(
      (found, max) -> "Integer must not be more than " + max + ", found " + found);

  private static final DynamicCommandException<Dynamic2ExceptionFunktion> LONG_TOO_SMALL = new DynamicCommandException<Dynamic2ExceptionFunktion>(
      (found, min) -> "Long must not be less than " + min + ", found " + found);

  private static final DynamicCommandException<Dynamic2ExceptionFunktion> LONG_TOO_BIG = new DynamicCommandException<Dynamic2ExceptionFunktion>(
      (found, max) -> "Long must not be more than " + max + ", found " + found);

  private static final DynamicCommandException<Dynamic2ExceptionFunktion> LOCATION_TOO_BIG = new DynamicCommandException<Dynamic2ExceptionFunktion>(
      (found, max) -> "Coordinate must not be more than " + max + ", found " + found);

  private static final DynamicCommandException<Dynamic1ExceptionFunktion> LITERAL_INCORRECT = new DynamicCommandException<Dynamic1ExceptionFunktion>(
      expected -> "Expected literal " + expected);

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

  private static final DynamicCommandException<Dynamic1ExceptionFunktion> READER_INVALID_RANGE = new DynamicCommandException<Dynamic1ExceptionFunktion>(
      value -> "Invalid Range '" + value + "'");

  private static final DynamicCommandException<Dynamic1ExceptionFunktion> READER_INVALID_LOCATION = new DynamicCommandException<Dynamic1ExceptionFunktion>(
      value -> "Invalid Location '" + value + "'");

  private static final SimpleCommandException READER_EXPECTED_FLOAT = new SimpleCommandException(
      "Expected float");

  private static final SimpleCommandException READER_EXPECTED_BOOL = new SimpleCommandException(
      "Expected bool");

  private static final SimpleCommandException READER_EXPECTED_RANGE = new SimpleCommandException(
      "Expected Range");

  private static final DynamicCommandException<Dynamic1ExceptionFunktion> READER_EXPECTED_SYMBOL = new DynamicCommandException<Dynamic1ExceptionFunktion>(
      symbol -> "Expected '" + symbol + "'");

  public static DynamicCommandException<Dynamic2ExceptionFunktion> doubleTooLow() {
    return DOUBLE_TOO_SMALL;
  }

  public static DynamicCommandException<Dynamic2ExceptionFunktion> doubleTooHigh() {
    return DOUBLE_TOO_BIG;
  }

  public static DynamicCommandException<Dynamic2ExceptionFunktion> floatTooLow() {
    return FLOAT_TOO_SMALL;
  }

  public static DynamicCommandException<Dynamic2ExceptionFunktion> floatTooHigh() {
    return FLOAT_TOO_BIG;
  }

  public static DynamicCommandException<Dynamic2ExceptionFunktion> integerTooLow() {
    return INTEGER_TOO_SMALL;
  }

  public static DynamicCommandException<Dynamic2ExceptionFunktion> integerTooHigh() {
    return INTEGER_TOO_BIG;
  }

  public static DynamicCommandException<Dynamic2ExceptionFunktion> longTooLow() {
    return LONG_TOO_SMALL;
  }

  public static DynamicCommandException<Dynamic2ExceptionFunktion> longTooHigh() {
    return LONG_TOO_BIG;
  }

  public static DynamicCommandException<Dynamic2ExceptionFunktion> locationTooHigh() {
    return LOCATION_TOO_BIG;
  }

  public static DynamicCommandException<Dynamic1ExceptionFunktion> literalIncorrect() {
    return LITERAL_INCORRECT;
  }

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

  public static DynamicCommandException<Dynamic1ExceptionFunktion> readerInvalidRange() {
    return READER_INVALID_RANGE;
  }

  public static DynamicCommandException<Dynamic1ExceptionFunktion> readerInvalidLocation() {
    return READER_INVALID_LOCATION;
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
