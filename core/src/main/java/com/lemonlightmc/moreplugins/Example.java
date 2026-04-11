package com.lemonlightmc.moreplugins;

import java.nio.file.Path;

import com.lemonlightmc.moreplugins.config.schema.Schema;
import com.lemonlightmc.moreplugins.config.schema.SchemaType;

public class Example {

  public static void main(String[] args) {
    Schema schema = Schema.create().addNodes(
        Schema.section("example", "desc").addNodes(
            Schema.pair("string", SchemaType.STRING),
            Schema.pair("int", SchemaType.INT),
            Schema.pair("bool", SchemaType.BOOL)),
        Schema.pair("test", SchemaType.BOOL));

    schema.save(Path.of("./testSchema.txt"));
    // ConfigData data = Configurate.yaml(Path.of("config.yml"), schema);

    // String str = data.getString("example.string");
  }
}
