package com.pvphub.disguise.util

import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import java.io.*
import java.nio.file.Path
import java.util.logging.Logger
import kotlin.io.path.absolutePathString
import kotlin.io.path.pathString

open class VelocityPlugin(
    val server: ProxyServer,
    val logger: Logger,
    val dataDirectory: Path
) {

    fun saveResource(resourcePath: String, replace: Boolean) {
        require(resourcePath != "") { "ResourcePath cannot be null or empty" }
        val resourcePath = resourcePath.replace('\\', '/')
        val `in` = javaClass.classLoader.getResourceAsStream(resourcePath)
            ?: throw IllegalArgumentException("The embedded resource '$resourcePath' cannot be found")
        val outFile = File(this.dataDirectory.absolutePathString(), resourcePath)
        val lastIndex = resourcePath.lastIndexOf('/')
        val outDir = File(this.dataDirectory.absolutePathString(), resourcePath.substring(0, if (lastIndex >= 0) lastIndex else 0))
        if (!outDir.exists()) {
            outDir.mkdirs()
        }
        try {
            if (!outFile.exists() || replace) {
                val out: OutputStream = FileOutputStream(outFile)
                val buf = ByteArray(1024)
                var len: Int
                while (`in`.read(buf).also { len = it } > 0) {
                    out.write(buf, 0, len)
                }
                out.close()
                `in`.close()
            } else {
            }
        } catch (ex: IOException) {
        }
    }

    fun getResource(filename: String): InputStream? {
        return try {
            val url = javaClass.getResource(filename) ?: return null
            val connection = url.openConnection()
            connection.useCaches = false
            connection.getInputStream()
        } catch (ex: IOException) {
            null
        }
    }
}