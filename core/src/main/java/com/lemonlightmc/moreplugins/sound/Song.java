package com.lemonlightmc.moreplugins.sound;

import java.util.List;

public class Song extends Playable {
  protected PlayableMetadata meta;
  protected long length;
  protected double lengthInSeconds;
  protected List<Note> notes;

  public Song() {
    super();
    this.length = 0;
    this.lengthInSeconds = length == 0 ? 0 : getTimeInSecondsAtTick(length);
  }

  public Song(final List<Note> notes) {
    super();
    this.length = notes.size();
    this.lengthInSeconds = length == 0 ? 0 : getTimeInSecondsAtTick(length);
  }

  public List<Note> getNotes() {
    return notes;
  }

  public void setNotes(List<Note> notes) {
    this.notes = notes;
  }

  public PlayableMetadata getMetadata() {
    return meta;
  }

  public void setMetadata(final PlayableMetadata meta) {
    this.meta = meta;
  }

  public long getLength() {
    return length;
  }

  public double getLengthInSeconds() {
    return lengthInSeconds;
  }

  public double getDelay() {
    return 20 / getTempo(0);
  }

  public double getTempo() {
    return Playable.COMMON_TEMPO;
  }

  public double getTempo(long tick) {
    if (tick < -1) {
      tick = -1;
    }
    return Playable.COMMON_TEMPO;
  }

  public double getTimeInSecondsAtTick(final long tick) {
    if (tick <= 0 || length == 0)
      return 0;

    if (tick >= length)
      return lengthInSeconds;

    return tick * (1 / getTempo(0));
  }
}
