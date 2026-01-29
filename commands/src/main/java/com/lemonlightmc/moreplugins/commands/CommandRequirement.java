package com.lemonlightmc.moreplugins.commands;

import java.util.function.Predicate;
import org.bukkit.command.CommandSender;

import com.lemonlightmc.moreplugins.interfaces.Cloneable;

public class CommandRequirement<S extends CommandSender> implements Cloneable<CommandRequirement<S>> {

  private final Predicate<CommandSource<S>> predicate;
  private String message;
  private boolean hide;

  public static final String defaultMessage = "&cYou do not meet the requirements to use this command!";
  public static final String defaultPermissionMessage = "&cYou do not have the permission to use this command!";

  public CommandRequirement(final Predicate<CommandSource<S>> predicate, final String message, final boolean hide) {
    if (predicate == null) {
      throw new IllegalArgumentException("Predicate cannot be null");
    }
    this.predicate = predicate;
    this.message = message;
    this.hide = hide;
  }

  public CommandRequirement(final Predicate<CommandSource<S>> predicate, final String message) {
    this(predicate, message, false);
  }

  public CommandRequirement(final Predicate<CommandSource<S>> predicate) {
    this(predicate, defaultMessage, false);
  }

  public static <T extends CommandSender> CommandRequirement<T> from(final Predicate<CommandSource<T>> predicate,
      final String message, final boolean hide) {
    if (predicate == null) {
      return null;
    }
    return new CommandRequirement<T>(predicate, message, hide);
  }

  public static <T extends CommandSender> CommandRequirement<T> from(final Predicate<CommandSource<T>> predicate,
      final boolean hide) {
    if (predicate == null) {
      return null;
    }
    return new CommandRequirement<T>(predicate, defaultMessage, hide);
  }

  public static <T extends CommandSender> CommandRequirement<T> from(final Predicate<CommandSource<T>> predicate,
      final String message) {
    if (predicate == null) {
      return null;
    }
    return new CommandRequirement<T>(predicate, message, false);
  }

  public static <T extends CommandSender> CommandRequirement<T> from(final Predicate<CommandSource<T>> predicate) {
    if (predicate == null) {
      return null;
    }
    return new CommandRequirement<T>(predicate, defaultMessage, false);
  }

  public static <T extends CommandSender> CommandRequirement<T> permission(final String permission,
      final String message, final boolean hide) {
    if (permission == null) {
      return null;
    }
    return new CommandRequirement<T>((s) -> s.hasPermission(permission), message, hide);
  }

  public static <T extends CommandSender> CommandRequirement<T> permission(final String permission,
      final boolean hide) {
    if (permission == null) {
      return null;
    }
    return new CommandRequirement<T>((s) -> s.hasPermission(permission), defaultPermissionMessage, hide);
  }

  public static <T extends CommandSender> CommandRequirement<T> permission(final String permission,
      final String message) {
    if (permission == null) {
      return null;
    }
    return new CommandRequirement<T>((s) -> s.hasPermission(permission), message, false);
  }

  public static <T extends CommandSender> CommandRequirement<T> permission(final String permission) {
    if (permission == null) {
      return null;
    }
    return new CommandRequirement<T>((s) -> s.hasPermission(permission), defaultPermissionMessage, false);
  }

  public boolean test(final CommandSource<S> source) {
    return predicate.test(source);
  }

  public boolean check(final CommandSource<S> source) {
    if (test(source)) {
      return true;
    } else {
      source.sender().sendMessage(message);
      return false;
    }
  }

  public Predicate<CommandSource<S>> getPredicate() {
    return predicate;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(final String message) {
    this.message = message;
  }

  public boolean shouldHide(final CommandSource<S> source) {
    return hide && test(source);
  }

  public boolean isHide() {
    return hide;
  }

  public void setHide(final boolean hide) {
    this.hide = true;
  }

  @Override
  public CommandRequirement<S> clone() {
    return new CommandRequirement<S>(predicate, message, hide);
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
