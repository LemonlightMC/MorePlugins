dependencies {
    compileOnly("org.spigotmc:spigot-api:1.21.8-R0.1-SNAPSHOT")
    //compileOnly("com.github.Traqueur-dev:RecipesAPI:2.0.2")
    //compileOnly("dev.jorel:commandapi-bukkit-shade:10.1.2")
    //compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    compileOnly("net.milkbowl.vault:VaultUnlockedAPI:2.15")
    implementation(project(":core"))
}

extensions.extraProperties["moduleName"] = "Integrations"