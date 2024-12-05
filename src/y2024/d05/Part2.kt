package y2024.d05

import inputLines
import println

fun Update.fix(rules: Rules, comparator: RulesComparator): Update {
    return Update(pages.sortedWith(comparator)).also {
        check(it.isValid(rules)) {
            "$it is not a valid fix for $this."
        }
    }
}

data class RulesComparator(
    private val rules: Rules
) : Comparator<Int> {
    override fun compare(a: Int, b: Int): Int {
        val afters = rules.getAfters(a)
        val befores = rules.getBefores(a)
        if (b in afters) {
            return -1
        }
        if (b in befores) {
            return 1
        }
        return 0
    }
}

fun part2() {
    val lines = inputLines("y2024/d05/input")
    val rules = Rules(lines.takeWhile { '|' in it }.map { Rule.valueOf(it) })
    val comparator = RulesComparator(rules)
    val updates = lines.dropWhile { ',' !in it }.map { Update.valueOf(it) }
    updates.filterNot { it.isValid(rules) }
        .map { it.fix(rules, comparator) }
        .sumOf { it.middle }
        .println("Solution")
}

