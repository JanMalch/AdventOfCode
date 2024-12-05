package y2024.d05

import inputLines
import println

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
    val middle: Int = pages[pages.lastIndex / 2]

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

    private val befores: Map<Int, List<Int>> = kotlin.run {
        val map = mutableMapOf<Int, MutableList<Int>>()
        for (rule in rules) {
            map.compute(rule.beforePage) { _, afters ->
                (afters ?: mutableListOf()).apply {
                    add(rule.page)
                }
            }
        }
        return@run map
    }

    fun getAfters(page: Int): List<Int> = afters[page].orEmpty()
    fun getBefores(page: Int): List<Int> = befores[page].orEmpty()

    fun validate(page: Int, isAfter: List<Int>): Boolean {
        if (isAfter.isEmpty()) return true

        for (afterPage in isAfter) {
            val aftersOfAfterPage = afters[afterPage].orEmpty()
            if (page !in aftersOfAfterPage) {
                return false
            }
        }
        return true
    }
}

fun Update.isValid(rules: Rules): Boolean {
    for (index in pages.indices) {
        val page = pages[index]
        val after = pages.take(index)
        if (!rules.validate(page, isAfter = after)) {
            return false
        }
    }
    return true
}

fun part1() {
    val lines = inputLines("y2024/d05/input")
    val rules = Rules(lines.takeWhile { '|' in it }.map { Rule.valueOf(it) })
    val updates = lines.dropWhile { ',' !in it }.map { Update.valueOf(it) }
    updates.filter { it.isValid(rules) }.sumOf { it.middle }.println("Solution")
}

