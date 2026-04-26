package com.lemonlightmc.zenith.events;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public abstract class BaseEvent extends Event {

  protected boolean isCancelled = false;

  public BaseEvent() {
    super(false);
  }

  public BaseEvent(boolean isAsync) {
    super(isAsync);
  }

  public boolean call() {
    Bukkit.getPluginManager().callEvent(this);
    return isCancelled;
  }
}
