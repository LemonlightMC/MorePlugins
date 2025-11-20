package com.lemonlightmc.moreplugins.base;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginAwareness;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoadOrder;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;

import com.lemonlightmc.moreplugins.messages.MessageProvider;
import com.lemonlightmc.moreplugins.scheduler.Scheduler;
import com.lemonlightmc.moreplugins.version.Version;

public interface IPluginBase extends Plugin {
  public static interface IPluginDescription {
    public String getName();

    public String getKey();

    public String getFullName();

    public Version getVersion();

    public String getPrefix();

    public String getDescription();

    public Version getApiVersion();

    public String getMain();

    public List<String> getAuthors();

    public List<String> getContributors();

    public String getWebsite();

    public Set<PluginAwareness> getAwareness();

    public List<String> getDepend();

    public List<String> getSoftDepend();

    public PluginLoadOrder getLoadOrder();

    public List<String> getLoadBefore();

    public List<String> getProvides();

    public List<String> getLibraries();

    public Set<String> getCommands();

    public List<Permission> getPermissions();

    public PermissionDefault getPermissionDefault();

  }

  public boolean isEnabled();

  public void onDisable();

  public void onLoad();

  public void onEnable();

  public String getName();

  public String getKey();

  public String getFullName();

  public PluginDescriptionFile getDescription();

  public String getPrefix();

  public Version getVersion();

  public FileConfiguration getConfig();

  public File getDataFolder();

  public void saveConfig();

  public void loadConfig(File file);

  public void loadConfig();

  @Deprecated
  public void saveDefaultConfig();

  @Deprecated
  public void reloadConfig();

  @Deprecated
  public InputStream getResource(String filename);

  @Deprecated
  public void saveResource(String resourcePath, boolean replace);

  public boolean isNaggable();

  public void setNaggable(boolean canNag);

  public PluginLoader getPluginLoader();

  public PluginManager getPluginManager();

  public Scheduler getScheduler();

  public ServicesManager getServicesManager();

  public Server getServer();

  public java.util.logging.Logger getLogger();

  public MessageProvider getMessageProvider();

  public ChunkGenerator getDefaultWorldGenerator(String worldName, String id);

  public BiomeProvider getDefaultBiomeProvider(String worldName, String id);

  @Deprecated
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args);

  @Deprecated
  public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args);
}
