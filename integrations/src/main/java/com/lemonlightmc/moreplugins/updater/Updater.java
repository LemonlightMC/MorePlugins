package com.lemonlightmc.moreplugins.updater;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.lemonlightmc.moreplugins.version.Version;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.regex.Matcher;

@SuppressWarnings("unused")
public class Updater {
    private final Plugin plugin;
    private final PluginData pluginData;

    public Updater(Plugin plugin, PluginData pluginData) {
        this.plugin = plugin;
        this.pluginData = pluginData;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public PluginData getPluginData() {
        return pluginData;
    }

    public boolean isUpdateAvailable() {
        return pluginData.isUpdateAvailable();
    }

    public boolean isAlreadyDownloaded() {
        return pluginData.isAlreadyDownloaded();
    }

    public CompletableFuture<Boolean> checkForUpdate() {
        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                Version currentVersion = new Version(pluginData.getCurrentVersion());
                Version latestVersion = VersionChecker.getLatestVersion(pluginData);

                pluginData.setCheckRan(true);
                if (!currentVersion.isAtLeast(latestVersion)) {
                    pluginData.setLatestVersion(latestVersion);
                    completableFuture.complete(true);
                } else {
                    completableFuture.complete(false);
                }
            } catch (IOException | IllegalStateException e) {
                plugin.getLogger().log(Level.SEVERE, e.getMessage(), e);
                completableFuture.complete(false);
            }
        });

        return completableFuture;
    }

    public CompletableFuture<Boolean> attemptDownload() {
        if (!pluginData.isEnabled() || !pluginData.isUpdateAvailable() || pluginData.isAlreadyDownloaded()) {
            return CompletableFuture.completedFuture(false);
        }
        return download();
    }

    public CompletableFuture<Boolean> forceDownload() {
        return download();
    }

    private CompletableFuture<Boolean> download() {
        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                if (VersionChecker.download(pluginData)) {
                    pluginData.setAlreadyDownloaded(true);
                    completableFuture.complete(true);
                } else {
                    completableFuture.complete(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
                completableFuture.completeExceptionally(e);
            }
        });

        return completableFuture;
    }
}
