import kotlin.math.max

interface Counter<T> : Collection<Pair<T, Int>> {
    operator fun get(key: T): Int
}

class MutableCounter<T> : Counter<T> {
    private val map = mutableMapOf<T, Int>()

    /**
     * Increments the given [key] by the given [amount].
     * @throws IllegalArgumentException if the amount is < 0
     * @return the new count for the given [key]
     */
    fun increment(key: T, amount: Int = 1): Int {
        require(amount >= 0) {
            "Increment amount must be non-negative, but is $amount."
        }
        return map.compute(key) { _, current -> (current ?: 0) + amount } ?: 0
    }

    /**
     * Decrements the given [key] by the given [amount].
     * @throws IllegalArgumentException if the amount is < 0
     * @return the new count for the given [key]
     */
    fun decrement(key: T, amount: Int = 1): Int {
        require(amount >= 0) {
            "Decrement amount must be non-negative, but is $amount."
        }
        return map.compute(key) { _, current -> max(0, (current ?: 0) - amount) } ?: 0
    }

    /**
     * Removes the specified key and its corresponding value from this counter.
     * @return the previous value associated with the key, or `null` if the key was not present in the map.
     */
    fun remove(key: T): Int? = map.remove(key)

    operator fun plusAssign(key: T) {
        increment(key)
    }

    operator fun minusAssign(key: T) {
        decrement(key)
    }

    override fun get(key: T): Int = map.getOrDefault(key, 0)

    // Collection implementations

    override val size: Int get() = map.size

    override fun isEmpty(): Boolean = map.isEmpty()

    override fun iterator(): Iterator<Pair<T, Int>> = map.asSequence().map { it.key to it.value }.iterator()

    override fun containsAll(elements: Collection<Pair<T, Int>>): Boolean = elements.all { it in this }

    override fun contains(element: Pair<T, Int>): Boolean = map[element.first] == element.second

    // Any implementations

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MutableCounter<*>

        return map == other.map
    }

    override fun hashCode(): Int = map.hashCode()

    override fun toString(): String = "MutableCounter$map"

}