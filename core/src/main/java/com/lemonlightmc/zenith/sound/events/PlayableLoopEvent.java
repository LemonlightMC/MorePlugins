package com.lemonlightmc.zenith.sound.events;

import org.bukkit.event.HandlerList;

import com.lemonlightmc.zenith.events.BaseEvent;
import com.lemonlightmc.zenith.sound.player.SoundPlayer;

public class PlayableLoopEvent extends BaseEvent {
  private static final HandlerList handlers = new HandlerList();
  private final SoundPlayer songPlayer;

  public PlayableLoopEvent(final SoundPlayer songPlayer) {
    this.songPlayer = songPlayer;
  }

  public SoundPlayer getSongPlayer() {
    return songPlayer;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }
}
