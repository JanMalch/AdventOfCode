package y2024.d04

import inputLines
import println
import toInt

fun List<List<Char>>.walk(x: Int, y: Int, require: Char, contX: Int, contY: Int): Boolean {
    try {
        if (this[y][x] != require) return false
        // equals require at this point, so if it's last char, we can exit the walk
        if (require == 'S') return true
        return walk(
            x + contX, y + contY, when (require) {
                'X' -> 'M'
                'M' -> 'A'
                'A' -> 'S'
                else -> error("Cannot continue")
            }, contX, contY
        )
    } catch (e: IndexOutOfBoundsException) {
        return false
    }
}

fun List<List<Char>>.countXmasAt(x: Int, y: Int): Int {
    if (this[y][x] != 'X') return 0
    var sum = 0
    sum += walk(x, y, 'X', 1, 0).toInt() // horizontal forwards
    sum += walk(x, y, 'X', -1, 0).toInt() // horizontal backwards

    sum += walk(x, y, 'X', 0, 1).toInt() // vertical forwards
    sum += walk(x, y, 'X', 0, -1).toInt() // vertical backwards

    sum += walk(x, y, 'X', 1, 1).toInt() // down/right
    sum += walk(x, y, 'X', 1, -1).toInt() // up/right
    sum += walk(x, y, 'X', -1, 1).toInt() // down/left
    sum += walk(x, y, 'X', -1, -1).toInt() // up/left

    return sum
}

fun part1() {
    val lines = inputLines("y2024/d04/input")
    val grid = lines.map {
        it.split("").mapNotNull { it.firstOrNull() }
    }
    var sum = 0
    for (y in grid.indices) {
        val row = grid[y]
        for (x in row.indices) {
            sum += grid.countXmasAt(x, y)
        }
    }
    sum.println("Solution")
}

