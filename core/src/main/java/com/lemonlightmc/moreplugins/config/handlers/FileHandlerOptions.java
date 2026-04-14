package com.lemonlightmc.moreplugins.config.handlers;

import java.util.List;

public class FileHandlerOptions {
  private String header = null;
  private String footer = null;
  private boolean parseComments = true;
  private char pathSeparator = '.';
  private boolean copyDefaults = false;

  public FileHandlerOptions() {
  }

  public String header() {
    return header;
  }

  public FileHandlerOptions header(final List<String> header) {
    this.header = header == null ? null : String.join("\n", header);
    return this;
  }

  public FileHandlerOptions header(final String header) {
    this.header = header;
    return this;
  }

  public FileHandlerOptions footer(final List<String> footer) {
    this.footer = footer == null ? null : String.join("\n", footer);
    return this;
  }

  public FileHandlerOptions footer(final String footer) {
    this.footer = footer;
    return this;
  }

  public String footer() {
    return footer;
  }

  public boolean parseComments() {
    return parseComments;
  }

  public FileHandlerOptions parseComments(final boolean parseComments) {
    this.parseComments = parseComments;
    return this;
  }

  public boolean copyDefaults() {
    return copyDefaults;
  }

  public FileHandlerOptions copyDefaults(boolean value) {
    this.copyDefaults = value;
    return this;
  }

  public char pathSeparator() {
    return pathSeparator;
  }

  public FileHandlerOptions pathSeparator(char value) {
    this.pathSeparator = value;
    return this;
  }

}
