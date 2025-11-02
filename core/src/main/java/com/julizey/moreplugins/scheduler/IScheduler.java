package com.julizey.moreplugins.scheduler;

import java.util.List;
import java.util.concurrent.Callable;

import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scheduler.BukkitWorker;

public interface IScheduler {

    public ScheduleBuilder builder();

    public IScheduler clone();

    public List<BukkitWorker> getWorkers();

    public List<BukkitTask> getPendingTasks();

    public void cancelTask(List<Integer> taskIds);

    public void cancelTask(int[] taskIds);

    public void cancelTask(int taskId);

    public void cancelTasks();

    public boolean isRunning(int taskId);

    public boolean isQueued(int taskId);

    public ScheduledTask run(Runnable task);

    public ScheduledTask run(Runnable[] tasks);

    public ScheduledTask runAsync(Runnable task);

    public ScheduledTask runAsync(Runnable[] tasks);

    public ScheduledTask runLater(Runnable task, long delay);

    public ScheduledTask runLater(Runnable[] tasks, long delay);

    public ScheduledTask runLaterAsync(Runnable task, long delay);

    public ScheduledTask runLaterAsync(Runnable[] tasks, long delay);

    public ScheduledTask runEvery(Runnable task, long interval);

    public ScheduledTask runEvery(Runnable task, long interval, long delay);

    public ScheduledTask runEvery(Runnable[] tasks, long interval);

    public ScheduledTask runEvery(Runnable[] tasks, long delay, long interval);

    public ScheduledTask runEveryAsync(Runnable task, long interval);

    public ScheduledTask runEveryAsync(Runnable task, long interval, long delay);

    public ScheduledTask runEveryAsync(Runnable[] tasks, long interval);

    public ScheduledTask runEveryAsync(Runnable[] tasks, long interval, long delay);

    public ScheduledTask repeat(Runnable task, int repetitions);

    public ScheduledTask repeat(Runnable task, int repetitions, long interval);

    public ScheduledTask repeat(Runnable task, int repetitions, Runnable onComplete);

    public ScheduledTask repeat(Runnable task, int repetitions, long interval,
            Runnable onComplete);

    public ScheduledTask repeatAsync(Runnable task, int repetitions);

    public ScheduledTask repeatAsync(Runnable task, int repetitions, long interval);

    public ScheduledTask repeatAsync(Runnable task, int repetitions, Runnable onComplete);

    public ScheduledTask repeatAsync(Runnable task, int repetitions, long interval,
            Runnable onComplete);

    public ScheduledTask repeatWhile(Runnable task, Callable<Boolean> predicate, long interval);

    public ScheduledTask repeatWhile(Runnable task, Callable<Boolean> predicate, long interval,
            long delay);

    public ScheduledTask repeatWhile(Runnable task, Callable<Boolean> predicate, long interval,
            Runnable onComplete);

    public ScheduledTask repeatWhile(Runnable task, Callable<Boolean> predicate, long interval,
            long delay,
            Runnable onComplete);

    public ScheduledTask repeatWhileAsync(Runnable task, Callable<Boolean> predicate,
            long interval);

    public ScheduledTask repeatWhileAsync(Runnable task, Callable<Boolean> predicate,
            long interval,
            Runnable onComplete);

    public ScheduledTask repeatWhileAsync(Runnable task, Callable<Boolean> predicate,
            long interval, long delay);

    public ScheduledTask repeatWhileAsync(Runnable task, Callable<Boolean> predicate, long interval,
            long delay,
            Runnable onComplete);

}