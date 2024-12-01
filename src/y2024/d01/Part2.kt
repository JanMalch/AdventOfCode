package y2024.d01

import inputLines
import println
import toPair

fun part2() {
    val lines = inputLines("y2024/d01/input")
    val pairs = lines.map { line -> line.split(' ').mapNotNull { it.trim().toIntOrNull(10) }.toPair() }
    val left = mutableListOf<Int>()
    val right = mutableListOf<Int>()
    for (pair in pairs) {
        left += pair.first
        right += pair.second
    }
    val amountInRight = mutableMapOf<Int, Int>()
    for (r in right) {
        amountInRight.compute(r) { _, v -> (v ?: 0) + 1 }
    }
    var sum = 0
    for (l in left) {
        sum += l * (amountInRight[l] ?: 0)
    }
    sum.println(note = "Solution")
}

