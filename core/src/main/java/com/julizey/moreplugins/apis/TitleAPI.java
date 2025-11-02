package com.julizey.moreplugins.apis;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.julizey.moreplugins.messages.MessageFormatter;
import com.julizey.moreplugins.time.Ticks;
import com.julizey.moreplugins.utils.StringUtils.Replaceable;

public class TitleAPI implements ITitleAPI {

  public static final int DEFAULT_FADEIN = 10;
  public static final int DEFAULT_STAY = 70;
  public static final int DEFAULT_FADEOUT = 20;

  public static void sendTitle(
      final Player p,
      final ITitleBuilder info) {
    if (info == null) {
      return;
    }
    final String title = MessageFormatter.parsePlaceholder(p, info.getTitle());
    final String subtitle = MessageFormatter.parsePlaceholder(p, info.getSubtitle());
    p.sendTitle(title, subtitle, info.getFadeIn(), info.getStay(), info.getFadeOut());
  }

  public static void sendSubTitle(
      final Player p,
      final ITitleBuilder info) {
    if (info == null) {
      return;
    }
    final String subtitle = MessageFormatter.parsePlaceholder(p, info.getSubtitle());
    p.sendTitle("", subtitle, info.getFadeIn(), info.getStay(), info.getFadeOut());
  }

  public static void sendTitle(
      final Player[] players,
      final ITitleBuilder info) {
    if (info == null) {
      return;
    }
    for (final Player p : players) {
      sendSubTitle(p, info);
    }
  }

  public static void sendSubTitle(
      final Player[] players,
      final ITitleBuilder info) {
    if (info == null) {
      return;
    }
    for (final Player p : players) {
      sendSubTitle(p, info);
    }
  }

  public static void sendTitle(
      final List<Player> players,
      final ITitleBuilder info) {
    if (info == null) {
      return;
    }
    for (final Player p : players) {
      sendSubTitle(p, info);
    }
  }

  public static void sendSubTitle(
      final List<Player> players,
      final ITitleBuilder info) {
    if (info == null) {
      return;
    }
    for (final Player p : players) {
      sendSubTitle(p, info);
    }
  }

  public static void sendTitle(
      final Set<Player> players,
      final ITitleBuilder info) {
    if (info == null) {
      return;
    }
    for (final Player p : players) {
      sendSubTitle(p, info);
    }
  }

  public static void sendSubTitle(
      final Set<Player> players,
      final ITitleBuilder info) {
    if (info == null) {
      return;
    }
    for (final Player p : players) {
      sendSubTitle(p, info);
    }
  }

  public static void broadcastTitle(
      final ITitleBuilder info) {
    if (info == null) {
      return;
    }
    for (final Player p : Bukkit.getOnlinePlayers()) {
      sendSubTitle(p, info);
    }
  }

  public static void broadcastSubTitle(
      final ITitleBuilder info) {
    if (info == null) {
      return;
    }
    for (final Player p : Bukkit.getOnlinePlayers()) {
      sendSubTitle(p, info);
    }
  }

  public static void sendTitle(
      final Player p,
      String msg,
      final int fadeIn,
      final int stay,
      final int fadeOut,
      final Replaceable... replaceables) {
    msg = MessageFormatter.parsePlaceholder(p, msg);
    msg = MessageFormatter.format(msg, true, true, replaceables);
    if (msg == null || msg.length() == 0)
      return;
    p.sendTitle(msg, "", fadeIn, stay, fadeOut);
  }

  public static void sendSubtitle(
      final Player p,
      String subMsg,
      final int fadeIn,
      final int stay,
      final int fadeOut,
      final Replaceable... replaceables) {
    subMsg = MessageFormatter.parsePlaceholder(p, subMsg);
    subMsg = MessageFormatter.format(subMsg, true, true, replaceables);
    if (subMsg == null || subMsg.length() == 0)
      return;
    p.sendTitle("", subMsg, fadeIn, stay, fadeOut);
  }

  public static void sendTitle(
      final Player p,
      String msg,
      final Replaceable... replaceables) {
    msg = MessageFormatter.parsePlaceholder(p, msg);
    msg = MessageFormatter.format(msg, true, true, replaceables);
    if (msg == null || msg.length() == 0)
      return;
    p.sendTitle(msg, "", DEFAULT_FADEIN, DEFAULT_STAY, DEFAULT_FADEOUT);
  }

  public static void sendSubtitle(
      final Player p,
      String subMsg,
      final Replaceable... replaceables) {
    subMsg = MessageFormatter.parsePlaceholder(p, subMsg);
    subMsg = MessageFormatter.format(subMsg, true, true, replaceables);
    if (subMsg == null || subMsg.length() == 0)
      return;
    p.sendTitle("", subMsg, DEFAULT_FADEIN, DEFAULT_STAY, DEFAULT_FADEOUT);
  }

  public static ITitleBuilder builder() {
    return new TitleBuilder();
  }

  public static class TitleBuilder implements ITitleBuilder {
    private int fadeIn;
    private int stay;
    private int fadeOut;
    private String title;
    private String subtitle;

    public TitleBuilder() {
      this.title = "";
      this.subtitle = "";
      this.fadeIn = DEFAULT_FADEIN;
      this.stay = DEFAULT_STAY;
      this.fadeOut = DEFAULT_FADEOUT;
    }

    public TitleBuilder(final String title) {
      this(title, "", DEFAULT_FADEIN, DEFAULT_STAY, DEFAULT_FADEOUT);
    }

