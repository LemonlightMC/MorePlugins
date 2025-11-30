package com.lemonlightmc.moreplugins.math;

public class Rotation {

  private float yaw;
  private float pitch;

  public Rotation(final float yaw, final float pitch) {
    this.yaw = yaw;
    this.pitch = pitch;
  }

  public Rotation(final float yaw) {
    this.yaw = yaw;
    this.pitch = 0;
  }

  public Rotation() {
    this.yaw = 0;
    this.pitch = 0;
  }

  public float getYaw() {
    return this.yaw;
  }

  public void setYaw(final float yaw) {
    this.yaw = yaw;
  }

  public float getPitch() {
    return this.pitch;
  }

  public void setPitch(final float pitch) {
    this.pitch = pitch;
  }

  // between +/-180
  public float getNormalizedYaw() {
    return normalizeYaw(yaw);
  }

  // between +/-90
  public float getNormalizedPitch() {
    return normalizePitch(pitch);
  }

  @Override
  public int hashCode() {
    return 31 * (31 + Float.floatToIntBits(yaw)) + Float.floatToIntBits(pitch);
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final Rotation other = (Rotation) obj;
    return Float.floatToIntBits(yaw) == Float.floatToIntBits(other.yaw)
        && Float.floatToIntBits(pitch) == Float.floatToIntBits(other.pitch);
  }

  @Override
  public String toString() {
    return "Rotation [yaw=" + yaw + ", pitch=" + pitch + "]";
  }

  public static float normalizeYaw(final float yaw) {
    float normalizedYaw = yaw % 360.0F;
    if (normalizedYaw >= 180.0F) {
      normalizedYaw -= 360.0F;
    } else if (normalizedYaw < -180.0F) {
      normalizedYaw += 360.0F;
    }
    return normalizedYaw;
  }

  public static float normalizePitch(final float pitch) {
    return pitch > 90.0F ? 90.0F : Math.max(pitch, -90.0F);
  }
}