package com.lemonlightmc.zenith.messages;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.function.Supplier;

import com.lemonlightmc.zenith.utils.FileUtils;
import com.lemonlightmc.zenith.utils.ResourceUtils;
import com.lemonlightmc.zenith.utils.StringUtils;
import com.lemonlightmc.zenith.version.Version;

public abstract class MessageSource<T extends MessageSource<T>> implements IMessageSource<T> {

  protected boolean hasDefault;
  public String author;
  public Version version;
  public String description;
  public Locale locale;

  private static final Locale DEFAULT_LOCALE = Locale.ENGLISH;
  private static final Version DEFAULT_VERSION = Version.FIRST_VERSION;
  private static final String DEFAULT_AUTHOR = "Unknown";
  private static final String DEFAULT_DESC = "No description";

  public MessageSource(final Locale locale, final Version version, final String author, final String desc) {
    this.locale = locale == null ? DEFAULT_LOCALE : locale;
    this.version = version == null ? DEFAULT_VERSION : version;
    this.author = author == null || !author.isEmpty() ? DEFAULT_AUTHOR : author;
    this.description = desc == null || !desc.isEmpty() ? DEFAULT_DESC : desc;
    this.hasDefault = false;
  }

  public MessageSource(final Locale locale, final Version version) {
    this(locale, version, null, null);
  }

  public MessageSource(final Locale locale) {
    this(locale, null, null, null);
  }

  public MessageSource(final String locale) {
    this(StringUtils.parseLocale(locale), null, null, null);
  }

  public MessageSource() {
    this(null, null, null, null);
  }

  public MessageSource(final IMessageSource<?> source) {
    if (source == null) {
      throw new IllegalArgumentException("Source cannot be null when cloning!");
    }
    this.locale = source.getLocale();
    this.version = source.getVersion();
    this.author = source.getAuthor();
    this.description = source.getDescription();
    this.hasDefault = source.hasDefault();
  }

  public MessageSource(final Properties props) {
    this.locale = StringUtils.parseLocale(props.getProperty("meta.locale"));
    if (locale == null) {
      throw new IllegalArgumentException("Invalid locale for MessageSource: " + props.getProperty("meta.locale"));
    }
    this.version = new Version(props.getProperty("meta.version", "1.0.0"));
    this.author = props.getProperty("meta.author", DEFAULT_AUTHOR);
    this.description = props.getProperty("meta.description", DEFAULT_DESC);
    this.hasDefault = false;
  }

  public static FileMessageSource from(final Path file) {
    return new FileMessageSource(file);
  }

  public static FileMessageSource from(final File file) {
    return new FileMessageSource(file);
  }

  public static ResourceBundleMessageSource from(final Locale locale, final ResourceBundle bundle) {
    return new ResourceBundleMessageSource(locale, bundle);
  }

  public static ResourceBundleMessageSource from(final ResourceBundle bundle) {
    return new ResourceBundleMessageSource(bundle);
  }

  public static SupplierMessageSource from(final Supplier<Map<String, String>> computeFunction) {
    return new SupplierMessageSource(computeFunction);
  }

  public static TranslatorMessageSource from(final ITranslator translator) {
    return new TranslatorMessageSource(translator);
  }

  @Override
  public T setLocale(final Locale locale) {
    this.locale = locale;
    return getInstance();
  }

  public T setLocale(final String locale) {
    this.locale = StringUtils.parseLocale(locale);
    return getInstance();
  }

  @Override
  public Locale getLocale() {
    return locale;
  }

  public T setVersion(final String version) {
    this.version = new Version(version);
    return getInstance();
  }

  public T setVersion(final Version version) {
    this.version = version;
    return getInstance();
  }

  public Version getVersion() {
    return version;
  }

  public T setAuthor(final String author) {
    this.author = author;
    return getInstance();
  }

  public String getAuthor() {
    return author;
  }

