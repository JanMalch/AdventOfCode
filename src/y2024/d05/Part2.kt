package y2024.d05

import inputLines
import println

fun Update.fix(comparator: RuleBasedComparator): Update = Update(pages.sortedWith(comparator))

fun part2() {
    val lines = inputLines("y2024/d05/input")
    val rules = Rules(lines.filter { '|' in it }.map { Rule.valueOf(it) })
    val comparator = RuleBasedComparator(rules)
    val updates = lines.filter { ',' in it }.map { Update.valueOf(it) }
    updates.filterNot { it.isValid(comparator) }
        .map { it.fix(comparator) }
        .sumOf { it.middle }
        .println("Solution")
}

