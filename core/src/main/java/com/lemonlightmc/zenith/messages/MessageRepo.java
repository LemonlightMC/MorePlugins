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
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.function.Supplier;

import com.lemonlightmc.zenith.interfaces.Cloneable;
import com.lemonlightmc.zenith.utils.FileUtils;
import com.lemonlightmc.zenith.utils.ResourceUtils;
import com.lemonlightmc.zenith.utils.StringUtils;
import com.lemonlightmc.zenith.version.Version;

public class MessageRepo<T extends MessageRepo<T>> implements Cloneable<T> {
  protected final String id;
  protected String name;
  protected final Locale locale;
  protected String description;
  protected float progress;
  protected Version version;
  protected List<String> contributors;

  protected Map<String, String> messages;
  protected long last_updated;

  private static final Locale DEFAULT_LOCALE = Locale.ENGLISH;
  private static final Version DEFAULT_VERSION = Version.FIRST_VERSION;
  private static final List<String> DEFAULT_CONTRIBUTORS = List.of();
  private static final String DEFAULT_DESC = "No description";

  public MessageRepo(final String id, final String name, final Locale locale, final Version version,
      final float progress, final String desc,
      final List<String> contributors) {
    this.id = id;
    if (id == null || id.isEmpty()) {
      throw new IllegalArgumentException("MessageRepo id cannot be null or empty");
    }
    this.name = name == null ? id : name;
    this.locale = locale == null ? DEFAULT_LOCALE : locale;
    this.version = version == null ? DEFAULT_VERSION : version;
    this.contributors = contributors == null || !contributors.isEmpty() ? DEFAULT_CONTRIBUTORS : contributors;
    this.description = desc == null || !desc.isEmpty() ? DEFAULT_DESC : desc;
    setProgress(progress);
  }

  public MessageRepo(final String id, final String name, final String locale, final Version version,
      final float progress, final String desc,
      final List<String> contributors) {
    this(id, name, StringUtils.parseLocale(locale), version, progress, desc, contributors);
  }

  public MessageRepo(final String id, final String name, final Locale locale, final Version version,
      final float progress, final String desc) {
    this(id, name, locale, version, progress, desc, DEFAULT_CONTRIBUTORS);
  }

  public MessageRepo(final String id, final String name, final String locale, final Version version,
      final float progress, final String desc) {
    this(id, name, StringUtils.parseLocale(locale), version, progress, desc, DEFAULT_CONTRIBUTORS);
  }

  public MessageRepo(final String id, final String name, final Locale locale, final float progress, final String desc) {
    this(id, name, locale, DEFAULT_VERSION, progress, desc, DEFAULT_CONTRIBUTORS);
  }

  public MessageRepo(final String id, final String name, final String locale, final float progress, final String desc) {
    this(id, name, StringUtils.parseLocale(locale), DEFAULT_VERSION, progress, desc, DEFAULT_CONTRIBUTORS);
  }

  public MessageRepo(final String id, final String name, final Locale locale, final float progress) {
    this(id, name, locale, DEFAULT_VERSION, progress, DEFAULT_DESC, DEFAULT_CONTRIBUTORS);
  }

  public MessageRepo(final String id, final String name, final String locale, final float progress) {
    this(id, name, StringUtils.parseLocale(locale), DEFAULT_VERSION, progress, DEFAULT_DESC, DEFAULT_CONTRIBUTORS);
  }

  public MessageRepo(final String id, final String name, final Locale locale) {
    this(id, name, locale, DEFAULT_VERSION, 0f, DEFAULT_DESC, DEFAULT_CONTRIBUTORS);
  }

  public MessageRepo(final String id, final String name, final String locale) {
    this(id, name, StringUtils.parseLocale(locale), DEFAULT_VERSION, 0f, DEFAULT_DESC, DEFAULT_CONTRIBUTORS);
  }

  public MessageRepo(final MessageRepo<?> repo) {
    if (repo == null) {
      throw new IllegalArgumentException("Message Repo cannot be null when cloning!");
    }
    this.id = repo.id;
    this.name = repo.name;
    this.locale = repo.locale;
    this.version = repo.version;
    this.progress = repo.progress;
    this.description = repo.description;
    this.contributors = repo.contributors;
    this.messages = repo.messages;
  }

  public MessageRepo(final Properties props) {
    this.id = props.getProperty("meta.id");
    if (id == null || id.isEmpty()) {
      throw new IllegalArgumentException("MessageRepo id cannot be null or empty");
    }
    this.locale = StringUtils.parseLocale(props.getProperty("meta.locale"));
    load(props);
  }

  protected T getInstance() {
    return (T) this;
  }

  public String getId() {
    return this.id;
  }

