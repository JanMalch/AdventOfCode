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
