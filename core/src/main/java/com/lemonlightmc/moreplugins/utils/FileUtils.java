package com.lemonlightmc.moreplugins.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FileUtils {

  public static record FileResult(boolean success, String execption, Object data) {
    public static FileResult successful(final Object data) {
      return new FileResult(true, null, data);
    }

    public static FileResult successful() {
      return new FileResult(true, null, null);
    }

    public static FileResult failed(final String exception) {
      return new FileResult(false, exception, null);
    }
  }

  public static boolean exists(final Path path) {
    try {
      return Files.exists(path);
    } catch (final Exception e) {
      return false;
    }
  }

  public static boolean exists(final File file) {
    try {
      return Files.exists(file.toPath());
    } catch (final Exception e) {
      return false;
    }
  }

  public static boolean notExists(final Path path) {
    try {
      return Files.notExists(path);
    } catch (final Exception e) {
      return false;
    }
  }

  public static boolean notExists(final File file) {
    try {
      return Files.notExists(file.toPath());
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

  public static boolean isFile(final File file) {
    try {
      return Files.isRegularFile(file.toPath());
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

  public static boolean isDirectory(final File file) {
    try {
      return Files.isDirectory(file.toPath());
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

  public static boolean isSymbolicLink(final File file) {
    try {
      return Files.isSymbolicLink(file.toPath());
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

  public static boolean isExecutable(final File file) {
    try {
      return Files.isExecutable(file.toPath());
    } catch (final Exception e) {
      return false;
    }
  }

  public static boolean isHidden(final Path path) {
    try {
      return Files.isHidden(path);
    } catch (final Exception e) {
      return false;
    }
  }

  public static boolean isHidden(final File file) {
    try {
      return Files.isHidden(file.toPath());
    } catch (final Exception e) {
      return false;
    }
  }

  public static boolean isRegularFile(final Path path) {
    try {
      return Files.isRegularFile(path);
    } catch (final Exception e) {
      return false;
    }
  }

  public static boolean isRegularFile(final File file) {
    try {
      return Files.isRegularFile(file.toPath());
    } catch (final Exception e) {
      return false;
    }
  }

  public static boolean isReadable(final Path path) {
    try {
      return Files.isReadable(path);
    } catch (final Exception e) {
      return false;
    }
  }

  public static boolean isReadable(final File file) {
    try {
      return Files.isReadable(file.toPath());
    } catch (final Exception e) {
      return false;
    }
  }

  public static boolean isWriteable(final Path path) {
    try {
      return Files.isWritable(path);
    } catch (final Exception e) {
      return false;
    }
  }

  public static boolean isWriteable(final File file) {
    try {
      return Files.isWritable(file.toPath());
    } catch (final Exception e) {
      return false;
    }
  }

  public static String readString(final Path path) {
    try {
      return Files.readString(path, StandardCharsets.UTF_8);
    } catch (final Exception ex) {
    }
    return null;
  }

  public static String readString(final File file) {
    try {
      return Files.readString(file.toPath(), StandardCharsets.UTF_8);
    } catch (final Exception ex) {
    }
    return null;
  }

  public static List<String> readLines(final Path path) {
    try {
      return Files.readAllLines(path, StandardCharsets.UTF_8);
    } catch (final Exception ex) {
    }
    return null;
  }

  public static List<String> readLines(final File file) {
    try {
      return Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
    } catch (final Exception ex) {
    }
    return null;
  }

  public static byte[] readBytes(final Path path) {
    try {
      return Files.readAllBytes(path);
    } catch (final Exception ex) {
    }
    return null;
  }

  public static FileResult write(final Path path, final byte[] text) {
    try {
      mkdirs(path.toFile());
      Files.write(path, text, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
      return FileResult.successful();
    } catch (final Exception ex) {
      return FileResult.failed("Failed to write to file: " + ex.getMessage());
    }
  }

  public static FileResult write(final Path path, final Iterable<? extends CharSequence> text) {
    try {
      mkdirs(path.toFile());
      Files.write(path, text, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING,
          StandardOpenOption.CREATE);
      return FileResult.successful();
    } catch (final Exception ex) {
      return FileResult.failed("Failed to write to file: " + ex.getMessage());
    }
  }

  public static FileResult write(final Path path, final String text) {
    try {
      mkdirs(path.toFile());
      Files.writeString(path, text, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING,
          StandardOpenOption.CREATE);
      return FileResult.successful();
    } catch (final Exception ex) {
      return FileResult.failed("Failed to write to file: " + ex.getMessage());
    }
  }

  public static FileResult append(final Path path, final byte[] text) {
    try {
      mkdirs(path.toFile());
      Files.write(path, text, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
      return FileResult.successful();
    } catch (final Exception ex) {
      return FileResult.failed("Failed to append to file: " + ex.getMessage());
    }
  }

  public static FileResult append(final Path path, final Iterable<? extends CharSequence> text) {
    try {
      mkdirs(path.toFile());
      Files.write(path, text, StandardCharsets.UTF_8, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
      return FileResult.successful();
    } catch (final Exception ex) {
      return FileResult.failed("Failed to append to file: " + ex.getMessage());
    }
  }

  public static FileResult append(final Path path, final String text) {
    try {
      mkdirs(path.toFile());
      Files.writeString(path, text, StandardCharsets.UTF_8, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
      return FileResult.successful();
    } catch (final Exception ex) {
      return FileResult.failed("Failed to append to file: " + ex.getMessage());
    }
  }

  public static FileResult delete(final Path path) {
    try {
      if (path == null || Files.notExists(path)) {
        return FileResult.successful();
      }
      if (Files.isDirectory(path) && !isSymbolicLink(path)) {
        for (final File f : listFiles(path.toFile())) {
          if (!isSymbolicLink(f.toPath()) && !delete(f.toPath()).success) {
            return FileResult.failed("Failed to delete file (in directory): " + f);
          }
        }
      }
      Files.delete(path);
      return FileResult.successful();
    } catch (final Exception e) {
      return FileResult.failed("Failed to delete file: " + e.getMessage());
    }
  }

  public static FileResult mkdirs(final String file) {
    if (file == null || file.isEmpty()) {
      return FileResult.failed("File path must not be null or empty");
    }
    return mkdirs(new File(file));
  }

  public static FileResult mkdirs(final Path file) {
    if (file == null) {
      return FileResult.failed("File path must not be null");
    }
    return mkdirs(file.toFile());
  }

  public static FileResult mkdirs(final File file) {
    if (file == null) {
      return FileResult.failed("File path must not be null");
    }
    try {
      if (file.exists()) {
        return FileResult.successful();
      }
      final File canonFile = file.getCanonicalFile();
      final File parent = canonFile.getParentFile();
      if (parent != null && (parent.mkdirs() || parent.exists())) {
        return FileResult.successful();
      } else {
        return FileResult.failed("Failed to create parent directories: " + file);
      }
    } catch (final Exception ex) {
      return FileResult.failed("Failed to create parent directories: " + ex.getMessage());
    }
  }

  public static List<File> listFiles(final Path directory) {
    return directory == null ? List.of() : listFiles(directory.toFile());
  }

  public static List<File> listFiles(final File directory) {
    final File[] listFiles = directory.listFiles();
    return listFiles == null ? List.of() : List.of(listFiles);
  }

  public static List<File> listFiles(final Path directory, final Predicate<File> filter, final boolean recursive) {
    return directory == null ? List.of() : listFiles(directory.toFile());
  }

  public static List<File> listFiles(final File directory, final boolean recursive, final FilenameFilter filter) {
    if (filter == null) {
      return listFiles(directory, (Predicate<File>) null, recursive);
    }
    return listFiles(directory, (f) -> filter.accept(f, f.getName()), recursive);
  }

  public static List<File> listFiles(final Path directory, final String[] extensions, final boolean recursive) {
    return directory == null ? List.of() : listFiles(directory.toFile());
  }

  public static List<File> listFiles(final File directory, final String[] extensions, final boolean recursive) {
    if (extensions == null || extensions.length == 0) {
      return listFiles(directory, (Predicate<File>) null, recursive);
    }
    final List<String> ext = List.of(extensions);
    return listFiles(directory, (f) -> ext.contains(FileNameUtils.getExtension(f.getName())), recursive);
  }

  public static List<File> listFiles(final File directory, final Predicate<File> filter, final boolean recursive) {
    if (directory == null || !directory.isDirectory() || !directory.exists()) {
      return List.of();
    }
    final File[] listFiles = directory.listFiles();
    if (listFiles == null) {
      return List.of();
    }
    final List<File> files = new ArrayList<>();
    for (final File file : listFiles) {
      if (filter != null && !filter.test(file)) {
        continue;
      }
      files.add(file);
      if (recursive && file.isDirectory()) {
        files.addAll(listFiles(directory, filter, recursive));
      }
    }
    return files;
  }

  public static FileResult moveFile(final File srcFile, final File destFile) throws IOException {
    return moveFile(srcFile, destFile, StandardCopyOption.COPY_ATTRIBUTES);
  }

  public static FileResult moveFile(final File srcFile, final File destFile, final CopyOption... copyOptions) {
    if (srcFile == null || !srcFile.exists()) {
      return FileResult.failed("Source File must exist and not be null");
    }
    if (destFile == null) {
      return FileResult.failed("Destination File must not be null");
    }
    if (destFile.exists()) {
      delete(destFile.toPath());
    }
    final boolean rename = srcFile.renameTo(destFile);
    if (!rename) {
      if (!copy(srcFile, destFile, copyOptions).success && !srcFile.delete()) {
        delete(destFile.toPath());
        return FileResult.failed("Failed to delete original file '" + srcFile + "' after copy to '" + destFile + "'");
      }
    }
    return FileResult.successful();
  }

  public static FileResult copy(final File src, final File dest) {
    return copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
  }

  public static FileResult copy(final File src, final File dest, final CopyOption... copyOptions) {
    if (src == null) {
      return FileResult.failed("Source File must not be null");
    }
    if (!src.exists()) {
      return FileResult.failed("Source File must exist");
    }
    if (dest == null) {
      return FileResult.failed("Destination File must not be null");
    }
    final String destCanonicalPath = FileNameUtils.getCanonicalPath(src);
    final String srcCanonicalPath = FileNameUtils.getCanonicalPath(dest);
    if (srcCanonicalPath != null && srcCanonicalPath.equals(destCanonicalPath)) {
      return FileResult.failed("Source '" + src + "' and destination '" + dest + "' are the same");
    }
    final FileResult result = mkdirs(dest);
    if (!result.success)
      return result;

    if (src.isFile()) {
      try {
        if (dest.exists() && dest.isDirectory()) {
          return FileResult.failed("Destination '" + dest + "' exists but is a directory");
        }
        Files.copy(src.toPath(), dest.toPath(), copyOptions);
        return FileResult.successful();
      } catch (final Exception e) {
        return FileResult.failed("Failed to copy file: " + e.getMessage());
      }
    } else if (src.isDirectory()) {
      if (dest.exists() && !dest.isDirectory()) {
        return FileResult.failed("Destination '" + dest + "' exists but is not a directory");
      }
      try {
        final List<String> exclusionList = destCanonicalPath != null && srcCanonicalPath != null
            && destCanonicalPath.startsWith(srcCanonicalPath)
                ? createCopyExclusionList(src, dest)
                : null;
        doCopyDirectory(src, dest, exclusionList, copyOptions);
        return FileResult.successful();
      } catch (final Exception e) {
        return FileResult.failed("Failed to copy directory: " + e.getMessage());
      }
    } else {
      return FileResult.failed("Source is neither a file nor a directory");
    }
  }

  private static List<String> createCopyExclusionList(final File srcDir, final File destDir) {
    final List<File> srcFiles = listFiles(srcDir);
    if (srcFiles.size() == 0) {
      return null;
    }
    final List<String> exclusionList = new ArrayList<>(srcFiles.size());
    for (final File srcFile : srcFiles) {
      exclusionList.add(FileNameUtils.getCanonicalPath(new File(destDir, srcFile.getName())));
    }
    return exclusionList;
  }

  private static void doCopyDirectory(final File srcDir, final File destDir,
      final List<String> exclusionList, final CopyOption... copyOptions) throws IOException {
    for (final File srcFile : listFiles(srcDir)) {
      final File destFile = new File(destDir, srcFile.getName());
      if (exclusionList != null && exclusionList.contains(FileNameUtils.getCanonicalPath(srcDir))) {
        continue;
      }
      if (srcFile.isDirectory()) {
        doCopyDirectory(srcFile, destFile, exclusionList, copyOptions);
      } else {
        Files.copy(srcFile.toPath(), destFile.toPath(), copyOptions);
      }
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
}
