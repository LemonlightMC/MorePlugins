package com.lemonlightmc.moreplugins.events;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;

import com.lemonlightmc.moreplugins.base.MorePlugins;

public class EventsAPI {

  public static <T extends Event> EventBuilder<T> listen(final Class<T> eventClass) {
    return new EventBuilder<>(eventClass, EventPriority.NORMAL);
  }

  public static <T extends Event> EventBuilder<T> listen(final Class<T> eventClass,
      final EventPriority priority) {
    return new EventBuilder<>(eventClass, priority);
  }

  public static <T extends Event> EventBuilder<T> builder(final Class<T> eventClass) {
    return new EventBuilder<>(eventClass, EventPriority.NORMAL);
  }

  public static <T extends Event> EventBuilder<T> builder(final Class<T> eventClass,
      final EventPriority priority) {
    return new EventBuilder<>(eventClass, priority);
  }

  public static <T extends Event> T call(final T event) {
    MorePlugins.getInstancePluginManager().callEvent(event);
    return event;
  }

  public static <T extends Event> T callAsync(final T event) {
    MorePlugins.getInstanceScheduler().runAsync(() -> MorePlugins.getInstancePluginManager().callEvent(event));
    return event;

  }

  public static <T extends Event> T callSync(final T event) {
    MorePlugins.getInstanceScheduler().run(() -> MorePlugins.getInstancePluginManager().callEvent(event));
    return event;
  }

  public static void unregisterGlobalAll() {
    HandlerList.unregisterAll();
  }

  public static void unregister(final Listener listener) {
    if (listener != null) {
      HandlerList.unregisterAll(listener);
    }
  }

  public static void unregister(final BaseListener listener) {
    if (listener == null || listener.isRegistered) {
      return;
    }
    listener.onUnregister();
    listener.isRegistered = false;
    HandlerList.unregisterAll(listener);
  }

  public static void register(final Listener listener) {
    if (listener != null) {
      MorePlugins.getInstancePluginManager().registerEvents(listener, MorePlugins.instance);
    }
  }

  public static void register(final BaseListener listener) {
    if (listener == null || listener.isRegistered) {
      return;
    }
    MorePlugins.getInstancePluginManager().registerEvents(listener, MorePlugins.instance);
    listener.isRegistered = true;
    listener.onRegister();
  }

  public static void register(final Class<? extends Event> event, final Listener listener, final EventPriority priority,
      final EventExecutor executor) {
    if (listener == null || event == null || executor == null) {
      return;
    }
    MorePlugins.getInstancePluginManager().registerEvent(event, listener,
        priority == null ? EventPriority.NORMAL : priority, executor, MorePlugins.instance);
  }

  public static void register(final Class<? extends Event> event, final Listener listener, final EventPriority priority,
      final EventExecutor executor, final boolean ignoreCancelled) {
    if (listener == null || event == null || executor == null) {
      return;
    }
    MorePlugins.getInstancePluginManager().registerEvent(event, listener,
        priority == null ? EventPriority.NORMAL : priority, executor, MorePlugins.instance,
        ignoreCancelled);
  }
}
