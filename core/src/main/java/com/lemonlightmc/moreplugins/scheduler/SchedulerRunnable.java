package com.lemonlightmc.moreplugins.scheduler;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import com.lemonlightmc.moreplugins.base.MorePlugins;
import com.lemonlightmc.moreplugins.scheduler.Scheduler.ThreadContext;

public abstract class SchedulerRunnable implements Runnable {

  protected ScheduledTask task;

  public synchronized boolean isCancelled() throws IllegalStateException {
    checkScheduled();
    return task.isCancelled();
  }

  public synchronized void cancel() throws IllegalStateException {
    if (task != null || task.getTaskId() != -1) {
      Bukkit.getScheduler().cancelTask(task.getTaskId());
    }
  }

  public synchronized ScheduledTask runTask() throws IllegalArgumentException, IllegalStateException {
    checkNotYetScheduled();
    return setupTask(
        Bukkit.getScheduler().runTask(MorePlugins.instance, this), ThreadContext.SYNC, -1, -1);
  }

  public synchronized ScheduledTask runTaskAsync() throws IllegalArgumentException, IllegalStateException {
    checkNotYetScheduled();
    return setupTask(
        Bukkit.getScheduler().runTaskAsynchronously(MorePlugins.instance, this), ThreadContext.ASYNC, -1, -1);
  }

  public synchronized ScheduledTask runTaskLater(long delay) throws IllegalArgumentException, IllegalStateException {
    checkNotYetScheduled();
    return setupTask(
        Bukkit.getScheduler().runTaskLater(MorePlugins.instance, this, delay), ThreadContext.SYNC, delay, -1);
  }

  public synchronized ScheduledTask runTaskLaterAsync(long delay)
      throws IllegalArgumentException, IllegalStateException {
    checkNotYetScheduled();
    return setupTask(
        Bukkit.getScheduler().runTaskLaterAsynchronously(MorePlugins.instance, this, delay),
        ThreadContext.ASYNC, delay, -1);
  }

  public synchronized ScheduledTask runTaskTimer(long delay, long interval)
      throws IllegalArgumentException, IllegalStateException {
    checkNotYetScheduled();
    return setupTask(
        Bukkit.getScheduler().runTaskTimer(MorePlugins.instance, this, delay, interval),
        ThreadContext.SYNC, delay, interval);
  }

  public synchronized ScheduledTask runTaskTimerAsync(long delay, long interval)
      throws IllegalArgumentException, IllegalStateException {
    checkNotYetScheduled();
    return setupTask(
        Bukkit.getScheduler().runTaskTimerAsynchronously(MorePlugins.instance, this, delay,
            interval),
        ThreadContext.ASYNC, delay, interval);
  }

  public synchronized int getTaskId() throws IllegalStateException {
    checkScheduled();
    return task.getTaskId();
  }

  private void checkScheduled() {
    if (task == null) {
      throw new IllegalStateException("Not scheduled yet");
    }
  }

  private void checkNotYetScheduled() {
    if (task != null) {
      throw new IllegalStateException("Already scheduled as " + task.getTaskId());
    }
  }

  private ScheduledTask setupTask(final BukkitTask task, ThreadContext ctx, long delay, long interval) {
    this.task = new ScheduledTask(task, task.getTaskId(), ctx, interval, delay);
    return this.task;
  }
}