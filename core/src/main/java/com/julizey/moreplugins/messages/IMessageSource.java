package com.julizey.moreplugins.messages;

import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import com.julizey.moreplugins.version.Version;

public interface IMessageSource {
  public static interface IMessageMeta {

    public void modify(final Properties props);

    public void modify(final ResourceBundle bundle);

    public void load(final Properties props);

    public void store(final Properties props);

    public Locale getLocale();

    public void setLocale(final Locale locale);

    public void setLocale(final String locale);

    public Version getVersion();

    public void setVersion(final Version version);

    public void setVersion(final String version);

    public String getAuthor();

    public void setAuthor(final String author);

    public String getDescription();

    public void setDescription(final String description);
  }

  public Map<String, String> getMessages();

  public Locale getLocale();

  public void setLocale(final Locale locale);

  public MessageMeta getMeta();

  public void setMeta(final MessageMeta meta);

  public void load();

  public void save(final Map<String, String> data);

  public void createDefault();

  public boolean hasDefault();

  public int hashCode();

  public boolean equals(final Object obj);

  public String toString();

}
