package com.pvphub.disguise.util

import com.pvphub.disguise.DisguiseVelocity
import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.proxy.server.RegisteredServer
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.simpleyaml.configuration.file.FileConfiguration
import java.util.regex.Pattern

object VelocityChat {
    private val serializer = LegacyComponentSerializer.builder().character('&').hexCharacter('#').hexColors().build()

    fun translatableList(path: String, config: FileConfiguration = DisguiseVelocity.instance.config): List<String> {
        if (config.getStringList(path).size > 0) {
            return config.getStringList(path)
        }
        return config.getString(path, null)?.let { listOf(it) } ?: listOf()
    }

    fun translatable(path: String, default: String? = null, config: FileConfiguration = DisguiseVelocity.instance.config): String? {
        return config.getString(path, default)
    }

    fun color(s: String, p: Player, server: RegisteredServer?): TextComponent {
        var s = s
        if (server != null) {
            s = s.replace("%server-name%", server.serverInfo.name)
                .replace("%server-connected%", Integer.toString(server.playersConnected.size))
                .replace("%server-online%", Integer.toString(server.playersConnected.size))
                .replace("%server-players%", Integer.toString(server.playersConnected.size))
        }
        return color(s, p)
    }

    fun clearChat(p: Player) {
        for (i in 0..99) {
            p.sendMessage(Component.text(""))
        }
    }

    fun color(s: String, p: Player): TextComponent {
        var s = s
        s = format(s, p)
        return serializer.deserialize(s)
    }

    fun color(s: String): TextComponent {
        var s = s
        s = format(s, null)
        return serializer.deserialize(s)
    }

    fun getSerializer(): LegacyComponentSerializer {
        return serializer
    }

    fun format(s: String, p: Player?): String {
        var s = s
        p?.let {
            s = s.replace("%player%", p.username)
                .replace("%username%", p.username)
        }
        return s
    }

    private val pattern = Pattern.compile("&#[a-f0-9]{6}|&[a-f0-9k-o]|&r", Pattern.CASE_INSENSITIVE)
    fun strip(s: String): String? {
        var s = s
        var match = pattern.matcher(s)
        while (match.find()) {
            val color = s.substring(match.start(), match.end())
            s = s.replace(color, "")
            match = pattern.matcher(s)
        }
        return s
    }
}