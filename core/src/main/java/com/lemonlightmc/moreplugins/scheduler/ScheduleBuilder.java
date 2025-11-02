package com.lemonlightmc.moreplugins.scheduler;

import java.util.concurrent.TimeUnit;

import com.lemonlightmc.moreplugins.base.MorePlugins;
import com.lemonlightmc.moreplugins.scheduler.Scheduler.ThreadContext;

public class ScheduleBuilder {
  private ThreadContext context = ThreadContext.SYNC;
  private long delay = 0;
  private long interval = 0;

  public ScheduleBuilder() {
  }

  public static ScheduleBuilder create() {
    return new ScheduleBuilder();
  }

  public ScheduleBuilder async() {
    this.context = ThreadContext.ASYNC;
    return this;
  }

  public ScheduleBuilder sync() {
    this.context = ThreadContext.SYNC;
    return this;
  }

  public ScheduleBuilder delay(long delay, TimeUnit unit) {
    this.delay = delay;
    return this;
  }

  public ScheduleBuilder delayTicks(long ticks) {
    this.delay = ticks;
    return this;
  }

  public ScheduleBuilder every(long interval, TimeUnit unit) {
    this.interval = interval;
    return this;
  }

  public ScheduleBuilder everyTicks(long ticks) {
    this.interval = ticks;
    return this;
  }

  public ScheduledTask run(Runnable runnable) {
    if (this.context == ThreadContext.ASYNC) {
      if (this.interval > 0) {
        return MorePlugins.instance.getScheduler().runEveryAsync(runnable, this.interval, this.delay);
      } else if (this.delay > 0) {
        return MorePlugins.instance.getScheduler().runEveryAsync(runnable, this.delay);
      } else {
        return MorePlugins.instance.getScheduler().runAsync(runnable);
      }
    }
    if (this.interval > 0) {
      return MorePlugins.instance.getScheduler().runEvery(runnable, this.interval, this.delay);
    } else if (this.delay > 0) {
      return MorePlugins.instance.getScheduler().runLater(runnable, this.delay);
    } else {
      return MorePlugins.instance.getScheduler().run(runnable);
    }
  }

  /*
   * public void run(Consumer<BukkitTask> consumer) {
   * if (this.context == ThreadContext.ASYNC) {
   * if (this.interval > 0) {
   * MorePlugins.instance.getScheduler().runEvery(consumer, this.interval,
   * this.delay);
   * } else if (this.delay > 0) {
   * MorePlugins.instance.getScheduler().runLater(consumer, this.delay);
   * } else {
   * MorePlugins.instance.getScheduler().run(consumer);
   * }
   * }
   * if (this.interval > 0) {
   * MorePlugins.instance.getScheduler().runEvery(consumer, this.interval,
   * this.delay);
   * } else if (this.delay > 0) {
   * MorePlugins.instance.getScheduler().runLater(consumer, this.delay);
   * } else {
   * MorePlugins.instance.getScheduler().run(consumer);
   * }
   * }
   * 
   */
}
