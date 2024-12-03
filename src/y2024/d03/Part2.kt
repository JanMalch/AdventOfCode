package y2024.d03

import checkEquals
import inputLines
import println

fun part2() {
    val lines = inputLines("y2024/d03/input")
    val results = mutableListOf<Int>()
    var enabled = true
    for (line in lines) {
        for (i in line.indices) {
            when (line[i]) {
                'd' -> {
                    if (line.startsWith("do()", i)) {
                        enabled = true
                    } else if (line.startsWith("don't()", i)) {
                        enabled = false
                    }
                }

                'm' -> {
                    if (!enabled) continue
                    val subject = line.substring(i)
                    val match = Regex("^mul\\((\\d{1,3}),(\\d{1,3})\\)").find(subject)
                    if (match != null) {
                        results += match.groupValues[1].toInt() * match.groupValues[2].toInt()
                    }
                }
            }
        }
    }
    results.sum()
        .checkEquals(94455185)
        .println("Solution")
}

