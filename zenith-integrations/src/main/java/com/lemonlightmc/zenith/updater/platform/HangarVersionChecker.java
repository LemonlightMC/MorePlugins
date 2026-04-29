package com.lemonlightmc.zenith.updater.platform;

import java.io.IOException;
import java.net.http.HttpResponse;

import com.lemonlightmc.zenith.updater.HttpUtil;
import com.lemonlightmc.zenith.updater.PluginData;
import com.lemonlightmc.zenith.updater.VersionChecker;
import com.lemonlightmc.zenith.updater.PlatformData.AbstractPlatformData;
import com.lemonlightmc.zenith.updater.PlatformData.HangarData;
import com.lemonlightmc.zenith.version.Version;

public class HangarVersionChecker extends VersionChecker {
    public static final String ENDPOINT = "https://hangar.papermc.io/api/v1";

    @Override
    public Version getLatestVersion(PluginData pluginData, AbstractPlatformData platformData)
            throws IOException, InterruptedException {
        if (!(platformData instanceof HangarData hangarData)) {
            return null;
        }

        HttpResponse<String> response = HttpUtil.sendRequest(String.format("%s/projects/%s/latestrelease",
                ENDPOINT, hangarData.getHangarProjectSlug()));

        if (response.statusCode() != 200) {
            throw new IllegalStateException("Received invalid response code (" + response.statusCode()
                    + ") whilst checking '" + pluginData.getPluginName() + "' for updates.");
        }

        return new Version(response.body());
    }

    @Override
    public String getDownloadUrl(PluginData pluginData, AbstractPlatformData platformData) {
        return platformData instanceof HangarData hangarData
                ? String.format("%s/projects/%s/versions/%s/PAPER/download", ENDPOINT,
                        hangarData.getHangarProjectSlug(), pluginData.getLatestVersion())
                : null;
    }
}
