package com.lemonlightmc.moreplugins.updater;

import org.bukkit.plugin.Plugin;

import com.lemonlightmc.moreplugins.updater.PlatformData.AbstractPlatformData;
import com.lemonlightmc.moreplugins.version.Version;

import java.util.List;

public class PluginData {
    private final String pluginName;
    private final List<AbstractPlatformData> platformData;
    private final Version currentVersion;
    private Version latestVersion;

    private boolean enabled = true;
    private boolean alreadyDownloaded = false;
    private boolean checkRan = false;

    public PluginData(final Plugin plugin, final List<AbstractPlatformData> platformData) {
        this.pluginName = plugin.getName();
        this.platformData = platformData;
        this.currentVersion = new Version(plugin.getDescription().getVersion());
    }

    public String getPluginName() {
        return pluginName;
    }

    public List<AbstractPlatformData> getPlatformData() {
        return platformData;
    }

    public void addPlatform(final AbstractPlatformData platformData) {
        this.platformData.add(platformData);
    }

    public Version getCurrentVersion() {
        return currentVersion;
    }

    public Version getLatestVersion() {
        return latestVersion;
    }

    public void setLatestVersion(final Version latestVersion) {
        this.latestVersion = latestVersion;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isUpdateAvailable() {
        return currentVersion.isAtLeast(latestVersion);
    }

    public boolean isAlreadyDownloaded() {
        return alreadyDownloaded;
    }

    public void setAlreadyDownloaded(final boolean alreadyDownloaded) {
        this.alreadyDownloaded = alreadyDownloaded;
    }

    public boolean hasCheckRan() {
        return checkRan;
    }

    public void setCheckRan(final boolean checkRan) {
        this.checkRan = checkRan;
    }
}
