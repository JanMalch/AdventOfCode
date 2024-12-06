package y2024.d06

import Grid
import checkEquals
import firstOrNull
import inputLines
import println
import toCharGrid
import y2024.Direction

enum class Facing(val step: Direction) {
    UP(Direction.UP), DOWN(Direction.DOWN), LEFT(Direction.LEFT), RIGHT(Direction.RIGHT);

    companion object {
        fun byFace(face: Char): Facing = when (face) {
            '^' -> UP
            'v' -> DOWN
            '>' -> RIGHT
            '<' -> LEFT
            else -> throw IllegalArgumentException("'$face' is not a valid face.")
        }
    }
}

private fun Char.isFree(): Boolean = this == '.'
private fun Char.isObstacle(): Boolean = this == '#'
private fun Char.isFace(): Boolean = when (this) {
    '^', 'v', '<', '>' -> true
    else -> false
}

class Map(private val grid: Grid<Char>) {

    private var steps = 0
    private lateinit var facing: Facing
    private var position = grid.firstOrNull { it.isFace() }
        ?.also { facing = Facing.byFace(it.value) }
        ?.position
        ?: throw IllegalStateException("Guard not found")
    private val visited = mutableSetOf(position)

    private fun next(): Boolean {
        val nextPos = position + facing.step.vec
        val ahead = try {
            grid[nextPos]
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
        when {
            ahead.isFree() || ahead.isFace() -> {
                position = nextPos
                visited += position
                steps++
                return true
            }

            ahead.isObstacle() -> {
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

    @Throws(StopWalkException::class)
    fun go(stopWalking: (Int) -> Boolean = { false }): Int {
        while (next()) {
            if (stopWalking(steps)) {
                throw StopWalkException(steps)
            }
        }
        return visited.size
    }
}

class StopWalkException(steps: Int) : RuntimeException("Callback told to exit after $steps steps.")

fun part1() {
    val lines = inputLines("y2024/d06/input")
    val map = Map(lines.toCharGrid())
    map.go().checkEquals(4722).println("Solution")
}

