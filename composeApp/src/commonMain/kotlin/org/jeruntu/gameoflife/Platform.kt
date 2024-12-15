package org.jeruntu.gameoflife

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform