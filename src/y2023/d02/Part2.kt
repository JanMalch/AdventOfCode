package y2023.d02

import inputLines
import println
import y2023.d02.Game.Draw

private fun Game.requirements(): Draw {
    var redMax = 0
    var greenMax = 0
    var blueMax = 0
    for (draw in draws) {
        if (draw.red > redMax) redMax = draw.red
        if (draw.green > greenMax) greenMax = draw.green
        if (draw.blue > blueMax) blueMax = draw.blue
    }
    return Draw(blue = blueMax, red = redMax, green = greenMax)
}

private fun Draw.power(): Int = red * blue * green

fun part2() {
    val lines = inputLines("y2023/d02/input")
    lines.map { Game.valueOf(it).requirements() }
        .sumOf { it.power() }
        .println("Solution")
}

