package com.julizey.moreplugins.base;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BaseEvent extends Event implements Cancellable {

  private static final HandlerList HANDLERS = new HandlerList();
  private boolean isCancelled;

  public static HandlerList getHandlerList() {
    return HANDLERS;
  }

  public BaseEvent() {
    super(false);
    this.isCancelled = false;
  }

  public BaseEvent(boolean isAsync) {
    super(isAsync);
    this.isCancelled = false;
  }

  @Override
  public boolean isCancelled() {
    return this.isCancelled;
  }

  @Override
  public void setCancelled(boolean isCancelled) {
    this.isCancelled = isCancelled;
  }

  @Override
  public HandlerList getHandlers() {
    return HANDLERS;
  }
}
