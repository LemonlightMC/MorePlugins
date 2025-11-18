package com.lemonlightmc.moreplugins.sound.mode;

import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import com.lemonlightmc.moreplugins.sound.Note;
import com.lemonlightmc.moreplugins.sound.Playable;
import com.lemonlightmc.moreplugins.sound.Sound;

public abstract class ChannelMode {

    public abstract void play(Player player, Location location, Note note, double volume, double pitch);

    public abstract void play(Player player, Location location, Sound sound,
            double volume, double pitch);

    public void play(final Player player, final Location location, final Note note, final double volume) {
        play(player, location, note, volume, Note.getPitchTransposed(note));
    }

    public void play(final Player player, final Location location, final Note note) {
        play(player, location, note, note.getVolume(), Note.getPitchTransposed(note));
    }

    public void play(final Player player, final Location location, final Sound sound,
            final double volume) {
        play(player, location, sound, volume, sound.getPitch());
    }

    public void play(final Player player, final Location location, final Sound sound) {
        play(player, location, sound, sound.getVolume(), sound.getPitch());
    }

    public static void playSound(final Player player, Location location, final String sound,
            final SoundCategory category, final double volume,
            final double pitch, final double distance) {
        location = stereoPan(location, distance);
        player.playSound(location, sound, category, (float) volume, (float) pitch, Playable.DEFAULT_SEED);
    }

    public static void playSound(final Player player, Location location, final org.bukkit.Sound sound,
            final SoundCategory category, final double volume,
            final double pitch, final double distance) {
        location = stereoPan(location, distance);
        player.playSound(location, sound, category, (float) volume, (float) pitch, Playable.DEFAULT_SEED);
    }

    public static Location stereoPan(final Location location, final double distance) {
        int angle = (int) location.getYaw();
        while (angle < 0)
            angle += 360;
        angle = angle % 360;
        return location.clone().add(Math.cos(angle) * distance, 0, Math.sin(angle) * distance);
    }
}
