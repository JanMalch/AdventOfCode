import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun inputLines(name: String) = Path("src/$name.txt").readText().trim().lines().map { it.trim() }

/**
 * Returns the md5 hash of this string.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * Also print this.
 */
fun <T> T.println(note: String? = null): T = also {
    if (note != null) print("$note: ")
    println(this)
}

/**
 * Print visual separator.
 */
fun printsep() = println("\n${"-".repeat(100)}\n")

/**
 * Returns the first digit in the string, or `null` if none exist.
 */
fun String.firstDigitOrNull(radix: Int = 10): Int? = firstOrNull { it.isDigit() }?.digitToIntOrNull(radix)

/**
 * Returns the last digit in the string, or `null` if none exist.
 */
fun String.lastDigitOrNull(radix: Int = 10): Int? = lastOrNull { it.isDigit() }?.digitToIntOrNull(radix)

/**
 * Returns the first digit in the string, or throws if none exist.
 */
fun String.firstDigit(radix: Int = 10): Int = first { it.isDigit() }.digitToInt(radix)

/**
 * Returns the last digit in the string, or throws if none exist.
 */
fun String.lastDigit(radix: Int = 10): Int = last { it.isDigit() }.digitToInt(radix)

/**
 * Converts `this` list to a pair, if and only if `this` list has exactly two items.
 * @throws IllegalArgumentException if the list doesn't have exactly two items.
 */
fun <T> List<T>.toPair(): Pair<T, T> {
    require(size == 2) { "Cannot convert a list to a pair, because the list has $size items." }
    return this[0] to this[1]
}

/**
 * Checks if `this` value equals the [other] value. If so, it will return `this`.
 * @throws IllegalStateException if the values are not equal.
 */
fun <T> T.checkEquals(other: T): T = also {
    check(this == other) {
        "Expected this $this to equal $other."
    }
}

/**
 * Returns a list of pairs built from the elements of `this` collection and [other] collection with the same index.
 * @throws IllegalArgumentException if the collections do not have the same size.
 */
infix fun <T, R> Collection<T>.zipStrict(other: Collection<R>): List<Pair<T, R>> {
    require(size == other.size) {
        "The size of this (${size}) does not equal the other size (${other.size})."
    }
    return zip(other)
}

/** Returns `1`, if `this` is `true`. Otherwise `0`. */
fun Boolean.toInt() = if (this) 1 else 0


/**
 * Creates a reverse lookup, where each input value is associated with a list of input keys.
 * @see reverseToSingle
 */
fun <K, V> Map<K, V>.reverseToGroup(): Map<V, List<K>> {
    val map = mutableMapOf<V, MutableList<K>>()
    for ((key, value) in this) {
        map.compute(value) { _, list ->
            (list ?: mutableListOf()).apply { add(key) }
        }
    }
    return map
}

/**
 * Creates a reverse lookup, where each input value is associated with a single input keys.
 * @throws IllegalArgumentException if an input value would be associated with multiple input keys
 * @see reverseToGroup
 */
fun <K, V> Map<K, V>.reverseToSingle(): Map<V, K> = reverseToGroup().map { (k, v) ->
    require(v.size == 1) {
        "Cannot invert to lookup, because a key (previously a value) would be associated with multiple values (previously keys): $k -> $v"
    }
    k to v.single()
}.toMap()

/**
 * Creates a reverse lookup, by associating every item in each entry's value list
 * with the key of that entry.
 */
fun <K, V> Map<K, List<V>>.reverseGrouping(): Map<V, List<K>> {
    val map = mutableMapOf<V, MutableList<K>>()
    for ((key, values) in this) {
        for (value in values) {
            map.compute(value) { _, list ->
                (list ?: mutableListOf()).apply { add(key) }
            }
        }
    }
    return map
}

val Int.isOdd: Boolean
    get() = this % 2 == 1

val Int.isEven: Boolean
    get() = this % 2 == 0

/**
 * Returns the item at the center of `this` list.
 * ```
 * 2 5 4 1 3
 *     ^
 *   center
 * ```
 * @throws IllegalArgumentException if the list is empty or has an even size.
 */
fun <T> List<T>.itemAtCenter(): T {
    require(isNotEmpty()) {
        "An empty list does not have an item in the center."
    }
    require(size.isOdd) {
        "A list of even size ($size) cannot have an item in the center."
    }
    return this[lastIndex / 2]
}
