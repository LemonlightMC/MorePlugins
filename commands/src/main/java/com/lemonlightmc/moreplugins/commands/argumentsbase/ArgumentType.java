package com.lemonlightmc.moreplugins.commands.argumentsbase;

public enum ArgumentType {

  ADVANCEMENT(),

  ADVENTURE_CHAT, // wont implemented
  ADVENTURE_CHATCOLOR, // wont implemented
  ADVENTURE_CHAT_COMPONENT, // wont implemented

  ANGLE(), // wont implemented
  ROTATION(),

  AXIS(),

  BIOME(),

  BLOCK_PREDICATE(), // wont implemented
  BLOCKSTATE(),
  BLOCKDATA(),
  MATERIAL(),

  CHAT(),
  CHAT_COMPONENT(), // Not implemented
  CHATCOLOR(),

  DIMENSION(), // Not implemented

  ENCHANTMENT(),

  ENTITY_SELECTOR(),
  ENTITY_TYPE(),

  ENVIRONMENT(), // Not implemented

  FUNCTION(),

  ITEMSTACK(),
  ITEMSTACK_PREDICATE(), // wont implemented

  LIST_GREEDY(),
  LIST_TEXT(),

  LOCATION(),
  LOCATION_2D(),

  LOOT_TABLE(),

  MAP(),

  MATH_OPERATION(),

  NAMESPACED_KEY(),

  PARTICLE(), // unfinished implemented

  PLAYER(),
  PLAYERPROFILE(),
  OFFLINE_PLAYER(),
  ASYNC_OFFLINE_PLAYER(), // wont implemented

  POTION_EFFECT(),

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

  RECIPE(),

  NBT_COMPOUND(), // wont implemented

  OBJECTIVE(),
  CRITERIA(),
  SCORE_HOLDER(), // wont implemented
  SCOREBOARD_SLOT(),

  SOUND(),

  TEAM(),

  TIME(),

  UUID(),
  WORLD(),

  COMMAND(),
  CUSTOM, // Not implemented
  LITERAL,
  MULTI_LITERAL,
  BRANCH();

  private ArgumentType() {
  }
}
