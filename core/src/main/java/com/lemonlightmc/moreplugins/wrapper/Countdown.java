package com.lemonlightmc.moreplugins.wrapper;

import java.util.function.Consumer;

import com.lemonlightmc.moreplugins.base.PluginBase;
import com.lemonlightmc.moreplugins.interfaces.Builder;
import com.lemonlightmc.moreplugins.scheduler.ScheduledTask;
import com.lemonlightmc.moreplugins.time.IPolyTimeUnit;

public class Countdown {

  public static final long DEFAULT_TIME = 100l;

  public CountdownBuilder builder() {
    return new CountdownBuilder();
  }

  private final Consumer<CountdownInfo> count;
  private final Consumer<CountdownInfo> done;
  private final Consumer<CountdownInfo> start;
  private final long duration;

  private long startTime = -1L;
  private ScheduledTask task;

  public Countdown(final Consumer<CountdownInfo> done) {
    this(done, (i) -> {
    }, (i) -> {
    }, DEFAULT_TIME);
  }

  public Countdown(final Consumer<CountdownInfo> done, final Consumer<CountdownInfo> count) {
    this(done, count, (i) -> {
    }, DEFAULT_TIME);
  }

  public Countdown(final Consumer<CountdownInfo> done, final Consumer<CountdownInfo> count,
      final Consumer<CountdownInfo> start) {
    this(done, count, start, DEFAULT_TIME);
  }

  public Countdown(final Consumer<CountdownInfo> done, final Consumer<CountdownInfo> count,
      final Consumer<CountdownInfo> start, final long ticks) {
    this.count = count;
    this.done = done;
    this.start = start;
    this.duration = ticks;
  }

  public long getTotalTime() {
    return duration;
  }

  public long getStartTime() {
    return startTime;
  }

  public long getEndTime() {
    return startTime + duration;
  }

  public long getTimeLeft() {
    return startTime + duration - System.currentTimeMillis();
  }

  public boolean hasStarted() {
    return task != null;
  }

  public boolean hasEnded() {
    return task != null && getTimeLeft() > 0;
  }

  public void start() {
    this.startTime = System.currentTimeMillis();
    start.accept(CountdownInfo.from(this));
    task = PluginBase.getInstanceScheduler().runEveryAsync(() -> {
      final CountdownInfo info = CountdownInfo.from(this);
      count.accept(info);
      if (hasEnded()) {
        done.accept(info);
      }
    }, 20, 0);
  }

  public void stop() {
    task.cancel();
    done.accept(CountdownInfo.from(this));
    task = null;
    startTime = -1l;
  }

  @Override
  public int hashCode() {
    int result = 31 + ((count == null) ? 0 : count.hashCode());
    result = 31 * result + ((done == null) ? 0 : done.hashCode());
    result = 31 * result + ((start == null) ? 0 : start.hashCode());
    result = 31 * result + (int) (duration ^ (duration >>> 32));
    result = 31 * result + (int) (startTime ^ (startTime >>> 32));
    result = 31 * result + ((task == null) ? 0 : task.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final Countdown other = (Countdown) obj;
    if (done == null && other.done != null || count == null && other.count != null
        || start == null && other.start != null || task == null && other.task != null) {
      return false;
    }
    return duration == other.duration && startTime == other.startTime && done.equals(other.done)
        && count.equals(other.count) && start.equals(other.start) && task.equals(other.task);
  }

  @Override
  public String toString() {
    return "Countdown [duration=" + duration + ", startTime=" + startTime + "]";
  }

  public static class CountdownBuilder implements Builder<Countdown> {
    private Consumer<CountdownInfo> count;
    private Consumer<CountdownInfo> done;
    private Consumer<CountdownInfo> start;
    private long time = DEFAULT_TIME;

    public CountdownBuilder() {
    }

    public CountdownBuilder count(final Consumer<CountdownInfo> count) {
      this.count = count;
      return this;
    }

    public CountdownBuilder done(final Consumer<CountdownInfo> done) {
      this.done = done;
      return this;
    }

    public CountdownBuilder start(final Consumer<CountdownInfo> start) {
      this.start = start;
      return this;
    }

    public CountdownBuilder time(final long ticks) {
      time = ticks;
      return this;
    }

    public CountdownBuilder time(final long ticks, final IPolyTimeUnit unit) {
      time = unit.toMillis(ticks);
      return this;
    }

    public Countdown build() {
      return new Countdown(done, count, start, time);
    }
  }

  public static record CountdownInfo(long startTime, long endTime, long duration) {
    public long getTimeLeft() {
      return startTime + duration - System.currentTimeMillis();
    }

    public long getTimePassed() {
      return System.currentTimeMillis() - startTime;
    }

    public int getCounts() {
      return (int) Math.floor(getTimePassed());
    }

    public int getRemainingCounts() {
      return (int) Math.ceil(getTimeLeft());
    }

    public static CountdownInfo from(final Countdown c) {
      return new CountdownInfo(c.startTime, c.getEndTime(), c.duration);
    }
  }
}
