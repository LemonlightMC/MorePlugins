package com.julizey.moreplugins.messages;

import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import com.julizey.moreplugins.messages.IMessageSource.IMessageMeta;
import com.julizey.moreplugins.utils.FileUtils;
import com.julizey.moreplugins.utils.StringUtils;
import com.julizey.moreplugins.version.Version;

public class MessageMeta implements IMessageMeta {
  public String author;
  public Version version;
  public String description;
  public Locale locale;

  public MessageMeta() {
    this.locale = StringUtils.parseLocale("en");
    this.version = new Version("1.0.0");
    this.author = "Unknown";
    this.description = "No description";
  }

  public MessageMeta(final String locale) {
    this.locale = StringUtils.parseLocale(locale);
    this.version = new Version("1.0.0");
    this.author = "Unknown";
    this.description = "No description";
  }

  public MessageMeta(final Locale locale) {
    this.locale = locale;
    this.version = new Version("1.0.0");
    this.author = "Unknown";
    this.description = "No description";
  }

  public MessageMeta(final Properties props) {
    this.locale = StringUtils.parseLocale(props.getProperty("meta.locale", "en"));
    this.version = new Version(props.getProperty("meta.version", "1.0.0"));
    this.author = props.getProperty("meta.author", "Unknown");
    this.description = props.getProperty("meta.description", "No description");
  }

  @Override
  public void modify(final Properties props) {
    if (props.containsKey("meta.locale")) {
      this.locale = StringUtils.parseLocale(props.getProperty("meta.locale", "en"));
    }
    if (props.containsKey("meta.version")) {
      this.version = new Version(props.getProperty("meta.version", "1.0.0"));
    }
    if (props.containsKey("meta.author")) {
      this.author = props.getProperty("meta.author", "Unknown");
    }
    if (props.containsKey("meta.description")) {
      this.description = props.getProperty("meta.description", "No description");
    }
  }

  @Override
  public void modify(final ResourceBundle bundle) {
    if (bundle.containsKey("meta.locale")) {
      this.locale = StringUtils.parseLocale(FileUtils.getResourceBundleString(bundle, "meta.locale", "en"));
    }
    if (bundle.containsKey("meta.version")) {
      this.version = new Version(FileUtils.getResourceBundleString(bundle, "meta.version", "1.0.0"));
    }
    if (bundle.containsKey("meta.author")) {
      this.author = FileUtils.getResourceBundleString(bundle, "meta.author", "Unknown");
    }
    if (bundle.containsKey("meta.description")) {
      this.description = FileUtils.getResourceBundleString(bundle, "meta.description", "No description");
    }
  }

  @Override
  public void load(final Properties props) {
    this.locale = StringUtils.parseLocale(props.getProperty("meta.locale", "en"));
    this.version = new Version(props.getProperty("meta.version", "1.0.0"));
    this.author = props.getProperty("meta.author", "Unknown");
    this.description = props.getProperty("meta.description", "No description");
  }

  @Override
  public void store(final Properties props) {
    props.setProperty("meta.locale", locale.toLanguageTag());
    props.setProperty("meta.version", version.toString());
    props.setProperty("meta.author", author);
    props.setProperty("meta.description", description);
  }

  public void setLocale(final Locale locale) {
    this.locale = locale;
  }

  public void setLocale(final String locale) {
    this.locale = StringUtils.parseLocale(locale);
  }

  public Locale getLocale() {
    return locale;
  }

  public void setVersion(final String version) {
    this.version = new Version(version);
  }

  public void setVersion(final Version version) {
    this.version = version;
  }

  public Version getVersion() {
    return version;
  }

  public void setAuthor(final String author) {
    this.author = author;
  }

  public String getAuthor() {
    return author;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public int hashCode() {
    int result = 31 + ((author == null) ? 0 : author.hashCode());
    result = 31 * result + ((version == null) ? 0 : version.hashCode());
    result = 31 * result + ((description == null) ? 0 : description.hashCode());
    return 31 * result + ((locale == null) ? 0 : locale.hashCode());
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final MessageMeta other = (MessageMeta) obj;
    if (locale == null && other.locale != null || version == null && other.version != null
        || description == null && other.description != null || author == null && other.author != null) {
      return false;
    }
    return locale.equals(other.locale) && version.equals(other.version) && author.equals(other.author)
        && description.equals(other.description);
  }

  @Override
  public String toString() {
    return "MessageMeta [author=" + author + ", version=" + version + ", description=" + description + ", locale="
        + locale + "]";
  }
}