  public T setDescription(final String description) {
    this.description = description;
    return getInstance();
  }

  public String getDescription() {
    return description;
  }

  @Override
  public T createDefault() {
    return getInstance();
  }

  @Override
  public boolean hasDefault() {
    return hasDefault;
  }

  @Override
  public Map<String, String> load(final Properties props) {
    if (props.containsKey("meta.locale")) {
      this.locale = StringUtils.parseLocale(props.getProperty("meta.locale", this.locale.toString()));
    }
    if (props.containsKey("meta.version")) {
      this.version = new Version(props.getProperty("meta.version", this.version.formatted(true)));
    }
    if (props.containsKey("meta.author")) {
      this.author = props.getProperty("meta.author", this.author);
    }
    if (props.containsKey("meta.description")) {
      this.description = props.getProperty("meta.description", this.description);
    }

    final Map<String, String> messages = new HashMap<>();
    for (final String key : props.stringPropertyNames()) {
      if (key.startsWith("meta.")) {
        continue;
      }
      messages.put(key, props.getProperty(key));
    }
    return messages;
  }

  @Override
  public Map<String, String> load(final ResourceBundle bundle) {
    if (bundle.containsKey("meta.locale")) {
      this.locale = StringUtils
          .parseLocale(ResourceUtils.getResourceBundleString(bundle, "meta.locale", this.locale.toString()));
    }
    if (bundle.containsKey("meta.version")) {
      this.version = new Version(
          ResourceUtils.getResourceBundleString(bundle, "meta.version", this.version.formatted(true)));
    }
    if (bundle.containsKey("meta.author")) {
      this.author = ResourceUtils.getResourceBundleString(bundle, "meta.author", this.author);
    }
    if (bundle.containsKey("meta.description")) {
      this.description = ResourceUtils.getResourceBundleString(bundle, this.description);
    }

    final Map<String, String> messages = new HashMap<>();
    for (final String key : bundle.keySet()) {
      if (key.startsWith("meta.")) {
        continue;
      }
      messages.put(key, ResourceUtils.getResourceBundleString(bundle, key, key));
    }
    return messages;
  }

  protected void loadMeta(final ITranslator translator) {
    if (translator.getLocale() != DEFAULT_LOCALE) {
      this.locale = translator.getLocale();
    }
    if (translator.getVersion() != DEFAULT_VERSION) {
      this.version = translator.getVersion();
    }
    if (translator.getAuthor() != DEFAULT_AUTHOR) {
      this.author = translator.getAuthor();
    }
    if (translator.getDescription() != DEFAULT_DESC) {
      this.description = translator.getDescription();
    }
  }

  protected void loadMeta(final IMessageSource<?> source) {
    if (source.getLocale() != DEFAULT_LOCALE) {
      this.locale = source.getLocale();
    }
    if (source.getVersion() != DEFAULT_VERSION) {
      this.version = source.getVersion();
    }
    if (source.getAuthor() != DEFAULT_AUTHOR) {
      this.author = source.getAuthor();
    }
    if (source.getDescription() != DEFAULT_DESC) {
      this.description = source.getDescription();
    }
  }

  protected void loadMeta(final Map<String, String> messages) {
    if (messages.containsKey("meta.locale")) {
      this.locale = StringUtils.parseLocale(messages.getOrDefault("meta.locale", this.locale.toString()));
    }
    if (messages.containsKey("meta.version")) {
      this.version = new Version(messages.getOrDefault("meta.version", this.version.formatted(true)));
    }
    if (messages.containsKey("meta.author")) {
      this.author = messages.getOrDefault("meta.author", this.author);
    }
    if (messages.containsKey("meta.description")) {
      this.description = messages.getOrDefault("meta.description", this.description);
    }
  }

  @Override
  public T save(final Properties props) {
    if (props == null) {
      return getInstance();
    }
    props.setProperty("meta.locale", locale.toLanguageTag());
    props.setProperty("meta.version", version.toString());
    props.setProperty("meta.author", author);
    props.setProperty("meta.description", description);
    return getInstance();
  }

