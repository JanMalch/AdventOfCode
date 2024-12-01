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

fun <T> List<T>.toPair(): Pair<T, T> {
    require(size == 2)
    return this[0] to this[1]
}