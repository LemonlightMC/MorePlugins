package com.lemonlightmc.moreplugins.updater;

import org.bukkit.Bukkit;

import com.lemonlightmc.moreplugins.messages.Logger;
import com.lemonlightmc.moreplugins.updater.PlatformData.AbstractPlatformData;
import com.lemonlightmc.moreplugins.updater.platform.GithubVersionChecker;
import com.lemonlightmc.moreplugins.updater.platform.HangarVersionChecker;
import com.lemonlightmc.moreplugins.updater.platform.ModrinthVersionChecker;
import com.lemonlightmc.moreplugins.updater.platform.SpigotVersionChecker;
import com.lemonlightmc.moreplugins.version.Version;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.HashMap;
import java.util.concurrent.Callable;

public abstract class VersionChecker {
    private static final HashMap<String, Callable<VersionChecker>> platforms = new HashMap<>();
    private static final HashMap<String, VersionChecker> cachedCheckers = new HashMap<>();

    static {
        platforms.put("github", GithubVersionChecker::new);
        platforms.put("hangar", HangarVersionChecker::new);
        platforms.put("modrinth", ModrinthVersionChecker::new);
        platforms.put("spigot", SpigotVersionChecker::new);
    }

    public static VersionChecker getVersionChecker(String platform) {
        if (cachedCheckers.containsKey(platform)) {
            return cachedCheckers.get(platform);
        }
        if (platforms.containsKey(platform)) {
            try {
                VersionChecker versionChecker = platforms.get(platform).call();
                if (versionChecker == null) {
                    return null;
                }
                cachedCheckers.put(platform, versionChecker);
                return versionChecker;
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    public abstract Version getLatestVersion(PluginData pluginData, AbstractPlatformData platformData)
            throws IOException, InterruptedException;

    public abstract String getDownloadUrl(PluginData pluginData, AbstractPlatformData platformData)
            throws IOException, InterruptedException;

    @FunctionalInterface
    interface VersionCheckerCallable<T> {
        T call(VersionChecker versionChecker, AbstractPlatformData platformData);
    }

    public boolean isUpdateAvailable(PluginData pluginData, AbstractPlatformData platformData)
            throws IOException, InterruptedException {
        Version currentVersion = pluginData.getCurrentVersion();
        Version latestVersion = getLatestVersion(pluginData, platformData);

        pluginData.setCheckRan(true);
        if (!currentVersion.isAtLeast(latestVersion)) {
            pluginData.setLatestVersion(latestVersion);
            return true;
        } else {
            return false;
        }
    }

    static Version getLatestVersion(PluginData pluginData) throws IOException {
        return attemptOnPlatforms(pluginData, (versionChecker, platformData) -> {
            try {
                return versionChecker.getLatestVersion(pluginData, platformData);
            } catch (Exception e) {
                Logger.warn("Failed to check latest version for plugin " + pluginData.getPluginName()
                        + " from Platform "
                        + platformData.getName());
                e.printStackTrace();
                return null;
            }
        });
    }

    static String getDownloadUrl(PluginData pluginData) throws IOException {
        return attemptOnPlatforms(pluginData, (versionChecker, platformData) -> {
            try {
                return versionChecker.getDownloadUrl(pluginData, platformData);
            } catch (Exception e) {
                Logger.warn("Failed to get download url for plugin " + pluginData.getPluginName()
                        + " from Platform "
                        + platformData.getName());
                e.printStackTrace();
                return "";
            }
        });
    }

    static boolean isUpdateAvailable(PluginData pluginData) throws IOException {
        return attemptOnPlatforms(pluginData, (versionChecker, platformData) -> {
            try {
                return versionChecker.isUpdateAvailable(pluginData, platformData);
            } catch (Exception e) {
                Logger.warn("Failed to check if an update is available for plugin" + pluginData.getPluginName()
                        + " from Platform "
                        + platformData.getName());
                e.printStackTrace();
                return false;
            }
        });
    }

    static boolean download(PluginData pluginData) {
        return attemptOnPlatforms(pluginData, (versionChecker, platformData) -> {
            try {
                return versionChecker.download(pluginData, platformData);
            } catch (Exception e) {
                Logger.warn("Failed to download update for plugin " + pluginData.getPluginName() + " from Platform "
                        + platformData.getName());
                e.printStackTrace();
                return false;
            }
        });
    }

    private static <T> T attemptOnPlatforms(PluginData pluginData, VersionCheckerCallable<T> callable) {
        try {
            for (AbstractPlatformData platformData : pluginData.getPlatformData()) {
                VersionChecker versionChecker = getVersionChecker(platformData.getName());
                if (versionChecker == null) {
                    continue;
                }
                return callable.call(versionChecker, platformData);
            }
        } catch (Exception e) {
            Logger.warn("Failed attempts on all available platforms for plugin " + pluginData.getPluginName());
        }
        return null;
    }

    public boolean download(PluginData pluginData, AbstractPlatformData platformData)
            throws IOException, InterruptedException, URISyntaxException {
        String pluginName = pluginData.getPluginName();
        Version latestVersion = pluginData.getLatestVersion();
        String downloadUrl = getDownloadUrl(pluginData, platformData);
        if (downloadUrl == null) {
            return false;
        }

        URL url = new URI(downloadUrl).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.addRequestProperty("User-Agent", "PluginUpdater/1.01.0");
        connection.setInstanceFollowRedirects(true);
        HttpURLConnection.setFollowRedirects(true);

        if (connection.getResponseCode() != 200) {
            throw new IllegalStateException("Response code was " + connection.getResponseCode());
        }

        // Get file name or default to PluginName-Version.jar
        String fileName = url.getFile();
        if (fileName.isEmpty() || fileName.contains("/") || fileName.contains("\\")) {
            fileName = pluginName + "-" + latestVersion.formatted() + ".jar";
        }

        // Ensures update folder exists
        Bukkit.getUpdateFolderFile().mkdirs();

        // Downloads file from url
        ReadableByteChannel rbc = Channels.newChannel(connection.getInputStream());
        File out = new File(Bukkit.getUpdateFolderFile(), fileName);
        Logger.info("Saving '" + fileName + "' to '" + out.getAbsolutePath() + "'");
        FileOutputStream fos = new FileOutputStream(out);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();

        return true;
    }
}
