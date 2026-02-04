package com.lemonlightmc.moreplugins.commands.argumentsbase;

public enum ArgumentType {

  ADVANCEMENT(),

  ADVENTURE_CHAT, // wont implemented
  ADVENTURE_CHATCOLOR, // wont implemented
  ADVENTURE_CHAT_COMPONENT, // wont implemented

  ANGLE(), // wont implemented
  ROTATION(),

  AXIS(),

  BLOCK_PREDICATE(), // wont implemented
  BLOCKSTATE(),
  BLOCKDATA(),
  MATERIAL(),

  CHAT(),
  CHAT_COMPONENT(), // Not implemented
  CHATCOLOR(),

  ENTITY_SELECTOR(),
  ENTITY_TYPE(),

  ITEMSTACK(),
  ITEMSTACK_PREDICATE(), // wont implemented

  NAMESPACED_KEY(),
  UUID(),

  PLAYER(),
  PLAYERPROFILE(),
  OFFLINE_PLAYER(),
  ASYNC_OFFLINE_PLAYER(), // wont implemented

  PRIMITIVE_BOOLEAN(),
  PRIMITIVE_DOUBLE(),
  PRIMITIVE_FLOAT(),
  PRIMITIVE_GREEDY_STRING(),
  PRIMITIVE_INTEGER(),
  PRIMITIVE_LONG(),
  PRIMITIVE_STRING(),
  PRIMITIVE_TEXT(),

  RANGE_INT(),
  RANGE_LONG(),
  RANGE_FLOAT(),
  RANGE_DOUBLE(),

  LIST_GREEDY(),
  LIST_TEXT(),
  FUNCTION(),
  MAP(),
  NBT_COMPOUND(), // wont implemented

  MATH_OPERATION(),
  OBJECTIVE(),
  CRITERIA(),
  SCORE_HOLDER(), // wont implemented
  SCOREBOARD_SLOT(),

  SOUND(),
  PARTICLE(), // unfinished implemented
  ENCHANTMENT(),
  LOOT_TABLE(),
  RECIPE(),
  POTION_EFFECT(),

  TEAM(),

  LOCATION(),
  LOCATION_2D(),
  TIME(),
  BIOME(),
  WORLD(),
  ENVIRONMENT(),
  DIMENSION(), // wont implemented
  LOOK_ANCHOR(),
  GAME_MODE(),
  STRUCTURE(),
  STRUCTURE_TYPE(),
  ATTRIBUTE(),

  COMMAND(),
  CUSTOM, // Not implemented
  FLAG,
  SWITCH,
  LITERAL,
  MULTI_LITERAL,
  BRANCH();

  private ArgumentType() {
  }

  public boolean isPrimitive() {
    return this.name().startsWith("PRIMITIVE_");
  }

  public boolean isList() {
    return this.name().startsWith("LIST_");
  }

  public boolean isLiteral() {
    return this == LITERAL || this == MULTI_LITERAL;
  }

  public boolean isGreedy() {
    return this == PRIMITIVE_GREEDY_STRING || this == LIST_GREEDY;
  }
}
