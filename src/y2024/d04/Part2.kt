package y2024.d04

import inputLines
import println


fun List<List<Char>>.hasMasXAt(x: Int, y: Int): Boolean {
    try {
        if (this[y][x] != 'A') return false

        if (this[y - 1][x - 1] == 'M') {
            // top/left is M, so check down/right for S
            if (this[y + 1][x + 1] != 'S') {
                return false
            }
        } else if (this[y - 1][x - 1] == 'S') {
            // else it can still be backwards on that diagonal
            // top/left is S, so check down/right for M
            if (this[y + 1][x + 1] != 'M') {
                return false
            }
        } else {
            return false
        }

        if (this[y - 1][x + 1] == 'M') {
            // top/right is M, so check down/left for S
            if (this[y + 1][x - 1] != 'S') {
                return false
            }
        } else if (this[y - 1][x + 1] == 'S') {
            // else it can still be backwards on that diagonal
            // top/right is S, so check down/left for M
            if (this[y + 1][x - 1] != 'M') {
                return false
            }
        } else {
            return false
        }
        return true
    } catch (e: IndexOutOfBoundsException) {
        return false
    }
}

fun part2() {
    val lines = inputLines("y2024/d04/input")
    val grid = lines.map {
        it.split("").mapNotNull { it.firstOrNull() }
    }
    var sum = 0
    for (y in grid.indices) {
        val row = grid[y]
        for (x in row.indices) {
            sum += grid.hasMasXAt(x, y).toInt()
        }
    }
    sum.println("Solution")
}

