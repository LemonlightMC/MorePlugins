package com.lemonlightmc.moreplugins.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.HashMap;
import java.util.UUID;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;

public class UUIDUtils {

  private static final String API_URL = "https://playerdb.co/api/player/minecraft/%s";
  private static HashMap<String, String> cache = new HashMap<>();

  public static boolean isUUID(final String thing) {
    if (thing == null || thing.length() == 0) {
      return false;
    }
    try {
      UUID.fromString(thing);
      return true;
    } catch (final IllegalArgumentException e) {
      return false;
    }
  }

  public static boolean isUUID(final UUID thing) {
    return thing != null && thing instanceof UUID;
  }

  public static UUID parseUUID(final String uuid) {
    if (uuid == null || uuid.length() == 0) {
      return null;
    }
    try {
      return UUID.fromString(uuid);
    } catch (final IllegalArgumentException e) {
      return null;
    }
  }

  public static UUID parseUUID(final UUID uuid) {
    if (uuid == null) {
      return null;
    }
    try {
      return UUID.fromString(uuid.toString());
    } catch (final IllegalArgumentException e) {
      return null;
    }
  }

  public static UUID toUUID(final UUID uuid) {
    return parseUUID(uuid);
  }

  public static UUID toUUID(final String name) {
    if (name == null || name.length() == 0) {
      return null;
    }
    final UUID uuid = parseUUID(name);
    if (uuid != null) {
      return uuid;
    }
    return fetchUUID(name);
  }

  public static String toName(final String name) {
    if (name == null || name.length() == 0) {
      return null;
    }
    if (isUUID(name)) {
      return fetchName(name);
    }
    return name;
  }

  public static String toName(final UUID uuid) {
    if (uuid == null) {
      return null;
    }
    return fetchName(uuid);
  }

  public static String fetchName(String uuid) {
    if (uuid == null || uuid.length() == 0) {
      return null;
    }
    uuid = uuid.toLowerCase(); // Had some issues with upper-case letters in the username
    return fetch(uuid, "username");
  }

  public static String fetchName(final UUID uuid) {
    if (uuid == null) {
      return null;
    }
    return fetchName(uuid.toString());
  }

  public static UUID fetchUUID(final String name) {
    if (name == null || name.length() == 0) {
      return null;
    }
    return UUID.fromString(fetch(name.toLowerCase(), "id"));
  }

  private static String fetch(final String value, final String key) {
    String cachedValue = cache.get(key + value);
    if (cachedValue != null) {
      return cachedValue;
    }
    try {
      final HttpURLConnection connection = (HttpURLConnection) (new URI(API_URL + value)).toURL().openConnection();

      connection.setUseCaches(false);
      connection.setDefaultUseCaches(false);
      connection.addRequestProperty("User-Agent", "Mozilla/5.0");
      connection.addRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate");
      connection.addRequestProperty("Pragma", "no-cache");
      connection.setReadTimeout(5000);

      // These connection parameters need to be set or the API won't accept the
      // connection.

      try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
        final StringBuilder response = new StringBuilder();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
          response.append(line);
        }

        final JsonElement parsed = JsonParser.parseString(response.toString());

        if (parsed == null || !parsed.isJsonObject()) {
          return null;
        }

        final JsonObject data = parsed.getAsJsonObject();

        String result = data.get("data")
            .getAsJsonObject()
            .get("player")
            .getAsJsonObject()
            .get(key) // Grab the UUID.
            .getAsString();
        cache.put(key + value, result);
        return result;
      }
    } catch (final Exception ignored) {
      // Ignoring exception since this is usually caused by non-existent usernames.
    }

    return null;
  }

}