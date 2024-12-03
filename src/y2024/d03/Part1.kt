package y2024.d03

import inputLines
import println

fun validMultResults(line: String): Sequence<Int> {
    return Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)").findAll(line)
        .map { match ->
            match.groupValues[1].toInt() * match.groupValues[2].toInt()
        }
}

fun part1() {
    val lines = inputLines("y2024/d03/input")
    lines.sumOf { validMultResults(it).sum() }.println("Solution")
}

