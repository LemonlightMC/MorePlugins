package com.lemonlightmc.moreplugins.commands;

import java.util.function.Predicate;

import com.lemonlightmc.moreplugins.interfaces.Cloneable;

@SuppressWarnings("rawtypes")
public class CommandRequirement<C extends CommandSource> implements Cloneable<CommandRequirement<C>> {

  private final Predicate<C> predicate;
  private String message;
  private boolean hide;

  public static final String defaultMessage = "&cYou do not meet the requirements to use this command!";
  public static final String defaultPermissionMessage = "&cYou do not have the permission to use this command!";

  public CommandRequirement(final Predicate<C> predicate, final String message, final boolean hide) {
    if (predicate == null) {
      throw new IllegalArgumentException("Predicate cannot be null");
    }
    this.predicate = predicate;
    this.message = message;
    this.hide = hide;
  }

  public CommandRequirement(final Predicate<C> predicate, final String message) {
    this(predicate, message, false);
  }

  public CommandRequirement(final Predicate<C> predicate) {
    this(predicate, defaultMessage, false);
  }

  public static <T extends CommandSource> CommandRequirement<T> from(final Predicate<T> predicate,
      final String message, final boolean hide) {
    return new CommandRequirement<T>(predicate, message, hide);
  }

  public static <T extends CommandSource> CommandRequirement<T> from(final Predicate<T> predicate,
      final boolean hide) {
    return new CommandRequirement<T>(predicate, defaultMessage, hide);
  }

  public static <T extends CommandSource> CommandRequirement<T> from(final Predicate<T> predicate,
      final String message) {
    return new CommandRequirement<T>(predicate, message, false);
  }

  public static <T extends CommandSource> CommandRequirement<T> from(final Predicate<T> predicate) {
    return new CommandRequirement<T>(predicate, defaultMessage, false);
  }

  public static <T extends CommandSource> CommandRequirement<T> permission(final String permission,
      final String message, final boolean hide) {
    return new CommandRequirement<T>((s) -> s.hasPermission(permission), message, hide);
  }

  public static <T extends CommandSource> CommandRequirement<T> permission(final String permission,
      final boolean hide) {
    return new CommandRequirement<T>((s) -> s.hasPermission(permission), defaultPermissionMessage, hide);
  }

  public static <T extends CommandSource> CommandRequirement<T> permission(final String permission,
      final String message) {
    return new CommandRequirement<T>((s) -> s.hasPermission(permission), message, false);
  }

  public static <T extends CommandSource> CommandRequirement<T> permission(final String permission) {
    return new CommandRequirement<T>((s) -> s.hasPermission(permission), defaultPermissionMessage, false);
  }

  public boolean test(final C source) {
    return predicate.test(source);
  }

  public boolean check(final C source) {
    if (test(source)) {
      return true;
    } else {
      source.sendMessage(message);
      return false;
    }
  }

  public Predicate<C> getPredicate() {
    return predicate;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(final String message) {
    this.message = message;
  }

  public boolean shouldHide(final C source) {
    return hide && test(source);
  }

  public boolean isHide() {
    return hide;
  }

  public void setHide(final boolean hide) {
    this.hide = true;
  }

  @Override
  public CommandRequirement<C> clone() {
    return new CommandRequirement<C>(predicate, message, hide);
  }

  @Override
  public int hashCode() {
    int result = 31 + predicate.hashCode();
    result = 31 * result + ((message == null) ? 0 : message.hashCode());
    return 31 * result + (hide ? 1231 : 1237);
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final CommandRequirement<?> other = (CommandRequirement<?>) obj;
    if (message == null) {
      if (other.message != null) {
        return false;
      }
    }
    return predicate.equals(other.predicate) && message.equals(other.message) && hide == other.hide;
  }

  @Override
  public String toString() {
    return "CommandRequirement [predicate=" + predicate + ", message=" + message + ", hide=" + hide + "]";
  }

}
