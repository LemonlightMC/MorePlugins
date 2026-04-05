package com.lemonlightmc.moreplugins.utils;

import java.io.File;

public class FileNameUtils {

  public static String getExtension(final String name) {
    return null;
  }

  public static String getBaseName(final String name) {
    return null;
  }

  public static String getBaseNameWithoutExtension(final String name) {
    return null;
  }

  public static String getFullPath(final String name) {
    return null;
  }

  public static String getCanonicalPath(final File file) {
    try {
      return file.getCanonicalPath();
    } catch (Exception e) {
      return null;
    }
  }
}
