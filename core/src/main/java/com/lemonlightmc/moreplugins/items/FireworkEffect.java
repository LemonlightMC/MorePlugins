package com.lemonlightmc.moreplugins.items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.bukkit.Color;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.google.common.collect.ImmutableList;
import com.lemonlightmc.moreplugins.interfaces.Builder;
import com.lemonlightmc.moreplugins.interfaces.Cloneable;

public class FireworkEffect
    implements ConfigurationSerializable, Cloneable<FireworkEffect> {
  public enum FireworkEffectType {
    BALL(org.bukkit.FireworkEffect.Type.BALL),
    BALL_LARGE(org.bukkit.FireworkEffect.Type.BALL_LARGE),
    STAR(org.bukkit.FireworkEffect.Type.STAR),
    BURST(org.bukkit.FireworkEffect.Type.BURST),
    CREEPER(org.bukkit.FireworkEffect.Type.CREEPER);

    org.bukkit.FireworkEffect.Type bukkitType;

    private FireworkEffectType(final org.bukkit.FireworkEffect.Type bukkitType) {
      this.bukkitType = bukkitType;
    }

    public org.bukkit.FireworkEffect.Type toBukkit() {
      return bukkitType;
    }
  }

  private static final String FLICKER = "flicker";
  private static final String TRAIL = "trail";
  private static final String COLORS = "colors";
  private static final String FADE_COLORS = "fade-colors";
  private static final String TYPE = "type";

  private boolean flicker;
  private boolean trail;
  private List<Color> colors;
  private List<Color> fadeColors;
  private FireworkEffectType type;

  public FireworkEffect(final FireworkEffectType type, final boolean flicker, final boolean trail,
      final List<Color> colors,
      final List<Color> fadeColors) {
    if (colors.isEmpty()) {
      throw new IllegalStateException("Cannot make FireworkEffect without any color");
    }
    if (type == null) {
      throw new IllegalArgumentException("Type cant be null");
    }
    this.flicker = flicker;
    this.trail = trail;
    this.colors = colors;
    this.fadeColors = fadeColors;
    this.type = type;
  }

  public FireworkEffect(final FireworkEffectType type, final boolean flicker, final boolean trail,
      final List<Color> colors) {
    this(type, flicker, trail, colors, null);
  }

  public FireworkEffect(final FireworkEffectType type, final List<Color> colors) {
    this(type, false, true, colors, null);
  }

  public FireworkEffect(final FireworkEffect effect) {
    if (effect == null) {
      return;
    }
    this.flicker = effect.flicker;
    this.trail = effect.trail;
    this.colors = effect.colors;
    this.fadeColors = effect.fadeColors;
    this.type = effect.type;
  }

  public static FireworkEffect from(final FireworkEffectType type, final boolean flicker, final boolean trail,
      final List<Color> colors,
      final List<Color> fadeColors) {
    return new FireworkEffect(type, flicker, trail, colors, fadeColors);
  }

  public static FireworkEffect from(final FireworkEffectType type, final boolean flicker, final boolean trail,
      final List<Color> colors) {
    return new FireworkEffect(type, flicker, trail, colors);
  }

  public static FireworkEffect from(final FireworkEffectType type, final List<Color> colors) {
    return new FireworkEffect(type, colors);
  }

  public static FireworkEffectBuilder builder() {
    return new FireworkEffectBuilder();
  }

  public boolean hasFlicker() {
    return flicker;
  }

  public void setFlicker(final boolean flicker) {
    this.flicker = flicker;
  }

  public boolean hasTrail() {
    return trail;
  }

  public void setTrail(final boolean trail) {
    this.trail = trail;
  }

  public boolean hasColors() {
    return colors != null && !colors.isEmpty();
  }

  public List<Color> getColors() {
    return colors;
  }

  public void setColors(final Color... colors) {
    if (colors == null) {
      return;
    }
    this.colors = List.of(colors);
  }

  public void setColors(final Collection<Color> colors) {
    if (colors == null) {
      return;
    }
    this.colors = List.copyOf(colors);
  }

  public void addColors(final Color... colors) {
    if (colors == null || colors.length == 0) {
      return;
    }
    if (this.colors == null) {
      this.colors = new ArrayList<>();
    }
    for (final Color color : colors) {
      if (color != null) {
        this.colors.add(color);
      }
    }
  }

  public void addColors(final Collection<Color> colors) {
    if (colors == null || colors.isEmpty()) {
      return;
    }
    if (this.colors == null) {
      this.colors = new ArrayList<>();
    }
    for (final Color color : colors) {
      if (color != null) {
        this.colors.add(color);
      }
    }
  }

  public List<Color> getFadeColors() {
    return fadeColors;
  }

  public boolean hasFadeColors() {
    return fadeColors != null && !fadeColors.isEmpty();
  }

  public void setFadeColors(final Color... colors) {
    if (colors == null || colors.length == 0) {
      return;
    }
    this.fadeColors = List.of(colors);
  }

  public void setFadeColors(final Collection<Color> fadeColors) {
    if (colors == null || colors.isEmpty()) {
      return;
    }
    this.fadeColors = List.copyOf(fadeColors);
  }

  public void addFadeColors(final Color... colors) {
    if (colors == null || colors.length == 0) {
      return;
    }
    if (this.fadeColors == null) {
      this.fadeColors = new ArrayList<>();
    }
    for (final Color color : colors) {
      if (color != null) {
        this.fadeColors.add(color);
      }
    }
  }

  public void addFadeColors(final Collection<Color> colors) {
    if (colors == null || colors.isEmpty()) {
      return;
    }
    if (this.fadeColors == null) {
      this.fadeColors = new ArrayList<>();
    }
    for (final Color color : colors) {
      if (color != null) {
        this.fadeColors.add(color);
      }
    }
  }

  public FireworkEffectType getType() {
    return type;
  }

  public void setType(final FireworkEffectType type) {
    if (type == null) {
      throw new IllegalArgumentException("Type cant be null");
    }
    this.type = type;
  }

  public static ConfigurationSerializable deserialize(final Map<String, Object> map) {

    return builder()
        .type(FireworkEffectType.valueOf((String) map.get(TYPE)))
        .flicker((Boolean) map.get(FLICKER))
        .trail((Boolean) map.get(TRAIL))
        .colors((Iterable<?>) map.get(COLORS))
        .fade((Iterable<?>) map.get(FADE_COLORS))
        .build();
  }

  @Override
  public Map<String, Object> serialize() {
    return Map.<String, Object>of(TYPE, type.name(),
        FLICKER, flicker,
        TRAIL, trail,
        COLORS, colors,
        FADE_COLORS, fadeColors);
  }

  @Override
  public FireworkEffect clone() {
    return new FireworkEffect(this);
  }

  @SuppressWarnings("null")
  public org.bukkit.FireworkEffect toBukkit() {
    try {
      return org.bukkit.FireworkEffect.class.getConstructor().newInstance(flicker, trail,
          ImmutableList.copyOf(colors == null ? List.of() : colors),
          ImmutableList.copyOf(fadeColors == null ? List.of() : fadeColors),
          type.toBukkit());
    } catch (final Exception e) {
      return null;
    }
  }

  @Override
  public int hashCode() {
    int result = 31 + (flicker ? 1231 : 1237);
    result = 31 * result + (trail ? 1231 : 1237);
    result = 31 * result + ((colors == null) ? 0 : colors.hashCode());
    result = 31 * result + ((fadeColors == null) ? 0 : fadeColors.hashCode());
    return 31 * result + ((type == null) ? 0 : type.hashCode());
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final FireworkEffect other = (FireworkEffect) obj;
    if (colors == null) {
      if (other.colors != null) {
        return false;
      }
    }
    if (fadeColors == null) {
      if (other.fadeColors != null) {
        return false;
      }
    }
    if (type != other.type) {
      return false;
    }
    return this.flicker == other.flicker
        && this.trail == other.trail
        && this.type == other.type
        && this.colors.equals(other.colors)
        && this.fadeColors.equals(other.fadeColors);
  }

  @Override
  public String toString() {
    return "FireworkEffect [flicker=" + flicker + ", trail=" + trail + ", colors=" + colors + ", fadeColors="
        + fadeColors + ", type=" + type + "]";
  }

  public static class FireworkEffectBuilder implements Builder<FireworkEffect>, Cloneable<FireworkEffectBuilder> {
    private boolean flicker = false;
    private boolean trail = false;
    private List<Color> colors;
    private List<Color> fadeColors;
    private FireworkEffectType type = FireworkEffectType.BALL;

    FireworkEffectBuilder() {
    }

    FireworkEffectBuilder(final FireworkEffectBuilder builder) {
      if (builder == null) {
        return;
      }
      this.colors = builder.colors;
      this.fadeColors = builder.fadeColors;
      this.flicker = builder.flicker;
      this.trail = builder.trail;
      this.type = builder.type;
    }

    @Override
    public FireworkEffectBuilder clone() {
      return new FireworkEffectBuilder(this);
    }

    public FireworkEffectBuilder type(final FireworkEffectType type) throws IllegalArgumentException {
      if (type == null) {
        throw new IllegalArgumentException("Type cant be null");
      }
      this.type = type;
      return this;
    }

    public FireworkEffectBuilder flicker() {
      flicker = true;
      return this;
    }

    public FireworkEffectBuilder flicker(final boolean flicker) {
      this.flicker = flicker;
      return this;
    }

    public FireworkEffectBuilder trail() {
      trail = true;
      return this;
    }

    public FireworkEffectBuilder trail(final boolean trail) {
      this.trail = trail;
      return this;
    }

    public FireworkEffectBuilder colors(final Color color) throws IllegalArgumentException {
      if (color != null) {
        if (this.colors == null) {
          this.colors = new ArrayList<>();
        }
        this.colors.add(color);
      }
      return this;
    }

    public FireworkEffectBuilder colors(final Color... colors) throws IllegalArgumentException {
      if (colors == null || colors.length == 0) {
        return this;
      }
      if (this.colors == null) {
        this.colors = new ArrayList<>();
      }
      for (final Color color : colors) {
        if (color != null) {
          this.colors.add(color);
        }
      }
      return this;
    }

    public FireworkEffectBuilder colors(final Iterable<?> colors) throws IllegalArgumentException {
      if (colors == null) {
        return this;
      }
      if (this.colors == null) {
        this.colors = new ArrayList<>();
      }
      for (final Object color : colors) {
        if (color != null && color instanceof final Color fadeColor) {
          this.colors.add(fadeColor);
        }
      }
      return this;
    }

    public FireworkEffectBuilder fade(final Color color) throws IllegalArgumentException {
      if (color == null) {
        return this;
      }
      if (fadeColors == null) {
        fadeColors = new ArrayList<>();
      }
      fadeColors.add(color);
      return this;
    }

    public FireworkEffectBuilder fade(final Color... colors) throws IllegalArgumentException {
      if (colors == null || colors.length == 0) {
        return this;
      }
      if (fadeColors == null) {
        fadeColors = new ArrayList<>();
      }

      for (final Color color : colors) {
        if (color != null) {
          fadeColors.add(color);
        }
      }
      return this;
    }

    public FireworkEffectBuilder fade(final Iterable<?> colors) throws IllegalArgumentException {
      if (colors == null) {
        return this;
      }
      if (fadeColors == null) {
        fadeColors = new ArrayList<>();
      }

      for (final Object color : colors) {
        if (color != null && color instanceof final Color fadeColor) {
          fadeColors.add(fadeColor);
        }
      }
      return this;
    }

    public FireworkEffect build() {
      return new FireworkEffect(type,
          flicker,
          trail,
          colors,
          fadeColors == null ? List.of() : fadeColors);
    }
  }
}
