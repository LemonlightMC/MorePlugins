package com.julizey.moreplugins.apis;

import java.util.OptionalLong;

import org.bukkit.NamespacedKey;

import com.julizey.moreplugins.apis.Sound.SoundBuilder;
import com.julizey.moreplugins.apis.Sound.Source;

public interface ISound {

  public NamespacedKey key();

  public Source source();

  public float volume();

  public float pitch();

  public OptionalLong seed();

  public boolean equals(Object other);

  public int hashCode();

  public String toString();

  public static interface ISoundBuilder {

    public ISoundBuilder key(final NamespacedKey key);

    public ISoundBuilder source(final Source source);

    public ISoundBuilder source(final Source.Provider source);

    public SoundBuilder volume(final float volume);

    public ISoundBuilder pitch(final float pitch);

    public ISoundBuilder seed(final long seed);

    public ISoundBuilder seed(final OptionalLong seed);

    public ISound build();

    public boolean equals(Object other);

    public int hashCode();

    public String toString();
  }
}