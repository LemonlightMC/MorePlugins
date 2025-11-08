package com.lemonlightmc.moreplugins.sound;

import org.bukkit.Note.Tone;

import com.google.common.base.Preconditions;
import com.lemonlightmc.moreplugins.utils.MathUtils;

public class Note {
  public static final int MINIMUM_NOTE = 0;
  public static final int MAXIMUM_NOTE = 87;

  private static final float[] pitchArray = new float[25];
  static {
    for (int i = 0; i <= 24; i++) {
      // See https://minecraft.wiki/w/Note_Block#Notes
      pitchArray[i] = (float) Math.pow(2, (i - 12) / 12f);
    }
  }

  private final byte note;
  private final Instrument instrument;
  private int pitch;
  private int panning;
  private byte volume;

  public Note(final int note, final Instrument instrument) {
    Preconditions.checkArgument(note >= 0 && note <= 24, "The note value has to be between 0 and 24.");

    this.note = (byte) note;
    this.instrument = instrument;
  }

  @SuppressWarnings("deprecation")
  public Note(final int octave, Tone tone, boolean sharped) {
    if (sharped && !tone.isSharpable()) {
      tone = Tone.values()[tone.ordinal() + 1];
      sharped = false;
    }
    if (octave < 0 || octave > 2 || (octave == 2 && !(tone == Tone.F && sharped))) {
      throw new IllegalArgumentException("Tone and octave have to be between F#0 and F#2");
    }

    this.note = (byte) (octave * Tone.TONES_COUNT + tone.getId(sharped));
    this.instrument = Instrument.NoteInstrument.HARP;
  }

  public Note(final NoteBuilder builder) {
    instrument = builder.instrument;
    note = builder.note;
    panning = builder.panning;
    volume = builder.volume;
  }

  public static Note flat(final int octave, Tone tone) {
    Preconditions.checkArgument(octave != 2, "Octave cannot be 2 for flats");
    tone = tone == Tone.G ? Tone.F : Tone.values()[tone.ordinal() - 1];
    return new Note(octave, tone, tone.isSharpable());
  }

  public static Note sharp(final int octave, final Tone tone) {
    return new Note(octave, tone, true);
  }

  public static Note natural(final int octave, final Tone tone) {
    Preconditions.checkArgument(octave != 2, "Octave cannot be 2 for naturals");
    return new Note(octave, tone, false);
  }

  static NoteBuilder builder() {
    return new NoteBuilder();
  }

  static NoteBuilder builder(final Note note) {
    return new NoteBuilder(note);
  }

  public byte getNote() {
    return note;
  }

  public Instrument getInstrument() {
    return instrument;
  }

  public boolean isCustomInstrument() {
    return instrument.isCustom();
  }

  public int getPanning() {
    return panning;
  }

  public float getPitch() {
    return pitchArray[this.note];
  }

  public byte getVolume() {
    return volume;
  }

  public int getOctave() {
    return note / Tone.TONES_COUNT;
  }

  private byte getToneByte() {
    return (byte) (note % Tone.TONES_COUNT);
  }

  @SuppressWarnings("deprecation")
  public Tone getTone() {
    return Tone.getById(getToneByte());
  }

  @SuppressWarnings("deprecation")
  public boolean isSharped() {
    final byte note = getToneByte();
    return Tone.getById(note).isSharped(note);
  }

  public Note sharped() {
    Preconditions.checkArgument(note < 24, "This note cannot be sharped because it is the highest known note!");
    return new Note(note + 1, instrument);
  }

  public Note flattened() {
    Preconditions.checkArgument(note > 0, "This note cannot be flattened because it is the lowest known note!");
    return new Note(note - 1, instrument);
  }

  @Override
  public int hashCode() {
    int result = 31 + note;
    result = 31 * result + instrument.hashCode();
    result = 31 * result + pitch;
    result = 31 * result + panning;
    return 31 * result + volume;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    final Note other = (Note) obj;
    return note == other.note && instrument.equals(other.instrument)
        && pitch == other.pitch && panning == other.panning && volume == other.volume;
  }

  @Override
  public String toString() {
    return "Note [note=" + getTone().toString() + (isSharped() ? "#" : "") + ", instrument=" + instrument
        + ", pitch=" + pitch + ", panning=" + panning + ", volume=" + volume + "]";
  }

  final static class NoteBuilder {
    Instrument instrument = Instrument.NoteInstrument.HARP;
    byte note = 45;
    float pitch = 0;
    int panning = 0;
    byte volume = 100;

    public NoteBuilder() {
    }

    public NoteBuilder(final Note note) {
      this.instrument = note.getInstrument();
      this.note = note.getNote();
      this.pitch = note.getPitch();
      this.panning = note.getPanning();
      this.volume = note.getVolume();
    }

    public NoteBuilder instrument(final Instrument instrument) {
      this.instrument = instrument;
      return this;
    }

    public NoteBuilder note(byte note) {
      note = (byte) MathUtils.normalizeRangeOrThrow((int) note, MINIMUM_NOTE, MAXIMUM_NOTE, "note");
      this.note = note;
      return this;
    }

    public NoteBuilder volume(float volume) {
      volume = MathUtils.normalizeRangeOrThrow(volume, Playable.MINIMUM_VOLUME, Playable.MAXIMUM_VOLUME, "Volume");
      this.volume = (byte) volume;
      return this;
    }

    public NoteBuilder pitch(float pitch) {
      pitch = MathUtils.normalizeRangeOrThrow(panning, Playable.MINIMUM_PITCH, Playable.MAXIMUM_PITCH, "Pitch");
      this.pitch = pitch;
      return this;
    }

    public NoteBuilder panning(int panning) {
      panning = MathUtils.normalizeRangeOrThrow(panning, Playable.MINIMUM_PANNING, Playable.MAXIMUM_PANNING, "Panning");
      this.panning = panning;
      return this;
    }

    public Note build() {
      return new Note(this);
    }
  }
}
