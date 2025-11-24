package com.lemonlightmc.moreplugins.commands.executors;

public enum ExecutorType {
  PLAYER,

  ENTITY,
  CONSOLE,
  BLOCK,

  ALL,

  /**
   * NativeProxyCommandSender
   */
  PROXY,

  /**
   * NativeProxyCommandSender (always)
   */
  NATIVE,

  /**
   * RemoteConsoleCommandSender
   */
  REMOTE,

  /**
   * {@code io.papermc.paper.commands.FeedbackForwardingSender}
   */
  FEEDBACK_FORWARDING,
}
