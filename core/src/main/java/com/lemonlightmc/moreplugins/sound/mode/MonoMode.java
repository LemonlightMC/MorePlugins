package com.lemonlightmc.moreplugins.sound.mode;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.lemonlightmc.moreplugins.sound.Instrument;
import com.lemonlightmc.moreplugins.sound.Note;

public class MonoMode extends ChannelMode {

  @Override
  public void play(final Player player, final Location location, final Note note, final double volume,
      final double pitch) {

    final String instrumentFileName = Instrument.getCustomInstrumentFileName(note);
    if (instrumentFileName != null) {
      player.playSound(location, instrumentFileName, note.getSource(), (float) volume, (float) pitch, 0);
    } else {
      player.playSound(location,
          Instrument.getInstrument(note), note.getSource(), (float) volume, (float) pitch, 0);
    }
  }
}
