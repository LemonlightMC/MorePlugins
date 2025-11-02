package com.julizey.moreplugins.apis;

import java.util.OptionalLong;

import org.bukkit.NamespacedKey;

import com.julizey.moreplugins.exceptions.RangeException;

public class Sound implements ISound {

  private static final Emitter EMITTER_SELF = new Emitter() {
    @Override
    public String toString() {
      return "SelfSoundEmitter";
    }
  };
  private final NamespacedKey key;
  private final Source source;
  private final float volume;
  private final float pitch;
  private final OptionalLong seed;

  Sound(final NamespacedKey key, final Source source, final float volume, final float pitch, final OptionalLong seed) {
    this.key = key;
    this.source = source;
    this.volume = volume;
    this.pitch = pitch;
    this.seed = seed;
  }

  Sound(final Source source, final float volume, final float pitch, final OptionalLong seed) {
    this.key = null;
    this.source = source;
    this.volume = volume;
    this.pitch = pitch;
    this.seed = seed;
  }

  static SoundBuilder sound() {
    return new Sound.SoundBuilder();
  }

  static SoundBuilder sound(final ISound existing) {
    return new Sound.SoundBuilder(existing);
  }

  static ISound sound(final NamespacedKey key, final Source source, final float volume,
      final float pitch) {
    return sound().key(key).source(source).volume(volume).pitch(pitch).build();
  }

  static ISound sound(final NamespacedKey name, final Source.Provider source, final float volume,
      final float pitch) {
    return sound(name, source.soundSource(), volume, pitch);
  }

  enum Source {
    MASTER("master"),
    MUSIC("music"),
    RECORD("record"),
    WEATHER("weather"),
    BLOCK("block"),
    HOSTILE("hostile"),
    NEUTRAL("neutral"),
    PLAYER("player"),
    AMBIENT("ambient"),
    VOICE("voice"),
    UI("ui");

    private final String name;

    Source(final String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    public interface Provider {
      Source soundSource();
    }
  }

  interface Emitter {
    static Emitter self() {
      return Sound.EMITTER_SELF;
    }
  }

  @Override
  public NamespacedKey key() {
    return this.key;
  }

  @Override
  public Source source() {
    return this.source;
  }

  @Override
  public float volume() {
    return this.volume;
  }

  @Override
  public float pitch() {
    return this.pitch;
  }

  @Override
  public OptionalLong seed() {
    return this.seed;
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other)
      return true;
    if (!(other instanceof Sound))
      return false;
    final Sound that = (Sound) other;
    return this.key().equals(that.key())
        && this.source == that.source
        && this.volume == that.volume
        && this.pitch == that.pitch
        && this.seed.equals(that.seed);
  }

  @Override
  public int hashCode() {
    int result = this.key().hashCode();
    result = (31 * result) + this.source.hashCode();
    result = (31 * result) + Float.hashCode(this.volume);
    result = (31 * result) + Float.hashCode(this.pitch);
    result = (31 * result) + this.seed.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "Sound [key=" + key + ", source=" + source + ", volume=" + volume + ", pitch=" + pitch + ", seed=" + seed
        + "]";
  }

  static final class SoundBuilder implements ISoundBuilder {
    private static final float DEFAULT_VOLUME = 1f;
    private static final float DEFAULT_PITCH = 1f;
    private NamespacedKey key = null;
    private Source source = Source.MASTER;
    private float volume = DEFAULT_VOLUME;
    private float pitch = DEFAULT_PITCH;
    private OptionalLong seed = OptionalLong.empty();

    SoundBuilder() {
    }

    SoundBuilder(final ISound existing) {
      this.source(existing.source())
          .volume(existing.volume())
          .pitch(existing.pitch())
          .seed(existing.seed());
    }

    public SoundBuilder key(final NamespacedKey key) {
      this.key = key;
      return this;
    }

    public SoundBuilder source(final Source source) {
      this.source = source;
      return this;
    }

    public SoundBuilder source(final Source.Provider source) {
      return this.source(source.soundSource());
    }

    public SoundBuilder volume(final float volume) {
      if (volume < 0 || volume > Integer.MAX_VALUE) {
        throw new RangeException("Invalid Range for Volume");
      }
      this.volume = volume;
      return this;
    }

    public SoundBuilder pitch(final float pitch) {
      if (volume < -1 || volume > 1) {
        throw new RangeException("Invalid Range for Volume");
      }
      this.pitch = pitch;
      return this;
    }

    public SoundBuilder seed(final long seed) {
      this.seed = OptionalLong.of(seed);
      return this;
    }

    public SoundBuilder seed(final OptionalLong seed) {
      this.seed = seed;
      return this;
    }

    public ISound build() {
      return new Sound(key, source, volume, pitch, seed);
    }

    @Override
    public int hashCode() {
      int result = 31 + ((key == null) ? 0 : key.hashCode());
      result = 31 * result + ((source == null) ? 0 : source.hashCode());
      result = 31 * result + Float.floatToIntBits(volume);
      result = 31 * result + Float.floatToIntBits(pitch);
      return 31 * result + ((seed == null) ? 0 : seed.hashCode());
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null || getClass() != obj.getClass()) {
        return false;
      }
      final SoundBuilder other = (SoundBuilder) obj;
      if (key == null && other.key != null || seed == null && other.seed != null) {
        return false;
      }
      return key.equals(other.key) && source != other.source
          && Float.floatToIntBits(volume) != Float.floatToIntBits(other.volume)
          && Float.floatToIntBits(pitch) != Float.floatToIntBits(other.pitch) && seed.equals(other.seed);
    }
  }
}
