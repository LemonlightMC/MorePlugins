package com.lemonlightmc.moreplugins.sound;

import java.util.OptionalLong;

import org.bukkit.NamespacedKey;
import org.bukkit.SoundCategory;

import com.lemonlightmc.moreplugins.utils.MathUtils;

public class Playable {
  public static final float DEFAULT_VOLUME = 1f;
  public static final float MINIMUM_VOLUME = 0f;
  public static final float MAXIMUM_VOLUME = 1f;

  public static final float DEFAULT_PITCH = 1f;
  public static final float MINIMUM_PITCH = 0f;
  public static final float MAXIMUM_PITCH = 1f;

  public static final int DEFAULT_PANNING = 0;
  public static final int MINIMUM_PANNING = 0;
  public static final int MAXIMUM_PANNING = 100;
  public static final SoundCategory DEFAULT_SOURCE = SoundCategory.MASTER;

  protected SoundCategory source;
  protected float volume;
  protected float pitch;
  protected OptionalLong seed;
  protected int panning;

  public Playable() {
    this(DEFAULT_SOURCE, DEFAULT_VOLUME, DEFAULT_PITCH, DEFAULT_PANNING, null);
  }

  public Playable(final SoundCategory source) {
    this(source, DEFAULT_VOLUME, DEFAULT_PITCH, DEFAULT_PANNING, null);
  }

  public Playable(final SoundCategory source, final float volume) {
    this(source, volume, DEFAULT_PITCH, DEFAULT_PANNING, null);
  }

  public Playable(final SoundCategory source, final float volume, final float pitch) {
    this(source, volume, pitch, DEFAULT_PANNING, null);
  }

  public Playable(final SoundCategory source, final float volume, final float pitch, final int panning) {
    this(source, volume, pitch, panning, null);
  }

  public Playable(final SoundCategory source, final float volume, final float pitch, final int panning,
      final OptionalLong seed) {
    this.volume = MathUtils.normalizeRangeOrThrow(volume, 0, 1, "Volume");
    this.pitch = MathUtils.normalizeRangeOrThrow(pitch, 0, 1, "Pitch");
    this.panning = MathUtils.normalizeRangeOrThrow(panning, 0, 100, "Panning");
    if (source == null) {
      throw new IllegalArgumentException("Sound Source cant be empty");
    }
    this.source = source;
    this.seed = seed;
  }

  public SoundCategory getSource() {
    return source;
  }

  public void setSource(final SoundCategory source) {
    this.source = source;
  }

  public float getVolume() {
    return volume;
  }

  public void setVolume(final float volume) {
    this.volume = volume;
  }

  public float getPitch() {
    return pitch;
  }

  public void setPitch(final float pitch) {
    this.pitch = pitch;
  }

  public int getPanning() {
    return panning;
  }

  public void setPanning(final int panning) {
    this.panning = panning;
  }

  public OptionalLong getSeed() {
    return seed;
  }

  public void setSeed(final OptionalLong seed) {
    this.seed = seed;
  }

  @Override
  public int hashCode() {
    int result = 31 + ((source == null) ? 0 : source.hashCode());
    result = 31 * result + Float.floatToIntBits(volume);
    result = 31 * result + Float.floatToIntBits(pitch);
    result = 31 * result + ((seed == null) ? 0 : seed.hashCode());
    result = 31 * result + panning;
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    final Playable other = (Playable) obj;
    if (Float.floatToIntBits(volume) != Float.floatToIntBits(other.volume))
      return false;
    if (Float.floatToIntBits(pitch) != Float.floatToIntBits(other.pitch))
      return false;
    if (seed == null) {
      if (other.seed != null)
        return false;
    } else if (!seed.equals(other.seed))
      return false;
    return source == other.source && panning == other.panning;
  }

  @Override
  public String toString() {
    return "Playable [source=" + source + ", volume=" + volume + ", pitch=" + pitch + ", seed=" + seed + ", panning="
        + panning + "]";
  }

  static final class PlayableBuilder {
    private final NamespacedKey key = null;
    private SoundCategory source = DEFAULT_SOURCE;
    private float volume = DEFAULT_VOLUME;
    private float pitch = DEFAULT_PITCH;
    private int panning = DEFAULT_PANNING;
    private OptionalLong seed = OptionalLong.empty();

    PlayableBuilder() {
    }

    PlayableBuilder(final Playable existing) {
      this.volume = existing.volume;
      this.pitch = existing.pitch;
      this.panning = existing.panning;
      this.source = existing.source;
      this.seed = existing.seed;
    }

    public PlayableBuilder source(final SoundCategory source) {
      this.source = source;
      return this;
    }

    public PlayableBuilder volume(final float volume) {
      this.volume = volume;
      return this;
    }

    public PlayableBuilder pitch(final float pitch) {
      this.pitch = pitch;
      return this;
    }

    public PlayableBuilder seed(final long seed) {
      this.seed = OptionalLong.of(seed);
      return this;
    }

    public PlayableBuilder seed(final OptionalLong seed) {
      this.seed = seed;
      return this;
    }

    public Playable build() {
      return new Playable(source, volume, pitch, panning, seed);
    }

    @Override
    public int hashCode() {
      int result = 31 + ((key == null) ? 0 : key.hashCode());
      result = 31 * result + ((source == null) ? 0 : source.hashCode());
      result = 31 * result + Float.floatToIntBits(volume);
      result = 31 * result + Float.floatToIntBits(pitch);
      result = 31 * result + panning;
      result = 31 * result + ((seed == null) ? 0 : seed.hashCode());
      return result;
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj)
        return true;
      if (obj == null || getClass() != obj.getClass())
        return false;
      final PlayableBuilder other = (PlayableBuilder) obj;
      if (key == null) {
        if (other.key != null)
          return false;
      } else if (!key.equals(other.key))
        return false;
      if (Float.floatToIntBits(volume) != Float.floatToIntBits(other.volume))
        return false;
      if (Float.floatToIntBits(pitch) != Float.floatToIntBits(other.pitch))
        return false;
      if (seed == null) {
        if (other.seed != null)
          return false;
      } else if (!seed.equals(other.seed))
        return false;
      return source == other.source && panning == other.panning;
    }

  }
}
