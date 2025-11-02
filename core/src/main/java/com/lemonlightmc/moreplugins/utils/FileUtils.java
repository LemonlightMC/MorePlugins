package com.lemonlightmc.moreplugins.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import com.lemonlightmc.moreplugins.base.MorePlugins;
import com.lemonlightmc.moreplugins.messages.Logger;

public class FileUtils {

  public static boolean existsFile(final Path path) {
    try {
      return Files.exists(path);
    } catch (final Exception e) {
      return false;
    }
  }

  public static boolean isFile(final Path path) {
    try {
      return Files.isRegularFile(path);
    } catch (final Exception e) {
      return false;
    }
  }

  public static boolean isDirectory(final Path path) {
    try {
      return Files.isDirectory(path);
    } catch (final Exception e) {
      return false;
    }
  }

  public static boolean isSymbolicLink(final Path path) {
    try {
      return Files.isSymbolicLink(path);
    } catch (final Exception e) {
      return false;
    }
  }

  public static boolean isExecutable(final Path path) {
    try {
      return Files.isExecutable(path);
    } catch (final Exception e) {
      return false;
    }
  }

  public static String getHash(final Path path) {
    byte[] digest;
    try {
      digest = MessageDigest.getInstance("MD5").digest(Files.readAllBytes(path));
    } catch (final Exception ex) {
      return null;
    }
    return StringUtils.bytesToHex(digest);
  }

  public static String readString(final Path path) {
    try {
      return Files.readString(path, StandardCharsets.UTF_8);
    } catch (final Exception ignored) {
    }
    return "";
  }

  public static void writeString(final Path path, final String text) {
    try {
      if (!Files.exists(path)) {
        mkdirs(path.toFile());
      }
      Files.writeString(path, text, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING,
          StandardOpenOption.CREATE);
    } catch (final Exception ignored) {
    }
  }

  public static void appendString(final Path path, final String text) {
    try {
      if (!Files.exists(path)) {
        mkdirs(path.toFile());
      }
      Files.writeString(path, text, StandardCharsets.UTF_8, StandardOpenOption.APPEND,
          StandardOpenOption.CREATE);
    } catch (final Exception ignored) {
    }
  }

  public static void mkdirs(final String file) {
    mkdirs(new File(file));
  }

  public static void mkdirs(final Path file) {
    mkdirs(file.toFile());
  }

  public static void mkdirs(final File file) {
    try {
      final File parent = file.getCanonicalFile().getParentFile();
      if (parent == null) {
        return;
      }
      final boolean success = parent.mkdirs();
      if (!success || !parent.isDirectory()) {
        throw new IOException();
      }
    } catch (final Exception ignored) {
    }
  }

  public static boolean hasResource(final String path) {
    final URL url = MorePlugins.instance.getClass().getClassLoader().getResource(path);
    return url != null;
  }

  public static List<String> getResourcesList(final String path) {
    try {
      final URL url = MorePlugins.instance.getClass().getClassLoader().getResource(path);
      final File file = url == null ? null : new File(url.toURI());
      if (file == null || !file.isDirectory()) {
        return List.of();
      }
      return List.of(file.list());
    } catch (final Exception e) {
      Logger.warn("Failed to get List of Resources for: " + path);
      e.printStackTrace();
      return null;
    }
  }

  public static File getResource(final String path) {
    try {
      final URL url = MorePlugins.instance.getClass().getClassLoader().getResource(path);
      if (url == null)
        return null;
      return new File(url.toURI());
    } catch (final Exception e) {
      Logger.warn("Failed to read Resource: " + path);
      e.printStackTrace();
      return null;
    }
  }

  public static boolean saveResource(final File inFile, final File target) {
    try {
      if (target.exists()) {
        return false;
      }
      Files.copy(inFile.toPath(), target.toPath());
      return true;
    } catch (final Exception e) {
      Logger.warn("Failed to save resource to: " + target.getPath());
      e.printStackTrace();
      return false;
    }
  }

  public static boolean saveResource(final InputStream in, final File target) {
    try {
      if (target.exists()) {
        return false;
      }
      Files.copy(in, target.toPath());
      return true;
    } catch (final Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public static Properties loadPropertiesFromJar(final String resourcePath) {
    final Properties props = new Properties();
    try (InputStream in = MorePlugins.instance.getClass().getResourceAsStream(resourcePath)) {
      if (in == null)
        return null;
      props.load(new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)));
      return props;
    } catch (final Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static Properties loadPropertiesFromFile(final File file) {
    if (!file.exists() || !file.isFile() || !file.getAbsolutePath().endsWith(".properties")) {
      return null;
    }
    final Properties props = new Properties();
    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
      props.load(reader);
      return props;
    } catch (final Exception e) {
      Logger.warn("Failed to read properties from: " + file.getPath());
      e.printStackTrace();
      return null;
    }
  }

  public static void savePropertiesToFile(final Properties props, final File file) {
    if (!file.exists() || !file.isFile() || !file.getAbsolutePath().endsWith(".properties")) {
      return;
    }
    try (BufferedWriter writer = new BufferedWriter(
        new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
      final List<String> keys = new ArrayList<>(props.stringPropertyNames());
      Collections.sort(keys);
      for (final String key : keys) {
        writer.write(key + "=" + props.getProperty(key));
        writer.newLine();
      }
    } catch (final Exception e) {
      Logger.warn("Failed to save properties to: " + file.getPath());
      e.printStackTrace();
    }
  }

  public static String getFileExtension(final File file) {
    final String name = file.getName();
    final int lastIndex = name.lastIndexOf('.');
    if (lastIndex == -1) {
      return "";
    }
    return name.substring(lastIndex + 1);
  }

  public static String getFileExtension(final String filename) {
    final int lastIndex = filename.lastIndexOf('.');
    if (lastIndex == -1) {
      return "";
    }
    return filename.substring(lastIndex + 1);
  }

  public static String getResourceBundleString(final ResourceBundle bundle, final String key) {
    try {
      return bundle.getString(key);
    } catch (final Exception e) {
      return null;
    }
  }

  public static String getResourceBundleString(final ResourceBundle bundle, final String key, final String def) {
    try {
      final String value = bundle.getString(key);
      return value != null ? value : def;
    } catch (final Exception e) {
      return def;
    }
  }

  public static boolean resourceBundleContainsKey(final ResourceBundle bundle, final String key) {
    try {
      return bundle.containsKey(key);
    } catch (final Exception e) {
      return false;
    }
  }

  public static int getResourceBundleSize(final ResourceBundle bundle) {
    try {
      return bundle.keySet().size();
    } catch (final Exception e) {
      return 0;
    }
  }

  public static List<String> getResourceBundleKeys(final ResourceBundle bundle) {
    try {
      return new ArrayList<>(bundle.keySet());
    } catch (final Exception e) {
      return List.of();
    }
  }

  public static List<String> getResourceBundleValues(final ResourceBundle bundle) {
    try {
      final List<String> values = new ArrayList<>();
      for (final String key : bundle.keySet()) {
        values.add(bundle.getString(key));
      }
      return values;
    } catch (final Exception e) {
      return List.of();
    }
  }

  public static Properties resourceBundleToProperties(final ResourceBundle bundle) {
    final Properties props = new Properties();
    try {
      for (final String key : bundle.keySet()) {
        props.setProperty(key, bundle.getString(key));
      }
    } catch (final Exception e) {
      return null;
    }
    return props;
  }

}
