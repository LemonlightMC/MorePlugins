package com.lemonlightmc.moreplugins.sound.mode;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import com.lemonlightmc.moreplugins.sound.Note;

public abstract class ChannelMode {

    public abstract void play(Player player, Location location, Note note, double volume, double pitch);

    public void play(final Player player, final Location location, final Note note, final double volume) {
        play(player, location, note, volume, Note.getPitchTransposed(note));
    }

    public static void playSound(final Player player, Location location, final String sound,
            final SoundCategory category, final double volume,
            final double pitch, double distance,
            final long seed) {
        location = stereoPan(location, distance);
        player.playSound(location, sound, category, (float) volume, (float) pitch, seed);
    }

    public static void playSound(final Player player, Location location, final Sound sound,
            final SoundCategory category, final double volume,
            final double pitch, double distance,
            final long seed) {
        location = stereoPan(location, distance);
        player.playSound(location, sound, category, (float) volume, (float) pitch, seed);
    }

    public static void playSound(final Player player, Location location, final String sound,
            final SoundCategory category, final double volume,
            final double pitch, double distance) {
        location = stereoPan(location, distance);
        player.playSound(location, sound, category, (float) volume, (float) pitch, 0);
    }

    public static void playSound(final Player player, Location location, final Sound sound,
            final SoundCategory category, final double volume,
            final double pitch, double distance) {
        location = stereoPan(location, distance);
        player.playSound(location, sound, category, (float) volume, (float) pitch, 0);
    }

    public static Location stereoPan(Location location, double distance) {
        int angle = (int) location.getYaw();
        while (angle < 0)
            angle += 360;
        angle = angle % 360;
        return location.clone().add(Math.cos(angle) * distance, 0, Math.sin(angle) * distance);
    }
}
