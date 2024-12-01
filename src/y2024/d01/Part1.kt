package y2024.d01

import checkEquals
import inputLines
import println
import toPair
import zipStrict
import kotlin.math.absoluteValue

fun part1() {
    val lines = inputLines("y2024/d01/input")
    val left = mutableListOf<Int>()
    val right = mutableListOf<Int>()
    for (line in lines) {
        val (l, r) = line.split(Regex("\\s+")).toPair()
        left += l.toInt(10)
        right += r.toInt(10)
    }

    (left.sorted() zipStrict right.sorted())
        .fold(0) { acc, (l, r) -> acc + (l - r).absoluteValue }
        .checkEquals(2769675)
        .println(note = "Solution")
}