  public Locale getLocale() {
    return locale;
  }

  public T setName(final String name) {
    if (name != null && !name.isEmpty()) {
      this.name = name;
    }
    return getInstance();
  }

  public String getName() {
    return this.name;
  }

  public T setVersion(final String version) {
    this.version = new Version(version);
    return getInstance();
  }

  public T setVersion(final Version version) {
    if (version != null) {
      this.version = version;
    }
    return getInstance();
  }

  public Version getVersion() {
    return version;
  }

  public T setProgress(final float progress) {
    this.progress = Math.max(Math.min(Math.round(progress), 0), 100);
    return getInstance();
  }

  public float getProgress() {
    return progress;
  }

  public T setDescription(final String description) {
    this.description = description;
    return getInstance();
  }

  public String getDescription() {
    return description;
  }

  public T setContributors(final List<String> contributors) {
    this.contributors = contributors;
    return getInstance();
  }

  public List<String> getContributors() {
    return this.contributors;
  }

  public T setMessages(final Map<String, String> messages) {
    this.messages = messages;
    return getInstance();
  }

  public Map<String, String> getMessages() {
    return messages;
  }

  public String getMessage(final String key) {
    if (key == null || key.length() == 0) {
      return null;
    }
    return messages.get(key);
  }

  public T clear() {
    messages.clear();
    return getInstance();
  }

  public boolean isEmpty() {
    return messages == null || messages.isEmpty();
  }

  public boolean isLoaded() {
    return messages != null && !messages.isEmpty();
  }

  public long getLastUpdated() {
    return last_updated;
  }

  public T load() {
    return getInstance();
  }

  public T save() {
    return getInstance();
  }

  @Override
  public T clone() {
    return getInstance().clone();
  }

  @Override
  public int hashCode() {
    int result = 31 + id.hashCode();
    result = 31 * result + locale.hashCode();
    result = 31 * result + ((name == null) ? 0 : name.hashCode());
    result = 31 * result + ((description == null) ? 0 : description.hashCode());
    result = 31 * result + Float.floatToIntBits(progress);
    result = 31 * result + ((contributors == null) ? 0 : contributors.hashCode());
    return 31 * result + version.hashCode();
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final MessageRepo<?> other = (MessageRepo<?>) obj;
    if (name == null && other.name != null || version == null && other.version != null
        || description == null && other.description != null || contributors == null && other.contributors != null) {
      return false;
    }
    return id.equals(other.id)
        && Float.floatToIntBits(progress) != Float.floatToIntBits(other.progress)
        && version.equals(other.version)
        && name.equals(other.name) && locale.equals(other.locale)
        && description.equals(other.description)
        && contributors.equals(other.contributors);
  }

  @Override
  public String toString() {
    return "MessageRepo [id=" + id + ", name=" + name + ", locale=" + locale + ", description=" + description
        + ", progress=" + progress + ", version=" + version + ", contributors=" + contributors + "]";
  }

  protected Properties createProperties() {
    final Properties props = new Properties();
    props.setProperty("meta.id", id);
    props.setProperty("meta.name", name);
    props.setProperty("meta.locale", locale.toLanguageTag());
    props.setProperty("meta.version", version.toString());
    props.setProperty("meta.progress", Float.toString(progress));
    props.setProperty("meta.description", description);
    props.setProperty("meta.contributors", String.join(",", contributors));
    return props;
  }

  protected void load(final Properties props) {
    loadMeta(props.getProperty("meta.id"), props.getProperty("meta.name"), props.getProperty("meta.locale"),
        props.getProperty("meta.version"), props.getProperty("meta.description"), props.getProperty("meta.progress"),
        props.getProperty("meta.contributors"));
    if (messages == null) {
      messages = new HashMap<>();
    } else {
      messages.clear();
    }
    for (final String key : props.stringPropertyNames()) {
      messages.put(key, props.getProperty(key));
    }
  }

  protected void load(final ResourceBundle bundle) {
    loadMeta(bundle.getString("meta.id"), bundle.getString("meta.name"), bundle.getString("meta.locale"),
        bundle.getString("meta.version"), bundle.getString("meta.description"), bundle.getString("meta.progress"),
        bundle.getString("meta.contributors"));
    if (messages == null) {
      messages = new HashMap<>();
    } else {
      messages.clear();
    }
    for (final String key : bundle.keySet()) {
      messages.put(key, ResourceUtils.getResourceBundleString(bundle, key, null));
    }
  }

