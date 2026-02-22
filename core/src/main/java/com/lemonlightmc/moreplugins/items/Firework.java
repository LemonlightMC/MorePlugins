package com.lemonlightmc.moreplugins.items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.inventory.meta.FireworkMeta;

import com.lemonlightmc.moreplugins.interfaces.Cloneable;

public class Firework implements Cloneable<Firework> {
  private List<FireworkEffect> effects;
  private int power;

  public Firework(final List<FireworkEffect> effects, final int power) {
    this.effects = effects;
    this.power = power;
  }

  public Firework(final List<FireworkEffect> effects) {
    this.effects = effects;
  }

  public Firework(final Firework firework) {
    if (firework == null) {
      return;
    }
    this.effects = firework.effects;
    this.power = firework.power;
  }

  public org.bukkit.entity.Firework toEntity(final Location loc) {
    final org.bukkit.entity.Firework entity = loc.getWorld().createEntity(loc, org.bukkit.entity.Firework.class);
    final FireworkMeta meta = entity.getFireworkMeta();
    for (final FireworkEffect fireworkEffect : effects) {
      meta.addEffects(fireworkEffect.toBukkit());
    }
    meta.setPower(power);
    return entity;
  }

  public org.bukkit.entity.Firework spawn(final Location loc) {
    return loc.getWorld().addEntity(toEntity(loc));
  }

  public List<FireworkEffect> effects() {
    return effects;
  }

  public boolean hasEffects() {
    return effects != null && !effects.isEmpty();
  }

  public Firework clearEffects() {
    if (effects != null) {
      effects.clear();
    }
    return this;
  }

  public Firework removeEffects(final FireworkEffect... effects) {
    if (effects == null || effects.length == 0 || this.effects == null || this.effects.isEmpty()) {
      return this;
    }
    return removeEffects(List.of(effects));
  }

  public Firework removeEffects(final Collection<FireworkEffect> effects) {
    if (effects == null || effects.isEmpty() || this.effects == null || this.effects.isEmpty()) {
      return this;
    }
    for (final FireworkEffect fireworkEffect : effects) {
      if (fireworkEffect != null) {
        this.effects.remove(fireworkEffect);
      }
    }
    return this;
  }

  public Firework effects(final FireworkEffect... effects) {
    if (effects == null || effects.length == 0) {
      return this;
    }
    return effects(List.of(effects));
  }

  public Firework effects(final Collection<FireworkEffect> effects) {
    if (effects == null || effects.isEmpty()) {
      return this;
    }
    if (this.effects == null) {
      this.effects = new ArrayList<>();
    }
    for (final FireworkEffect fireworkEffect : effects) {
      if (fireworkEffect != null) {
        this.effects.add(fireworkEffect);
      }
    }
    return this;
  }

  public int effectCount() {
    return this.effects.size();
  }

  public int power() {
    return this.power;
  }

  public Firework power(final int power) {
    this.power = power;
    return this;
  }

  @Override
  public Firework clone() {
    return new Firework(this);
  }

  @Override
  public int hashCode() {
    final int result = 31 + ((effects == null) ? 0 : effects.hashCode());
    return 31 * result + power;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final Firework other = (Firework) obj;
    if (effects == null && other.effects != null) {
      return false;
    }
    return power == other.power && effects.equals(other.effects);
  }

  @Override
  public String toString() {
    return "Firework [effects=" + effects + ", power=" + power + "]";
  }
}
