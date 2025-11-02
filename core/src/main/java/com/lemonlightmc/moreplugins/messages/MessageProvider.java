package com.lemonlightmc.moreplugins.messages;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MessageProvider implements IMessageProvider {
  private final Map<Locale, Map<String, String>> messages = new HashMap<>();
  private final Map<Locale, IMessageSource> sources = new HashMap<>();
  private Locale defaultLocale = Locale.ENGLISH;

  @Override
  public String getMessage(final String key, Locale locale) {
    if (key == null || key.length() == 0) {
      return null;
    }
    if (locale == null) {
      locale = defaultLocale;
    }
    final Map<String, String> localeMessages = getMessages(locale);
    return localeMessages == null ? null : localeMessages.get(key);
  }

  @Override
  public String getMessage(final String key) {
    if (key == null || key.length() == 0) {
      return null;
    }
    final Map<String, String> localeMessages = getMessages(defaultLocale);
    return localeMessages == null ? null : localeMessages.get(key);
  }

  @Override
  public Map<String, String> getMessages(final Locale locale) {
    if (locale == null) {
      return null;
    }
    final Map<String, String> localeMessages = messages.get(locale);
    return localeMessages == null ? loadLocale(locale) : localeMessages;
  }

  @Override
  public Map<Locale, String> getMessages(final String key) {
    final Map<Locale, String> result = new HashMap<>();
    String value;
    for (final Locale locale : messages.keySet()) {
      value = getMessage(key, locale);
      if (value != null) {
        result.put(locale, value);
      }
    }
    return result;
  }

  @Override
  public Map<Locale, Map<String, String>> getMessages() {
    return messages;
  }

  @Override
  public Locale getDefaultLocale() {
    return defaultLocale;
  }

  @Override
  public void setDefaultLocale(final Locale locale) {
    this.defaultLocale = locale;
  }

  @Override
  public void reload() {
    messages.clear();
    for (final IMessageSource source : sources.values()) {
      messages.put(source.getLocale(), source.getMessages());
    }
  }

  @Override
  public void load() {
    for (final IMessageSource source : sources.values()) {
      messages.put(source.getLocale(), source.getMessages());
    }
  }

  @Override
  public void save() {
    for (final IMessageSource source : sources.values()) {
      final Map<String, String> localeMessages = messages.get(source.getLocale());
      if (localeMessages != null) {
        source.save(localeMessages);
      }
    }
  }

  @Override
  public void addSource(final IMessageSource source) {
    sources.put(source.getLocale(), source);
  }

  @Override
  public void removeSource(final IMessageSource source) {
    sources.remove(source.getLocale());
  }

  @Override
  public IMessageSource getSource(final Locale locale) {
    return sources.get(locale);
  }

  @Override
  public Map<Locale, IMessageSource> getSources() {
    return sources;
  }

  @Override
  public boolean hasSource(final IMessageSource source) {
    return sources.containsValue(source);
  }

  @Override
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
    final MessageProvider other = (MessageProvider) obj;
    return defaultLocale.equals(other.defaultLocale) && sources.equals(other.sources)
        && messages.equals(other.messages);
  }

  @Override
  public String toString() {
    return "MessageProvider{sources=" + sources + ", messages=" + messages + "}";
  }

  private Map<String, String> loadLocale(final Locale locale) {
    final IMessageSource source = sources.get(locale);
    if (source == null || !source.getLocale().equals(locale)) {
      Logger.warn("No message source found for locale: " + locale);
      return null;
    }
    final Map<String, String> localeMessage = source.getMessages();
    if (localeMessage == null || localeMessage.isEmpty()) {
      Logger.warn("Failed to load messages for locale: " + locale);
      return null;
    }
    messages.put(locale, localeMessage);
    return localeMessage;
  }

}
