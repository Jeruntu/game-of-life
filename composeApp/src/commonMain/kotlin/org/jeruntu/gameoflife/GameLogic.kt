package org.jeruntu.gameoflife

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GameLogic(
    private val gridSize: Int,
) {
    private val _grid = MutableStateFlow(Array(gridSize) { Array(gridSize) { false } })
    val grid: StateFlow<Array<Array<Boolean>>> = _grid

    private var simulationJob: Job? = null // Keeps track of the simulation coroutine

    fun toggleCell(x: Int, y: Int) {
        val updatedGrid = _grid.value.map { it.copyOf() }.toTypedArray()
        updatedGrid[y][x] = !updatedGrid[y][x]
        _grid.value = updatedGrid
    }

    fun nextGeneration() {
        val currentGrid = _grid.value
        val updatedGrid = Array(currentGrid.size) { Array(currentGrid.size) { false } }

        for (y in currentGrid.indices) {
            for (x in currentGrid[y].indices) {
                val liveNeighbors = countLiveNeighbors(currentGrid, x, y)
                updatedGrid[y][x] = when {
                    currentGrid[y][x] && liveNeighbors in 2..3 -> true  // Survive
                    !currentGrid[y][x] && liveNeighbors == 3 -> true   // Born
                    else -> false                                     // Die
                }
            }
        }

        _grid.value = updatedGrid
    }

    private fun countLiveNeighbors(grid: Array<Array<Boolean>>, x: Int, y: Int): Int {
        var count = 0
        for (dx in -1..1) {
            for (dy in -1..1) {
                if (dx == 0 && dy == 0) continue // Skip the current cell
                val nx = x + dx
                val ny = y + dy
                if (nx in grid.indices && ny in grid.indices && grid[ny][nx]) {
                    count++
                }
            }
        }
        return count
    }

       // Start the simulation loop
    fun startSimulation() {
        if (simulationJob == null) {
            simulationJob = CoroutineScope(Dispatchers.Default).launch {
                while (isActive) {
                    nextGeneration()
                    delay(1000L) // Wait 1 second before generating the next state
                }
            }
        }
    }

    // Stop the simulation loop
    fun stopSimulation() {
        simulationJob?.cancel()
        simulationJob = null
    }
}
