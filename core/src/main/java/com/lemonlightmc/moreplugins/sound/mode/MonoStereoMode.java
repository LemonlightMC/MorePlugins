package com.lemonlightmc.moreplugins.sound.mode;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.lemonlightmc.moreplugins.sound.Instrument;
import com.lemonlightmc.moreplugins.sound.Instrument.CustomInstrument;
import com.lemonlightmc.moreplugins.sound.Note;

public class MonoStereoMode extends ChannelMode {
  private double distance = 2;

  @Override
  public void play(final Player player, final Location location,
      final Note note, final double volume, final double pitch) {
    final CustomInstrument customInstrument = Instrument.getCustomInstrumentForNote(note);

    if (customInstrument != null) {
      ChannelMode.playSound(player, location, customInstrument.getFileName(), note.getSource(), volume, pitch,
          distance);
      ChannelMode.playSound(player, location, customInstrument.getFileName(), note.getSource(), volume, pitch,
          -distance);
    } else {
      final org.bukkit.Sound sound = Instrument.getInstrument(note);
      ChannelMode.playSound(player, location, sound, note.getSource(), volume, pitch, distance);
      ChannelMode.playSound(player, location, sound, note.getSource(), volume, pitch, -distance);
    }
  }

  @Override
  public void play(final Player player, final Location location, final Note note, final double volume) {
    final double pitch = Note.getPitchTransposed(note);
    final CustomInstrument customInstrument = Instrument.getCustomInstrumentForNote(note);

    if (customInstrument != null) {
      ChannelMode.playSound(player, location, customInstrument.getFileName(), note.getSource(), volume, pitch,
          distance);
      ChannelMode.playSound(player, location, customInstrument.getFileName(), note.getSource(), volume, pitch,
          -distance);
    } else {
      final org.bukkit.Sound sound = Instrument.getInstrument(note);
      ChannelMode.playSound(player, location, sound, note.getSource(), volume, pitch, distance);
      ChannelMode.playSound(player, location, sound, note.getSource(), volume, pitch, -distance);
    }
  }

  public double getDistance() {
    return distance;
  }

  public void setDistance(final double distance) {
    this.distance = distance;
  }
}
