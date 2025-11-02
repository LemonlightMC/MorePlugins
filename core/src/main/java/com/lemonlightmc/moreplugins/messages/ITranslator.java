package com.lemonlightmc.moreplugins.messages;

import java.util.List;
import java.util.Locale;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

public interface ITranslator {

  public NamespacedKey name();

  public Locale providesLocale();

  public List<String> providesTranslations();

  public boolean canTranslate(final String key);

  public boolean canTranslate(final Locale locale);

  public boolean canTranslate(final String key, final Locale locale);

  public boolean canTranslate(final String key, final Player player);

  public String translate(final String key, final Locale locale);

  public String translate(final String key, final Player player);

}
