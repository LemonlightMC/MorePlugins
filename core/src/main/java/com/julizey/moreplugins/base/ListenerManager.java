package com.julizey.moreplugins.base;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

public class ListenerManager {
  private static HashMap<String, BaseListener> listeners = new HashMap<>();

  public static void register(final BaseListener listener) {
    listener.onRegister();
    listener.isRegistered = true;
    listeners.put(listener.getName(), listener);
    Bukkit.getServer().getPluginManager().registerEvents(listener, MorePlugins.instance);
  }

  public static void unregister(final BaseListener listener) {
    listener.onUnRegister();
    listener.isRegistered = false;
    listeners.remove(listener.getName(), listener);
    HandlerList.unregisterAll(listener);
  }

  public static void unregisterAll() {
    for (final BaseListener listener : listeners.values()) {
      unregister(listener);
    }
  }

  public static void unregisterGlobalAll() {
    HandlerList.unregisterAll();
  }

  public static Collection<BaseListener> getListeners() {
    return listeners.values();
  }

  public static Set<String> getListenerNames() {
    return listeners.keySet();
  }
}
