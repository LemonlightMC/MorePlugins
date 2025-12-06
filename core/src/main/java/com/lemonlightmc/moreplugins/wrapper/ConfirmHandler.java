package com.lemonlightmc.moreplugins.wrapper;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.lemonlightmc.moreplugins.base.MorePlugins;
import com.lemonlightmc.moreplugins.interfaces.Builder;
import com.lemonlightmc.moreplugins.scheduler.ScheduledTask;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConfirmHandler {
  static Map<CommandSender, ConfirmRequest> requests = new ConcurrentHashMap<>();

  public static ConfirmableBuilder builder(CommandSender sender, String confirmableString) {
    return new ConfirmableBuilder(sender, confirmableString);
  }

  public static String getString(Player player) {
    return requests.get(player).message;
  }

  public static boolean senderAffected(Player player) {
    return requests.containsKey(player);
  }

  public static void onSuccess(Player player) {
    ConfirmRequest confirmRequest = requests.get(player);
    confirmRequest.stopTimer();
    MorePlugins.instance.getScheduler().run(confirmRequest.onSuccess);
    requests.remove(player);
  }

  public static void onExpired(Player player) {
    ConfirmRequest confirmRequest = requests.get(player);
    confirmRequest.stopTimer();
    MorePlugins.instance.getScheduler().run(confirmRequest.onExpired);
    requests.remove(player);
  }

  public static class ConfirmableBuilder implements Builder<ConfirmRequest> {
    ConfirmRequest request;

    public ConfirmableBuilder(CommandSender sender, String confirmableString) {
      request = new ConfirmRequest(sender, confirmableString);
    }

    public ConfirmableBuilder success(Runnable success) {
      request.onSuccess = success;
      return this;
    }

    public ConfirmableBuilder expired(Runnable expired) {
      request.onExpired = expired;
      return this;
    }

    public ConfirmableBuilder time(long ticks) {
      request.ticksToExpire = ticks;
      return this;
    }

    public ConfirmableBuilder message(String message) {
      request.message = message;
      return this;
    }

    public ConfirmRequest build() {
      requests.put(request.sender, request);
      request.startTimer();
      return request;
    }
  }

  public static class ConfirmRequest {
    CommandSender sender;
    String message;
    long ticksToExpire;
    ScheduledTask expiredTask = null;
    Runnable onSuccess = null;
    Runnable onExpired = null;

    public ConfirmRequest(CommandSender sender, String message) {
      this.sender = sender;
      this.message = message;
    }

    public void startTimer() {
      expiredTask = MorePlugins.instance.getScheduler().runLater(() -> {
        if (onExpired != null)
          onExpired.run();
        ConfirmHandler.requests.remove(sender, this);
      }, ticksToExpire);
    }

    public void stopTimer() {
      expiredTask.stop();
    }
  }
}