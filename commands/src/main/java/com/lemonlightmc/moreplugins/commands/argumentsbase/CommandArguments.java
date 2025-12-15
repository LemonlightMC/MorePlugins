package com.lemonlightmc.moreplugins.commands.argumentsbase;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import com.lemonlightmc.moreplugins.commands.Utils;

public class CommandArguments {
  public ParsedArgument[] args;
  public Map<String, ParsedArgument> argsMap;
  public String fullInput;

  public CommandArguments(
      final ParsedArgument[] args,
      final Map<String, ParsedArgument> argsMap,
      final String fullInput) {
    this.args = args;
    this.argsMap = argsMap;
    this.fullInput = fullInput;
  }

  public CommandArguments(
      final Collection<ParsedArgument> args,
      final Map<String, ParsedArgument> argsMap,
      final String fullInput) {
    this.args = args.toArray(ParsedArgument[]::new);
    this.argsMap = argsMap;
    this.fullInput = fullInput;
  }

  public CommandArguments(
      final Map<String, ParsedArgument> argsMap, final String fullInput) {
    this.args = argsMap.values().toArray(ParsedArgument[]::new);
    this.argsMap = argsMap;
    this.fullInput = fullInput;
  }

  public String getFullInput() {
    return fullInput;
  }

  public int count() {
    return args.length;
  }

  public Map<String, ParsedArgument> map() {
    return Collections.unmodifiableMap(argsMap);
  }

  public Set<String> keys() {
    return Collections.unmodifiableSet(argsMap.keySet());
  }

  public Collection<ParsedArgument> values() {
    return Collections.unmodifiableCollection(argsMap.values());
  }

  // normal
  public Object get(final int index) {
    if (args.length <= index) {
      return null;
    } else {
      return args[index] == null ? null : args[index].value();
    }
  }

  public Object get(final String nodeName) {
    return argsMap.get(nodeName);
  }

  public Object getOptional(final int index) {
    return Optional.ofNullable(get(index));
  }

  public Object getOptional(final String nodeName) {
    return Optional.ofNullable(get(nodeName));
  }

  public Object getOrDefault(final int index, final Object defaultValue) {
    if (args.length <= index) {
      return defaultValue;
    } else {
      return args[index] == null ? null : args[index].value();
    }
  }

  public Object getOrDefault(final String nodeName, final Object defaultValue) {
    final ParsedArgument v = argsMap.get(nodeName);
    return v == null ? defaultValue : v.value();
  }

  public Object getOrDefault(final int index, final Supplier<?> defaultValue) {
    if (args.length <= index) {
      return defaultValue.get();
    } else {
      return args[index] == null ? null : args[index].value();
    }
  }

  public Object getOrDefault(final String nodeName, final Supplier<?> defaultValue) {
    final ParsedArgument v = argsMap.get(nodeName);
    return v == null ? defaultValue.get() : v.value();
  }

  // raw
  public String getRaw(final int index) {
    if (args.length <= index) {
      return null;
    } else {
      return args[index] == null ? null : args[index].raw();
    }
  }

  public String getRaw(final String nodeName) {
    final ParsedArgument v = argsMap.get(nodeName);
    return v == null ? null : v.raw();
  }

  public Object getRawOptional(final int index) {
    return Optional.ofNullable(getRaw(index));
  }

  public Object getRawOptional(final String nodeName) {
    return Optional.ofNullable(getRaw(nodeName));
  }

  public String getOrDefaultRaw(final int index, final String defaultValue) {
    if (args.length <= index) {
      return defaultValue;
    } else {
      return args[index] == null ? null : args[index].raw();
    }
  }

  public String getOrDefaultRaw(final String nodeName, final String defaultValue) {
    final ParsedArgument v = argsMap.get(nodeName);
    return v == null ? defaultValue : v.raw();
  }

  public String getOrDefaultRaw(final int index, final Supplier<String> defaultValue) {
    if (args.length <= index) {
      return defaultValue.get();
    } else {
      return args[index] == null ? null : args[index].raw();
    }
  }

  public String getOrDefaultRaw(
      final String nodeName,
      final Supplier<String> defaultValue) {
    final ParsedArgument v = argsMap.get(nodeName);
    return v == null ? defaultValue.get() : v.raw();
  }

  // unchecked
  @SuppressWarnings("unchecked")
  public <T> T getUnchecked(final int index) {
    return (T) get(index);
  }

  @SuppressWarnings("unchecked")
  public <T> T getUnchecked(final String nodeName) {
    return (T) get(nodeName);
  }

  public Object getUncheckedOptional(final int index) {
    return Optional.ofNullable(getUnchecked(index));
  }

  public Object getUncheckedOptional(final String nodeName) {
    return Optional.ofNullable(getUnchecked(nodeName));
  }

  @SuppressWarnings("unchecked")
  public <T> T getOrDefaultUnchecked(final int index, final T defaultValue) {
    return (T) getOrDefault(index, defaultValue);
  }

