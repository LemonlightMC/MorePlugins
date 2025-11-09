package com.lemonlightmc.moreplugins.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.lemonlightmc.moreplugins.base.PluginBase;
import com.lemonlightmc.moreplugins.wrapper.Actions.*;

public class ActionManager<T> {
  private final ArrayList<Action<T>> actions = new ArrayList<>();

  public ActionManager() {

  }

  public void execute(final T player) {
    PluginBase.getInstance().getScheduler().runAsync(() -> {
      for (final Action<T> action : actions) {
        action.execute(player);
      }
    });
  }

  public void addAction(final Action<T> action) {
    actions.add(action);
  }

  public void hasAction(final Action<T> action) {
    actions.contains(action);
  }

  public void removeAction(final Action<T> action) {
    actions.remove(action);
  }

  public void clearActions() {
    actions.clear();
  }

  public List<Action<T>> getActions() {
    return actions;
  }

  public boolean isEmpty() {
    return actions.isEmpty();
  }

  public int size() {
    return actions.size();
  }
}
