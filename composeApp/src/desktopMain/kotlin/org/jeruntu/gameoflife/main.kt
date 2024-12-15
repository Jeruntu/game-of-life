package org.jeruntu.gameoflife

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Game Of Life Multi Platform",
    ) {
        App()
    }
}