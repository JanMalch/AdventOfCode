package y2024.d06

import inputLines
import println
import printsep


private fun hasLoop(grid: List<MutableList<Char>>): Boolean {
    val map = Map(grid)
    val area = grid.size * grid[0].size
    var hasLoop = false
    try {
        map.go { step ->
            // taking a lot of steps, is a good heuristic for detecting loop, lol
            (step > area * 2).also {
                hasLoop = it
            }
        }
    } catch (e: IllegalStateException) {
        // ok
    }
    return hasLoop
}

fun part2() {
    val lines = inputLines("y2024/d06/input")
    val grid = lines.map { line -> line.toCharArray().toList() }

    val variations = mutableSetOf<List<MutableList<Char>>>()

    for (y in grid.indices) {
        val row = grid[y]
        for (x in row.indices) {
            val cell = row[x]
            if (cell == '.') {
                variations += grid.mapIndexed { index, mRow ->
                    mRow.toMutableList().apply {
                        if (index == y) {
                            this@apply[x] = '#'
                        }
                    }
                }
            }
        }
    }

    variations.size.println("vars")
    printsep()
    variations.count { hasLoop(it) }.println("Solution")

}

