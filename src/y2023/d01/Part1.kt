package y2023.d01

import firstDigit
import inputLines
import lastDigit
import println

fun part1() {
    inputLines("y2023/d01/input")
        .sumOf { it.firstDigit() * 10 + it.lastDigit() }
        .println()
}

