dependencies {
    compileOnly("org.spigotmc:spigot-api:1.21.8-R0.1-SNAPSHOT")
    //compileOnly("dev.jorel:commandapi-bukkit-shade:10.1.2")
    implementation(project(":core"))
}

extensions.extraProperties["moduleName"] = "commandapi-common"