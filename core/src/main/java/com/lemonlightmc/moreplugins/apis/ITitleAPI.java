package com.lemonlightmc.moreplugins.apis;

import java.time.Duration;
import java.util.List;

import org.bukkit.entity.Player;

import com.lemonlightmc.moreplugins.interfaces.Builder;
import com.lemonlightmc.moreplugins.utils.StringUtils.Replaceable;

/**
 * Public contracts for title creation and sending.
 *
 * <p>
 * This interface groups the nested builder/info interfaces used by
 * {@link com.lemonlightmc.moreplugins.apis.TitleAPI} to construct and send
 * titles/subtitles to players.
 * </p>
 */
public interface ITitleAPI {

  /**
   * Represents a fully-formed title (fade-in, stay, fade-out, title and
   * subtitle) that can be sent to players.
   */
  public static interface ITitleInfo {
    public int getFadeIn();

    public void setFadeIn(final int fadeIn);

    public int getStay();

    public void setStay(final int stay);

    public int getFadeOut();

    public void setFadeOut(final int fadeOut);

    public String getTitle();

    public void setTitle(final String title);

    public String getSubtitle();

    public void setSubtitle(final String subtitle);

    /**
     * Send this title to a single player.
     *
     * @param player recipient player
     */
    public void send(final Player player);

    /**
     * Send this title to a list of players.
     *
     * @param players recipients
     */
    public void send(final List<Player> players);

    /**
     * Send this title to an array of players.
     *
     * @param players recipients
     */
    public void send(final Player[] players);

    /**
     * Broadcast this title to all online players.
     */
    public void broadcast();

    @Override
    public int hashCode();

    @Override
    public boolean equals(final Object obj);

    @Override
    public String toString();
  }

  public static interface ITitleBuilder extends Builder<ITitleInfo> {

    public String title();

    public ITitleBuilder title(String title);

    public ITitleBuilder title(String title, final Replaceable[] replaceable);

    public String subTitle();

    public ITitleBuilder subtitle(String subtitle);

    public ITitleBuilder subtitle(String subtitle, final Replaceable[] replaceable);

    public int fadeIn();

    public ITitleBuilder fadeIn(int duration);

    public ITitleBuilder fadeIn(Duration duration);

    public int stay();

    public ITitleBuilder stay(int duration);

    public ITitleBuilder stay(Duration duration);

    public int fadeOut();

    public ITitleBuilder fadeOut(int duration);

    public ITitleBuilder fadeOut(Duration duration);

    public int hashCode();

    public boolean equals(Object obj);

    public String toString();
  }
}