package y2024.d01

import inputLines
import println
import toPair

fun part1() {
    val lines = inputLines("y2024/d01/input")
    val pairs = lines.map { line -> line.split(' ').mapNotNull { it.trim().toIntOrNull(10) }.toPair() }
    val left = mutableListOf<Int>()
    val right = mutableListOf<Int>()
    for (pair in pairs) {
        left += pair.first
        right += pair.second
    }
    left.sort()
    right.sort()
    var sum = 0
    for ((i, l) in left.withIndex()) {
        val r = right[i]
        if (l < r) {
            sum += r - l
        } else {
            sum += l - r
        }
    }
    sum.println(note = "Solution")
}

