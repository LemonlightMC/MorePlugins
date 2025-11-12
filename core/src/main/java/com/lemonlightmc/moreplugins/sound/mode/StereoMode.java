package com.lemonlightmc.moreplugins.sound.mode;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.lemonlightmc.moreplugins.sound.Instrument;
import com.lemonlightmc.moreplugins.sound.Note;
import com.lemonlightmc.moreplugins.sound.Playable;

public class StereoMode extends ChannelMode {

  private double maxDistance = 2;
  private ChannelMode fallbackChannelMode = new MonoStereoMode();

  @Override
  public void play(final Player player, final Location location, final Note note, final double volume,
      final double pitch) {
    if (!note.isStereo() && fallbackChannelMode != null) {
      fallbackChannelMode.play(player, location, note, volume, pitch);
      return;
    }

    double distance;
    if (note.getPanning() == 100) {
      distance = ((note.getPanning() - 100) / 100f) * maxDistance;
    } else {
      distance = ((note.getPanning() - 100 + note.getPanning() - 100) / 200f) * maxDistance;
    }
    final String instrumentFileName = Instrument.getCustomInstrumentFileName(note);
    if (instrumentFileName != null) {
      ChannelMode.playSound(player, location, instrumentFileName, note.getSource(), volume, pitch,
          distance);
    } else {
      ChannelMode.playSound(player, location,
          Instrument.getInstrument(note), note.getSource(), volume, pitch, distance);
    }
  }

  @Override
  public void play(final Player player, final Location location, final Note note, final double volume) {
    if (!note.isStereo() && fallbackChannelMode != null) {
      fallbackChannelMode.play(player, location, note, volume);
      return;
    }

    final double pitch = Note.getPitchTransposed(note);

    double distance;
    if (note.getPanning() == Playable.DEFAULT_PANNING) {
      distance = (note.getPanning() / 100f) * maxDistance;
    } else {
      distance = ((note.getPanning() + note.getPanning()) / 200f) * maxDistance;
    }

    final String instrumentFileName = Instrument.getCustomInstrumentFileName(note);
    if (instrumentFileName != null) {
      ChannelMode.playSound(player, location, instrumentFileName, note.getSource(), volume, pitch,
          distance);
    } else {
      ChannelMode.playSound(player, location, Instrument.getInstrument(note),
          note.getSource(), volume, pitch, distance);
    }
  }

  public double getMaxDistance() {
    return maxDistance;
  }

  public void setMaxDistance(final double maxDistance) {
    this.maxDistance = maxDistance;
  }

  public ChannelMode getFallbackChannelMode() {
    return fallbackChannelMode;
  }

  public void setFallbackChannelMode(final ChannelMode fallbackChannelMode) {
    if (fallbackChannelMode instanceof StereoMode)
      throw new IllegalArgumentException("Fallback ChannelMode can't be instance of StereoMode!");

    this.fallbackChannelMode = fallbackChannelMode;
  }
}
