package com.lemonlightmc.moreplugins.commands.argumentsbase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.bukkit.command.CommandSender;

import com.lemonlightmc.moreplugins.commands.CommandRequirement;
import com.lemonlightmc.moreplugins.commands.CommandSource;
import com.lemonlightmc.moreplugins.commands.exceptions.CommandSyntaxException;
import com.lemonlightmc.moreplugins.commands.suggestions.SuggestionInfo;
import com.lemonlightmc.moreplugins.commands.suggestions.Suggestions;

public abstract class Argument<Type, ArgType> {
  protected String name;
  protected ArgumentType rawType;
  protected Class<Type> primitiveType;
  protected boolean isOptional = false;
  protected boolean isListed = true;
  protected List<CommandRequirement<CommandSender>> requirements;
  protected List<Suggestions<CommandSender>> suggestions = new ArrayList<>(4);

  protected Argument(final String name, final Class<Type> primitiveType, final ArgumentType rawType) {
    if (name == null || name.length() == 0) {
      throw new IllegalArgumentException("Invalid Argument Name");
    }
    if (primitiveType == null || !(primitiveType instanceof Class<Type>)) {
      throw new IllegalArgumentException("Invalid Primitive Type");
    }
    if (rawType == null || !(rawType instanceof ArgumentType)) {
      throw new IllegalArgumentException("Invalid Argument Type");
    }
    this.name = name;
    this.rawType = rawType;
    this.primitiveType = primitiveType;
  }

  public abstract ArgType getInstance();

  public CommandSyntaxException createError(final StringReader reader, final String value) {
    return new CommandSyntaxException(reader, "Invalid Value '" + value + "' for Argument '" + name + "'");
  }

  public abstract Type parseArgument(CommandSource<CommandSender> source, StringReader reader, String key)
      throws CommandSyntaxException;

  public String getName() {
    return name;
  }

  public ArgumentType getType() {
    return rawType;
  }

  public Class<Type> getPrimitiveType() {
    return primitiveType;
  }

  public ArgumentType getArgumentType() {
    return rawType;
  }

  // Listed
  public boolean isListed() {
    return this.isListed;
  }

  public ArgType setListed(final boolean listed) {
    this.isListed = listed;
    return getInstance();
  }

  // Optional
  public boolean isOptional() {
    return isOptional;
  }

  public ArgType setOptional(final boolean optional) {
    this.isOptional = optional;
    return getInstance();
  }

  // Suggestions

  public ArgType withSuggestions(final Suggestions<CommandSender> func) {
    this.suggestions.add(func);
    return getInstance();
  }

  public ArgType withSuggestions(final Function<SuggestionInfo<CommandSender>, Collection<String>> func) {
    this.suggestions.add(Suggestions.from(func));
    return getInstance();
  }

  public ArgType withSuggestions(final String... suggestions) {
    this.suggestions.add(Suggestions.from(suggestions));
    return getInstance();
  }

  public ArgType withSuggestions(final Collection<String> suggestions) {
    this.suggestions.add(Suggestions.from(suggestions));
    return getInstance();
  }

  public List<Suggestions<CommandSender>> getSuggestions() {
    return this.suggestions;
  }
  // requirements

  public ArgType withRequirement(final CommandRequirement<CommandSender> requirement) {
    if (requirements == null) {
      requirements = new ArrayList<>();
    }
    requirements.add(requirement);
    return getInstance();
  }

  public ArgType withRequirement(final Predicate<CommandSource<CommandSender>> requirement) {
    return withRequirement(CommandRequirement.from(requirement));
  }

  public ArgType withPermission(final String permission) {
    return withRequirement(CommandRequirement.permission(permission));
  }

  public boolean hasRequirements() {
    return requirements != null && !requirements.isEmpty();
  }

  public ArgType clearRequirements() {
    if (requirements != null) {
      requirements.clear();
    }
    return getInstance();
  }

  public List<CommandRequirement<CommandSender>> getRequirements() {
    return requirements;
  }

  public boolean checkRequirements(final CommandSource<CommandSender> source) {
    if (requirements == null || requirements.size() == 0) {
      return true;
    }
    for (final CommandRequirement<CommandSender> requirement : requirements) {
      if (!requirement.check(source)) {
        return false;
      }
    }
    return true;
  }

  // Help
  public String getHelpString() {
    if (!isListed) {
      return name;
    }
    return isOptional ? "[" + name + "]" : "<" + name + ">";
  }

  @Override
  public int hashCode() {
    int result = 31 + name.hashCode();
    result = 31 * result + rawType.hashCode();
    result = 31 * result + primitiveType.hashCode();
    result = 31 * result + (isOptional ? 1231 : 1237);
    result = 31 * result + (isListed ? 1231 : 1237);
    result = 31 * result + requirements.hashCode();
    result = 31 * result + suggestions.hashCode();
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (getInstance() == obj) {
      return true;
    }
    if (obj == null || getInstance().getClass() != obj.getClass()) {
      return false;
    }
    final Argument<?, ?> other = (Argument<?, ?>) obj;
    return isListed == other.isListed && isOptional == other.isOptional && isOptional == other.isOptional
        && rawType == other.rawType
        && name.equals(other.name) && name.equals(other.name)
        && primitiveType.equals(other.primitiveType)
        && requirements.equals(other.requirements)
        && suggestions.equals(other.suggestions);
  }

  @Override
  public String toString() {
    return getInstance().getClass().getName() + " [name=" + name + ", rawType=" + rawType + ", primitiveType="
        + primitiveType + ", isOptional="
        + isOptional + ", isListed=" + isListed + ", requirements=" + requirements
        + ", suggestions=" + suggestions + "]";
  }

  public String toStringWithMore(final String str) {
    return getInstance().getClass().getName() + " [name=" + name + ", rawType=" + rawType + ", primitiveType="
        + primitiveType + ", isOptional="
        + isOptional + ", isListed=" + isListed + ", requirements=" + requirements
        + ", suggestions=" + suggestions + ", " + str + "]";
  }

}
