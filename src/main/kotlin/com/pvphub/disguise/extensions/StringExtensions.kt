package com.pvphub.disguise.extensions

import com.pvphub.disguise.util.VelocityChat
import com.pvphub.disguise.util.VelocityPlugin
import com.velocitypowered.api.proxy.Player
import net.kyori.adventure.text.TextComponent

fun String.translatable(player: Player? = null) : String? {
    return VelocityChat.translatable(this)?.format(player)
}

fun String.translatableList(player: Player? = null) : List<String> {
    return VelocityChat.translatableList(this).map { it.format(player) }
}

fun List<String>.toComponents() : List<TextComponent> {
    return this.map { it.toComponent() }
}

fun String.toComponent() : TextComponent {
    return VelocityChat.color(this)
}

fun String.format(player: Player?) : String {
    return VelocityChat.format(this, player)
}