package com.lemonlightmc.zenith.messages;

import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import com.lemonlightmc.zenith.version.Version;
import com.lemonlightmc.zenith.interfaces.Cloneable;

public interface IMessageSource<T extends IMessageSource<T>> extends Cloneable<T> {

  public T setLocale(final Locale locale);

  public T setLocale(final String locale);

  public Locale getLocale();

  public Version getVersion();

  public T setVersion(final Version version);

  public T setVersion(final String version);

  public String getAuthor();

  public T setAuthor(final String author);

  public String getDescription();

  public T setDescription(final String description);

  public Map<String, String> load(Properties props);

  public Map<String, String> load(ResourceBundle props);

  public Map<String, String> load();

  public T save(Properties props);

  public T save();

  public T createDefault();

  public boolean hasDefault();

  public T clone();

  public int hashCode();

  public boolean equals(final Object obj);

  public String toString();
}
