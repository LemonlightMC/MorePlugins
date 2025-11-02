package com.julizey.moreplugins.scheduler;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import com.julizey.moreplugins.exceptions.SchedulerException;
import com.julizey.moreplugins.scheduler.Scheduler.ThreadContext;

public class ScheduledTask implements IScheduled<BukkitTask>, Runnable {
  public static final int TASK_NOT_SCHEDULED = -1;
  public static final int TASK_SCHEDULED_SOON = -2;

  private final IScheduled<BukkitTask> backing;
  private final AtomicInteger counter = new AtomicInteger(0);
  private final AtomicBoolean cancelled = new AtomicBoolean(false);
  private final int taskId;
  private final ThreadContext ctx;
  private final long interval;
  private final long delay;

  @SuppressWarnings("unchecked")
  public ScheduledTask(final BukkitTask backing, final int taskId, final ThreadContext ctx,
      final long interval,
      final long delay) {
    this((IScheduled<BukkitTask>) backing, taskId, ctx, interval, delay);
  }

  public ScheduledTask(final IScheduled<BukkitTask> backing, final int taskId, final ThreadContext ctx,
      final long interval,
      final long delay) {
    this.backing = backing;
    this.taskId = taskId;
    this.ctx = ctx;
    this.interval = interval;
    this.delay = delay;
  }

  @Override
  public void run() {
    try {
      this.counter.incrementAndGet();
    } catch (final Throwable e) {
      throw new SchedulerException(e);
    }
  }

  @Override
  public boolean stop() {
    if (!isCancelled()) {
      cancel();
      return true;
    }
    return false;
  }

  @Override
  public void cancel() {
    if (taskId != -1) {
      Bukkit.getScheduler().cancelTask(taskId);
    }
  }

  @Override
  public boolean isCancelled() {
    return cancelled == null ? false : this.cancelled.get();
  }

  @Override
  public int getTaskId() {
    return taskId;
  }

  @Override
  public IScheduled<BukkitTask> getBacking() {
    return this.backing;
  }

  @Override
  public boolean isClosed() {
    return cancelled == null ? false : this.cancelled.get();
  }

  @Override
  public boolean isRunning() {
    return taskId != -1 && Bukkit.getScheduler().isCurrentlyRunning(taskId);
  }

  @Override
  public boolean isQueued() {
    return taskId != -1 && Bukkit.getScheduler().isQueued(taskId);
  }

  @Override
  public int getTimesRan() {
    return counter == null ? 0 : this.counter.get();
  }

  @Override
  public Plugin getOwner() {
    return backing.getOwner();
  }

  @Override
  public ThreadContext getThreadContext() {
    return ctx;
  }

  @Override
  public boolean isSync() {
    return ctx == ThreadContext.SYNC;
  }

  @Override
  public boolean isAsync() {
    return ctx == ThreadContext.ASYNC;
  }

  @Override
  public boolean isRepeating() {
    return interval > 0;
  }

  @Override
  public boolean isDelayed() {
    return delay > 0;
  }

  @Override
  public long getDelay() {
    return delay;
  }

  @Override
  public long getInterval() {
    return interval;

  }
}