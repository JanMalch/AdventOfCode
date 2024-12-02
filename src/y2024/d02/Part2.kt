package y2024.d02

import inputLines
import println

fun List<Int>.subsetsOneRemoved(): List<List<Int>> {
    val subsets = mutableListOf<List<Int>>()
    for (index in indices) {
        subsets += toMutableList().apply { removeAt(index) }
    }
    return subsets
}

fun part2() {
    val lines = inputLines("y2024/d02/input")
    val reports = lines.map { line -> line.split(" ").map { it.toInt() } }
    reports.count { report ->
        report.isSafe() || report.subsetsOneRemoved().any { it.isSafe() }
    }.println("Solution")
}

