package com.lemonlightmc.zenith.base;

import java.io.File;
import java.io.InputStream;
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

import com.lemonlightmc.zenith.config.Configurate;
import com.lemonlightmc.zenith.messages.MessageProvider;
import com.lemonlightmc.zenith.scheduler.Scheduler;
import com.lemonlightmc.zenith.utils.ResourceUtils;
import com.lemonlightmc.zenith.utils.StringUtils;
import com.lemonlightmc.zenith.version.Version;

public abstract class PluginBase implements IPluginBase {

  private boolean isEnabled = false;
  private PluginLoader loader = null;
  private Server server = null;
  private Scheduler scheduler = null;

  private File file = null;
  private PluginInfo info = null;
  private final File dataFolder = null;
  private ClassLoader classLoader = null;
  private boolean naggable = true;
  private MessageProvider messageProvider = null;

  private static PluginBase instance = null;

  public PluginBase() {
    super();
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

  @Override
  public PluginInfo getInfo() {
    return info;
  }

  @Override
  public String getKey() {
    return info.getKey();
  }

  @Override
  public String getName() {
    return info.getName();
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

  @Override
  public PluginDescriptionFile getDescription() {
    return info.descriptionFile;
  }

  protected File getFile() {
    return file;
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
  public boolean isEnabled() {
    return isEnabled;
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

  @Override
  public Server getServer() {
    return server;
  }

  @Override
  public PluginLoader getPluginLoader() {
    return loader;
  }

  public ClassLoader getClassLoader() {
    return classLoader;
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
    ResourceUtils.saveResource(file, new File(dataFolder, path));
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
  public int hashCode() {
    return 31 * (31 + file.hashCode()) + info.hashCode();
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final PluginBase other = (PluginBase) obj;
    return info.equals(other.info) && file.equals(other.file);
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
