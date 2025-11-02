package com.julizey.moreplugins.apis;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import com.julizey.moreplugins.apis.ToastAPI.ToastType;
import com.julizey.moreplugins.base.MorePlugins;
import com.julizey.moreplugins.exceptions.OutdatedVersionError;
import com.julizey.moreplugins.messages.MessageFormatter;
import com.julizey.moreplugins.version.MCVersion;

import me.clip.placeholderapi.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import me.clip.placeholderapi.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class AdvancementAPI {
  @SuppressWarnings("deprecation")
  void showToast(Collection<? extends Player> players, String icon, String message, ToastType style, Object modelData,
      String modelDataType, boolean glowing) {
    NamespacedKey advancementKey = createAdvancement(icon, message, style, modelData, modelDataType, glowing);

    for (Player p : players) {
      MorePlugins.instance.getScheduler().runLater(() -> {
        grantAdvancement(p, advancementKey);
        MorePlugins.instance.getScheduler().runLater(() -> revokeAdvancement(p, advancementKey), 10);
      }, 1);
    }

    MorePlugins.instance.getScheduler().runLater(() -> Bukkit.getUnsafe().removeAdvancement(advancementKey), 40);
  }

  @SuppressWarnings("deprecation")
  void showToastToAll(String icon, String message, ToastType style, Object modelData, String modelDataType,
      boolean glowing) {
    Collection<? extends Player> allPlayers = Bukkit.getOnlinePlayers();

    if (allPlayers.isEmpty()) {
      return;
    }

    NamespacedKey advancementKey = createAdvancement(icon, message, style, modelData, modelDataType, glowing);

    for (Player p : allPlayers) {
      MorePlugins.instance.getScheduler().runLater(() -> {
        grantAdvancement(p, advancementKey);
        MorePlugins.instance.getScheduler().runLater(() -> revokeAdvancement(p, advancementKey), 10);
      }, 1);
    }

    MorePlugins.instance.getScheduler().runLater(() -> Bukkit.getUnsafe().removeAdvancement(advancementKey), 40);
  }

  @SuppressWarnings("deprecation")
  private NamespacedKey legacyType(String icon, String message, ToastType style, Object modelData, boolean glowing,
      NamespacedKey advancementKey) {

    int modelDataInt = 0;
    if (modelData instanceof Integer) {
      modelDataInt = (Integer) modelData;
    } else if (modelData instanceof Float) {
      modelDataInt = ((Float) modelData).intValue();
    } else if (modelData instanceof String) {
      try {
        modelDataInt = Integer.parseInt(modelData.toString());
      } catch (NumberFormatException ignored) {
        // TODO: If parsing fails, modelDataInt remains 0
      }
    }

    String json = "{\n" +
        " \"criteria\": {\n" +
        " \"trigger\": {\n" +
        " \"trigger\": \"minecraft:impossible\"\n" +
        " }\n" +
        " },\n" +
        " \"display\": {\n" +
        " \"icon\": {\n" +
        " \"item\": \"minecraft:" + icon + "\",\n" +
        " \"nbt\": \"{CustomModelData:" + modelDataInt +
        (glowing ? ",Enchantments:[{lvl:1,id:\\\"minecraft:protection\\\"}]" : "") + "}\"\n" +
        " },\n" +
        " \"title\": " + message + ",\n" +
        " \"description\": {\n" +
        " \"text\": \"\"\n" +
        " },\n" +
        " \"background\": \"minecraft:textures/gui/advancements/backgrounds/adventure.png\",\n" +
        " \"frame\": \"" + style.toString().toLowerCase() + "\",\n" +
        " \"announce_to_chat\": false,\n" +
        " \"show_toast\": true,\n" +
        " \"hidden\": true\n" +
        " }\n" +
        "}";

    Bukkit.getUnsafe().loadAdvancement(advancementKey, json);
    return advancementKey;
  }

  @SuppressWarnings("deprecation")
  private NamespacedKey middleType(String icon, String message, ToastType style, Object modelData, boolean glowing,
      NamespacedKey advancementKey) {
    int modelDataInt = 0;
    if (modelData instanceof Integer) {
      modelDataInt = (Integer) modelData;
    } else if (modelData instanceof Float) {
      modelDataInt = ((Float) modelData).intValue();
    } else if (modelData instanceof String) {
      try {
        modelDataInt = Integer.parseInt(modelData.toString());
      } catch (NumberFormatException ignored) {
        // TODO: If parsing fails, modelDataInt remains 0
      }
    }

    String json = "{\n" +
        " \"criteria\": {\n" +
        "   \"trigger\": {\n" +
        "     \"trigger\": \"minecraft:impossible\"\n" +
        "   }\n" +
        " },\n" +
        " \"display\": {\n" +
        "   \"icon\": {\n" +
        "     \"id\": \"minecraft:" + icon + "\",\n" +
        "     \"components\": {\n" +
        "       \"minecraft:custom_model_data\": " + modelDataInt +
        (glowing ? ",\n       \"minecraft:enchantments\": {\n" +
            "         \"levels\": {\n" +
            "           \"minecraft:protection\": 1\n" +
            "         }\n" +
            "       }" : "")
        +
        "\n     },\n" +
        "     \"count\": 1\n" +
        "   },\n" +
        "   \"title\": " + message + ",\n" +
        "   \"description\": {\n" +
        "     \"text\": \"\"\n" +
        "   },\n" +
        "   \"background\": \"minecraft:textures/gui/advancements/backgrounds/adventure.png\",\n" +
        "   \"frame\": \"" + style.toString().toLowerCase() + "\",\n" +
        "   \"announce_to_chat\": false,\n" +
        "   \"show_toast\": true,\n" +
        "   \"hidden\": true\n" +
        " }\n" +
        "}";

    Bukkit.getUnsafe().loadAdvancement(advancementKey, json);
    return advancementKey;
  }

  @SuppressWarnings("deprecation")
  private NamespacedKey modernType(String icon, String message, ToastType style, Object modelData, String modelDataType,
      boolean glowing, NamespacedKey advancementKey) {
    String customModelData;
    if (modelDataType == null) {
      modelDataType = modelData instanceof String ? "string"
          : (modelData instanceof Float || modelData instanceof Double) ? "float" : "integer";
    }

    if ("float".equals(modelDataType) || "integer".equals(modelDataType)) {
      customModelData = "\"minecraft:custom_model_data\": {\n" +
          " \"floats\": [" + modelData + "]\n" +
          " }";
    } else {
      customModelData = "\"minecraft:custom_model_data\": {\n" +
          " \"strings\": [\n" +
          " \"" + modelData + "\"\n" +
          " ]\n" +
          " }";
    }

    String json = "{\n" +
        " \"criteria\": {\n" +
        " \"trigger\": {\n" +
        " \"trigger\": \"minecraft:impossible\"\n" +
        " }\n" +
        " },\n" +
        " \"display\": {\n" +
        " \"icon\": {\n" +
        " \"id\": \"minecraft:" + icon + "\",\n" +
        " \"components\": {\n" +
        " " + customModelData +
        (glowing ? ",\n \"minecraft:enchantments\": {\n" +
            " \"minecraft:protection\": 1\n" +
            " }" : "")
        + "\n" +
        " },\n" +
        " \"count\": 1\n" +
        " },\n" +
        " \"title\": " + message + ",\n" +
        " \"description\": {\n" +
        " \"text\": \"\"\n" +
        " },\n" +
        " \"background\": \"minecraft:textures/gui/advancements/backgrounds/adventure.png\",\n" +
        " \"frame\": \"" + style.toString().toLowerCase() + "\",\n" +
        " \"announce_to_chat\": false,\n" +
        " \"show_toast\": true,\n" +
        " \"hidden\": true\n" +
        " }\n" +
        "}";

    Bukkit.getUnsafe().loadAdvancement(advancementKey, json);
    return advancementKey;
  }

  private NamespacedKey createAdvancement(String icon, String message, ToastType style, Object modelData,
      String modelDataType, boolean glowing) {

    message = MessageFormatter.format(message, true, false);
    String json = GsonComponentSerializer.gson()
        .serialize(LegacyComponentSerializer.legacyAmpersand().deserialize(message));
    json = json.replace("|", "\n");

    UUID randomUUID = UUID.randomUUID();
    NamespacedKey advancementKey = new NamespacedKey(MorePlugins.instance, "anelib_" + randomUUID);

    if (MCVersion.isBetween(MCVersion.v1_16_0, MCVersion.v1_20_4)) {
      // 1.16 - 1.20.4: NBT format with integer CustomModelData
      if (modelData == null) {
        modelData = 0;
      }
      return legacyType(icon, json, style, modelData, glowing, advancementKey);
    } else if (MCVersion.isBetween(MCVersion.v1_20_5, MCVersion.v1_21_3)) {
      // 1.20.5 - 1.21.3: Components format with integer CustomModelData
      if (modelData == null) {
        modelData = 0;
      }
      return middleType(icon, json, style, modelData, glowing, advancementKey);
    } else if (MCVersion.isNewerThan(MCVersion.v1_21_3)) {
      // 1.21.4+: Components format with floats/strings arrays
      if (modelData == null) {
        modelData = "anemys";
        modelDataType = "string";
      }
      return modernType(icon, json, style, modelData, modelDataType, glowing, advancementKey);
    } else {
      throw new OutdatedVersionError("Advancements are not supported on " + MCVersion.getCurrent());
    }

  }

  private void grantAdvancement(Player p, NamespacedKey advancementKey) {
    p.getAdvancementProgress(Objects.requireNonNull(Bukkit.getAdvancement(advancementKey)))
        .awardCriteria("trigger");
  }

  private void revokeAdvancement(Player p, NamespacedKey advancementKey) {
    p.getAdvancementProgress(Objects.requireNonNull(Bukkit.getAdvancement(advancementKey)))
        .revokeCriteria("trigger");
  }
}
