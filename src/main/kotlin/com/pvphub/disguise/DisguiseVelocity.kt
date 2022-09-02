package com.pvphub.disguise

import com.pvphub.disguise.command.DisguiseCommand
import com.pvphub.disguise.util.Config
import com.pvphub.disguise.util.VelocityPlugin
import com.velocitypowered.api.command.CommandMeta
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
import com.google.inject.Inject
import com.pvphub.disguise.listeners.Listener
import java.util.UUID
import kotlin.io.path.absolutePathString

@Plugin(
    id = "disguise",
    name = "DisguiseVelocity",
    version = "1.0",
    description = "Allow your players to hide themselves as other players!",
    authors = ["MattMX"]
)
class DisguiseVelocity @Inject constructor(
    server: ProxyServer,
    logger: Logger,
    @DataDirectory
    dataDirectory: Path
) : VelocityPlugin(server, logger, dataDirectory) {

    val config = Config["${dataDirectory.absolutePathString()}/config.yml", "config.yml", this];
    val disguised = hashMapOf<UUID, GameProfile>()

    init {
        instance = this
    }

    @Subscribe
    fun onProxyInitializeEvent(e: ProxyInitializeEvent) {
        server.commandManager.register("disguise",DisguiseCommand(this))
        server.eventManager.register(this,Listener(this))
    }

    fun version() : String {
        return (this::class.annotations.first { it is Plugin } as Plugin).version
    }

    companion object {
        lateinit var instance: DisguiseVelocity
    }

}