package com.lemonlightmc.moreplugins.sound;

import com.lemonlightmc.moreplugins.utils.MathUtils;

public class CustomInstrument extends Instrument {
  private final String name;
  private final String fileName;
  private final boolean shouldPressKey;

  public CustomInstrument(final int key, final String name, final String fileName) {
    super(key, true);
    this.name = name;
    this.fileName = fileName;
    this.shouldPressKey = false;
  }

  public CustomInstrument(final InstrumentBuilder builder) {
    super(builder.key, true);
    name = builder.name;
    fileName = builder.fileName;
    shouldPressKey = builder.shouldPressKey;
  }

  public String getName() {
    return name;
  }

  public String getFileName() {
    return fileName;
  }

  public String getSoundFileName() {
    return fileName;
  }

  public org.bukkit.Sound getSound() {
    return null;
  }

  public boolean shouldPressKey() {
    return shouldPressKey;
  }

  public static InstrumentBuilder builder() {
    return new InstrumentBuilder();
  }

  public static InstrumentBuilder builder(final CustomInstrument other) {
    return new InstrumentBuilder(other);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + ((name == null) ? 0 : name.hashCode());
    result = 31 * result + ((fileName == null) ? 0 : fileName.hashCode());
    result = 31 * result + (shouldPressKey ? 1231 : 1237);
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj) || getClass() != obj.getClass())
      return false;
    final CustomInstrument other = (CustomInstrument) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (fileName == null) {
      if (other.fileName != null)
        return false;
    } else if (!fileName.equals(other.fileName))
      return false;
    if (shouldPressKey != other.shouldPressKey)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "CustomInstrument [key=" + key + ", name=" + name + ", isCustom=" + isCustom + ", fileName=" + fileName
        + ", shouldPressKey=" + shouldPressKey + "]";
  }

  public static final class InstrumentBuilder {

    private String name = "";
    private String fileName = "";
    private int key = 45;
    private boolean shouldPressKey = false;

    private InstrumentBuilder() {
    }

    private InstrumentBuilder(final CustomInstrument other) {
      this.name = other.getName();
      this.fileName = other.getFileName();
      this.key = other.getKey();
      this.shouldPressKey = other.shouldPressKey();
    }

    public InstrumentBuilder setName(final String name) {
      if (name == null || name.isEmpty()) {
        throw new IllegalArgumentException("Instrument Name cant be empty");
      }
      this.name = name;
      return this;
    }

    public InstrumentBuilder setFileName(final String fileName) {
      if (fileName == null || name.isEmpty()) {
        throw new IllegalArgumentException("Instrument File Name cant be empty");
      }

      this.fileName = fileName;
      return this;
    }

    public InstrumentBuilder setKey(final int key) {
      this.key = MathUtils.normalizeRangeOrThrow(key, Note.MINIMUM_NOTE, Note.MAXIMUM_NOTE, "Instrument Key");
      return this;
    }

    public InstrumentBuilder setShouldPressKey(final boolean shouldPressKey) {
      this.shouldPressKey = shouldPressKey;
      return this;
    }

    public CustomInstrument build() {
      return new CustomInstrument(this);
    }
  }
}
