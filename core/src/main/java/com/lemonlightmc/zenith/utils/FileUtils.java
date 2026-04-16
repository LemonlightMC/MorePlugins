package com.lemonlightmc.zenith.utils;

import java.io.*;
import java.nio.charset.Charset;
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

import com.lemonlightmc.zenith.exceptions.FileException;
import com.lemonlightmc.zenith.messages.Logger;

public class FileUtils {

  public static record FileResult(boolean success, boolean invalid, boolean failed, String message, Object data) {
    public static FileResult successful(final Object data) {
      return new FileResult(true, false, false, null, data);
    }

    public static FileResult successful() {
      return new FileResult(true, false, false, null, null);
    }

    public static FileResult invalid(final String message) {
      return new FileResult(false, true, false, message, null);
    }

    public static FileResult failed(final String message) {
      return new FileResult(false, false, true, message, null);
    }

    public static FileResult failed(final Exception ex) {
      return new FileResult(false, false, true, ex.getMessage(), null);
    }

    public FileResult throwIfFailed() {
      if (failed) {
        throw new FileException(message);
      }
      return this;
    }

    public FileResult throwIfFailed(final String message) {
      if (failed) {
        throw new FileException(message);
      }
      return this;
    }

    public <E extends RuntimeException> FileResult throwIfFailed(final E exception) {
      if (failed) {
        throw exception;
      }
      return this;
    }

    public <E extends RuntimeException> FileResult throwIfFailed(final Class<E> exceptionType) {
      if (!failed) {
        return this;
      }
      final E ex;
      try {
        ex = exceptionType.getConstructor(String.class).newInstance(message);
      } catch (final Exception e) {
        throw new FileException(message);
      }
      throw ex;
    }

    public <E extends RuntimeException> FileResult throwIfFailedWith(final Class<E> exceptionType,
        final String message2) {
      if (!failed) {
        return this;
      }
      final E ex;
      try {
        ex = exceptionType.getConstructor(String.class).newInstance(message2 + " " + message);
      } catch (final Exception e) {
        throw new FileException(message);
      }
      throw ex;
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
    if (path == null) {
      return null;
    }
    try {
      return Files.readString(path, StandardCharsets.UTF_8);
    } catch (final Exception ex) {
      return null;
    }
  }

  public static String readString(final File file) {
    if (file == null) {
      return null;
    }
    try {
      return Files.readString(file.toPath(), StandardCharsets.UTF_8);
    } catch (final Exception ex) {
      return null;
    }
  }

  public static List<String> readLines(final Path path) {
    if (path == null) {
      return null;
    }
    try {
      return Files.readAllLines(path, StandardCharsets.UTF_8);
    } catch (final Exception ex) {
      return null;
    }
  }

  public static List<String> readLines(final File file) {
    if (file == null) {
      return null;
    }
    try {
      return Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
    } catch (final Exception ex) {
      return null;
    }
  }

  public static byte[] readBytes(final Path path) {
    if (path == null) {
      return null;
    }
    try {
      return Files.readAllBytes(path);
    } catch (final Exception ex) {
      return null;
    }
  }

  public static String readFile(final Path path) {
    return readFile(path, StandardCharsets.UTF_8);
  }

  public static String readFile(final Path path, final Charset charset) {
    if (path == null) {
      return null;
    }
    try (BufferedReader input = createReader(path)) {
      final StringBuilder builder = new StringBuilder();
      String line;
      while ((line = input.readLine()) != null) {
        builder.append(line);
        builder.append('\n');
      }
      input.close();
      return builder.toString();
    } catch (final Exception e) {
      Logger.warn("Failed to read File: " + path.toString());
      return null;
    }
  }

