package com.julizey.moreplugins.commands.argumentsbase;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * This class stores the arguments for this command
 *
 * @param args      The arguments for this command
 * @param argsMap   The arguments for this command mapped to their node names. This is an ordered map
 * @param args   The raw arguments for this command.
 * @param argsMap   The raw arguments for this command mapped to their node names. This is an ordered map
 * @param fullInput The command string a player has entered (including the /)
 */
public record CommandArguments(
  ParsedArgument<?, ?>[] args,
  Map<String, ParsedArgument<?, ?>> argsMap,
  String fullInput
) {
  private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER = Map.of(
    boolean.class,
    Boolean.class,
    char.class,
    Character.class,
    byte.class,
    Byte.class,
    short.class,
    Short.class,
    int.class,
    Integer.class,
    long.class,
    Long.class,
    float.class,
    Float.class,
    double.class,
    Double.class
  );

  public Map<String, ParsedArgument<?, ?>> argsMap() {
    return Collections.unmodifiableMap(argsMap);
  }

  public int count() {
    return args.length;
  }

  public Object get(int index) {
    if (args.length <= index) {
      return null;
    } else {
      return args[index].value();
    }
  }

  public Object get(String nodeName) {
    return argsMap.get(nodeName);
  }

  public Object getOrDefault(int index, Object defaultValue) {
    if (args.length <= index) {
      return defaultValue;
    } else {
      return args[index].value();
    }
  }

  public Object getOrDefault(String nodeName, Object defaultValue) {
    ParsedArgument<?, ?> v = argsMap.get(nodeName);
    return v != null || argsMap.containsKey(nodeName)
      ? v.value()
      : defaultValue;
  }

  public Object getOrDefault(int index, Supplier<?> defaultValue) {
    if (args.length <= index) {
      return defaultValue.get();
    } else {
      return args[index].value();
    }
  }

  public Object getOrDefault(String nodeName, Supplier<?> defaultValue) {
    ParsedArgument<?, ?> v = argsMap.get(nodeName);
    return v != null || argsMap.containsKey(nodeName)
      ? v.value()
      : defaultValue.get();
  }

  public Optional<Object> getOptional(int index) {
    if (args.length <= index) {
      return Optional.empty();
    } else {
      return Optional.of(args[index].value());
    }
  }

  public Optional<Object> getOptional(String nodeName) {
    if (!argsMap.containsKey(nodeName)) {
      return Optional.empty();
    }
    return Optional.of(argsMap.get(nodeName).value());
  }

  public String getRaw(int index) {
    if (args.length <= index) {
      return null;
    } else {
      return args[index].raw();
    }
  }

  public String getRaw(String nodeName) {
    return argsMap.get(nodeName).raw();
  }

  public String getOrDefaultRaw(int index, String defaultValue) {
    if (args.length <= index) {
      return defaultValue;
    } else {
      return args[index].raw();
    }
  }

  public String getOrDefaultRaw(String nodeName, String defaultValue) {
    ParsedArgument<?, ?> v = argsMap.get(nodeName);
    return v != null || argsMap.containsKey(nodeName) ? v.raw() : defaultValue;
  }

  public String getOrDefaultRaw(int index, Supplier<String> defaultValue) {
    if (args.length <= index) {
      return defaultValue.get();
    } else {
      return args[index].raw();
    }
  }

  public String getOrDefaultRaw(
    String nodeName,
    Supplier<String> defaultValue
  ) {
    ParsedArgument<?, ?> v = argsMap.get(nodeName);
    return v != null || argsMap.containsKey(nodeName)
      ? v.raw()
      : defaultValue.get();
  }

  public Optional<String> getRawOptional(int index) {
    if (args.length <= index) {
      return Optional.empty();
    } else {
      return Optional.of(args[index].raw());
    }
  }

  public Optional<String> getRawOptional(String nodeName) {
    if (!argsMap.containsKey(nodeName)) {
      return Optional.empty();
    }
    return Optional.of(argsMap.get(nodeName).raw());
  }

  @SuppressWarnings("unchecked")
  public <T> T getUnchecked(int index) {
    return (T) get(index);
  }

  @SuppressWarnings("unchecked")
  public <T> T getUnchecked(String nodeName) {
    return (T) get(nodeName);
  }

  @SuppressWarnings("unchecked")
  public <T> T getOrDefaultUnchecked(int index, T defaultValue) {
    return (T) getOrDefault(index, defaultValue);
  }

  @SuppressWarnings("unchecked")
  public <T> T getOrDefaultUnchecked(String nodeName, T defaultValue) {
    return (T) getOrDefault(nodeName, defaultValue);
  }

  @SuppressWarnings("unchecked")
  public <T> T getOrDefaultUnchecked(int index, Supplier<T> defaultValue) {
    return (T) getOrDefault(index, defaultValue);
  }

  @SuppressWarnings("unchecked")
  public <T> T getOrDefaultUnchecked(
    String nodeName,
    Supplier<T> defaultValue
  ) {
    return (T) getOrDefault(nodeName, defaultValue);
  }

  @SuppressWarnings("unchecked")
  public <T> Optional<T> getOptionalUnchecked(int index) {
    return (Optional<T>) getOptional(index);
  }

  @SuppressWarnings("unchecked")
  public <T> Optional<T> getOptionalUnchecked(String nodeName) {
    return (Optional<T>) getOptional(nodeName);
  }

  public <T> T getByArgument(Argument<T, ?> argumentType) {
    return castArgument(
      get(argumentType.getName()),
      argumentType.getPrimitiveType(),
      argumentType.getName()
    );
  }

  public <T> T getByArgumentOrDefault(
    Argument<T, ?> argumentType,
    T defaultValue
  ) {
    T argument = getByArgument(argumentType);
    return (argument != null) ? argument : defaultValue;
  }

  public <T> Optional<T> getOptionalByArgument(Argument<T, ?> argumentType) {
    return Optional.ofNullable(getByArgument(argumentType));
  }

  public <T> T getByClass(String nodeName, Class<T> argumentType) {
    return castArgument(get(nodeName), argumentType, nodeName);
  }

  public <T> T getByClassOrDefault(
    String nodeName,
    Class<T> argumentType,
    T defaultValue
  ) {
    T argument = getByClass(nodeName, argumentType);
    return (argument != null) ? argument : defaultValue;
  }

  public <T> Optional<T> getOptionalByClass(
    String nodeName,
    Class<T> argumentType
  ) {
    return Optional.ofNullable(getByClass(nodeName, argumentType));
  }

  public <T> T getByClass(int index, Class<T> argumentType) {
    return castArgument(get(index), argumentType, index);
  }

  public <T> T getByClassOrDefault(
    int index,
    Class<T> argumentType,
    T defaultValue
  ) {
    T argument = getByClass(index, argumentType);
    return (argument != null) ? argument : defaultValue;
  }

  public <T> Optional<T> getOptionalByClass(int index, Class<T> argumentType) {
    return Optional.ofNullable(getByClass(index, argumentType));
  }

  @SuppressWarnings("unchecked")
  private <T> T castArgument(
    Object argument,
    Class<T> argumentType,
    Object argumentNameOrIndex
  ) {
    if (argument == null) {
      return null;
    }
    if (
      !PRIMITIVE_TO_WRAPPER
        .getOrDefault(argumentType, argumentType)
        .isAssignableFrom(argument.getClass())
    ) {
      throw new IllegalArgumentException(
        buildExceptionMessage(
          argumentNameOrIndex,
          argument.getClass().getSimpleName(),
          argumentType.getSimpleName()
        )
      );
    }
    return (T) argument;
  }

  private String buildExceptionMessage(
    Object argumentNameOrIndex,
    String expectedClass,
    String actualClass
  ) {
    if (argumentNameOrIndex instanceof Integer i) {
      return (
        "Argument at index '" +
        i +
        "' is defined as " +
        expectedClass +
        ", not " +
        actualClass
      );
    }
    if (argumentNameOrIndex instanceof String s) {
      return (
        "Argument '" +
        s +
        "' is defined as " +
        expectedClass +
        ", not " +
        actualClass
      );
    }
    throw new IllegalStateException(
      "Unexpected behaviour detected while building exception message!" +
      "This should never happen - if you're seeing this message, please" +
      "contact the developers of the CommandAPI, we'd love to know how you managed to get this error!"
    );
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.deepHashCode(args);
    result = prime * result + Arrays.hashCode(args);
    result = prime * result + Objects.hash(argsMap, fullInput, argsMap);
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    CommandArguments other = (CommandArguments) obj;
    return (
      equalsArgs(args, other.args) &&
      argsMap.equals(other.argsMap) &&
      fullInput.equals(other.fullInput)
    );
  }

  private boolean equalsArgs(
    ParsedArgument<?, ?>[] a1,
    ParsedArgument<?, ?>[] a2
  ) {
    if (a1 == a2) return true;
    if (a1 == null || a2 == null) return false;
    int length = a1.length;
    if (a2.length != length) return false;

    for (int i = 0; i < length; i++) {
      ParsedArgument<?, ?> e1 = a1[i];
      ParsedArgument<?, ?> e2 = a2[i];

      if (e1 == e2) continue;
      if (e1 == null) return false;
      if (!e1.equals(e2)) return false;
    }
    return true;
  }
}
