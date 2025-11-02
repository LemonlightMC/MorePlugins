package com.lemonlightmc.moreplugins.commands.argumentsbase;

import com.lemonlightmc.moreplugins.commands.Permission;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

public abstract class Argument<T, Impl> {

  private final String name;
  private final ArgumentType rawType;
  private final Class<T> primitiveType;
  private boolean isOptional = false;
  private boolean isListed = true;
  private Permission permission = Permission.NONE;
  private Predicate<CommandSender> requirements = _ -> true;
  private final List<Argument<?, ?>> combinedArguments = new ArrayList<>();

  private List<CompletableFuture<Suggestions<CommandSender>>> suggestionsModifiers = new ArrayList<>(4);

  protected Argument(final String name, final Class<T> primitiveType, final ArgumentType rawType) {
    if (name == null || name.length() == 0) {
      throw new IllegalArgumentException("Invalid Argument Name");
    }
    if (primitiveType == null || !(primitiveType instanceof Class<T>)) {
      throw new IllegalArgumentException("Invalid Primitive Type");
    }
    if (rawType == null || !(rawType instanceof ArgumentType)) {
      throw new IllegalArgumentException("Invalid Argument Type");
    }
    this.name = name;
    this.rawType = rawType;
    this.primitiveType = primitiveType;
  }

  public abstract Impl getInstance();

  public String getName() {
    return name;
  }

  public ArgumentType getType() {
    return rawType;
  }

  public interface Context<Source> {
  }

  public Class<T> getPrimitiveType() {
    return primitiveType;
  }

  public ArgumentType getArgumentType() {
    return rawType;
  }

  public abstract <Source> Double parseArgument(
      Context<Source> cmdCtx,
      String key,
      CommandArguments previousArgs) throws CommandException;

  public Impl applySuggestions(final Suggestions<CommandSender> func) {
    this.suggestionsModifiers.add(CompletableFuture.completedFuture(func));
    return getInstance();
  }

  public List<CompletableFuture<Suggestions<CommandSender>>> getSuggestionModfiers() {
    return this.suggestionsModifiers;
  }

  public final Impl withPermission(final Permission permission) {
    this.permission = permission;
    return getInstance();
  }

  public final Impl withPermission(final String permission) {
    this.permission = Permission.fromString(permission);
    return getInstance();
  }

  public final Permission getArgumentPermission() {
    return permission;
  }

  public final Predicate<CommandSender> getRequirements() {
    return this.requirements;
  }

  public final Impl withRequirement(final Predicate<CommandSender> requirement) {
    this.requirements = this.requirements.and(requirement);
    return getInstance();
  }

  void resetRequirements() {
    this.requirements = _ -> true;
  }

  public boolean isListed() {
    return this.isListed;
  }

  public Impl setListed(final boolean listed) {
    this.isListed = listed;
    return getInstance();
  }

  public boolean isOptional() {
    return isOptional;
  }

  public Impl setOptional(final boolean optional) {
    this.isOptional = optional;
    return getInstance();
  }

  public List<Argument<?, ?>> getCombinedArguments() {
    return combinedArguments;
  }

  public boolean hasCombinedArguments() {
    return !combinedArguments.isEmpty();
  }

  public final Impl combineWith(final Argument<?, ?>... combinedArguments) {
    for (final Argument<?, ?> argument : combinedArguments) {
      this.combinedArguments.add(argument);
    }
    return getInstance();
  }

  public void copyPermissionsAndRequirements(final Argument<?, ?> argument) {
    this.resetRequirements();
    this.withRequirement(argument.getRequirements());
    this.withPermission(argument.getArgumentPermission());
  }

  public String getHelpString() {
    return "<" + this.getName() + ">";
  }

  @Override
  public int hashCode() {
    int result = 31 + ((name == null) ? 0 : name.hashCode());
    result = 31 * result + ((rawType == null) ? 0 : rawType.hashCode());
    result = 31 * result + (isOptional ? 1231 : 1237);
    result = 31 * result + (isListed ? 1231 : 1237);
    result = 31 * result + ((permission == null) ? 0 : permission.hashCode());
    return 31 * result + ((requirements == null) ? 0 : requirements.hashCode());
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    final Argument<?, ?> other = (Argument<?, ?>) obj;
    if (!name.equals(other.name))
      return false;
    if (rawType.equals(other.rawType))
      return false;
    if (isOptional != other.isOptional)
      return false;
    if (isListed != other.isListed)
      return false;
    if (!permission.equals(other.permission))
      return false;
    if (!requirements.equals(other.requirements))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return this.getName() + "<" + this.getClass().getSimpleName() + ">";
  }
}
