package y2023.d02

import inputLines
import println
import toPair

data class Game(
    val id: Int,
    val draws: List<Draw>,
) {

    data class Draw(
        val blue: Int,
        val red: Int,
        val green: Int,
    )

    companion object {
        fun valueOf(line: String): Game {
            val id = line.drop(5).substringBefore(':').toInt(10)
            val draws = line.substringAfter(':').split(';')
                .map { drawStr ->
                    var blue = 0
                    var red = 0
                    var green = 0
                    for (colorDraw in drawStr.split(',')) {
                        val (amountStr, colorStr) = colorDraw.trim().split(' ').toPair()
                        when (colorStr.trim()) {
                            "blue" -> blue = amountStr.toInt(10)
                            "red" -> red = amountStr.toInt(10)
                            "green" -> green = amountStr.toInt(10)
                        }
                    }
                    Draw(blue, red, green)
                }
            return Game(id, draws)
        }
    }
}

fun Game.isPossible(
    redMax: Int = 12,
    greenMax: Int = 13,
    blueMax: Int = 14,
): Boolean {
    for (draw in draws) {
        if (draw.red > redMax) return false
        if (draw.green > greenMax) return false
        if (draw.blue > blueMax) return false
    }
    return true
}

fun part1() {
    val lines = inputLines("y2023/d02/input")
    val games = lines.map { Game.valueOf(it) }
    val possibleGames = games.filter { it.isPossible() }
    possibleGames.sumOf { it.id }.println(note = "Solution")
}

