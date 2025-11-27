package com.lemonlightmc.moreplugins.commands.executors;

public enum ExecutorType {
  ALL,

  PLAYER,

  ENTITY,

  CONSOLE,

  BLOCK,

  // NativeProxyCommandSender
  PROXY,

  // NativeProxyCommandSender (always)
  NATIVE,

  // RemoteConsoleCommandSender
  REMOTE,

  // Paper's FeedbackForwardingSender
  FEEDBACK_FORWARDING,
}