  public static FileResult write(final Path path, final byte[] text) {
    if (path == null) {
      return FileResult.invalid("Path must not be null");
    }
    if (text == null) {
      return FileResult.invalid("Text must not be null");
    }
    try {
      mkdirs(path.toFile());
      Files.write(path, text, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
      return FileResult.successful();
    } catch (final Exception ex) {
      return FileResult.failed("Failed to write to file: " + ex.getMessage());
    }
  }

  public static FileResult write(final Path path, final Iterable<? extends CharSequence> text) {
    if (path == null) {
      return FileResult.invalid("Path must not be null");
    }
    if (text == null) {
      return FileResult.invalid("Text must not be null");
    }
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
    if (path == null) {
      return FileResult.invalid("Path must not be null");
    }
    if (text == null) {
      return FileResult.invalid("Text must not be null");
    }
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
    if (path == null) {
      return FileResult.invalid("Path must not be null");
    }
    if (text == null) {
      return FileResult.invalid("Text must not be null");
    }
    try {
      mkdirs(path.toFile());
      Files.write(path, text, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
      return FileResult.successful();
    } catch (final Exception ex) {
      return FileResult.failed("Failed to append to file: " + ex.getMessage());
    }
  }

  public static FileResult append(final Path path, final Iterable<? extends CharSequence> text) {
    if (path == null) {
      return FileResult.invalid("Path must not be null");
    }
    if (text == null) {
      return FileResult.invalid("Text must not be null");
    }
    try {
      mkdirs(path.toFile());
      Files.write(path, text, StandardCharsets.UTF_8, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
      return FileResult.successful();
    } catch (final Exception ex) {
      return FileResult.failed("Failed to append to file: " + ex.getMessage());
    }
  }

  public static FileResult append(final Path path, final String text) {
    if (path == null) {
      return FileResult.invalid("Path must not be null");
    }
    if (text == null) {
      return FileResult.invalid("Text must not be null");
    }
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
      return FileResult.invalid("File path must not be null or empty");
    }
    return mkdirs(new File(file));
  }

  public static FileResult mkdirs(final Path file) {
    if (file == null) {
      return FileResult.invalid("File path must not be null");
    }
    return mkdirs(file.toFile());
  }

  public static FileResult mkdirs(final File file) {
    if (file == null) {
      return FileResult.invalid("File path must not be null");
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

  public static List<File> listFiles(final Path directory, final Predicate<File> filter) {
    return directory == null ? List.of() : listFiles(directory.toFile());
  }

  public static List<File> listFiles(final Path directory, final String[] extensions) {
    return directory == null ? List.of() : listFiles(directory.toFile());
  }

  public static List<File> listFiles(final File directory) {
    if (directory == null || !directory.isDirectory() || !directory.exists()) {
      return List.of();
    }
    final File[] listFiles = directory.listFiles();
    return listFiles == null ? List.of() : List.of(listFiles);
  }

  public static List<File> listFiles(final File directory, final boolean recursive, final FilenameFilter filter) {
    if (filter == null) {
      return listFiles(directory, (Predicate<File>) null, recursive);
    }
    return listFiles(directory, (f) -> filter.accept(f, f.getName()), recursive);
  }

  public static List<File> listFiles(final File directory, final String[] extensions, final boolean recursive) {
    if (extensions == null || extensions.length == 0) {
      return listFiles(directory, (Predicate<File>) null, recursive);
    }
    final List<String> ext = List.of(extensions);
    return listFiles(directory, (f) -> ext.contains(FilenameUtils.getExtension(f.getName())), recursive);
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

  public static FileResult moveFile(final File srcFile, final File destFile) {
    return moveFile(srcFile, destFile, StandardCopyOption.COPY_ATTRIBUTES);
  }

  public static FileResult moveFile(final File srcFile, final File destFile, final CopyOption... copyOptions) {
    if (srcFile == null || !srcFile.exists()) {
      return FileResult.invalid("Source File must exist and not be null");
    }
    if (destFile == null) {
      return FileResult.invalid("Destination File must not be null");
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

  public static FileResult copy(final InputStream src, final File dest) {
    return copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
  }

  public static FileResult copy(final File src, final File dest, final CopyOption... copyOptions) {
    if (src == null) {
      return FileResult.invalid("Source File must not be null");
    }
    if (!src.exists()) {
      return FileResult.invalid("Source File must exist");
    }
    if (dest == null) {
      return FileResult.invalid("Destination File must not be null");
    }
    final String destCanonicalPath = getCanonical(src);
    final String srcCanonicalPath = getCanonical(dest);
    if (srcCanonicalPath != null && srcCanonicalPath.equals(destCanonicalPath)) {
      return FileResult.invalid("Source '" + src + "' and destination '" + dest + "' are the same");
    }
    final FileResult result = mkdirs(dest);
    if (!result.success)
      return result;

    if (src.isFile()) {
      try {
        if (dest.exists() && dest.isDirectory()) {
          return FileResult.invalid("Destination '" + dest + "' exists but is a directory");
        }
        Files.copy(src.toPath(), dest.toPath(), copyOptions);
        return FileResult.successful();
      } catch (final Exception e) {
        return FileResult.failed("Failed to copy file: " + e.getMessage());
      }
    } else if (src.isDirectory()) {
      if (dest.exists() && !dest.isDirectory()) {
        return FileResult.invalid("Destination '" + dest + "' exists but is not a directory");
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
      return FileResult.invalid("Source is neither a file nor a directory");
    }
  }

  public static FileResult copy(final InputStream in, final File dest, final CopyOption... copyOptions) {
    if (in == null) {
      return FileResult.invalid("InputStream must not be null");
    }
    if (dest == null) {
      return FileResult.invalid("Destination File must not be null");
    }
    final FileResult result = mkdirs(dest);
    if (!result.success)
      return result;

    try {
      Files.copy(in, dest.toPath(), copyOptions);
      return FileResult.successful();
    } catch (final Exception e) {
      return FileResult.failed("Failed to copy file: " + e.getMessage());
    }
  }

  public static BufferedReader createReader(final Path path) throws FileNotFoundException {
    return createReader(path, StandardCharsets.UTF_8);
  }

  public static BufferedReader createReader(final Path path, final Charset charset) throws FileNotFoundException {
    if (path == null || notExists(path)) {
      throw new IllegalArgumentException("Path must not be null and exist");
    }
    return new BufferedReader(
        new InputStreamReader(new FileInputStream(path.toFile()), charset == null ? StandardCharsets.UTF_8 : null));
  }

  public static BufferedReader createReader(final File file) throws FileNotFoundException {
    return createReader(file, StandardCharsets.UTF_8);
  }

  public static BufferedReader createReader(final File file, final Charset charset) throws FileNotFoundException {
    if (file == null || notExists(file)) {
      throw new IllegalArgumentException("Path must not be null and exist");
    }
    return new BufferedReader(
        new InputStreamReader(new FileInputStream(file), charset == null ? StandardCharsets.UTF_8 : null));
  }

  public static BufferedReader createReader(final InputStream stream) throws FileNotFoundException {
    return createReader(stream, StandardCharsets.UTF_8);
  }

  public static BufferedReader createReader(final InputStream stream, final Charset charset)
      throws FileNotFoundException {
    if (stream == null) {
      throw new IllegalArgumentException("Stream must not be null");
    }
    return new BufferedReader(new InputStreamReader(stream, charset == null ? StandardCharsets.UTF_8 : null));
  }

  public static BufferedWriter createWriter(final Path path) throws FileNotFoundException {
    return createWriter(path, StandardCharsets.UTF_8);
  }

  public static BufferedWriter createWriter(final Path path, final Charset charset) throws FileNotFoundException {
    if (path == null || notExists(path)) {
      throw new IllegalArgumentException("Path must not be null and exist");
    }
    return new BufferedWriter(
        new OutputStreamWriter(new FileOutputStream(path.toFile()), charset == null ? StandardCharsets.UTF_8 : null));
  }

  public static BufferedWriter createWriter(final File file) throws FileNotFoundException {
    return createWriter(file, StandardCharsets.UTF_8);
  }

  public static BufferedWriter createWriter(final File file, final Charset charset) throws FileNotFoundException {
    if (file == null || notExists(file)) {
      throw new IllegalArgumentException("Path must not be null and exist");
    }
    return new BufferedWriter(
        new OutputStreamWriter(new FileOutputStream(file), charset == null ? StandardCharsets.UTF_8 : null));
  }

  public static BufferedWriter createWriter(final OutputStream stream) throws FileNotFoundException {
    return createWriter(stream, StandardCharsets.UTF_8);
  }

  public static BufferedWriter createWriter(final OutputStream stream, final Charset charset)
      throws FileNotFoundException {
    if (stream == null) {
      throw new IllegalArgumentException("Stream must not be null");
    }
    return new BufferedWriter(new OutputStreamWriter(stream, charset == null ? StandardCharsets.UTF_8 : null));
  }

  public static BufferedInputStream createInputStream(final Path path)
      throws FileNotFoundException {
    if (path == null || notExists(path)) {
      throw new IllegalArgumentException("Path must not be null and exist");
    }
    return new BufferedInputStream(new FileInputStream(path.toFile()));
  }

  public static BufferedInputStream createInputStream(final File file)
      throws FileNotFoundException {
    if (file == null || notExists(file)) {
      throw new IllegalArgumentException("Path must not be null and exist");
    }
    return new BufferedInputStream(new FileInputStream(file));
  }

  public static BufferedInputStream createInputStream(final InputStream stream)
      throws FileNotFoundException {
    if (stream == null) {
      throw new IllegalArgumentException("Path must not be null");
    }
    return new BufferedInputStream(stream);
  }

  public static BufferedOutputStream createOutputStream(final Path path)
      throws FileNotFoundException {
    if (path == null || notExists(path)) {
      throw new IllegalArgumentException("Path must not be null and exist");
    }
    return new BufferedOutputStream(new FileOutputStream(path.toFile()));
  }

  public static BufferedOutputStream createOutputStream(final File file)
      throws FileNotFoundException {
    if (file == null || notExists(file)) {
      throw new IllegalArgumentException("Path must not be null and exist");
    }
    return new BufferedOutputStream(new FileOutputStream(file));
  }

  public static BufferedOutputStream createOutputStream(final OutputStream stream)
      throws FileNotFoundException {
    if (stream == null) {
      throw new IllegalArgumentException("Path must not be null");
    }
    return new BufferedOutputStream(stream);
  }

  private static List<String> createCopyExclusionList(final File srcDir, final File destDir) {
    final List<File> srcFiles = listFiles(srcDir);
    if (srcFiles.size() == 0) {
      return null;
    }
    final List<String> exclusionList = new ArrayList<>(srcFiles.size());
    for (final File srcFile : srcFiles) {
      exclusionList.add(getCanonical(new File(destDir, srcFile.getName())));
    }
    return exclusionList;
  }

  private static void doCopyDirectory(final File srcDir, final File destDir,
      final List<String> exclusionList, final CopyOption... copyOptions) throws IOException {
    for (final File srcFile : listFiles(srcDir)) {
      final File destFile = new File(destDir, srcFile.getName());
      if (exclusionList != null && exclusionList.contains(getCanonical(srcDir))) {
        continue;
      }
      if (srcFile.isDirectory()) {
        doCopyDirectory(srcFile, destFile, exclusionList, copyOptions);
      } else {
        Files.copy(srcFile.toPath(), destFile.toPath(), copyOptions);
      }
    }
  }

  private static String getCanonical(final File file) {
    try {
      return file.getCanonicalPath();
    } catch (final Exception e) {
      return null;
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
