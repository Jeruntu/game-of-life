package org.jeruntu.gameoflife

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        GameOfLifeScreen(GameLogic(20))
    }
}
