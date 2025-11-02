package com.lemonlightmc.moreplugins.modular;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;
import com.lemonlightmc.moreplugins.messages.Logger;

public class Hooks {
  private static HashMap<String, Hook> hooks = new HashMap<>(6);

  public static interface IHook {
    public void reload();

    public void unload();

    public void load();
  }

  public static HashMap<String, Hook> getHooks() {
    return hooks;
  }

  public static Hook getHook(final String name) {
    return hooks.get(name);
  }

  @SuppressWarnings("unchecked")
  public static <H extends Hook> void consumeHook(final String name, final Consumer<H> consumer) {
    final Hook hook = hooks.get(name);
    if (hook == null || !hook.isEnabled) {
      return;
    }
    consumer.accept((H) hook);
  }

  @SuppressWarnings("unchecked")
  public static <H extends Hook> boolean predicateHook(final String name, final Predicate<H> consumer) {
    final Hook hook = hooks.get(name);
    if (hook == null || !hook.isEnabled) {
      return false;
    }
    return consumer.test((H) hook);
  }

  public static boolean isLoaded(final String name) {
    return hooks.containsKey(name);
  }

  public static boolean isEnabled(final String name) {
    final Hook hook = hooks.get(name);
    if (hook == null) {
      return false;
    }
    return hook.isEnabled;
  }

  public static void enable(final String name) {
    final Hook hook = hooks.get(name);
    if (hook == null) {
      return;
    }
    hook.enable();
  }

  public static void disable(final String name) {
    final Hook hook = hooks.get(name);
    if (hook == null) {
      return;
    }
    hook.disable();
  }

  public static void load() {
    for (final Hook hook : hooks.values()) {
      hook.load();
      hook.isEnabled = true;
    }
  }

  public static void reload() {
    for (final Hook hook : hooks.values()) {
      hook.reload();
    }
  }

  public static void unload() {
    for (final Hook hook : hooks.values()) {
      hook.isEnabled = false;
      hook.unload();
    }
  }

  public static void clear() {
    hooks.clear();
  }

  public static void createHook(final Class<? extends Hook> hookCls) {
    try {
      final Hook hook = hookCls.getDeclaredConstructor(hookCls).newInstance();
      hooks.put(hook.name, hook);
    } catch (final Exception e) {
      Logger.warn("Failed to create hook: " + hookCls.getName());
    }
  }

  public static abstract class Hook implements IHook {
    protected final String name;
    protected boolean isEnabled;

    public Hook(final String name) {
      this.name = name;
      this.isEnabled = true;
    }

    public String getName() {
      return name;
    }

    public boolean isEnabled() {
      return isEnabled;
    }

    public void enable() {
      this.isEnabled = true;
    }

    public void disable() {
      this.isEnabled = false;
    }

    @Override
    abstract public void reload();

    @Override
    abstract public void unload();

    @Override
    abstract public void load();
  }
}
