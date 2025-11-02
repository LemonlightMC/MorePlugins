package com.lemonlightmc.moreplugins.version;

public interface IVersion {
  public int getMajor();

  public int getMinor();

  public int getPatch();

  public int getBuild();

  public String getDisplayName();

  public String getDisplayName(boolean includeEmpty);

  public boolean isMajor(IVersion version);

  public boolean isMinor(IVersion version);

  public boolean isPatch(IVersion version);

  public boolean isBuild(IVersion version);

  public boolean isSame(IVersion version);

  public boolean isNewerThan(IVersion version);

  public boolean isOlderThan(IVersion version);

  public boolean isAtLeast(IVersion version);

  public int compareTo(Version v);

  public Object clone();

  public boolean equals(Object o);

  public int hashCode();

  public String toString();
}
