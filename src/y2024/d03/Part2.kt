package y2024.d03

import inputLines
import println

fun part2() {
    val lines = inputLines("y2024/d03/input")
    val results = mutableListOf<Int>()
    var enabled = true
    for (line in lines) {
        for (i in 0..<line.length) {
            val subject = line.substring(i)
            // TODO: skip over
            if (subject.startsWith("do()")) {
                enabled = true
            } else if (subject.startsWith("don't()")) {
                enabled = false
            } else if (enabled) {
                val match = Regex("^mul\\((\\d{1,3}),(\\d{1,3})\\)").find(subject)
                if (match != null) {
                    results += match.groupValues[1].toInt() * match.groupValues[2].toInt()
                }
            }
        }
    }
    results.sum().println("Solution")
}

