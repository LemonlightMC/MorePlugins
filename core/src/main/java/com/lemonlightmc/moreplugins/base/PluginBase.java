package com.lemonlightmc.moreplugins.base;

import com.lemonlightmc.moreplugins.data.Config;
import com.lemonlightmc.moreplugins.messages.Logger;
import com.lemonlightmc.moreplugins.messages.MessageProvider;
import com.lemonlightmc.moreplugins.scheduler.Scheduler;
import com.lemonlightmc.moreplugins.utils.StringUtils;
import com.lemonlightmc.moreplugins.version.Version;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPluginLoader;

public class PluginBase extends org.bukkit.plugin.PluginBase implements IPluginBase {

  private boolean isEnabled = false;
  private PluginLoader loader = null;
  private Server server = null;
  private Scheduler scheduler = null;

  private File file = null;
  private PluginInfo info = null;
  private final File dataFolder = null;
  private ClassLoader classLoader = null;
  private boolean naggable = true;
  private Config config = null;
  private MessageProvider messageProvider = null;

  private static PluginBase instance = null;

  public PluginBase() {
    classLoader = this.getClass().getClassLoader();
    this.server = Bukkit.getServer();
    this.scheduler = new Scheduler();
    messageProvider = new MessageProvider();
    PluginBase.instance = this;
  }

  protected PluginBase(
      final JavaPluginLoader loader,
      final PluginDescriptionFile info,
      final File dataFolder,
      final File file) {
    this();
    this.loader = loader;
    this.file = file;
    this.info = new PluginInfo(info);
    this.config = Config.from(dataFolder.toPath().resolve("config.yml"));
  }

  public static boolean hasInstance() {
    return instance != null;
  }

  @SuppressWarnings("unchecked")
  public static <I extends PluginBase> I getInstance() {
    if (instance == null) {
      throw new RuntimeException(
          "Plugin is not enabled - Plugin Instance can not be obtained!");
    }
    return (I) instance;
  }

  public static PluginLoader getInstancePluginLoader() {
    return getInstance().loader;
  }

  public static PluginManager getInstancePluginManager() {
    return getInstance().server.getPluginManager();
  }

  public static Server getInstanceServer() {
    return getInstance().server;
  }

  public static ServicesManager getInstanceServicesManager() {
    return getInstance().server.getServicesManager();
  }

  public static Scheduler getInstanceScheduler() {
    return getInstance().scheduler;
  }

  public static MessageProvider getInstanceMessageProvider() {
    return getInstance().messageProvider;
  }

  @Override
  public String getFullName() {
    return info.getFullName();
  }

  @Override
  public String getKey() {
    return info.getKey();
  }

  @Override
  public PluginDescriptionFile getDescription() {
    return info.descriptionFile;
  }

  @Override
  public String getPrefix() {
    return info.getPrefix();
  }

  @Override
  public Version getVersion() {
    return info.getVersion();
  }

  @Override
  public File getDataFolder() {
    return dataFolder;
  }

  public File getDataFile(final String... path) {
    if (path == null || path.length == 0) {
      return this.getDataFolder();
    }
    return new File(this.getDataFolder(), StringUtils.join(File.separator, path));
  }

  @Override
  public PluginLoader getPluginLoader() {
    return loader;
  }

  @Override
  public PluginManager getPluginManager() {
    return server.getPluginManager();
  }

  @Override
  public Server getServer() {
    return server;
  }

  @Override
  public ServicesManager getServicesManager() {
    return server.getServicesManager();
  }

  @Override
  public Scheduler getScheduler() {
    return scheduler;
  }

  @Override
  public MessageProvider getMessageProvider() {
    return messageProvider;
  }

  @Override
  public boolean isEnabled() {
    return isEnabled;
  }

  protected File getFile() {
    return file;
  }

  @Override
  public FileConfiguration getConfig() {
    return config.getConfig();
  }

  @Override
  public void reloadConfig() {
    config.reload();
  }

  @Override
  public void saveConfig() {
    config.save();
  }

  @Override
  public void loadConfig() {
    config.load();
  }

