package com.julizey.moreplugins.commands.executors;

public enum ExecutorType {
  /**
   * An executor where the CommandSender is a Player
   */
  PLAYER,

  /**
   * An executor where the CommandSender is an Entity
   */
  ENTITY,

  /**
   * An executor where the CommandSender is a ConsoleCommandSender
   */
  CONSOLE,

  /**
   * An executor where the CommandSender is a BlockCommandSender
   */
  BLOCK,

  /**
   * An executor where the CommandSender is any CommandSender
   */
  ALL,

  /**
   * An executor where the CommandSender is a NativeProxyCommandSender
   */
  PROXY,

  /**
   * An executor where the CommandSender is (always) a NativeProxyCommandSender
   */
  NATIVE,

  /**
   * An executor where the CommandSender is a RemoteConsoleCommandSender
   */
  REMOTE,

  /**
   * An executor where the CommandSender is a {@code io.papermc.paper.commands.FeedbackForwardingSender}
   */
  FEEDBACK_FORWARDING,
}
