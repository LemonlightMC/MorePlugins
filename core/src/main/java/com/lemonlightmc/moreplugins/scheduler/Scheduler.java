package com.lemonlightmc.moreplugins.scheduler;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scheduler.BukkitWorker;

import com.lemonlightmc.moreplugins.base.MorePlugins;

import java.util.List;
import java.util.concurrent.Callable;

public class Scheduler implements IScheduler {

  public static enum ThreadContext {
    SYNC,
    ASYNC;

    public static ThreadContext forCurrentThread() {
      return Bukkit.getServer().isPrimaryThread() ? SYNC : ASYNC;
    }

    public static ThreadContext forThread(Thread thread) {
      return (Bukkit.getServer().isPrimaryThread() && thread == Thread.currentThread())
          ? SYNC
          : ASYNC;
    }
  }

  @Override
  public ScheduleBuilder builder() {
    return new ScheduleBuilder();
  }

  @Override
  public IScheduler clone() {
    return new Scheduler();
  }

  private ScheduledTask wrap(final BukkitTask task, final ThreadContext ctx, final long interval,
      final long delay) {
    return new ScheduledTask(task, task.getTaskId(), ctx, interval, delay);
  }

  @Override
  public List<BukkitWorker> getWorkers() {
    return Bukkit.getScheduler().getActiveWorkers();
  }

  @Override
  public List<BukkitTask> getPendingTasks() {
    return Bukkit.getScheduler().getPendingTasks();
  }

  @Override
  public void cancelTask(final List<Integer> taskIds) {
    for (final int id : taskIds) {
      Bukkit.getScheduler().cancelTask(id);
    }
  }

  @Override
  public void cancelTask(final int[] taskIds) {
    for (final int id : taskIds) {
      Bukkit.getScheduler().cancelTask(id);
    }
  }

  @Override
  public void cancelTask(final int taskId) {
    Bukkit.getScheduler().cancelTask(taskId);
  }

  @Override
  public void cancelTasks() {
    Bukkit.getScheduler().cancelTasks(MorePlugins.instance);
  }

  @Override
  public boolean isRunning(final int taskId) {
    return Bukkit.getScheduler().isCurrentlyRunning(taskId);
  }

  @Override
  public boolean isQueued(final int taskId) {
    return Bukkit.getScheduler().isQueued(taskId);
  }

  // run

  @Override
  public ScheduledTask run(final Runnable task) {
    return wrap(Bukkit.getScheduler().runTask(MorePlugins.instance, task),
        ThreadContext.SYNC, 0, 0);

  }

  @Override
  public ScheduledTask run(final Runnable[] tasks) {
    return wrap(new BukkitRunnable() {
      @Override
      public void run() {
        for (final Runnable runnable : tasks) {
          runnable.run();
        }
        this.cancel();
      }
    }.runTask(MorePlugins.instance), ThreadContext.SYNC, 0, 0);
  }

  // runAsync

  @Override
  public ScheduledTask runAsync(final Runnable task) {
    return wrap(Bukkit.getScheduler().runTaskAsynchronously(MorePlugins.instance, task),
        ThreadContext.SYNC, 0, 0);
  }

  @Override
  public ScheduledTask runAsync(final Runnable[] tasks) {
    return wrap(new BukkitRunnable() {

      @Override
      public void run() {
        for (final Runnable runnable : tasks) {
          runnable.run();
        }
        this.cancel();
      }
    }.runTaskAsynchronously(MorePlugins.instance), ThreadContext.SYNC, 0, 0);
  }

  // runLater

  @Override
  public ScheduledTask runLater(final Runnable task, long delay) {
    delay = Math.round(Math.min(Math.max(delay, 0), Integer.MAX_VALUE));
    return wrap(Bukkit.getScheduler().runTaskLater(MorePlugins.instance, task, delay),
        ThreadContext.SYNC, 0, delay);

  }

  @Override
  public ScheduledTask runLater(final Runnable[] tasks, long delay) {
    delay = Math.round(Math.min(Math.max(delay, 0), Integer.MAX_VALUE));
    return wrap(new BukkitRunnable() {
      @Override
      public void run() {
        for (final Runnable runnable : tasks) {
          runnable.run();
        }
        this.cancel();
      }
    }.runTaskLater(MorePlugins.instance, delay), ThreadContext.SYNC, 0, delay);
  }

  // runLaterAsync

