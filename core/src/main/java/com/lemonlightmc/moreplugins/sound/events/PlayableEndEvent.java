package com.lemonlightmc.moreplugins.sound.events;

import org.bukkit.event.HandlerList;

import com.lemonlightmc.moreplugins.base.BaseEvent;
import com.lemonlightmc.moreplugins.sound.player.SoundPlayer;

public class PlayableEndEvent extends BaseEvent {

  private static final HandlerList handlers = new HandlerList();
  private final SoundPlayer songPlayer;

  public PlayableEndEvent(final SoundPlayer songPlayer) {
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