package com.lemonlightmc.moreplugins.messages;

import java.util.Locale;
import java.util.Map;

import com.lemonlightmc.moreplugins.interfaces.Reloadable;

public interface IMessageProvider extends Reloadable {

  public String getMessage(final String key, final Locale locale);

  public String getMessage(final String key);

  public Map<Locale, String> getMessages(final String key);

  public Map<String, String> getMessages(final Locale locale);

  public Map<Locale, Map<String, String>> getMessages();

  public Locale getDefaultLocale();

  public void setDefaultLocale(final Locale locale);

  public void reload();

  public void load();

  public void save();

  public void addSource(final IMessageSource source);

  public IMessageSource getSource(final Locale locale);

  public boolean hasSource(final IMessageSource source);

  public boolean hasSource(final Locale locale);

  public void removeSource(final IMessageSource source);

  public Map<Locale, IMessageSource> getSources();

  public int hashCode();

  public boolean equals(final Object obj);

  public String toString();
}
