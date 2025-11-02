package com.lemonlightmc.moreplugins.items;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemUtils {

  public ItemStack getPlayerHead(UUID uuid, String name) {
    return getPlayerHead(Bukkit.getOfflinePlayer(uuid), name);
  }

  public ItemStack getPlayerHead(UUID uuid) {
    OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
    return getPlayerHead(p, p.getName());
  }

  public ItemStack getPlayerHead(Player p, String name) {
    return getPlayerHead(p, name);
  }

  public ItemStack getPlayerHead(Player p) {
    return getPlayerHead(p, p.getName());
  }

  public ItemStack getPlayerHead(OfflinePlayer p) {
    return getPlayerHead(p, p.getName());
  }

  public ItemStack getPlayerHead(OfflinePlayer p, String name) {
    ItemStack item = new ItemStack(Material.PLAYER_HEAD);
    SkullMeta meta = (SkullMeta) (item.hasItemMeta() ? item.getItemMeta()
        : Bukkit.getItemFactory().getItemMeta(item.getType()));
    assert meta != null;
    meta.setOwningPlayer(p);
    meta.setDisplayName(name);
    item.setItemMeta(meta);
    return item;
  }
}
