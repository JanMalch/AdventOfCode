package y2023.d01

import inputLines
import println

val words = listOf(
    "one",
    "two",
    "three",
    "four",
    "five",
    "six",
    "seven",
    "eight",
    "nine",
)

fun String.findDigitOrWord(fromFront: Boolean): Int {
    var result: Int? = null
    outer@ for (i in if (fromFront) indices else indices.reversed()) {
        if (this[i].isDigit()) {
            result = this[i].digitToInt()
            break
        }
        for ((index, word) in words.withIndex()) {
            if (this.substring(i).startsWith(word)) {
                result = index + 1
                break@outer
            }
        }
    }
    return checkNotNull(result) { "Failed to find any digit in $this." }
}

fun part2() {
    inputLines("y2023/d01/input")
        .sumOf { it.findDigitOrWord(fromFront = true) * 10 + it.findDigitOrWord(fromFront = false) }
        .println(note = "Solution")
}

