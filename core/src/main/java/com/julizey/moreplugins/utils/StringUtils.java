package com.julizey.moreplugins.utils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class StringUtils {
  private static final byte[] HEX_ARRAY = "0123456789abcdef".getBytes(StandardCharsets.US_ASCII);

  public record Replaceable(String placeholder, String value) {
    public String apply(final String message) {
      return message.replaceAll(placeholder, value);
    }
  }

  public static boolean isBlank(final String str) {
    return str == null || str.length() == 0;
  }

  public static boolean isEmpty(final String str) {
    return str == null;
  }

  public static String applyReplacements(String message, final String... replacements) {
    if (message == null || message.length() == 0)
      return null;
    message = message.replaceAll("\\n", "\n");
    for (int i = 0; i < replacements.length; i += 2) {
      message = message.replaceAll(replacements[i], replacements[i + 1]);
    }
    return message;
  }

  public static String applyReplacements(String message, final Replaceable... replacements) {
    if (message == null || message.length() == 0)
      return null;
    message = message.replace("\\n", "\n");
    for (int i = 0; i < replacements.length; i++) {
      message = replacements[i].apply(message);
    }
    return message;
  }

  public static String bytesToHex(final byte[] bytes) {
    final byte[] hexChars = new byte[bytes.length * 2];
    for (int j = 0; j < bytes.length; j++) {
      final int v = bytes[j] & 0xFF;
      hexChars[j * 2] = HEX_ARRAY[v >>> 4];
      hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
    }
    return new String(hexChars, StandardCharsets.UTF_8);
  }

  public static Locale parseLocale(final String string) {
    final String[] segments = string.split("_", 3); // language_country_variant
    final int length = segments.length;
    if (length == 1) {
      return Locale.of(string); // language
    } else if (length == 2) {
      return Locale.of(segments[0], segments[1]); // language + country
    } else if (length == 3) {
      return Locale.of(segments[0], segments[1], segments[2]); // language + country + variant
    }
    return null;
  }

  public static String join(final String delimiter, final String[] strings) {
    final StringBuilder sb = new StringBuilder();
    for (final String string : strings) {
      sb.append(delimiter).append(string);
    }
    return sb.substring(1);
  }

  public static String join(final String delimiter, final String[] strings, final int begin) {
    return join(delimiter, strings, begin, strings.length);
  }

  public static String join(final String delimiter, final String[] strings, final int begin, final int end) {
    final StringBuilder sb = new StringBuilder();
    for (int i = begin; i < end; i++) {
      sb.append(delimiter).append(strings[i]);
    }
    return sb.substring(1);
  }

  public static String join(final String delimiter, final List<String> strings) {
    final StringBuilder sb = new StringBuilder();
    for (final String string : strings) {
      sb.append(delimiter).append(string);
    }
    return sb.substring(1);
  }

  public static String join(final String delimiter, final List<String> strings, final int begin) {
    return join(delimiter, strings, begin, strings.size());
  }

  public static String join(final String delimiter, final List<String> strings, final int begin, final int end) {
    final StringBuilder sb = new StringBuilder();
    for (int i = begin; i < end; i++) {
      sb.append(delimiter).append(strings.get(i));
    }
    return sb.substring(1);
  }

  public static String join(final String delimiter, final Collection<String> strings) {
    final StringBuilder sb = new StringBuilder();
    for (final String string : strings) {
      sb.append(delimiter).append(string);
    }
    return sb.substring(1);
  }

  public static String join(final String delimiter, final Set<String> strings) {
    final StringBuilder sb = new StringBuilder();
    for (final String string : strings) {
      sb.append(delimiter).append(string);
    }
    return sb.substring(1);
  }

  @SuppressWarnings("unchecked")
  public static <T extends Collection<? super String>> T copyPartialMatches(final String token,
      final Iterable<String> originals, T collection) {
    if (collection == null) {
      collection = (T) new ArrayList<>();
    }
    if (originals == null) {
      return collection;
    }
    for (final String string : originals) {
      if (startsWithIgnoreCase(string, token)) {
        collection.add(string);
      }
    }

    return collection;
  }

  public static boolean startsWithIgnoreCase(final String string, final String prefix) {
    if (prefix == null) {
      return true;
    }
    if (string == null || string.length() < prefix.length()) {
      return false;
    }
    return string.regionMatches(true, 0, prefix, 0, prefix.length());
  }
}
