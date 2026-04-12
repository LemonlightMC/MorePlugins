package com.lemonlightmc.moreplugins;

import java.nio.file.Path;

import com.lemonlightmc.moreplugins.config.ConfigData;
import com.lemonlightmc.moreplugins.config.Configurate;
import com.lemonlightmc.moreplugins.config.schema.BuildSchema;
import com.lemonlightmc.moreplugins.config.schema.Schema;
import com.lemonlightmc.moreplugins.config.schema.SchemaType;

public class Example {

  public static void main(final String[] args) {
    final BuildSchema schema = Schema.create().addNodes(
        Schema.section("example", "desc").addNodes(
            Schema.pair("string", SchemaType.STRING),
            Schema.pair("int", SchemaType.INT),
            Schema.pair("bool", SchemaType.BOOL)),
        Schema.pair("test", SchemaType.BOOL)).build();

    schema.save(Path.of("./testSchema.txt"));
    final ConfigData data = Configurate.yaml(Path.of("config.yml"), schema);

    final String str = data.getString("example.string");
    System.out.println(str);
  }
}
