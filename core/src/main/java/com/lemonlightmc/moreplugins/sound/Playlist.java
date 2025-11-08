package com.lemonlightmc.moreplugins.sound;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

  private List<Playable> playables;

  public Playlist(final List<Playable> playables) {
    if (check(playables)) {
      this.playables = new ArrayList<>();
    } else {
      this.playables = playables;
    }
  }

  public void add(final List<Playable> playables) {
    if (check(playables)) {
      return;
    }
    this.playables.addAll(playables);
  }

  public void insert(final int index, final List<Playable> playables) {
    if (check(playables)) {
      return;
    }
    if (index > this.playables.size()) {
      throw new IllegalArgumentException("Index is higher than playlist size");
    }
    this.playables.addAll(index, playables);
  }

  public void remove(final List<Playable> playables) {
    if (check(playables)) {
      return;
    }
    final ArrayList<Playable> playablesTemp = new ArrayList<>(this.playables);
    playablesTemp.removeAll(playables);
    if (playablesTemp.size() > 0) {
      this.playables = playablesTemp;
    } else {
      throw new IllegalArgumentException("Cannot remove all playables from playlist");
    }
  }

  public Playable getPlayable(final int playableNumber) {
    return playables.get(playableNumber);
  }

  public int getCount() {
    return playables.size();
  }

  public boolean hasNext(final int playableNumber) {
    return playables.size() > (playableNumber + 1);
  }

  public boolean exist(final int playableNumber) {
    return playables.size() > playableNumber;
  }

  public int getIndex(final Playable playable) {
    return playables.indexOf(playable);
  }

  public boolean contains(final Playable playable) {
    return playables.contains(playable);
  }

  public List<Playable> getSongs() {
    return new ArrayList<>(playables);
  }

  private boolean check(final List<Playable> playables) {
    if (playables == null) {
      throw new IllegalArgumentException("Song List cant be null");
    }
    if (playables.contains(null)) {
      throw new IllegalArgumentException("Cannot add null to playlist");
    }
    return playables.size() == 0;
  }
}