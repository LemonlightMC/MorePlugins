package com.lemonlightmc.moreplugins.apis;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.bukkit.entity.Player;

import com.lemonlightmc.moreplugins.utils.StringUtils.Replaceable;

public interface ITitleAPI {

  public static interface ITitleBuilder {

    public int getFadeIn();

    public int getStay();

    public int getFadeOut();

    public String getTitle();

    public String getSubtitle();

    public ITitleBuilder title(String title);

    public ITitleBuilder title(String title, final Replaceable replaceable);

    public ITitleBuilder subtitle(String subtitle);

    public ITitleBuilder subtitle(String subtitle, final Replaceable replaceable);

    public ITitleBuilder fadeIn(int duration);

    public ITitleBuilder stay(int duration);

    public ITitleBuilder fadeOut(int duration);

    public ITitleBuilder fadeIn(Duration duration);

    public ITitleBuilder stay(Duration duration);

    public ITitleBuilder fadeOut(Duration duration);

    public ITitleBuilder send(final Player player);

    public ITitleBuilder send(final Player[] players);

    public ITitleBuilder send(final List<Player> players);

    public ITitleBuilder send(final Set<Player> players);

    public ITitleBuilder broadcast();

    public int hashCode();

    public boolean equals(Object obj);

    public String toString();
  }
}