  @Override
  public Map<String, String> load() {
    return Map.of();
  }

  @Override
  public T save() {
    return getInstance();
  }

  protected abstract T getInstance();

  public abstract T clone();

  @Override
  public int hashCode() {
    int result = 31 + (hasDefault ? 1231 : 1237);
    result = 31 * result + ((author == null) ? 0 : author.hashCode());
    result = 31 * result + ((version == null) ? 0 : version.hashCode());
    result = 31 * result + ((description == null) ? 0 : description.hashCode());
    return 31 * result + locale.hashCode();
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final MessageSource<?> other = (MessageSource<?>) obj;
    if (locale == null && other.locale != null || version == null && other.version != null
        || description == null && other.description != null || author == null && other.author != null) {
      return false;
    }
    return hasDefault == other.hasDefault && locale.equals(other.locale) && version.equals(other.version)
        && author.equals(other.author)
        && description.equals(other.description);
  }

  @Override
  public String toString() {
    return "MessageSource [author=" + author + ", version=" + version + ", description="
        + description + ", locale=" + locale + "]";
  }

  // File based MessageSource
  public static class FileMessageSource extends MessageSource<FileMessageSource> {

    public static final String[] resourceFolders = { "translations", "translation", "lang", "languages", "language",
        "messages" };
    private final Path path;
    private final File file;

    public FileMessageSource(final String file) {
      this(Path.of(file));
    }

    public FileMessageSource(final Path path) {
      super();
      if (path == null) {
        throw new IllegalArgumentException("Message File Path cannot be null!");
      }
      this.file = path.toFile();
      this.path = path;
      this.hasDefault = ResourceUtils.hasResource(path.toString());
    }

    public FileMessageSource(final File file) {
      super();
      if (file == null) {
        throw new IllegalArgumentException("Message File cannot be null!");
      }
      this.file = file;
      this.path = file.toPath();
      this.hasDefault = ResourceUtils.hasResource(path.toString());
    }

    public FileMessageSource(final FileMessageSource source) {
      super(source);
      this.file = source.file;
      this.path = source.path;
      this.hasDefault = source.hasDefault;
    }

    public File getFile() {
      return file;
    }

    public Path getPath() {
      return path;
    }

    @Override
    protected FileMessageSource getInstance() {
      return this;
    }

    @Override
    public FileMessageSource clone() {
      return new FileMessageSource(this);
    }

    @Override
    public int hashCode() {
      final int result = 31 * super.hashCode() + path.hashCode();
      return 31 * result + file.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null || !super.equals(obj) || getClass() != obj.getClass()) {
        return false;
      }
      final FileMessageSource other = (FileMessageSource) obj;
      return path.equals(other.path) && file.equals(other.file);
    }

    @Override
    public Map<String, String> load() {
      if (FileUtils.notExists(path)) {
        hasDefault = ResourceUtils.hasResource(path.toString());
        if (!hasDefault) {
          Logger.warn("Message file does not exist and no default is available: " + file);
          return null;
        } else {
          createDefault();
        }
      }

      final Properties props = new Properties();
      try (BufferedReader in = FileUtils.createReader(file)) {
        props.load(in);
        return load(props);
      } catch (final Exception e) {
        Logger.warn("Failed to load messages from file: " + file);
        e.printStackTrace();
        return null;
      }
    }

    @Override
    public FileMessageSource save() {
      FileUtils.mkdirs(file).throwIfFailed();
      final Properties props = new Properties();
      save(props);
      try (BufferedWriter writer = FileUtils.createWriter(file)) {
        props.store(writer, null);
      } catch (final Exception e) {
        Logger.warn("Failed to save messages to file: " + file);
        e.printStackTrace();
      }
      return this;
    }

