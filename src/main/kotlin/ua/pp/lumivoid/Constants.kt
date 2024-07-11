package ua.pp.lumivoid

import io.wispforest.owo.network.OwoNetChannel
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.SharedConstants
import net.minecraft.util.Identifier
import org.slf4j.LoggerFactory

object Constants {
    const val MOD_ID = "redstone-helper"
    const val MOD_MODRINTH_ID = "cwYR2Bh1"
    @Suppress("DEPRECATION") const val MINECRAFT_VERSION = SharedConstants.VERSION_NAME
    val MOD_VERSION = FabricLoader.getInstance().getModContainer(MOD_ID).get().metadata.version.toString()
    val LOGGER = LoggerFactory.getLogger(MOD_ID)!!
    val NET_CHANNEL: OwoNetChannel = OwoNetChannel.create(Identifier.of(MOD_ID, "main"))
    val aMinecraftClass: Identifier = Identifier.of(MOD_ID, "main")
    val TOAST_ID = Identifier.of(MOD_ID, "toast")
    val CONFIG_FOLDER_PATH = "${System.getProperty("user.dir")}\\config\\${MOD_ID}"
    //URLS
    const val MODRINTH_API_URL = "https://api.modrinth.com/v2/project/${MOD_MODRINTH_ID}/version"
    const val GITHUB_MANUAL_OWO_LIB_URL = "https://github.com/Bumer-32/Redstone-Helper/blob/main/doc/owo%20lib.png?raw=true"
}
