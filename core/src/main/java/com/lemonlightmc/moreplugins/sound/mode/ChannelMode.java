package com.lemonlightmc.moreplugins.sound.mode;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import com.lemonlightmc.moreplugins.sound.Note;
import com.lemonlightmc.moreplugins.utils.MathUtils;

public abstract class ChannelMode {

    public abstract void play(Player player, Location location, Note note, double volume, double pitch);

    public abstract void play(Player player, Location location, Note note, double volume);

    public static void playSound(final Player player, Location location, final String sound,
            final SoundCategory category, final double volume,
            final double pitch, double distance,
            final long seed) {
        location = MathUtils.stereoPan(location, distance);
        player.playSound(location, sound, category, (float) volume, (float) pitch, seed);
    }

    public static void playSound(final Player player, Location location, final Sound sound,
            final SoundCategory category, final double volume,
            final double pitch, double distance,
            final long seed) {
        location = MathUtils.stereoPan(location, distance);
        player.playSound(location, sound, category, (float) volume, (float) pitch, seed);
    }

    public static void playSound(final Player player, Location location, final String sound,
            final SoundCategory category, final double volume,
            final double pitch, double distance) {
        location = MathUtils.stereoPan(location, distance);
        player.playSound(location, sound, category, (float) volume, (float) pitch, 0);
    }

    public static void playSound(final Player player, Location location, final Sound sound,
            final SoundCategory category, final double volume,
            final double pitch, double distance) {
        location = MathUtils.stereoPan(location, distance);
        player.playSound(location, sound, category, (float) volume, (float) pitch, 0);
    }
}
