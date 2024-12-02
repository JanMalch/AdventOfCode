package y2024.d02

import inputLines
import println
import kotlin.math.absoluteValue

fun List<Int>.isSafe(): Boolean {
    if (this != sorted() && this != sortedDescending()) {
        return false
    }
    var prev: Int = first()
    for (el in this.drop(1)) {
        val diff = el - prev
        if (diff.absoluteValue < 1 || diff.absoluteValue > 3) {
            return false
        }
        prev = el
    }
    return true
}

fun part1() {
    val lines = inputLines("y2024/d02/input")
    val reports = lines.map { line -> line.split(" ").map { it.toInt() } }
    reports.count { it.isSafe() }.println("Solution")
}

