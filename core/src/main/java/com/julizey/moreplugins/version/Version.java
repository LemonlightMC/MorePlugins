package com.julizey.moreplugins.version;

public class Version implements IVersion {

  private final int major;
  private final int minor;
  private final int patch;
  private final int build;

  public Version(Version v) {
    this.major = v.getMajor();
    this.minor = v.getMinor();
    this.patch = v.getPatch();
    this.build = v.getBuild();
  }

  public Version(
      final int major,
      final int minor,
      final int patch,
      final int build) {
    this.major = major;
    this.minor = minor;
    this.patch = patch;
    this.build = build;
  }

  public Version(final int major, final int minor, final int patch) {
    this.major = major;
    this.minor = minor;
    this.patch = patch;
    this.build = 0;
  }

  public Version(final int major, final int minor) {
    this.major = major;
    this.minor = minor;
    this.patch = 0;
    this.build = 0;
  }

  public Version(final int major) {
    this.major = major;
    this.minor = 0;
    this.patch = 0;
    this.build = 0;
  }

  public Version(final String str) {
    String s = str.trim();
    if (s.startsWith("v") || s.startsWith("V")) {
      s = s.substring(1);
    }
    final String[] parts = s.split("[\\\\.|_|-]");
    if (parts.length < 1 || parts.length > 4) {
      throw new IllegalArgumentException("Invalid version string: " + str);
    }
    try {
      major = parseIntSafe(parts[0]);
      minor = (parts.length >= 2) ? parseIntSafe(parts[1]) : 0;
      patch = (parts.length == 3) ? parseIntSafe(parts[2]) : 0;
      build = (parts.length == 4) ? parseIntSafe(parts[3]) : 0;
    } catch (final NumberFormatException e) {
      throw new IllegalArgumentException("Invalid version string: " + str, e);
    }
  }

  private static int parseIntSafe(final String str) {
    try {
      return Integer.parseInt(str, 10);
    } catch (final Exception ignored) {
      return 0;
    }
  }

  @Override
  public int getMajor() {
    return major;
  }

  @Override
  public int getMinor() {
    return minor;
  }

  @Override
  public int getPatch() {
    return patch;
  }

  @Override
  public int getBuild() {
    return build;
  }

  @Override
  public String getDisplayName() {
    return getDisplayName(false);
  }

  @Override
  public String getDisplayName(final boolean includeEmpty) {
    return (major +
        ((minor == 0 && !includeEmpty) ? "" : "." + minor) +
        ((patch == 0 && !includeEmpty) ? "" : "." + patch) +
        ((build == 0 && !includeEmpty) ? "" : "." + build));
  }

  @Override
  public boolean isMajor(final IVersion version) {
    checkNotNull(version);
    return major == version.getMajor();
  }

  @Override
  public boolean isMinor(final IVersion version) {
    checkNotNull(version);
    return minor == version.getMinor();
  }

  @Override
  public boolean isPatch(final IVersion version) {
    checkNotNull(version);
    return patch == version.getPatch();
  }

  @Override
  public boolean isBuild(final IVersion version) {
    checkNotNull(version);
    return build == version.getBuild();
  }

  @Override
  public boolean isSame(final IVersion version) {
    checkNotNull(version);
    return (major == version.getMajor() &&
        minor == version.getMajor() &&
        patch == version.getPatch() &&
        build == version.getBuild());
  }

  @Override
  public boolean isNewerThan(final IVersion version) {
    checkNotNull(version);
    return ((major > version.getMajor()) ||
        (major == version.getMajor() && minor > version.getMinor()) ||
        (major == version.getMajor() &&
            minor == version.getMinor() &&
            patch > version.getPatch())
        ||
        (major == version.getMajor() &&
            minor == version.getMinor() &&
            patch == version.getPatch() &&
            build > version.getBuild()));
  }

  @Override
  public boolean isOlderThan(final IVersion version) {
    checkNotNull(version);
    return ((major < version.getMajor()) ||
        (major == version.getMajor() && minor < version.getMinor()) ||
        (major == version.getMajor() &&
            minor == version.getMinor() &&
            patch < version.getPatch())
        ||
        (major == version.getMajor() &&
            minor == version.getMinor() &&
            patch == version.getPatch() &&
            build < version.getBuild()));
  }

  @Override
  public boolean isAtLeast(final IVersion version) {
    checkNotNull(version);
    return ((major >= version.getMajor()) ||
        (major == version.getMajor() && minor >= version.getMinor()) ||
        (major == version.getMajor() &&
            minor == version.getMinor() &&
            patch >= version.getPatch())
        ||
        (major == version.getMajor() &&
            minor == version.getMinor() &&
            patch == version.getPatch() &&
            build >= version.getBuild()));
  }

  @Override
  public int compareTo(final Version v) {
    checkNotNull(v);
    if (major > v.getMajor())
      return 1;
    if (major < v.getMajor())
      return -1;
    if (minor > v.getMinor())
      return 1;
    if (minor < v.getMinor())
      return -1;
    if (patch > v.getPatch())
      return 1;
    if (patch < v.getPatch())
      return -1;
    if (build > v.getBuild())
      return 1;
    if (build < v.getBuild())
      return -1;
    return 0;
  }

  private void checkNotNull(final IVersion version) {
    if (version == null) {
      throw new IllegalArgumentException("Version cannot be null");
    }
  }

  @Override
  public Object clone() {
    return new Version(major, minor, patch, build);
  }

  @Override
  public boolean equals(final Object o) {
    if (!(o instanceof IVersion))
      return false;
    final IVersion other = (IVersion) o;
    return (major == other.getMajor() &&
        minor == other.getMajor() &&
        patch == other.getPatch() &&
        build == other.getBuild());
  }

  @Override
  public int hashCode() {
    int result = 31 * 9 + major;
    result = 31 * result + minor;
    result = 31 * result + patch;
    return 31 * result + build;
  }

  public String formatted() {
    return major + "." + minor + "." + patch + (build != 0 ? "." + build : "");
  }

  @Override
  public String toString() {
    return ("Version(major=" +
        major +
        "minor=" +
        minor +
        ", patch=" +
        patch +
        ", build=" +
        build +
        ")");
  }
}
