package com.lemonlightmc.moreplugins.utils;

import java.io.File;
import java.util.Collection;
import java.util.Locale;
import java.util.stream.Stream;

public class FilenameUtils {

  private static final char OTHER_SEPARATOR = flipSeparator(File.separatorChar);
  public static final char SEPERATOR = File.separatorChar;
  private static final boolean supportsDriveLetter;

  static {
    if (getOsMatchesName("Linux")) {
      supportsDriveLetter = false;
    } else if (getOsMatchesName("Mac")) {
      supportsDriveLetter = false;
    } else if (getOsMatchesName("Windows")) {
      supportsDriveLetter = true;
    } else {
      supportsDriveLetter = false;
    }
  }

  private static boolean getOsMatchesName(final String osNamePrefix) {
    String str = null;
    try {
      str = System.getProperty("os.name");
    } catch (final SecurityException ex) {
    }
    return str != null && str.toUpperCase(Locale.ROOT).startsWith(osNamePrefix.toUpperCase(Locale.ROOT));
  }

  public static String getExtension(final String fileName) throws IllegalArgumentException {
    if (fileName == null) {
      return null;
    }
    final int index = indexOfExtension(fileName);
    if (index == -1) {
      return "";
    }
    return fileName.substring(index + 1);
  }

  public static boolean isExtension(final String fileName, final String extension) {
    if (fileName == null) {
      return false;
    }
    requireNonNullChars(fileName);
    final int idx = indexOfExtension(fileName);
    if (idx == -1) {
      return extension == null || extension.isEmpty();
    }
    return fileName.substring(idx + 1).equals(extension);
  }

  public static boolean isExtension(final String fileName, final Collection<String> extensions) {
    if (fileName == null) {
      return false;
    }
    requireNonNullChars(fileName);
    final int idx = indexOfExtension(fileName);
    if (idx == -1) {
      return extensions == null || extensions.isEmpty();
    }
    final String fileExt = fileName.substring(idx + 1);
    return extensions.contains(fileExt);
  }

  public static boolean isExtension(final String fileName, final String... extensions) {
    if (fileName == null) {
      return false;
    }
    requireNonNullChars(fileName);
    final int idx = indexOfExtension(fileName);
    if (idx == -1) {
      return extensions == null || extensions.length == 0;
    }
    final String fileExt = fileName.substring(idx + 1);
    return Stream.of(extensions).anyMatch(fileExt::equals);
  }

  public static String getName(final String fileName) {
    if (fileName == null) {
      return null;
    }
    return requireNonNullChars(fileName).substring(indexOfLastSeparator(fileName) + 1);
  }

  public static String getBaseName(final String fileName) {
    if (fileName == null) {
      return null;
    }
    requireNonNullChars(fileName);
    final int sepIdx = indexOfLastSeparator(fileName);
    final int extIdx = indexOfExtension(fileName);
    return fileName.substring(sepIdx + 1, extIdx == -1 ? fileName.length() : extIdx);
  }

  public static String getFullPath(final String fileName) {
    if (fileName == null || fileName.isEmpty()) {
      return null;
    }
    final int prefix = getPrefixLength(fileName);
    if (prefix < 0) {
      return null;
    }
    if (prefix >= fileName.length()) {
      return getPrefix(fileName);
    }
    final int index = indexOfLastSeparator(fileName);
    if (index < 0) {
      return fileName.substring(0, prefix);
    }
    return fileName.substring(0, index == -1 ? 1 : index + 1);
  }

  public static String getPath(final String fileName) {
    if (fileName == null || fileName.isEmpty()) {
      return null;
    }
    final int prefix = getPrefixLength(fileName);
    if (prefix < 0) {
      return null;
    }
    final int index = indexOfLastSeparator(fileName);
    if (prefix >= fileName.length() || index < 0 || prefix >= index + 1) {
      return "";
    }
    return requireNonNullChars(fileName.substring(prefix, index + 1));
  }

  public static String getPrefix(final String fileName) {
    if (fileName == null || fileName.isEmpty()) {
      return null;
    }
    final int len = getPrefixLength(fileName);
    if (len < 0) {
      return null;
    }
    if (len > fileName.length()) {
      requireNonNullChars(fileName);
      return fileName + '/';
    }
    return requireNonNullChars(fileName.substring(0, len));
  }

  public static String normalize(final String fileName) {
    return doNormalize(fileName, File.separatorChar);
  }

  public static String normalize(final String fileName, final boolean unixSeparator) {
    return doNormalize(fileName, unixSeparator ? '/' : '\\');
  }

  private static int indexOfExtension(final String fileName) throws IllegalArgumentException {
    if (isSystemWindows()) {
      if (fileName.indexOf(':', getAdsCriticalOffset(fileName)) != -1) {
        throw new IllegalArgumentException("NTFS ADS separator (':') in file name is forbidden.");
      }
    }
    final int extensionPos = fileName.lastIndexOf('.');
    return indexOfLastSeparator(fileName) > extensionPos ? -1 : extensionPos;
  }

  private static int indexOfLastSeparator(final String fileName) {
    return Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
  }

