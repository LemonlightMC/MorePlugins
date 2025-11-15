package com.lemonlightmc.moreplugins.sound;

import org.bukkit.SoundCategory;

import com.lemonlightmc.moreplugins.utils.MathUtils;
import com.lemonlightmc.moreplugins.wrapper.Builder;

public abstract class Playable implements Cloneable {
  public static final float DEFAULT_VOLUME = 1f;
  public static final float MINIMUM_VOLUME = 0f;
  public static final float MAXIMUM_VOLUME = 1f;

  public static final float DEFAULT_PITCH = 1f;
  public static final float MINIMUM_PITCH = 0f;
  public static final float MAXIMUM_PITCH = 1f;

  public static final int DEFAULT_PANNING = 0;
  public static final int MINIMUM_PANNING = 0;
  public static final int MAXIMUM_PANNING = 100;

  // Assumes that most common tempo is close to 10 tps
  public static final double COMMON_TEMPO = 10d;
  public static final SoundCategory DEFAULT_SOURCE = SoundCategory.MASTER;
  public static final long DEFAULT_SEED = 0;

  protected SoundCategory source;
  protected double volume;
  protected double pitch;
  protected long seed;
  protected int panning;
  protected boolean isStereo = false;

  public Playable() {
    this(DEFAULT_SOURCE, DEFAULT_VOLUME, DEFAULT_PITCH, DEFAULT_PANNING, DEFAULT_SEED);
  }

  public Playable(final SoundCategory source) {
    this(source, DEFAULT_VOLUME, DEFAULT_PITCH, DEFAULT_PANNING, DEFAULT_SEED);
  }

  public Playable(final SoundCategory source, final double volume) {
    this(source, volume, DEFAULT_PITCH, DEFAULT_PANNING, DEFAULT_SEED);
  }

  public Playable(final SoundCategory source, final double volume, final double pitch) {
    this(source, volume, pitch, DEFAULT_PANNING, DEFAULT_SEED);
  }

  public Playable(final SoundCategory source, final double volume, final double pitch, final int panning) {
    this(source, volume, pitch, panning, DEFAULT_SEED);
  }

  public Playable(final Playable playable) {
    this.volume = playable.volume;
    this.pitch = playable.pitch;
    this.panning = playable.panning;
    this.source = playable.source;
    this.seed = playable.seed;
  }

  public Playable(final SoundCategory source, final double volume, final double pitch, final int panning,
      final long seed) {
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

  public double getVolume() {
    return volume;
  }

  public void setVolume(final double volume) {
    this.volume = volume;
  }

  public double getPitch() {
    return pitch;
  }

  public void setPitch(final double pitch) {
    this.pitch = pitch;
  }

  public int getPanning() {
    return panning;
  }

  public void setPanning(final int panning) {
    this.panning = panning;
  }

  public long getSeed() {
    return seed;
  }

  public void setSeed(final long seed) {
    this.seed = seed;
  }

  public void setStereo(final boolean isStereo) {
    this.isStereo = isStereo;
  }

  public boolean isStereo() {
    return isStereo;
  }

  @Override
  public int hashCode() {
    int result = 31 + ((source == null) ? 0 : source.hashCode());
    result = 31 * result + Long.hashCode(Double.doubleToLongBits(volume));
    result = 31 * result + Long.hashCode(Double.doubleToLongBits(pitch));
    result = 31 * result + Long.hashCode(seed);
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
    return source == other.source && panning == other.panning && seed == other.seed
        && Double.doubleToLongBits(volume) != Double.doubleToLongBits(other.volume)
        && Double.doubleToLongBits(pitch) != Double.doubleToLongBits(other.pitch);
  }

  @Override
  public String toString() {
    return "Playable [source=" + source + ", volume=" + volume + ", pitch=" + pitch + ", seed=" + seed + ", panning="
        + panning + "]";
  }

  public static class PlayableBuilder implements Builder<Playable> {
    private SoundCategory source = DEFAULT_SOURCE;
    private double volume = DEFAULT_VOLUME;
    private double pitch = DEFAULT_PITCH;
    private int panning = DEFAULT_PANNING;
    private long seed = DEFAULT_SEED;

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

    public SoundCategory source() {
      return source;
    }

    public PlayableBuilder volume(final double volume) {
      this.volume = volume;
      return this;
    }

    public double volume() {
      return volume;
    }

    public PlayableBuilder pitch(final double pitch) {
      this.pitch = pitch;
      return this;
    }

    public double pitch() {
      return pitch;
    }

    public PlayableBuilder panning(final int panning) {
      this.panning = panning;
      return this;
    }

    public int panning() {
      return panning;
    }

    public PlayableBuilder seed(final long seed) {
      this.seed = seed;
      return this;
    }

    public long seed() {
      return seed;
    }

    @Override
    public int hashCode() {
      int result = 31 + ((source == null) ? 0 : source.hashCode());
      result = 31 * result + Long.hashCode(Double.doubleToLongBits(volume));
      result = 31 * result + Long.hashCode(Double.doubleToLongBits(pitch));
      result = 31 * result + panning;
      result = 31 * result + Long.hashCode(seed);
      return result;
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj)
        return true;
      if (obj == null || getClass() != obj.getClass())
        return false;
      final PlayableBuilder other = (PlayableBuilder) obj;
      return source == other.source && panning == other.panning && seed == other.seed
          && Double.doubleToLongBits(volume) != Double.doubleToLongBits(other.volume)
          && Double.doubleToLongBits(pitch) != Double.doubleToLongBits(other.pitch);
    }

    @Override
    public String toString() {
      return "PlayableBuilder [source=" + source + ", volume=" + volume + ", pitch=" + pitch
          + ", panning=" + panning + ", seed=" + seed + "]";
    }

    @Override
    public Playable build() {
      throw new UnsupportedOperationException("Cant directly build 'Playable'");
    }
  }
}