  @Override
  public void loadConfig(final File file) {
    config.load(file);
  }

  @Override
  public void saveDefaultConfig() {
    config.createDefault();
  }

  protected Reader getTextResource(final String file) {
    final InputStream in = getResource(file);

    return in == null
        ? null
        : new InputStreamReader(in, StandardCharsets.UTF_8);
  }

  @Override
  public void saveResource(String resourcePath, final boolean replace) {
    if (resourcePath == null || resourcePath.equals("")) {
      throw new IllegalArgumentException(
          "ResourcePath cannot be null or empty");
    }

    resourcePath = resourcePath.replace('\\', '/');
    final InputStream in = getResource(resourcePath);
    if (in == null) {
      throw new IllegalArgumentException(
          "The embedded resource '" +
              resourcePath +
              "' cannot be found in " +
              file);
    }

    final File outFile = new File(dataFolder, resourcePath);
    final int lastIndex = resourcePath.lastIndexOf('/');
    final File outDir = new File(
        dataFolder,
        resourcePath.substring(0, lastIndex >= 0 ? lastIndex : 0));

    if (!outDir.exists()) {
      outDir.mkdirs();
    }

    try {
      if (!outFile.exists() || replace) {
        final OutputStream out = new FileOutputStream(outFile);
        final byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
          out.write(buf, 0, len);
        }
        out.close();
        in.close();
      } else {
        Logger.warn(
            "Could not save " +
                outFile.getName() +
                " to " +
                outFile +
                " because " +
                outFile.getName() +
                " already exists.");
      }
    } catch (final IOException ex) {
      Logger.warn("Could not save " + outFile.getName() + " to " + outFile);
      ex.printStackTrace();
    }
  }

  @Override
  public InputStream getResource(final String filename) {
    if (filename == null) {
      throw new IllegalArgumentException("Filename cannot be null");
    }

    try {
      final URL url = getClassLoader().getResource(filename);
      if (url == null) {
        return null;
      }

      final URLConnection connection = url.openConnection();
      connection.setUseCaches(false);
      return connection.getInputStream();
    } catch (final IOException ex) {
      return null;
    }
  }

  public ClassLoader getClassLoader() {
    return classLoader;
  }

  public void setEnabled(final boolean enabled) {
    if (isEnabled != enabled) {
      isEnabled = enabled;

      if (isEnabled) {
        onEnable();
      } else {
        onDisable();
      }
    }
  }

  @Deprecated
  public PluginCommand getCommand(final String name) {
    final String alias = name.toLowerCase(java.util.Locale.ENGLISH);
    PluginCommand command = getServer().getPluginCommand(alias);

    if (command == null || command.getPlugin() != this) {
      command = getServer()
          .getPluginCommand(
              info.getName().toLowerCase(java.util.Locale.ENGLISH) +
                  ":" +
                  alias);
    }

    if (command != null && command.getPlugin() == this) {
      return command;
    } else {
      return null;
    }
  }

  @Override
  public void onLoad() {
  }

  @Override
  public void onDisable() {
  }

  @Override
  public void onEnable() {
  }

  @Override
  public boolean isNaggable() {
    return naggable;
  }

  @Override
  public void setNaggable(final boolean canNag) {
    this.naggable = canNag;
  }

  @Override
  public String toString() {
    return info.getFullName();
  }

  @Override
  public java.util.logging.Logger getLogger() {
    return server.getLogger();
  }

  @Override
  @Deprecated
  public List<String> onTabComplete(final CommandSender sender, final Command command, final String label,
      final String[] args) {
    throw new UnsupportedOperationException("Unimplemented method 'onTabComplete'");
  }

  @Override
  @Deprecated
  public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
    throw new UnsupportedOperationException("Unimplemented method 'onCommand'");
  }

  @Override
  public ChunkGenerator getDefaultWorldGenerator(final String worldName, final String id) {
    return null;
  }

  @Override
  public BiomeProvider getDefaultBiomeProvider(final String worldName, final String id) {
    return null;
  }
}
