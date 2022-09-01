package com.pvphub.disguise.command

import com.pvphub.disguise.DisguiseVelocity
import com.pvphub.disguise.extensions.toComponent
import com.pvphub.disguise.extensions.toComponents
import com.pvphub.disguise.extensions.translatable
import com.pvphub.disguise.extensions.translatableList
import com.velocitypowered.api.command.SimpleCommand
import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.util.GameProfile
import java.util.*

class DisguiseCommand(private val plugin: DisguiseVelocity) : SimpleCommand {
    override fun execute(invocation: SimpleCommand.Invocation?) {
        if (invocation?.source() !is Player) {
            "messages.player-only"
                .translatableList()
                .toComponents()
                .forEach { invocation?.source()?.sendMessage(it) }
            return
        }
        val player = invocation.source() as Player
        val args = invocation.arguments()
        if (args.isNotEmpty()) {
            when(args[0].lowercase()) {
                "random" -> {
                    // Generate a random UUID and username
                    val uuid = UUID.randomUUID()
                    // temp we need a better way of doing this
                    val username = UUID.randomUUID().toString().replace("-", "")
                    // todo: Skin textures (mojang api or namemc?)
                    val gameProfile = GameProfile(uuid, username, mutableListOf())
                    plugin.disguised[player] = gameProfile
                }
                "set" -> {
                    if (args.size <= 1) {
                        // player has provided a specific username to disguise as
                        // skin shit
                        // make profile ting
                        return
                    }
                }
                "stop", "revert", "cancel", "exit" -> {
                    // Set back to normal
                    plugin.disguised.remove(player)
                    /**
                     * We somehow need to forcefully update this player's game profile
                     * for themselves and all online players.
                     * We need to make sure the player can still behave normally in terms of permissions
                     * on backend servers.
                     *
                     * "Fake Disconnect" the player and simulate the player join server event,
                     * using the disguised game profile ?
                     *
                     * Marked as TODO
                     */

                    // disconnect from backend (while sending keep alive)

                    // reconnect with this new profile ?
                }
            }
        } else {

        }
    }

    override fun suggest(invocation: SimpleCommand.Invocation?): MutableList<String> {
        return super.suggest(invocation)
    }

}