  protected void loadMeta(final String id, final String name, final String locale, final String version,
      final String description, final String progress,
      final String contributors) {
    if (id == null || id.isEmpty() || !this.id.equals(id)) {
      throw new IllegalArgumentException(
          "ID mismatch when loading messages: expected " + this.id + " but got " + id);
    }
    final Locale parsedLocale = StringUtils.parseLocale(locale);
    if (parsedLocale == null || !this.locale.equals(parsedLocale)) {
      throw new IllegalArgumentException(
          "Locale mismatch when loading messages: expected " + this.locale + " but got " + parsedLocale);
    }
    if (name != null) {
      this.name = name;
    }
    if (version != null) {
      this.version = new Version(version);
    }
    if (description != null) {
      this.description = description;
    }
    if (progress != null) {
      this.progress = Float.parseFloat(progress);
    }
    if (contributors != null) {
      this.contributors = List.of(contributors.split(","));
    }
  }

  protected void loadMeta(final Map<String, String> messages) {
    loadMeta(messages.get("meta.id"), messages.get("meta.name"), messages.get("meta.locale"),
        messages.get("meta.version"),
        messages.get("meta.description"), messages.get("meta.progress"), messages.get("meta.contributors"));
  }

  public static class StaticMessageRepo extends MessageRepo<StaticMessageRepo> {

    public StaticMessageRepo(final Locale locale, final Map<String, String> messages) {
      super(null, null, locale);
      this.messages = messages;
    }

    public StaticMessageRepo(final StaticMessageRepo repo) {
      super(repo);
    }

    @Override
    protected StaticMessageRepo getInstance() {
      return this;
    }
  }

  public static class FileMessageRepo extends MessageRepo<FileMessageRepo> {
    public static final String[] resourceFolders = { "translations", "translation", "lang", "languages", "language",
        "messages" };
    private final Path path;
    private final File file;
    private boolean hasDefault;

    public FileMessageRepo(final String file) {
      this(Path.of(file));
    }

    public FileMessageRepo(final Path path) {
      super(path.getFileName().toString(), path.getFileName().toString(), getLocale(path));
      this.file = path.toFile();
      this.path = path;
      this.hasDefault = ResourceUtils.hasResource(path.toString());
    }

    public FileMessageRepo(final File file) {
      super(file.getName(), file.getName(), getLocale(file));
      this.file = file;
      this.path = file.toPath();
      this.hasDefault = ResourceUtils.hasResource(path.toString());
    }

    public FileMessageRepo(final FileMessageRepo repo) {
      super(repo);
      this.file = repo.file;
      this.path = repo.path;
      this.hasDefault = repo.hasDefault;
    }

    @Override
    protected FileMessageRepo getInstance() {
      return this;
    }

    public File getFile() {
      return file;
    }

    public Path getPath() {
      return path;
    }

    private static Locale getLocale(final File file) {
      return file == null ? null : StringUtils.parseLocale(file.getName().replace("lang_", ""));
    }

    private static Locale getLocale(final Path path) {
      return path == null ? null : StringUtils.parseLocale(path.getFileName().toString().replace("lang_", ""));
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
      final FileMessageRepo other = (FileMessageRepo) obj;
      return path.equals(other.path) && file.equals(other.file);
    }

    public FileMessageRepo load() {
      if (FileUtils.notExists(path)) {
        hasDefault = ResourceUtils.hasResource(path.toString());
        if (!hasDefault) {
          Logger.warn("Message file does not exist and no default is available: " + file);
          return this;
        } else {
          createDefault();
        }
      }

      final Properties props = new Properties();
      try (BufferedReader in = FileUtils.createReader(file)) {
        props.load(in);
        load(props);
      } catch (final Exception e) {
        Logger.warn("Failed to load messages from file: " + file);
        e.printStackTrace();
      }
      return this;
    }

    public FileMessageRepo save() {
      FileUtils.mkdirs(file).throwIfFailed();
      try (BufferedWriter writer = FileUtils.createWriter(file)) {
        createProperties().store(writer, null);
      } catch (final Exception e) {
        Logger.warn("Failed to save messages to file: " + file);
        e.printStackTrace();
      }
      return this;
    }

    public FileMessageRepo save(final ResourceBundle bundle) {
      FileUtils.mkdirs(file).throwIfFailed();
      try (BufferedWriter writer = FileUtils.createWriter(file)) {
      } catch (final Exception e) {
        Logger.warn("Failed to save messages to file: " + file);
        e.printStackTrace();
      }
      return this;
    }

