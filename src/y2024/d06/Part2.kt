package y2024.d06

import Grid
import area
import cells
import checkEquals
import copyWith
import inputLines
import println
import toCharGrid


private fun hasLoop(grid: Grid<Char>): Boolean {
    val map = Map(grid)
    val area = grid.area
    var hasLoop = false
    try {
        map.go { step ->
            // taking a lot of steps, is a good heuristic for detecting loop, lol
            (step > area * 2).also {
                hasLoop = it
            }
        }
    } catch (e: StopWalkException) {
        // ok
    }
    return hasLoop
}

fun part2() {
    val lines = inputLines("y2024/d06/input")
    val grid = lines.toCharGrid()

    val variations = mutableSetOf<Grid<Char>>()

    for (cell in grid.cells()) {
        if (cell.value == '.') {
            variations += grid.copyWith(cell.x, cell.y, '#')
        }
    }

    variations.size.checkEquals(16060).println("vars")
    variations.count { hasLoop(it) }.checkEquals(1602).println("Solution")

}

