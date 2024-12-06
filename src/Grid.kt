private fun <T> List<List<T>>.requireRectangular() {
    firstOrNull()?.size?.let { expectedWidth ->
        for ((index, row) in withIndex()) {
            require(row.size == expectedWidth) {
                "Expected all rows to have the same width of $expectedWidth, but row at index $index has a width of ${row.size}."
            }
        }
    }
}

abstract class Grid<T> {
    abstract val rows: List<List<T>>
    val height: Int get() = rows.size
    val width: Int get() = rows.getOrNull(0)?.size ?: 0

    @Throws(IndexOutOfBoundsException::class)
    operator fun get(position: Vec2): T = get(x = position.x, y = position.y)

    @Throws(IndexOutOfBoundsException::class)
    operator fun get(x: Int, y: Int): T {
        val row =
            rows.getOrElse(y) { throw IndexOutOfBoundsException("This ${width}x$height grid has no cell at ($x,$y).") }
        if (x >= 0 && x < row.size) {
            return row[x]
        } else {
            throw IndexOutOfBoundsException("This ${width}x$height grid has no cell at ($x,$y).")
        }
    }

    override fun toString(): String = toString(spacer = "")
    fun toString(spacer: String): String = rows.joinToString(separator = "\n") { row ->
        row.joinToString(separator = spacer) { it.toString() }
    }


    data class Cell<T>(
        val value: T,
        val position: Vec2,
    ) {
        val x: Int get() = position.x
        val y: Int get() = position.y
    }
}

private class PersistentGrid<T>(
    override val rows: List<List<T>>
) : Grid<T>() {

    init {
        rows.requireRectangular()
    }

    override fun hashCode(): Int = rows.hashCode()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PersistentGrid<*>

        return rows == other.rows
    }
}

class MutableGrid<T>(private val mutableRows: List<MutableList<T>>) : Grid<T>() {
    override val rows: List<List<T>> get() = mutableRows.map { it.toList() }

    init {
        mutableRows.requireRectangular()
    }

    @Throws(IndexOutOfBoundsException::class)
    operator fun set(position: Vec2, value: T) = set(x = position.x, y = position.y, value = value)

    @Throws(IndexOutOfBoundsException::class)
    operator fun set(x: Int, y: Int, value: T) {
        val row =
            mutableRows.getOrElse(y) { throw IndexOutOfBoundsException("This ${width}x$height grid has no cell at ($x,$y).") }
        if (x >= 0 && x < row.size) {
            row[x] = value
        } else {
            throw IndexOutOfBoundsException("This ${width}x$height grid has no cell at ($x,$y).")
        }
    }

    override fun hashCode(): Int = rows.hashCode()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MutableGrid<*>

        return mutableRows == other.mutableRows
    }
}

fun <T> Grid<T>.cells(): Iterator<Grid.Cell<T>> = iterator {
    rows.forEachIndexed { y, row ->
        row.forEachIndexed { x, value ->
            yield(Grid.Cell(value, Vec2(x = x, y = y)))
        }
    }
}

fun <T> Grid<T>.firstOrNull(predicate: (T) -> Boolean): Grid.Cell<T>? {
    for (cell in cells()) {
        if (predicate(cell.value)) {
            return cell
        }
    }
    return null
}

fun <T> Grid<T>.lastOrNull(predicate: (T) -> Boolean): Grid.Cell<T>? {
    for (cell in cells().asSequence().toList().asReversed()) {
        if (predicate(cell.value)) {
            return cell
        }
    }
    return null
}

val Grid<*>.area: Int get() = width * height
fun Grid<*>.isEmpty(): Boolean = area == 0

fun <A, B> Grid<A>.map(transform: (value: A, position: Vec2) -> B): Grid<B> =
    PersistentGrid(rows.mapIndexed { y, row ->
        row.mapIndexed { x, value ->
            transform(value, Vec2(x = x, y = y))
        }
    })

fun <T> Grid<T>.copyWith(position: Vec2, value: T) = copyWith(x = position.x, y = position.y, overwrite = value)
fun <T> Grid<T>.copyWith(x: Int, y: Int, overwrite: T): Grid<T> = map { value, pos ->
    when {
        pos.x == x && pos.y == y -> overwrite
        else -> value
    }
}

fun <T> Grid<T>.toGrid(): Grid<T> = map { t, _ -> t }
fun <T> Grid<T>.toMutableGrid(): MutableGrid<T> =
    MutableGrid(rows.map { it.toMutableList() })

fun Iterable<String>.toCharGrid(): Grid<Char> = PersistentGrid(map { it.toCharArray().toList() })