  private static int getPrefixLength(final String fileName) {
    final int len = fileName.length();
    char ch0 = fileName.charAt(0);
    if (ch0 == ':') {
      return -1;
    }
    if (len == 1) {
      if (ch0 == '~') {
        return 2; // return a length greater than the input
      }
      return isSeparator(ch0) ? 1 : 0;
    }
    if (ch0 == '~') {
      int posUnix = fileName.indexOf('/', 1);
      int posWin = fileName.indexOf('\\', 1);
      if (posUnix == -1 && posWin == -1) {
        return len + 1; // return a length greater than the input
      }
      posUnix = posUnix == -1 ? posWin : posUnix;
      posWin = posWin == -1 ? posUnix : posWin;
      return Math.min(posUnix, posWin) + 1;
    }
    final char ch1 = fileName.charAt(1);
    if (ch1 == ':') {
      ch0 = Character.toUpperCase(ch0);
      if (ch0 >= 'A' && ch0 <= 'Z') {
        if (len == 2 && !supportsDriveLetter) {
          return 0;
        }
        if (len == 2 || !isSeparator(fileName.charAt(2))) {
          return 2;
        }
        return 3;
      }
      return ch0 == '/' ? 1 : -1;

    }
    return !isSeparator(ch0) ? 0 : !isSeparator(ch1) ? 1 : -1;
  }

  private static int getAdsCriticalOffset(final String fileName) {
    final int offset1 = fileName.lastIndexOf(File.separatorChar);
    final int offset2 = fileName.lastIndexOf(OTHER_SEPARATOR);
    if (offset1 == -1) {
      return offset2 == -1 ? 0 : offset2 + 1;
    }
    if (offset2 == -1) {
      return offset1 + 1;
    }
    return Math.max(offset1, offset2) + 1;
  }

  private static String requireNonNullChars(final String path) {
    if (path.indexOf(0) >= 0) {
      throw new IllegalArgumentException(
          "Null character present in file/path name. There are no known legitimate use cases for such data, but several injection attacks may use it");
    }
    return path;
  }

  private static boolean isSystemWindows() {
    return File.separatorChar == '\\';
  }

  private static boolean isSeparator(final char ch) {
    return ch == '/' || ch == '\\';
  }

  private static char flipSeparator(final char ch) {
    if (ch == '/') {
      return '\\';
    }
    if (ch == '\\') {
      return '/';
    }
    throw new IllegalArgumentException(String.valueOf(ch));
  }

  private static String doNormalize(final String fileName, final char separator) {
    if (fileName == null) {
      return null;
    }
    requireNonNullChars(fileName);
    int size = fileName.length();
    if (size == 0) {
      return fileName;
    }
    final int prefix = getPrefixLength(fileName);
    if (prefix < 0) {
      return null;
    }
    final char[] array = new char[size + 2]; // +1 for possible extra slash, +2 for arraycopy
    fileName.getChars(0, fileName.length(), array, 0);
    // fix separators throughout
    final char otherSeparator = flipSeparator(separator);
    for (int i = 0; i < array.length; i++) {
      if (array[i] == otherSeparator) {
        array[i] = separator;
      }
    }
    // add extra separator on the end to simplify code below
    boolean lastIsDirectory = true;
    if (array[size - 1] != separator) {
      array[size++] = separator;
      lastIsDirectory = false;
    }
    // adjoining slashes
    // If we get here, prefix can only be 0 or greater, size 1 or greater
    // If prefix is 0, set loop start to 1 to prevent index errors
    for (int i = prefix != 0 ? prefix : 1; i < size; i++) {
      if (array[i] == separator && array[i - 1] == separator) {
        System.arraycopy(array, i, array, i - 1, size - i);
        size--;
        i--;
      }
    }
    // period slash
    for (int i = prefix + 1; i < size; i++) {
      if (array[i] == separator && array[i - 1] == '.' && (i == prefix + 1 || array[i - 2] == separator)) {
        if (i == size - 1) {
          lastIsDirectory = true;
        }
        System.arraycopy(array, i + 1, array, i - 1, size - i);
        size -= 2;
        i--;
      }
    }
    // double period slash
    outer: for (int i = prefix + 2; i < size; i++) {
      if (array[i] == separator && array[i - 1] == '.' && array[i - 2] == '.'
          && (i == prefix + 2 || array[i - 3] == separator)) {
        if (i == prefix + 2) {
          return null;
        }
        if (i == size - 1) {
          lastIsDirectory = true;
        }
        int j;
        for (j = i - 4; j >= prefix; j--) {
          if (array[j] == separator) {
            // remove b/../ from a/b/../c
            System.arraycopy(array, i + 1, array, j + 1, size - i);
            size -= i - j;
            i = j + 1;
            continue outer;
          }
        }
        // remove a/../ from a/../c
        System.arraycopy(array, i + 1, array, prefix, size - i);
        size -= i + 1 - prefix;
        i = prefix + 1;
      }
    }
    if (size <= 0) { // should never be less than 0
      return "";
    }
    if (size <= prefix || lastIsDirectory) {
      return new String(array, 0, size); // keep trailing separator
    }
    return new String(array, 0, size - 1); // lose trailing separator
  }
}
