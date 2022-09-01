package com.pvphub.disguise

import com.pvphub.disguise.util.Config
import com.pvphub.disguise.util.VelocityPlugin
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.util.GameProfile
import org.simpleyaml.configuration.file.FileConfiguration
import java.io.File
import java.nio.file.Path
import java.util.logging.Logger
import javax.inject.Inject
import kotlin.io.path.absolutePathString

@Plugin(
    id = "disguise",
    name = "DisguiseVelocity",
    version = "1.0",
    description = "Allow your players to hide themselves as other players!",
    authors = ["MattMX"]
)
class DisguiseVelocity(
    server: ProxyServer,
    logger: Logger,
    @DataDirectory
    dataDirectory: Path
) : VelocityPlugin(server, logger, dataDirectory) {

    val config = Config["${dataDirectory.absolutePathString()}/config.yml", "config.yml", this];
    val disguised = hashMapOf<Player, GameProfile>()

    init {
        instance = this
    }

    @Subscribe
    fun onProxyInitializeEvent(e: ProxyInitializeEvent) {

    }

    fun version() : String {
        return (this::class.annotations.first { it is Plugin } as Plugin).version
    }

    companion object {
        lateinit var instance: DisguiseVelocity
    }

}