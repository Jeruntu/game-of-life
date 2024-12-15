package org.jeruntu.gameoflife

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun GameOfLifeScreen(gameState: GameLogic) {
    val gridState = gameState.grid.collectAsState() // Observe grid updates

    val isSimulationActive = remember { mutableStateOf(false) }

    Column {
        gridState.value.forEachIndexed { y, row ->
            Row {
                row.forEachIndexed { x, cell ->
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(if (cell) Color.Black else Color.White)
                            .border(1.dp, Color.Gray)
                            .clickable { gameState.toggleCell(x, y) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(onClick = { gameState.nextGeneration() }) {
                Text("Next Generation")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (!isSimulationActive.value) {
                        isSimulationActive.value = true
                        gameState.startSimulation()
                    } else {
                        isSimulationActive.value = false
                        gameState.stopSimulation()
                    }
                }
            ) {
                if (!isSimulationActive.value) {
                    Text("Start Simulation")
                } else {
                    Text("Stop Simulation")
                }
            }
        }
    }
}
