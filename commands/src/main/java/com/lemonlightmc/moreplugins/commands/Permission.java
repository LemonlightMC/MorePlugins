package com.lemonlightmc.moreplugins.commands;

import java.util.Optional;

/**
 * A representation of permission nodes for commands. Represents permission
 * nodes, being op and having all permissions
 */
public class Permission {

  private enum PermissionNode {
    NONE,
    OP,
  }

  public static final Permission NONE = new Permission(PermissionNode.NONE);
  public static final Permission OP = new Permission(PermissionNode.OP);

  private boolean negated = false;
  private String permission;
  private PermissionNode permissionNode;

  private Permission(PermissionNode permissionNode) {
    this.permission = null;
    this.permissionNode = permissionNode;
  }

  private Permission(String permission) {
    this.permission = permission;
    this.permissionNode = null;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Permission)) {
      return false;
    }
    Permission other = (Permission) obj;
    return (negated == other.negated &&
        permission.equals(other.permission) &&
        permissionNode == other.permissionNode);
  }

  @Override
  public int hashCode() {
    int result = 31 + (negated ? 1 : 0);
    result = 31 * result + permission.hashCode();
    return 31 * result + permissionNode.hashCode();
  }

  @Override
  public String toString() {
    final String result;
    if (permissionNode != null) {
      if (permissionNode == PermissionNode.OP) {
        result = "OP";
      } else {
        result = "NONE";
      }
    } else {
      result = permission;
    }
    return (negated ? "not " : "") + result;
  }

  public static Permission fromString(String permission) {
    return new Permission(permission);
  }

  public Optional<String> getPermission() {
    return Optional.ofNullable(this.permission);
  }

  public PermissionNode getPermissionNode() {
    return this.permissionNode;
  }

  public boolean isNegated() {
    return this.negated;
  }

  public Permission negate() {
    this.negated = true;
    return this;
  }
}