    public TitleBuilder(final String title, final String subtitle) {
      this(title, subtitle, DEFAULT_FADEIN, DEFAULT_STAY, DEFAULT_FADEOUT);
    }

    public TitleBuilder(final String title, final String subtitle, final int stay) {
      this(title, subtitle, DEFAULT_FADEIN, stay, DEFAULT_FADEOUT);

    }

    public TitleBuilder(final String title, final String subtitle, final int fadeIn, final int stay) {
      this(title, subtitle, fadeIn, stay, 20);
    }

    public TitleBuilder(final String title, final String subtitle, final int fadeIn, final int stay,
        final int fadeOut) {
      this.title = MessageFormatter.format(title, true, false);
      this.subtitle = MessageFormatter.format(subtitle, true, false);
      this.fadeIn = fadeIn;
      this.stay = stay;
      this.fadeOut = fadeOut;
    }

    @Override
    public int getFadeIn() {
      return fadeIn;
    }

    @Override
    public int getStay() {
      return stay;
    }

    @Override
    public int getFadeOut() {
      return fadeOut;
    }

    @Override
    public String getTitle() {
      return title;
    }

    @Override
    public String getSubtitle() {
      return subtitle;
    }

    @Override
    public ITitleBuilder title(final String title) {
      this.title = MessageFormatter.format(title, true, false);
      if (title == null || title.length() == 0) {
        throw new NullPointerException("Title cant be null");
      }
      return this;
    }

    @Override
    public ITitleBuilder subtitle(final String subtitle) {
      this.subtitle = MessageFormatter.format(subtitle, true, false);
      if (title == null || title.length() == 0) {
        throw new NullPointerException("Subtitle cant be null");
      }
      return this;
    }

    @Override
    public ITitleBuilder title(final String title, final Replaceable replaceable) {
      this.title = MessageFormatter.format(title, true, false, replaceable);
      if (title == null || title.length() == 0) {
        throw new NullPointerException("Title cant be null");
      }
      return this;
    }

    @Override
    public ITitleBuilder subtitle(final String subtitle, final Replaceable replaceable) {
      this.subtitle = MessageFormatter.format(subtitle, true, false, replaceable);
      if (title == null || title.length() == 0) {
        throw new NullPointerException("Subtitle cant be null");
      }
      return this;
    }

    @Override
    public ITitleBuilder fadeIn(final int duration) {
      if (duration < 0 || duration > Integer.MAX_VALUE) {
        throw new IllegalArgumentException("Duration be negative or to high");
      }
      this.fadeIn = duration;
      return this;
    }

    @Override
    public ITitleBuilder stay(final int duration) {
      if (duration < 0 || duration > Integer.MAX_VALUE) {
        throw new IllegalArgumentException("Duration be negative or to high");
      }
      this.stay = duration;
      return this;
    }

    @Override
    public ITitleBuilder fadeOut(final int duration) {
      if (duration < 0 || duration > Integer.MAX_VALUE) {
        throw new IllegalArgumentException("Duration be negative or to high");
      }
      this.fadeOut = duration;
      return this;
    }

    @Override
    public ITitleBuilder fadeIn(final Duration duration) {
      if (duration == null) {
        throw new NullPointerException("Duration cant be null");
      }
      this.fadeIn = Ticks.fromDuration(duration);
      return this;
    }

    @Override
    public ITitleBuilder stay(final Duration duration) {
      if (duration == null) {
        throw new NullPointerException("Duration cant be null");
      }
      this.stay = Ticks.fromDuration(duration);
      return this;
    }

    @Override
    public ITitleBuilder fadeOut(final Duration duration) {
      if (duration == null) {
        throw new NullPointerException("Duration cant be null");
      }
      this.fadeOut = Ticks.fromDuration(duration);
      return this;
    }

    @Override
    public ITitleBuilder send(final Player player) {
      sendTitle(player, this);
      return this;
    }

    @Override
    public ITitleBuilder send(final Player[] players) {
      sendTitle(players, this);
      return this;
    }

    @Override

    public ITitleBuilder send(final List<Player> players) {
      sendTitle(players, this);
      return this;
    }

    @Override
    public ITitleBuilder send(final Set<Player> players) {
      sendTitle(players, this);
      return this;
    }

    @Override
    public ITitleBuilder broadcast() {
      broadcastTitle(this);
      return this;
    }

    @Override
    public int hashCode() {
      int result = 31 * (31 + fadeIn) + stay;
      result = 31 * result + fadeOut;
      result = 31 * result + ((title == null) ? 0 : title.hashCode());
      return 31 * result + ((subtitle == null) ? 0 : subtitle.hashCode());
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null || getClass() != obj.getClass()) {
        return false;
      }
      final ITitleBuilder other = (ITitleBuilder) obj;
      if (title == null && other.getTitle() != null || subtitle == null && other.getSubtitle() != null) {
        return false;
      }
      return title.equals(other.getTitle()) && subtitle.equals(other.getSubtitle()) && fadeIn == other.getFadeIn()
          && stay == other.getStay() && fadeOut == other.getFadeOut();
    }

    @Override
    public String toString() {
      return "TitleBuilder [fadeIn=" + fadeIn + ", stay=" + stay + ", fadeOut=" + fadeOut + ", title=" + title
          + ", subtitle=" + subtitle + "]";
    }
  }
}