    @Override
    public FileMessageSource createDefault() {
      final File resource = ResourceUtils.getResourceFile(path.toString());
      if (resource == null) {
        Logger.warn("No default message resource found for: " + file);
        return this;
      }
      try {
        ResourceUtils.saveResource(file, resource);
      } catch (final Exception e) {
      }
      return this;
    }
  }

  // ResourceBundle based MessageSource
  public static class ResourceBundleMessageSource extends MessageSource<ResourceBundleMessageSource> {
    private final String baseName;

    public ResourceBundleMessageSource(final Locale locale, final String baseName) {
      super(locale);
      this.baseName = baseName;
      if (baseName == null || baseName.isEmpty()) {
        throw new IllegalArgumentException("Base name cannot be null or empty for ResourceBundleMessageSource!");
      }
    }

    public ResourceBundleMessageSource(final Locale locale, final ResourceBundle bundle) {
      super(locale);
      this.baseName = bundle.getBaseBundleName();
      if (baseName == null || baseName.isEmpty()) {
        throw new IllegalArgumentException("Base name cannot be null or empty for ResourceBundleMessageSource!");
      }
    }

    public ResourceBundleMessageSource(final String baseName) {
      super();
      this.baseName = baseName;
      if (baseName == null || baseName.isEmpty()) {
        throw new IllegalArgumentException("Base name cannot be null or empty for ResourceBundleMessageSource!");
      }
    }

    public ResourceBundleMessageSource(final ResourceBundle bundle) {
      super(bundle.getLocale());
      this.baseName = bundle.getBaseBundleName();
      if (baseName == null || baseName.isEmpty()) {
        throw new IllegalArgumentException("Base name cannot be null or empty for ResourceBundleMessageSource!");
      }
    }

    public ResourceBundleMessageSource(final ResourceBundleMessageSource source) {
      super(source);
      this.baseName = source.baseName;
    }

    public String getBaseName() {
      return baseName;
    }

    @Override
    protected ResourceBundleMessageSource getInstance() {
      return this;
    }

    @Override
    public ResourceBundleMessageSource clone() {
      return new ResourceBundleMessageSource(this);
    }

    @Override
    public int hashCode() {
      return 31 * super.hashCode() + baseName.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null || !super.equals(obj) || getClass() != obj.getClass()) {
        return false;
      }
      return baseName.equals(((ResourceBundleMessageSource) obj).baseName);
    }

    @Override
    public Map<String, String> load() {
      try {
        final ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
        return load(bundle);
      } catch (final Exception e) {
        Logger.warn("Failed to load ResourceBundle: " + baseName + " for locale: " + locale);
        e.printStackTrace();
        return null;
      }
    }
  }

  public static class SupplierMessageSource extends MessageSource<SupplierMessageSource> {
    private final Supplier<Map<String, String>> supplier;

    public SupplierMessageSource(final Supplier<Map<String, String>> supplier) {
      super();
      if (supplier == null) {
        throw new IllegalArgumentException("Supplier cannot be null for SupplierMessageSource!");
      }
      this.supplier = supplier;
    }

    public SupplierMessageSource(final SupplierMessageSource source) {
      super(source);
      this.supplier = source.supplier;
    }

    public Supplier<Map<String, String>> getSupplier() {
      return supplier;
    }

    @Override
    protected SupplierMessageSource getInstance() {
      return this;
    }

    @Override
    public SupplierMessageSource clone() {
      return new SupplierMessageSource(this);
    }

    @Override
    public int hashCode() {
      return 31 * super.hashCode() + supplier.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null || !super.equals(obj) || getClass() != obj.getClass()) {
        return false;
      }
      return supplier.equals(((SupplierMessageSource) obj).supplier);
    }

    @Override
    public Map<String, String> load() {
      final Map<String, String> messages = supplier.get();
      loadMeta(messages);
      return messages;
    }
  }

  public static class TranslatorMessageSource extends MessageSource<TranslatorMessageSource> {
    private final ITranslator translator;

    public TranslatorMessageSource(final ITranslator translator) {
      super(translator == null ? null : translator.providesLocale());
      if (translator == null) {
        throw new IllegalArgumentException("Translator cannot be null for TranslatorMessageSource!");
      }
      this.translator = translator;
    }

    public TranslatorMessageSource(final TranslatorMessageSource source) {
      super(source);
      this.translator = source.translator;
    }

    @Override
    protected TranslatorMessageSource getInstance() {
      return this;
    }

    @Override
    public TranslatorMessageSource clone() {
      return new TranslatorMessageSource(this);
    }

    @Override
    public int hashCode() {
      return 31 * super.hashCode() + translator.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null || !super.equals(obj) || getClass() != obj.getClass()) {
        return false;
      }
      return translator.equals(((TranslatorMessageSource) obj).translator);
    }

    @Override
    public Map<String, String> load() {
      loadMeta(translator);
      final Map<String, String> messages = new HashMap<>();
      for (final String key : translator.providesTranslations()) {
        messages.put(key, translator.translate(key, locale));
      }
      return messages;
    }
  }

  public static class URLMessageSource extends MessageSource<URLMessageSource> {
    private final URL url;

    public URLMessageSource(final String url) {
      super();
      try {
        this.url = new URI(url).toURL();
      } catch (final Exception e) {
        throw new IllegalArgumentException("Invalid URL: " + url, e);
      }
    }

    public URLMessageSource(final URL url) {
      super();
      this.url = url;
    }

    public URLMessageSource(final URLMessageSource source) {
      super(source);
      this.url = source.url;
    }

    public URL getUrl() {
      return url;
    }

    @Override
    protected URLMessageSource getInstance() {
      return this;
    }

    @Override
    public URLMessageSource clone() {
      return new URLMessageSource(this);
    }

    @Override
    public int hashCode() {
      return 31 * super.hashCode() + url.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null || !super.equals(obj) || getClass() != obj.getClass()) {
        return false;
      }
      return url.equals(((URLMessageSource) obj).url);
    }

    @Override
    public Map<String, String> load() {
      HttpURLConnection conn = null;
      try {
        conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);

        final int code = conn.getResponseCode();
        if (code < 200 || code >= 300) {
          Logger.warn("Failed to load messages from URL (HTTP " + code + "): " + url);
          return null;
        }

        final Properties props = new Properties();
        try (final BufferedReader reader = FileUtils.createReader(conn.getInputStream())) {
          props.load(reader);
          return load(props);
        }
      } catch (final Exception e) {
        Logger.warn("Failed to load messages from URL: " + url);
        e.printStackTrace();
        return null;
      } finally {
        if (conn != null) {
          conn.disconnect();
          conn = null;
        }
      }
    }

    @Override
    public URLMessageSource save() {
      final Properties props = new Properties();
      save(props);
      HttpURLConnection conn = null;
      try {
        conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        conn.setDoOutput(true);
        // Try PUT first; many endpoints accept PUT for replacing resources.
        try {
          conn.setRequestMethod("PUT");
        } catch (final ProtocolException ex) {
          // fallback to POST if PUT not allowed by handler
          conn.setRequestMethod("POST");
        }
        conn.setRequestProperty("Content-Type", "text/plain; charset=UTF-8");

        try (final BufferedWriter writer = FileUtils.createWriter(conn.getOutputStream())) {
          props.store(writer, null);
        }

        final int code = conn.getResponseCode();
        if (code < 200 || code >= 300) {
          Logger.warn("Failed to save messages to URL (HTTP " + code + "): " + url);
          return this;
        }
      } catch (final Exception e) {
        Logger.warn("Failed to save messages to URL: " + url);
        e.printStackTrace();
      } finally {
        if (conn != null) {
          conn.disconnect();
          conn = null;
        }
      }
      return this;
    }
  }
}
