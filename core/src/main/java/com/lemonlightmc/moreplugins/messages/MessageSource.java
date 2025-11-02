package com.lemonlightmc.moreplugins.messages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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

import com.lemonlightmc.moreplugins.utils.FileUtils;

public class MessageSource implements IMessageSource {

  protected Map<String, String> messages;
  protected MessageMeta meta;
  protected boolean hasDefault;

  public MessageSource() {
    this.messages = null;
    this.meta = new MessageMeta();
    this.hasDefault = false;
  }

  public MessageSource(final Locale locale) {
    this.messages = null;
    this.meta = new MessageMeta(locale);
    this.hasDefault = false;
  }

  public MessageSource(final Locale locale, final Map<String, String> messages) {
    this.messages = messages;
    this.meta = new MessageMeta(locale);
    this.hasDefault = false;
  }

  public MessageSource(final Properties props) {
    this.hasDefault = false;
    this.messages = new HashMap<>();
    this.meta.modify(props);

    for (final String name : props.stringPropertyNames()) {
      if (name.startsWith("meta.")) {
        continue;
      }
      messages.put(name, props.getProperty(name));
    }
  }

  public static IMessageSource from(final Locale locale, final Map<String, String> messages) {
    return new MessageSource(locale, messages);
  }

  public static IMessageSource from(final Properties props) {
    return new MessageSource(props);
  }

  public static IMessageSource from(final File file) {
    return new FileMessageSource(file);
  }

  public static IMessageSource from(final Locale locale, final ResourceBundle bundle) {
    return new ResourceBundleMessageSource(locale, bundle);
  }

  public static IMessageSource from(final ResourceBundle bundle) {
    return new ResourceBundleMessageSource(bundle);
  }

  public static IMessageSource from(final Supplier<Map<String, String>> computeFunction) {
    return new SupplierMessageSource(computeFunction);
  }

  public static IMessageSource from(final ITranslator translator) {
    return new TranslatorMessageSource(translator);
  }

  public static IMessageSource empty() {
    return new MessageSource();
  }

  @Override
  public Map<String, String> getMessages() {
    if (messages == null) {
      load();
    }
    return messages;
  }

  @Override
  public Locale getLocale() {
    return meta.locale;
  }

  @Override
  public void setLocale(final Locale locale) {
    this.meta.setLocale(locale);
  }

  @Override
  public MessageMeta getMeta() {
    return meta;
  }

  @Override
  public void setMeta(final MessageMeta meta) {
    this.meta = meta;
  }

  @Override
  public void createDefault() {
  }

  @Override
  public boolean hasDefault() {
    return hasDefault;
  }

  @Override
  public void load() {
  }

  @Override
  public void save(final Map<String, String> data) {
    messages.clear();
    messages.putAll(data);
  }

  // File based MessageSource
  public static class FileMessageSource extends MessageSource {

    private final File file;

    public FileMessageSource(final String file) {
      this(Path.of(file).toFile());
    }

    public FileMessageSource(final Path file) {
      this(file.toFile());
    }

    public FileMessageSource(final File file) {
      super();
      this.file = file;
      hasDefault = FileUtils.hasResource("translations/" + file.getName() + ".properties");
    }

    public File getFile() {
      return file;
    }

    @Override
    public void load() {
      if (messages == null) {
        messages = new HashMap<>();
      } else {
        messages.clear();
      }
      if (!file.exists()) {
        if (!hasDefault()) {
          Logger.warn("Message file does not exist and no default is available: " + file);
          return;
        } else {
          createDefault();
        }
      }

      final Properties props = new Properties();
      try (InputStream in = new FileInputStream(file)) {
        props.load(in);
        meta.modify(props);
        for (final String key : props.stringPropertyNames()) {
          if (key.startsWith("meta.")) {
            continue;
          }
          messages.put(key, props.getProperty(key));
        }
      } catch (final Exception e) {
        Logger.warn("Failed to load messages from file: " + file);
        e.printStackTrace();
      }
    }

    @Override
    public void save(final Map<String, String> data) {
      final File parent = file.getParentFile();
      if (parent != null && !parent.exists()) {
        parent.mkdirs();
      }

      final Properties props = new Properties();
      if (data != null) {
        props.putAll(data);
        meta.store(props);
      }

      try (OutputStream out = new FileOutputStream(file)) {
        props.store(out, null);
      } catch (final Exception e) {
        Logger.warn("Failed to save messages to file: " + file);
        e.printStackTrace();
      }

      if (messages == null) {
        messages = new HashMap<>(data != null ? data : Map.of());
      } else {
        messages.clear();
        if (data != null) {
          messages.putAll(data);
        }
      }
    }

