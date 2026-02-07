package com.lemonlightmc.moreplugins.commands;

import com.lemonlightmc.moreplugins.commands.executors.AbstractCommand;

public class SimpleSubCommand<S> extends AbstractCommand<SimpleSubCommand<S>, S> {

  public SimpleSubCommand(final String... aliases) {
    super();
    withAliases(aliases);
  }

  public static <T> SimpleSubCommand<T> create(final Class<T> sender, final String... aliases) {
    return new SimpleSubCommand<T>(aliases);
  }

  public SimpleSubCommand<S> getInstance() {
    return this;
  }

}