  @Override
  public ScheduledTask runLaterAsync(final Runnable task, long delay) {
    delay = Math.round(Math.min(Math.max(delay, 0), Integer.MAX_VALUE));
    return wrap(Bukkit.getScheduler().runTaskLaterAsynchronously(MorePlugins.instance, task, delay),
        ThreadContext.SYNC, 0, delay);

  }

  @Override
  public ScheduledTask runLaterAsync(final Runnable[] tasks, long delay) {
    delay = Math.round(Math.min(Math.max(delay, 0), Integer.MAX_VALUE));
    return wrap(new BukkitRunnable() {
      @Override
      public void run() {
        for (final Runnable runnable : tasks) {
          runnable.run();
        }
        this.cancel();
      }
    }.runTaskLaterAsynchronously(MorePlugins.instance, delay), ThreadContext.SYNC, 0, delay);
  }

  // runEvery

  @Override
  public ScheduledTask runEvery(final Runnable task, final long interval) {
    return runEvery(task, interval, 0L);
  }

  @Override
  public ScheduledTask runEvery(final Runnable task, final long interval, long delay) {
    delay = Math.round(Math.min(Math.max(delay, 0), Integer.MAX_VALUE));
    return wrap(Bukkit.getScheduler().runTaskTimer(MorePlugins.instance, task, delay, interval), ThreadContext.SYNC,
        interval, delay);

  }

  @Override
  public ScheduledTask runEvery(final Runnable[] tasks, final long interval) {
    return runEvery(tasks, interval, 0L);
  }

  @Override
  public ScheduledTask runEvery(final Runnable[] tasks, long delay, final long interval) {
    delay = Math.round(Math.min(Math.max(delay, 0), Integer.MAX_VALUE));
    return wrap(new BukkitRunnable() {
      @Override
      public void run() {
        for (final Runnable runnable : tasks) {
          runnable.run();
        }
        this.cancel();
      }
    }.runTaskTimer(MorePlugins.instance, delay, interval), ThreadContext.SYNC, interval, delay);
  }

  // runEveryAsync

  @Override
  public ScheduledTask runEveryAsync(final Runnable task, final long interval) {
    return runEveryAsync(task, interval, 0L);
  }

  @Override
  public ScheduledTask runEveryAsync(final Runnable task, final long interval, long delay) {
    delay = Math.round(Math.min(Math.max(delay, 0), Integer.MAX_VALUE));
    return wrap(Bukkit.getScheduler().runTaskTimerAsynchronously(MorePlugins.instance, task, delay, interval),
        ThreadContext.ASYNC, interval, delay);

  }

  @Override
  public ScheduledTask runEveryAsync(final Runnable[] tasks, final long interval) {
    return runEveryAsync(tasks, interval, 0L);
  }

  @Override
  public ScheduledTask runEveryAsync(final Runnable[] tasks, long interval, long delay) {
    delay = Math.round(Math.min(Math.max(delay, 0), Integer.MAX_VALUE));
    interval = Math.round(Math.min(Math.max(interval, 0), Integer.MAX_VALUE));
    return wrap(new BukkitRunnable() {
      @Override
      public void run() {
        for (final Runnable runnable : tasks) {
          runnable.run();
        }
        this.cancel();
      }
    }.runTaskTimerAsynchronously(MorePlugins.instance, delay, interval), ThreadContext.ASYNC, interval, delay);
  }

  // repeat

  @Override
  public ScheduledTask repeat(final Runnable task, final int repetitions) {
    return repeat(task, repetitions, 1, null);
  }

  @Override
  public ScheduledTask repeat(final Runnable task, final int repetitions, final long interval) {
    return repeat(task, repetitions, interval, null);
  }

  @Override
  public ScheduledTask repeat(final Runnable task, final int repetitions, final Runnable onComplete) {
    return repeat(task, repetitions, 1, onComplete);
  }

  @Override
  public ScheduledTask repeat(final Runnable task, final int repetitions, long interval,
      final Runnable onComplete) {
    final int repetitions0 = Math.round(Math.min(Math.max(repetitions, 0), Integer.MAX_VALUE));
    interval = Math.round(Math.min(Math.max(interval, 0), Integer.MAX_VALUE));
    return wrap(new BukkitRunnable() {
      private int index = 0;

      @Override
      public void run() {
        if (++this.index >= repetitions0) {
          this.cancel();
          if (onComplete == null) {
            return;
          }
          onComplete.run();
          return;
        }
        task.run();
      }
    }.runTaskTimer(MorePlugins.instance, 0L, interval), ThreadContext.SYNC, interval, 0);
  }

