package com.lemonlightmc.moreplugins.commands;

import com.lemonlightmc.moreplugins.commands.executors.AbstractCommand;

public class SimpleSubCommand extends AbstractCommand<SimpleSubCommand> {

  public SimpleSubCommand(final String... aliases) {
    super();
    withAliases(aliases);
  }

  public static SimpleSubCommand create(final String... aliases) {
    return new SimpleSubCommand(aliases);
  }

  public SimpleSubCommand getInstance() {
    return this;
  }

}
