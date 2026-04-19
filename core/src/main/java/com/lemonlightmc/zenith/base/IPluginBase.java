package com.lemonlightmc.zenith.base;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;

import com.lemonlightmc.zenith.messages.MessageStore;
import com.lemonlightmc.zenith.scheduler.Scheduler;
import com.lemonlightmc.zenith.version.Version;

public interface IPluginBase extends Plugin {

  public boolean isEnabled();

  public void onDisable();

  public void onLoad();

  public void onEnable();

  public PluginInfo getInfo();

  public String getKey();

  public String getName();

  public String getFullName();

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

  public MessageStore getMessageStore();

  public ChunkGenerator getDefaultWorldGenerator(String worldName, String id);

  public BiomeProvider getDefaultBiomeProvider(String worldName, String id);

  @Deprecated
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args);

  @Deprecated
  public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args);

}