    public FileMessageRepo createDefault() {
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

  public static class ResourceBundleMessageRepo extends MessageRepo<ResourceBundleMessageRepo> {
    private final String baseName;

    public ResourceBundleMessageRepo(final String baseName, final Locale locale) {
      super(baseName, baseName, locale);
      this.baseName = baseName;
      if (baseName == null || baseName.isEmpty()) {
        throw new IllegalArgumentException("Base name cannot be null or empty for ResourceBundleMessageRepo!");
      }
    }

    public ResourceBundleMessageRepo(final ResourceBundle bundle) {
      super(bundle.getBaseBundleName(), bundle.getBaseBundleName(), bundle.getLocale());
      this.baseName = bundle.getBaseBundleName();
      if (baseName == null || baseName.isEmpty()) {
        throw new IllegalArgumentException("Base name cannot be null or empty for ResourceBundleMessageRepo!");
      }
    }

    public ResourceBundleMessageRepo(final ResourceBundleMessageRepo source) {
      super(source);
      this.baseName = source.baseName;
    }

    @Override
    protected ResourceBundleMessageRepo getInstance() {
      return this;
    }

    public String getBaseName() {
      return baseName;
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
      return baseName.equals(((ResourceBundleMessageRepo) obj).baseName);
    }

    public ResourceBundleMessageRepo load() {
      try {
        load(ResourceBundle.getBundle(baseName));
      } catch (final Exception e) {
        Logger.warn("Failed to load ResourceBundle: " + baseName);
        e.printStackTrace();
      }
      return this;
    }
  }

  public static class SupplierMessageRepo extends MessageRepo<SupplierMessageRepo> {
    private final Supplier<Map<String, String>> supplier;

    public SupplierMessageRepo(final Locale locale, final Supplier<Map<String, String>> supplier) {
      super(null, null, locale);
      if (supplier == null) {
        throw new IllegalArgumentException("Supplier cannot be null for SupplierMessageRepo!");
      }
      this.supplier = supplier;
    }

    public SupplierMessageRepo(final SupplierMessageRepo source) {
      super(source);
      this.supplier = source.supplier;
    }

    @Override
    protected SupplierMessageRepo getInstance() {
      return this;
    }

    public Supplier<Map<String, String>> getSupplier() {
      return supplier;
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
      return supplier.equals(((SupplierMessageRepo) obj).supplier);
    }

    public SupplierMessageRepo load() {
      this.messages = supplier.get();
      loadMeta(messages.get("meta.id"), messages.get("meta.name"), messages.get("meta.locale"),
          messages.get("meta.version"),
          messages.get("meta.description"), messages.get("meta.progress"), messages.get("meta.contributors"));
      return this;
    }
  }

  public static class TranslatorMessageRepo extends MessageRepo<TranslatorMessageRepo> {
    private final ITranslator translator;

    public TranslatorMessageRepo(final ITranslator translator) {
      super(translator.name().getKey(), translator.name().getKey(), translator.providesLocale());
      this.translator = translator;
    }

    public TranslatorMessageRepo(final TranslatorMessageRepo source) {
      super(source);
      this.translator = source.translator;
    }

    @Override
    protected TranslatorMessageRepo getInstance() {
      return this;
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
      return translator.equals(((TranslatorMessageRepo) obj).translator);
    }

    public TranslatorMessageRepo load() {
      if (!this.locale.equals(translator.providesLocale())) {
        throw new IllegalArgumentException(
            "Locale mismatch when loading messages: expected " + this.locale + " but got "
                + translator.providesLocale());
      }
      messages = new HashMap<>();
      for (final String key : translator.providesTranslations()) {
        messages.put(key, translator.translate(key, locale));
      }
      return this;
    }
  }

  public static class URLMessageRepo extends MessageRepo<URLMessageRepo> {
    private final URL url;

    public URLMessageRepo(final Locale locale, final String url) {
      super(null, null, locale);
      if (url == null || url.isEmpty()) {
        throw new IllegalArgumentException("Invalid URL: " + url);
      }
      try {
        this.url = new URI(url).toURL();
      } catch (final Exception e) {
        throw new IllegalArgumentException("Invalid URL: " + url, e);
      }
    }

    public URLMessageRepo(final Locale locale, final URL url) {
      super(null, null, locale);
      this.url = url;
    }

    public URLMessageRepo(final URLMessageRepo source) {
      super(source);
      this.url = source.url;
    }

    @Override
    protected URLMessageRepo getInstance() {
      return this;
    }

    public URL getUrl() {
      return url;
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
      return url.equals(((URLMessageRepo) obj).url);
    }

    public URLMessageRepo load() {
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
          return this;
        }

        final Properties props = new Properties();
        try (final BufferedReader reader = FileUtils.createReader(conn.getInputStream())) {
          props.load(reader);
          load(props);
          return this;
        }
      } catch (final Exception e) {
        Logger.warn("Failed to load messages from URL: " + url);
        e.printStackTrace();
        return this;
      } finally {
        if (conn != null) {
          conn.disconnect();
          conn = null;
        }
      }
    }

    public URLMessageRepo save(final Properties props) {
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
          createProperties().store(writer, null);
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
