package com.lemonlightmc.moreplugins.sound;

import com.lemonlightmc.moreplugins.utils.MathUtils;

public class Instrument {

  public static final int DEFAULT_INSTRUMENT = NoteInstrument.HARP.getKey();

  protected final int key;
  protected final boolean isCustom;

  public Instrument(final int key, final boolean isCustom) {
    this.key = MathUtils.normalizeRangeOrThrow(key, Note.MINIMUM_NOTE, Note.MAXIMUM_NOTE, "Instrument Key");
    this.isCustom = isCustom;
  }

  public int getKey() {
    return key;
  }

  public boolean isCustom() {
    return isCustom;
  }

  @Override
  public int hashCode() {
    return 31 * (31 + key) + (isCustom ? 1231 : 1237);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    Instrument other = (Instrument) obj;
    return key == other.key && isCustom == other.isCustom;
  }

  @Override
  public String toString() {
    return "Instrument [key=" + key + ", isCustom=" + isCustom + "]";
  }

  /**
   * Enumeration of Minecraft non-custom instruments
   */
  public static class NoteInstrument extends Instrument {
    /**
     * Block: Any than is not used by other instruments
     * Same as {@link #HARP}
     */
    public static final NoteInstrument PIANO = new NoteInstrument(0);

    /**
     * Block: Any than is not used by other instruments
     * Same as {@link #PIANO}
     */
    public static final NoteInstrument HARP = new NoteInstrument(0);

    /**
     * Block: Wood
     */
    public static final NoteInstrument BASS = new NoteInstrument(1);

    /**
     * Block: Stone
     */
    public static final NoteInstrument BASS_DRUM = new NoteInstrument(2);

    /**
     * Block: Sand
     */
    public static final NoteInstrument SNARE_DRUM = new NoteInstrument(3);

    /**
     * Block: Glass
     */
    public static final NoteInstrument CLICK = new NoteInstrument(4);

    /**
     * Block: Wool
     */
    public static final NoteInstrument GUITAR = new NoteInstrument(5);

    /**
     * Block: Clay
     */
    public static final NoteInstrument FLUTE = new NoteInstrument(6);

    /**
     * Block: Gold
     */
    public static final NoteInstrument BELL = new NoteInstrument(7);

    /**
     * Block: Packed Ice
     */
    public static final NoteInstrument CHIME = new NoteInstrument(8);

    /**
     * Block: Bone
     */
    public static final NoteInstrument XYLOPHONE = new NoteInstrument(9);

    /**
     * Block: Iron
     */
    public static final NoteInstrument IRON_XYLOPHONE = new NoteInstrument(10);

    /**
     * Block: Soul Sand
     */
    public static final NoteInstrument COW_BELL = new NoteInstrument(11);

    /**
     * Block: Pumpkin
     */
    public static final NoteInstrument DIDGERIDOO = new NoteInstrument(12);

    /**
     * Block: Emerald
     */
    public static final NoteInstrument BIT = new NoteInstrument(13);

    /**
     * Block: Hay
     */
    public static final NoteInstrument BANJO = new NoteInstrument(14);

    /**
     * Block: Glowstone
     */
    public static final NoteInstrument PLING = new NoteInstrument(15);

    public NoteInstrument(final int key) {
      super(key, false);
    }
  }
}
