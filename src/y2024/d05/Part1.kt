package y2024.d05

import inputLines
import itemAtCenter
import println
import reverseGrouping

data class Rule(
    val page: Int,
    val beforePage: Int
) {
    override fun toString(): String {
        return "$page|$beforePage"
    }

    companion object {
        fun valueOf(value: String): Rule {
            val (a, b) = value.split('|')
            return Rule(
                page = a.trim().toInt(),
                beforePage = b.trim().toInt(),
            )
        }
    }
}

data class Update(
    val pages: List<Int>
) {
    val middle: Int = pages.itemAtCenter()

    override fun toString(): String {
        return pages.joinToString(separator = ",")
    }

    companion object {
        fun valueOf(value: String): Update {
            return Update(value.split(',').map { it.toInt() })
        }
    }
}

data class Rules(
    private val rules: List<Rule>
) {
    private val afters: Map<Int, List<Int>> =
        rules.groupBy(keySelector = { it.page }, valueTransform = { it.beforePage })

    private val befores: Map<Int, List<Int>> = afters.reverseGrouping()

    operator fun get(page: Int): Pair<List<Int>, List<Int>> = befores[page].orEmpty() to afters[page].orEmpty()
}

class RuleBasedComparator(
    private val rules: Rules
) : Comparator<Int> {
    override fun compare(a: Int, b: Int): Int {
        val (befores, afters) = rules[a]
        if (b in afters) {
            return -1
        }
        if (b in befores) {
            return 1
        }
        return 0
    }
}

fun Update.isValid(comparator: RuleBasedComparator): Boolean = pages == pages.sortedWith(comparator)

fun part1() {
    val lines = inputLines("y2024/d05/input")
    val rules = Rules(lines.filter { '|' in it }.map { Rule.valueOf(it) })
    val comparator = RuleBasedComparator(rules)
    val updates = lines.filter { ',' in it }.map { Update.valueOf(it) }
    updates.filter { it.isValid(comparator) }.sumOf { it.middle }.println("Solution")
}

