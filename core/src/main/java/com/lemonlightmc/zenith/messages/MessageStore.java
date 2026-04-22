package com.lemonlightmc.zenith.messages;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class MessageStore implements Iterable<MessageRepo<?>> {
  private final Map<Locale, MessageRepo<?>> repos = new HashMap<>();
  // private final Map<Locale, Map<String, String>> messages = new HashMap<>();
  private Locale defaultLocale = Locale.ENGLISH;

  public MessageStore() {
  }

  public MessageStore(final Locale defaultLocale) {
    this.defaultLocale = defaultLocale;
  }

  public void reloadAll() {
    for (final MessageRepo<?> repo : repos.values()) {
      if (repo.isEmpty()) {
        repo.load();
      }
    }
  }

  public void loadAll() {
    for (final MessageRepo<?> repo : repos.values()) {
      if (repo.isEmpty()) {
        repo.load();
      }
    }
  }

  public void clearAll() {
    repos.clear();
  }

  public String getMessage(final String key, final Locale locale) {
    if (key == null || key.length() == 0) {
      return null;
    }
    final MessageRepo<?> repo = repos.get(locale);
    return repo == null ? null : repo.getMessage(key);
  }

  public String getMessage(final String key) {
    if (key == null || key.length() == 0) {
      return null;
    }
    final MessageRepo<?> repo = repos.get(defaultLocale);
    return repo == null ? null : repo.getMessage(key);
  }

  public Locale getDefaultLocale() {
    return defaultLocale;
  }

  public void setDefaultLocale(final Locale locale) {
    this.defaultLocale = locale;
  }

  public void addRepo(final MessageRepo<?> repo) {
    repos.put(repo.getLocale(), repo);
  }

  public void removeRepo(final MessageRepo<?> repo) {
    repos.remove(repo.getLocale());
  }

  public MessageRepo<?> getRepo(final Locale locale) {
    return repos.get(locale);
  }

  public Map<Locale, MessageRepo<?>> getRepos() {
    return repos;
  }

  public boolean hasRepo(final MessageRepo<?> repo) {
    return repos.containsValue(repo);
  }

  public boolean hasRepo(final Locale locale) {
    return repos.containsKey(locale);
  }

  public Iterator<MessageRepo<?>> iterator() {
    return repos.values().iterator();
  }

  @Override
  public int hashCode() {
    return 31 * defaultLocale.hashCode() + repos.hashCode();
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
    return defaultLocale.equals(other.defaultLocale) && repos.equals(other.repos);
  }

  @Override
  public String toString() {
    return "MessageStore [repos=" + repos + ", defaultLocale=" + defaultLocale + "]";
  }
}
