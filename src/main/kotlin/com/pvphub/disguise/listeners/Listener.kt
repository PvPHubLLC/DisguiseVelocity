package com.pvphub.disguise.listeners

import com.pvphub.disguise.DisguiseVelocity
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.player.GameProfileRequestEvent

class Listener(private val plugin: DisguiseVelocity) {

    @Subscribe
    fun onRequestGameProfile(e: GameProfileRequestEvent) {
        val results = plugin.disguised.filter { v -> v.key == e.originalProfile.id }
        if (results.isEmpty()) {
            return
        }
        val player = results.keys.toList()[0]
        val gameProfile = results[player]

        e.gameProfile = gameProfile
    }

}