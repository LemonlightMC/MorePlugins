package com.lemonlightmc.moreplugins.commands.argumentsbase;

public enum ArgumentType {

  ADVANCEMENT("api:advancement"),

  ADVENTURE_CHAT,
  ADVENTURE_CHATCOLOR,
  ADVENTURE_CHAT_COMPONENT,

  ANGLE("minecraft:angle"),

  ASYNC_OFFLINE_PLAYER("api:async_offline_player"),

  AXIS("minecraft:swizzle"),

  BIOME("api:biome"),

  BLOCK_PREDICATE("minecraft:block_predicate"),
  BLOCKSTATE("minecraft:block_state"),

  CHAT("minecraft:message"),
  CHAT_COMPONENT("minecraft:component"),
  CHATCOLOR("minecraft:color"),

  COMMAND("api:command"),

  CUSTOM,

  DIMENSION("minecraft:dimension"),
  ENCHANTMENT("minecraft:item_enchantment"),

  ENTITY_SELECTOR("minecraft:entity"),
  ENTITY_TYPE("minecraft:entity_summon"),

  ENVIRONMENT("api:environment"),

  FLOAT_RANGE("minecraft:float_range"),

  FUNCTION("minecraft:function"),

  INT_RANGE("minecraft:int_range"),

  ITEMSTACK("minecraft:item_stack"),
  ITEMSTACK_PREDICATE("minecraft:item_predicate"),

  LIST("api:list"),
  LIST_TEXT("api:list_text"),

  LITERAL,

  LOCATION(new String[] { "minecraft:vec3", "minecraft:block_pos" }),
  LOCATION_2D(new String[] { "minecraft:vec2", "minecraft:column_pos" }),

  LOOT_TABLE("api:loot_table"),

  MAP("api:map"),

  MATH_OPERATION("minecraft:operation"),

  MULTI_LITERAL,

  NAMESPACED_KEY("minecraft:resource_location"),

  NBT_COMPOUND("minecraft:nbt_compound_tag"),
  OBJECTIVE("minecraft:objective"),
  OBJECTIVE_CRITERIA("minecraft:objective_criteria"),

  OFFLINE_PLAYER("api:offline_player"),

  PARTICLE("minecraft:particle"),

  PLAYER("minecraft:game_profile"),

  POTION_EFFECT("minecraft:mob_effect"),

  PRIMITIVE_BOOLEAN("brigadier:bool"),
  PRIMITIVE_DOUBLE("brigadier:double"),
  PRIMITIVE_FLOAT("brigadier:float"),
  PRIMITIVE_GREEDY_STRING("api:greedy_string"),
  PRIMITIVE_INTEGER("brigadier:integer"),
  PRIMITIVE_LONG("brigadier:long"),
  PRIMITIVE_STRING("brigadier:string"),
  PRIMITIVE_TEXT("api:text"),

  RECIPE("api:recipe"),

  ROTATION("minecraft:rotation"),

  SCORE_HOLDER("minecraft:score_holder"),
  SCOREBOARD_SLOT("minecraft:scoreboard_slot"),

  SOUND("api:sound"),

  TEAM("minecraft:team"),

  TIME("minecraft:time"),

  UUID("minecraft:uuid");

  public static ArgumentType fromInternal(final String internal) {
    for (final ArgumentType type : ArgumentType.values()) {
      for (String key : type.internals) {
        if (key == internal) {
          return type;
        }
      }
    }
    return null;
  }

  private final String[] internals;

  ArgumentType() {
    internals = new String[0];
  }

  ArgumentType(final String internal) {
    this.internals = new String[] { internal };
  }

  ArgumentType(final String[] internals) {
    this.internals = internals;
  }
}
