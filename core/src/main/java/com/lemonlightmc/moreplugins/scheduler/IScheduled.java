package com.lemonlightmc.moreplugins.scheduler;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import com.lemonlightmc.moreplugins.scheduler.Scheduler.ThreadContext;

public interface IScheduled<T> extends BukkitTask {
  public int getTaskId();

  public Plugin getOwner();

  public T getBacking();

  public boolean isClosed();

  public boolean stop();

  public boolean isRunning();

  public boolean isQueued();

  public ThreadContext getThreadContext();

  public boolean isSync();

  public boolean isAsync();

  public boolean isRepeating();

  public long getInterval();

  public boolean isDelayed();

  public long getDelay();

  public int getTimesRan();

}