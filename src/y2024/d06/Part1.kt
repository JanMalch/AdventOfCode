package y2024.d06

import inputLines
import println

data class Vec2(val x: Int, val y: Int) {
    operator fun plus(other: Vec2): Vec2 = Vec2(x + other.x, y + other.y)
}

enum class Facing(val step: Vec2) {
    UP(Vec2(0, -1)), DOWN(Vec2(0, 1)), LEFT(Vec2(-1, 0)), RIGHT(Vec2(1, 0))
}

class Map(private val chars: List<MutableList<Char>>) {

    private var steps = 0
    private var facing: Facing
    private var position = kotlin.run {
        for (y in chars.indices) {
            val row = chars[y]
            for (x in row.indices) {
                when (row[x]) {
                    '^', 'v', '<', '>' -> {
                        facing = when (row[x]) {
                            '^' -> Facing.UP
                            'v' -> Facing.DOWN
                            '>' -> Facing.RIGHT
                            '<' -> Facing.LEFT
                            else -> error("Failed to determine initial facing direction")
                        }
                        row[x] = '.'
                        return@run Vec2(x, y)
                    }
                }
            }
        }
        throw IllegalStateException("Guard not found")
    }
    private val visited = mutableSetOf(position)

    private fun next(): Boolean {
        val nextPos = position + facing.step
        val ahead = try {
            chars[nextPos.y][nextPos.x]
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
        when (ahead) {
            '.' -> {
                position = nextPos
                visited += position
                steps++
                return true
            }

            '#' -> {
                facing = when (facing) {
                    Facing.UP -> Facing.RIGHT
                    Facing.DOWN -> Facing.LEFT
                    Facing.LEFT -> Facing.UP
                    Facing.RIGHT -> Facing.DOWN
                }
                return true
            }

            else -> throw IllegalStateException("Unknown thing ahead: '${ahead}'")
        }
    }

    fun go(cb: (Int) -> Boolean = { false }): Int {
        while (next()) {
            if (cb(steps)) {
                throw IllegalStateException("Callback told to exit after $steps steps.")
            }
        }
        return visited.size
    }
}

fun part1() {
    val lines = inputLines("y2024/d06/input")
    val map = Map(lines.map { line -> line.toCharArray().toMutableList() })
    map.go().println("Solution")
}

