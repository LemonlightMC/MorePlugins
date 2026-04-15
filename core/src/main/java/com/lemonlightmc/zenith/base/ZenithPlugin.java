package com.lemonlightmc.zenith.base;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;

import com.lemonlightmc.zenith.config.Configurate;
import com.lemonlightmc.zenith.messages.MessageProvider;
import com.lemonlightmc.zenith.scheduler.Scheduler;
import com.lemonlightmc.zenith.utils.ResourceUtils;
import com.lemonlightmc.zenith.utils.StringUtils;
import com.lemonlightmc.zenith.version.Version;

public abstract class ZenithPlugin extends org.bukkit.plugin.java.JavaPlugin implements IPluginBase {

  private final Server server = null;
  private Scheduler scheduler = null;

  private final File file = null;
  private PluginInfo info = null;
  private MessageProvider messageProvider = null;

  private static ZenithPlugin instance = null;

  public ZenithPlugin() {
    super();
    this.info = new PluginInfo(getDescription());
    this.scheduler = new Scheduler();
    messageProvider = new MessageProvider();
    ZenithPlugin.instance = this;
  }

  public static boolean hasInstance() {
    return instance != null;
  }

  @SuppressWarnings("unchecked")
  public static <I extends ZenithPlugin> I getInstance() {
    if (instance == null) {
      throw new RuntimeException(
          "Plugin is not enabled - Plugin Instance can not be obtained!");
    }
    return (I) instance;
  }

  @Override
  public PluginInfo getInfo() {
    return info;
  }

  @Override
  public String getKey() {
    return info.getKey();
  }

  @Override
  public String getFullName() {
    return info.getFullName();
  }

  @Override
  public String getPrefix() {
    return info.getPrefix();
  }

  @Override
  public Version getVersion() {
    return info.getVersion();
  }

  protected File getFile() {
    return file;
  }

  public File getDataFile(final String... path) {
    if (path == null || path.length == 0) {
      return this.getDataFolder();
    }
    return new File(this.getDataFolder(), StringUtils.join(File.separator, path));
  }

  @Override
  public PluginManager getPluginManager() {
    return server.getPluginManager();
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
  public java.util.logging.Logger getLogger() {
    return server.getLogger();
  }

  @Override
  public MessageProvider getMessageProvider() {
    return messageProvider;
  }

  @Deprecated
  @Override
  public FileConfiguration getConfig() {
    throw new UnsupportedOperationException(
        "FileConfiguration is not supported in PluginBase. Use Configurate instead.");
  }

  @Override
  public void reloadConfig() {
    Configurate.reloadAll();
  }

  @Override
  public void saveConfig() {
    Configurate.saveAll();
  }

  @Override
  public void loadConfig() {
    Configurate.loadAll();
  }

  @Override
  public void loadConfig(final File file) {
    Configurate.load(file.getName());
  }

  @Override
  public void saveDefaultConfig() {
    Configurate.createDefault();
  }

  @Deprecated
  @Override
  public InputStream getResource(final String filename) {
    return ResourceUtils.getResourceStream(filename);
  }

  @Deprecated
  @Override
  public void saveResource(final String path, final boolean replace) {
    final File file = ResourceUtils.getResourceFile(path);
    if (file == null) {
      return;
    }
    ResourceUtils.saveResource(file, new File(getDataFolder(), path));
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
  public String toString() {
    return info.getFullName();
  }

  @Override
  @Deprecated
  public List<String> onTabComplete(final CommandSender sender, final Command command, final String label,
      final String[] args) {
    throw new UnsupportedOperationException(
        "onTabComplete is not supported in Main Plugin. Create Command with CommandAPI instead!");
  }

  @Override
  @Deprecated
  public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
    throw new UnsupportedOperationException(
        "onCommand is not supported in Main Plugin. Create Command with CommandAPI instead!");
  }

  @Deprecated
  @Override
  public ChunkGenerator getDefaultWorldGenerator(final String worldName, final String id) {
    return null;
  }

  @Deprecated
  @Override
  public BiomeProvider getDefaultBiomeProvider(final String worldName, final String id) {
    return null;
  }
}