  @SuppressWarnings("unchecked")
  public <T> T getOrDefaultUnchecked(final String nodeName, final T defaultValue) {
    return (T) getOrDefault(nodeName, defaultValue);
  }

  @SuppressWarnings("unchecked")
  public <T> T getOrDefaultUnchecked(final int index, final Supplier<T> defaultValue) {
    return (T) getOrDefault(index, defaultValue);
  }

  @SuppressWarnings("unchecked")
  public <T> T getOrDefaultUnchecked(
      final String nodeName,
      final Supplier<T> defaultValue) {
    return (T) getOrDefault(nodeName, defaultValue);
  }

  // byArgument
  public <T> T getByArgument(final Argument<T, ?> argumentType) {
    return castArgument(
        get(argumentType.getName()),
        argumentType.getPrimitiveType(),
        argumentType.getName());
  }

  public <T> Optional<T> getByArgumentOptional(final Argument<T, ?> argumentType) {
    return Optional.ofNullable(getByArgument(argumentType));
  }

  public <T> T getByArgumentOrDefault(
      final Argument<T, ?> argumentType,
      final T defaultValue) {
    final T argument = getByArgument(argumentType);
    return argument == null ? defaultValue : argument;
  }

  public <T> T getByClass(final String nodeName, final Class<T> argumentType) {
    return castArgument(get(nodeName), argumentType, nodeName);
  }

  public <T> T getByClass(final int index, final Class<T> argumentType) {
    return castArgument(get(index), argumentType, index);
  }

  public <T> Optional<T> getByClassOptional(final String nodeName, final Class<T> argumentType) {
    return Optional.ofNullable(getByClass(nodeName, argumentType));
  }

  public <T> Optional<T> getByClassOptional(final int index, final Class<T> argumentType) {
    return Optional.ofNullable(getByClass(index, argumentType));
  }

  public <T> T getByClassOrDefault(
      final String nodeName,
      final Class<T> argumentType,
      final T defaultValue) {
    final T argument = getByClass(nodeName, argumentType);
    return (argument != null) ? argument : defaultValue;
  }

  public <T> T getByClassOrDefault(
      final int index,
      final Class<T> argumentType,
      final T defaultValue) {
    final T argument = getByClass(index, argumentType);
    return argument == null ? defaultValue : argument;
  }

  private <T> T castArgument(
      final Object argument,
      final Class<T> argumentType,
      final Object argumentNameOrIndex) {
    if (argument == null) {
      return null;
    }
    try {
      return Utils.primitive2wrapper(argument, argumentType);
    } catch (final Exception e) {
      throwExceptionMessage(
          argumentNameOrIndex,
          argument.getClass().getSimpleName(),
          argumentType.getSimpleName());
    }
    return null;
  }

  private String throwExceptionMessage(
      final Object argumentNameOrIndex,
      final String expectedClass,
      final String actualClass) {
    if (argumentNameOrIndex instanceof final Integer i) {
      throw new IllegalArgumentException("Argument at index '" +
          i +
          "' is defined as " +
          expectedClass +
          ", not " +
          actualClass);
    }
    if (argumentNameOrIndex instanceof final String s) {
      throw new IllegalArgumentException("Argument '" +
          s +
          "' is defined as " +
          expectedClass +
          ", not " +
          actualClass);
    }
    throw new IllegalStateException(
        "Unexpected behaviour detected while building exception message! This should never happen - if you're seeing this message, please contact the developers!");
  }

  @Override
  public int hashCode() {
    int result = 31 + Arrays.deepHashCode(args);
    result = 31 * result + ((argsMap == null) ? 0 : argsMap.hashCode());
    result = 31 * result + ((fullInput == null) ? 0 : fullInput.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final CommandArguments other = (CommandArguments) obj;
    if (argsMap == null) {
      if (other.argsMap != null)
        return false;
    } else if (!argsMap.equals(other.argsMap))
      return false;
    if (fullInput == null) {
      if (other.fullInput != null)
        return false;
    } else if (!fullInput.equals(other.fullInput))
      return false;
    return equalsArgs(args, other.args);
  }

  @Override
  public String toString() {
    return "CommandArguments [args=" + Arrays.toString(args) + ",fullInput=" + fullInput + "]";
  }

  private boolean equalsArgs(
      final ParsedArgument[] a1,
      final ParsedArgument[] a2) {
    if (a1 == a2)
      return true;
    if (a1 == null || a2 == null)
      return false;
    final int length = a1.length;
    if (a2.length != length)
      return false;

    for (int i = 0; i < length; i++) {
      final ParsedArgument e1 = a1[i];
      final ParsedArgument e2 = a2[i];

      if (e1 == e2)
        continue;
      if (e1 == null)
        return false;
      if (!e1.equals(e2))
        return false;
    }
    return true;
  }
}