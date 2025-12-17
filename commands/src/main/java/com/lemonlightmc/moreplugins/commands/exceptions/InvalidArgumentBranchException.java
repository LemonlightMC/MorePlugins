package com.lemonlightmc.moreplugins.commands.exceptions;

public class InvalidArgumentBranchException extends RuntimeException {
  public InvalidArgumentBranchException(final String reason, final String argName) {
    super(
        "Branch cant be created due " + reason + " for Argument '" + argName + "'");
  }
}
