package com.julizey.moreplugins.updater.platform;

import com.julizey.moreplugins.updater.HttpUtil;
import com.julizey.moreplugins.updater.PluginData;
import com.julizey.moreplugins.updater.VersionChecker;
import com.julizey.moreplugins.updater.PlatformData.AbstractPlatformData;
import com.julizey.moreplugins.updater.PlatformData.SpigotData;
import com.julizey.moreplugins.version.Version;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.http.HttpResponse;

public class SpigotVersionChecker extends VersionChecker {
    public static final String ENDPOINT = "https://api.spiget.org/v2";

    @Override
    public Version getLatestVersion(PluginData pluginData, AbstractPlatformData platformData)
            throws IOException, InterruptedException {
        if (!(platformData instanceof SpigotData spigotData)) {
            return null;
        }

        HttpResponse<String> response = HttpUtil.sendRequest(String.format("%s/resources/%s/versions/latest",
                ENDPOINT, spigotData.getSpigotResourceId()));

        if (response.statusCode() != 200) {
            throw new IllegalStateException("Received invalid response code (" + response.statusCode()
                    + ") whilst checking '" + pluginData.getPluginName() + "' for updates.");
        }

        JsonObject pluginJson = JsonParser.parseString(response.body()).getAsJsonObject();
        return new Version(pluginJson.get("name").getAsString());
    }

    @Override
    public String getDownloadUrl(PluginData pluginData, AbstractPlatformData platformData) {
        return platformData instanceof SpigotData spigotData
                ? String.format("%s/resources/%s/download", ENDPOINT,
                        spigotData.getSpigotResourceId())
                : null;
    }
}