    @Override
    public void createDefault() {
      final String resourcePath = "translations/" + file + ".properties";
      final File resource = FileUtils.getResource(resourcePath);
      if (resource == null || !resource.exists()) {
        Logger.warn("No default message resource found for: " + file);
        return;
      }
      try {
        FileUtils.saveResource(file, resource);
      } catch (final Exception e) {
      }
    }
  }

  // ResourceBundle based MessageSource
  public static class ResourceBundleMessageSource extends MessageSource {
    private final String baseName;

    public ResourceBundleMessageSource(final Locale locale, final String baseName) {
      super(locale);
      this.baseName = baseName;
    }

    public ResourceBundleMessageSource(final Locale locale, final ResourceBundle bundle) {
      super(locale);
      this.baseName = bundle.getBaseBundleName();
      _load(bundle);
    }

    public ResourceBundleMessageSource(final String baseName) {
      super();
      this.baseName = baseName;
    }

    public ResourceBundleMessageSource(final ResourceBundle bundle) {
      super(bundle.getLocale());
      this.baseName = bundle.getBaseBundleName();
      _load(bundle);
    }

    public String getBaseName() {
      return baseName;
    }

    @Override
    public void load() {
      try {
        final ResourceBundle bundle = ResourceBundle.getBundle(baseName, meta.locale);
        _load(bundle);
      } catch (final Exception e) {
        Logger.warn("Failed to load ResourceBundle: " + baseName + " for locale: " + meta.locale);
        e.printStackTrace();
      }
    }

    @Override
    public void save(final Map<String, String> data) {
      throw new UnsupportedOperationException("Cannot save to ResourceBundleMessageSource");
    }

    private void _load(final ResourceBundle bundle) {
      if (messages == null) {
        messages = new HashMap<>();
      } else {
        messages.clear();
      }
      meta.modify(bundle);
      for (final String key : bundle.keySet()) {
        if (key.startsWith("meta.")) {
          continue;
        }
        messages.put(key, FileUtils.getResourceBundleString(bundle, key, key));
      }
    }
  }

  public static class SupplierMessageSource extends MessageSource {
    private final Supplier<Map<String, String>> supplier;

    public SupplierMessageSource(final Supplier<Map<String, String>> supplier) {
      super();
      this.supplier = supplier;
    }

    @Override
    public void load() {
      messages = supplier.get();
    }
  }

  public static class TranslatorMessageSource extends MessageSource {
    private final ITranslator translator;

    public TranslatorMessageSource(final ITranslator translator) {
      super(translator.providesLocale());
      this.translator = translator;
    }

    @Override
    public void load() {
      messages.clear();
      for (final String key : translator.providesTranslations()) {
        messages.put(key, translator.translate(key, meta.locale));
      }
    }
  }

  public static class URLMessageSource extends MessageSource {
    private final String url;

    public URLMessageSource(final String url) {
      super();
      this.url = url;
    }

    public String getUrl() {
      return url;
    }

    @Override
    public void load() {
      if (messages == null) {
        messages = new HashMap<>();
      } else {
        messages.clear();
      }

      HttpURLConnection conn = null;
      try {
        final URL u = new URI(url).toURL();
        conn = (HttpURLConnection) u.openConnection();
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);

        final int code = conn.getResponseCode();
        if (code < 200 || code >= 300) {
          Logger.warn("Failed to load messages from URL (HTTP " + code + "): " + url);
          return;
        }

        final Properties props = new Properties();
        try (final InputStream in = conn.getInputStream()) {
          props.load(in);
        }

        meta.modify(props);
        for (final String key : props.stringPropertyNames()) {
          if (key.startsWith("meta.")) {
            continue;
          }
          messages.put(key, props.getProperty(key));
        }
      } catch (final Exception e) {
        Logger.warn("Failed to load messages from URL: " + url);
        e.printStackTrace();
      } finally {
        if (conn != null) {
          conn.disconnect();
          conn = null;
        }
      }
    }

    @Override
    public void save(final Map<String, String> data) {
      final Properties props = new Properties();
      if (data != null) {
        props.putAll(data);
      }
      meta.store(props);

      HttpURLConnection conn = null;
      try {
        final URL u = new URI(url).toURL();
        conn = (HttpURLConnection) u.openConnection();
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

        try (final OutputStream out = conn.getOutputStream()) {
          props.store(out, null);
        }

        final int code = conn.getResponseCode();
        if (code < 200 || code >= 300) {
          Logger.warn("Failed to save messages to URL (HTTP " + code + "): " + url);
          return;
        }

        if (messages == null) {
          messages = new HashMap<>(data != null ? data : Map.of());
        } else {
          messages.clear();
          if (data != null) {
            messages.putAll(data);
          }
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
    }
  }
}
