package com.lemonlightmc.moreplugins.commands;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import com.lemonlightmc.moreplugins.commands.exceptions.CommandSyntaxException;
import com.lemonlightmc.moreplugins.commands.executors.Executors.ExecutorType;

public interface CommandSource<S> {

  public <T extends S> CommandSource<T> copyFor(final T newSender);

  public CommandSource<S> copy();

  public S sender();

  public Location location();

  public Entity entity();

  public World world();

  public ExecutorType executorType();

  public void sendMessage(final String str);

  public void sendError(final CommandSyntaxException e);

  public boolean hasPermission(final String perm);
}
