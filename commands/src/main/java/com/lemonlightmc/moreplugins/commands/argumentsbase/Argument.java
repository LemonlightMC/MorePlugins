package com.lemonlightmc.moreplugins.commands.argumentsbase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

import com.lemonlightmc.moreplugins.commands.suggestions.SuggestionInfo;
import com.lemonlightmc.moreplugins.commands.suggestions.Suggestions;

public abstract class Argument<Type, ArgType> {
  protected String name;
  protected ArgumentType rawType;
  protected Class<Type> primitiveType;
  protected boolean isOptional = false;
  protected boolean isListed = true;
  protected String permission = "";
  protected Predicate<CommandSender> requirements = _ -> true;
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

  // Parse
  public abstract <Source> Double parseArgument(
      Context<Source> cmdCtx,
      String key,
      CommandArguments previousArgs) throws CommandException;

  public interface Context<Source> {
  }

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

  // Permission
  public ArgType withPermission(final String permission) {
    this.permission = permission;
    return getInstance();
  }

  public String getPermission() {
    return permission;
  }

  public void clearPermission() {
    this.permission = "";
  }

  // Requirements
  public ArgType withRequirement(final Predicate<CommandSender> requirement) {
    this.requirements = this.requirements.and(requirement);
    return getInstance();
  }

  public Predicate<CommandSender> getRequirements() {
    return this.requirements;
  }

  public void clearRequirements() {
    this.requirements = _ -> true;
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

  // Help
  public String getHelpString() {
    if (!isListed) {
      return "";
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
    result = 31 * result + ((permission == null) ? 0 : permission.hashCode());
    result = 31 * result + requirements.hashCode();
    result = 31 * result + suggestions.hashCode();
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
    final Argument<?, ?> other = (Argument<?, ?>) obj;
    if (permission == null && other.permission != null) {
      return false;
    }
    return isListed == other.isListed && isOptional == other.isOptional && isOptional == other.isOptional
        && rawType == other.rawType
        && name.equals(other.name) && name.equals(other.name)
        && primitiveType.equals(other.primitiveType)
        && permission.equals(other.permission)
        && requirements.equals(other.requirements)
        && suggestions.equals(other.suggestions);
  }

  @Override
  public String toString() {
    return "Argument [name=" + name + ", rawType=" + rawType + ", primitiveType=" + primitiveType + ", isOptional="
        + isOptional + ", isListed=" + isListed + ", permission=" + permission + ", requirements=" + requirements
        + ", suggestions=" + suggestions + "]";
  }

}
