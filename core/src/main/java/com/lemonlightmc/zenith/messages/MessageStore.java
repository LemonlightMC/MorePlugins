package com.lemonlightmc.zenith.messages;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MessageStore {
  private final Map<Locale, IMessageSource<?>> sources = new HashMap<>();
  private final Map<Locale, Map<String, String>> messages = new HashMap<>();
  private Locale defaultLocale = Locale.ENGLISH;

  public void reloadAll() {
    for (final IMessageSource<?> source : sources.values()) {
      source.load();
    }
  }

  public void loadAll() {
    for (final Map<String, String> localeMap : messages.values()) {
      localeMap.clear();
    }
    messages.clear();
    for (final Map.Entry<Locale, IMessageSource<?>> entry : sources.entrySet()) {
      loadSource(entry.getKey(), entry.getValue());
    }
  }

  private Map<String, String> loadLocale(final Locale locale) {
    final IMessageSource<?> source = sources.get(locale);
    if (source == null) {
      Logger.warn("No message source found for locale: " + locale);
      return null;
    }
    final Map<String, String> localeMessage = source.load();
    if (localeMessage == null || localeMessage.isEmpty()) {
      Logger.warn("Failed to load messages for locale: " + locale);
      return null;
    }
    messages.put(locale, localeMessage);
    return localeMessage;
  }

  private Map<String, String> loadSource(final Locale locale, final IMessageSource<?> source) {
    final Map<String, String> localeMessage = source.load();
    if (localeMessage == null || localeMessage.isEmpty()) {
      Logger.warn("Failed to load messages for locale: " + locale);
      return null;
    }
    messages.put(locale, localeMessage);
    return localeMessage;
  }

  public String getMessage(final String key, final Locale locale) {
    if (key == null || key.length() == 0) {
      return null;
    }
    final Map<String, String> localeMessages = getMessages(locale == null ? defaultLocale : locale);
    return localeMessages == null ? null : localeMessages.get(key);
  }

  public String getMessage(final String key) {
    if (key == null || key.length() == 0) {
      return null;
    }
    final Map<String, String> localeMessages = getMessages(defaultLocale);
    return localeMessages == null ? null : localeMessages.get(key);
  }

  public Map<String, String> getMessages(final Locale locale) {
    if (locale == null) {
      return null;
    }
    final Map<String, String> localeMessages = messages.get(locale);
    return localeMessages == null ? loadLocale(locale) : localeMessages;
  }

  public Map<Locale, Map<String, String>> getMessages() {
    return messages;
  }

  public Locale getDefaultLocale() {
    return defaultLocale;
  }

  public void setDefaultLocale(final Locale locale) {
    this.defaultLocale = locale;
  }

  public void addSource(final IMessageSource<?> source) {
    sources.put(source.getLocale(), source);
  }

  public void removeSource(final IMessageSource<?> source) {
    sources.remove(source.getLocale());
  }

  public IMessageSource<?> getSource(final Locale locale) {
    return sources.get(locale);
  }

  public Map<Locale, IMessageSource<?>> getSources() {
    return sources;
  }

  public boolean hasSource(final IMessageSource<?> source) {
    return sources.containsValue(source);
  }

  public boolean hasSource(final Locale locale) {
    return sources.containsKey(locale);
  }

  @Override
  public int hashCode() {
    int result = 31 * defaultLocale.hashCode() + messages.hashCode();
    result = 31 * result + sources.hashCode();
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final MessageStore other = (MessageStore) obj;
    return defaultLocale.equals(other.defaultLocale) && sources.equals(other.sources)
        && messages.equals(other.messages);
  }

  @Override
  public String toString() {
    return "MessageStore [sources=" + sources + ", messages=" + messages + ", defaultLocale=" + defaultLocale + "]";
  }
}