  // repeatAsync

  @Override
  public ScheduledTask repeatAsync(final Runnable task, final int repetitions) {
    return repeatAsync(task, repetitions, 1, null);
  }

  @Override
  public ScheduledTask repeatAsync(final Runnable task, final int repetitions, final long interval) {
    return repeatAsync(task, repetitions, interval, null);
  }

  @Override
  public ScheduledTask repeatAsync(final Runnable task, final int repetitions, final Runnable onComplete) {
    return repeatAsync(task, repetitions, 1, onComplete);
  }

  @Override
  public ScheduledTask repeatAsync(final Runnable task, final int repetitions, long interval,
      final Runnable onComplete) {
    final int repetitions0 = Math.round(Math.min(Math.max(repetitions, 0), Integer.MAX_VALUE));
    interval = Math.round(Math.min(Math.max(interval, 0), Integer.MAX_VALUE));
    return wrap(new BukkitRunnable() {
      private int index = 0;

      @Override
      public void run() {
        if (++this.index >= repetitions0) {
          this.cancel();
          if (onComplete == null) {
            return;
          }

          onComplete.run();
          return;
        }

        task.run();
      }
    }.runTaskTimerAsynchronously(MorePlugins.instance, 0L, interval), ThreadContext.ASYNC, interval, 0);
  }

  // repeatWhile

  @Override
  public ScheduledTask repeatWhile(final Runnable task, final Callable<Boolean> predicate, final long interval) {
    return repeatWhileAsync(task, predicate, interval, 0L, null);
  }

  @Override
  public ScheduledTask repeatWhile(final Runnable task, final Callable<Boolean> predicate, final long interval,
      final long delay) {
    return repeatWhileAsync(task, predicate, interval, delay, null);
  }

  @Override
  public ScheduledTask repeatWhile(final Runnable task, final Callable<Boolean> predicate, final long interval,
      final Runnable onComplete) {
    return repeatWhileAsync(task, predicate, interval, 0L, onComplete);
  }

  @Override
  public ScheduledTask repeatWhile(final Runnable task, final Callable<Boolean> predicate, long interval,
      long delay,
      final Runnable onComplete) {
    delay = Math.round(Math.min(Math.max(delay, 0), Integer.MAX_VALUE));
    interval = Math.round(Math.min(Math.max(interval, 0), Integer.MAX_VALUE));
    return wrap(new BukkitRunnable() {
      @Override
      public void run() {
        try {
          if (!predicate.call()) {
            this.cancel();
            if (onComplete == null) {
              return;
            }

            onComplete.run();
            return;
          }

          task.run();
        } catch (final Exception e) {
          e.printStackTrace();
        }
      }
    }.runTaskTimer(MorePlugins.instance, 0L, interval), ThreadContext.SYNC, interval, 0);
  }

  // repeatWhileAsync

  @Override
  public ScheduledTask repeatWhileAsync(final Runnable task, final Callable<Boolean> predicate,
      final long interval) {
    return repeatWhileAsync(task, predicate, interval, 0L, null);
  }

  @Override
  public ScheduledTask repeatWhileAsync(final Runnable task, final Callable<Boolean> predicate,
      final long interval,
      final Runnable onComplete) {
    return repeatWhileAsync(task, predicate, interval, 0L, onComplete);
  }

  @Override
  public ScheduledTask repeatWhileAsync(final Runnable task, final Callable<Boolean> predicate,
      final long interval, final long delay) {
    return repeatWhileAsync(task, predicate, interval, delay, null);

  }

  @Override
  public ScheduledTask repeatWhileAsync(final Runnable task, final Callable<Boolean> predicate, long interval,
      long delay,
      final Runnable onComplete) {
    delay = Math.round(Math.min(Math.max(delay, 0), Integer.MAX_VALUE));
    interval = Math.round(Math.min(Math.max(interval, 0), Integer.MAX_VALUE));
    return wrap(new BukkitRunnable() {
      @Override
      public void run() {
        try {
          if (!predicate.call()) {
            this.cancel();
            if (onComplete == null) {
              return;
            }

            onComplete.run();
            return;
          }

          task.run();
        } catch (final Exception e) {
          e.printStackTrace();
        }
      }
    }.runTaskTimerAsynchronously(MorePlugins.instance, delay, interval), ThreadContext.ASYNC, interval, delay);
  }

}