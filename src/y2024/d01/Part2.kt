package y2024.d01

import MutableCounter
import checkEquals
import inputLines
import println
import toPair

fun part2() {
    val lines = inputLines("y2024/d01/input")
    val left = mutableListOf<Int>()
    val countsInRight = MutableCounter<Int>()
    for (line in lines) {
        val (l, r) = line.split(Regex("\\s+")).toPair()
        left += l.toInt(10)
        countsInRight += r.toInt(10)
    }
    left.sorted()
        .sumOf { it * countsInRight[it] }
        .checkEquals(24643097)
        .println(note = "Solution")
}

