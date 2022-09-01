package com.pvphub.disguise.util

import com.pvphub.disguise.DisguiseVelocity
import org.simpleyaml.configuration.file.YamlConfiguration
import java.io.File

object Config {
    operator fun get(path: String, def: String?, plugin: DisguiseVelocity): YamlConfiguration {
        val file = File(path)
        if (!file.exists()) {
            try {
                if (def != null) {
                    plugin.saveResource(def, false)
                } else {
                    file.createNewFile()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return YamlConfiguration.loadConfiguration(file)
    }
}