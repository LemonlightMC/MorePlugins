package com.lemonlightmc.moreplugins.commands.exceptions;

public class BadLiteralException extends RuntimeException {

  public BadLiteralException(boolean isNull) {
    super(isNull ? "Cannot create a LiteralArgument with a null string"
        : "Cannot create a LiteralArgument with an empty string");
  }

}
