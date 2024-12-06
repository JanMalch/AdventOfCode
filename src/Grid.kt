import y2024.Direction

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

    fun getCellOrNull(position: Vec2): Cell<T>? = getCellOrNull(x = position.x, y = position.y)
    fun getCellOrNull(x: Int, y: Int): Cell<T>? = try {
        Cell(get(x, y), Vec2(x, y))
    } catch (e: IndexOutOfBoundsException) {
        null
    }

    @Throws(IndexOutOfBoundsException::class)
    fun getSurroundings(position: Vec2) = Surroundings(
        center = Cell(get(position), position),
        up = getCellOrNull(position + Direction.UP.vec),
        down = getCellOrNull(position + Direction.DOWN.vec),
        right = getCellOrNull(position + Direction.RIGHT.vec),
        left = getCellOrNull(position + Direction.LEFT.vec),
        upRight = getCellOrNull(position + Direction.UP_RIGHT.vec),
        downRight = getCellOrNull(position + Direction.DOWN_RIGHT.vec),
        upLeft = getCellOrNull(position + Direction.UP_LEFT.vec),
        downLeft = getCellOrNull(position + Direction.DOWN_LEFT.vec),
    )

    @Throws(IndexOutOfBoundsException::class)
    fun getSurroundings(x: Int, y: Int) = getSurroundings(Vec2(x = x, y = y))

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

    data class Surroundings<T>(
        val center: Cell<T>,
        val up: Cell<T>?,
        val down: Cell<T>?,
        val right: Cell<T>?,
        val left: Cell<T>?,
        val upRight: Cell<T>?,
        val downRight: Cell<T>?,
        val upLeft: Cell<T>?,
        val downLeft: Cell<T>?,
    ) : Iterable<Cell<T>?> {
        val isAtBottom get() = down == null
        val isAtTop get() = up == null
        val isAtLeftEdge get() = left == null
        val isAtRightEdge get() = right == null
        val isAtTopLeftCorner get() = upLeft == null
        val isAtTopRightCorner get() = upRight == null
        val isAtBottomLeftCorner get() = downLeft == null
        val isAtBottomRightCorner get() = downRight == null

        operator fun get(direction: Direction): Cell<T>? = when (direction) {
            Direction.UP -> up
            Direction.DOWN -> down
            Direction.RIGHT -> right
            Direction.LEFT -> left
            Direction.UP_RIGHT -> upRight
            Direction.DOWN_RIGHT -> downRight
            Direction.UP_LEFT -> upLeft
            Direction.DOWN_LEFT -> downLeft
        }

        override fun iterator(): Iterator<Cell<T>?> = iterator {
            yield(up)
            yield(upRight)
            yield(right)
            yield(downRight)
            yield(down)
            yield(downLeft)
            yield(left)
            yield(upLeft)
        }

        override fun toString(): String {
            fun fmt(cell: Cell<T>?): String = when (cell) {
                null -> "░"
                else -> when (cell.value) {
                    null -> "␀"
                    else -> cell.value.toString()
                }
            }

            val centerValue = fmt(center)
            val upValue = fmt(up)
            val downValue = fmt(down)
            val rightValue = fmt(right)
            val leftValue = fmt(left)
            val upRightValue = fmt(upRight)
            val downRightValue = fmt(downRight)
            val upLeftValue = fmt(upLeft)
            val downLeftValue = fmt(downLeft)
            val longestValueWithPadding = arrayOf(
                centerValue,
                upValue,
                downValue,
                rightValue,
                leftValue,
                upRightValue,
                downRightValue,
                upLeftValue,
                downLeftValue,
            ).maxOf { it.length + 2 }

            fun String.padToCenter() = this.padEnd(
                longestValueWithPadding - 2, padChar = when (this) {
                    "░" -> '░'
                    else -> ' '
                }
            )
            return buildString {

                append('┌')
                repeat(longestValueWithPadding) { append('─') }
                append('┬')
                repeat(longestValueWithPadding) { append('─') }
                append('┬')
                repeat(longestValueWithPadding) { append('─') }
                appendLine('┐')

                append("│ ")
                append(upLeftValue.padToCenter())
                append(" │ ")
                append(upValue.padToCenter())
                append(" │ ")
                append(upRightValue.padToCenter())
                appendLine(" │")

                append('├')
                repeat(longestValueWithPadding) { append('─') }
                append('┼')
                repeat(longestValueWithPadding) { append('─') }
                append('┼')
                repeat(longestValueWithPadding) { append('─') }
                appendLine('┤')

                append("│ ")
                append(leftValue.padToCenter())
                append(" │ ")
                append(centerValue.padToCenter())
                append(" │ ")
                append(rightValue.padToCenter())
                appendLine(" │")

                append('├')
                repeat(longestValueWithPadding) { append('─') }
                append('┼')
                repeat(longestValueWithPadding) { append('─') }
                append('┼')
                repeat(longestValueWithPadding) { append('─') }
                appendLine('┤')

                append("│ ")
                append(downLeftValue.padToCenter())
                append(" │ ")
                append(downValue.padToCenter())
                append(" │ ")
                append(downRightValue.padToCenter())
                appendLine(" │")

                append('└')
                repeat(longestValueWithPadding) { append('─') }
                append('┴')
                repeat(longestValueWithPadding) { append('─') }
                append('┴')
                repeat(longestValueWithPadding) { append('─') }
                appendLine('┘')

            }
        }
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
val <T> Grid<T>.centerCell: Grid.Cell<T>
    get() {
        require(!isEmpty()) { "Cannot get the center item of an empty grid." }
        require(width.isOdd) { "Cannot get the center item of a ${width}x${height} grid, because its width is even." }
        require(height.isOdd) { "Cannot get the center item of a ${width}x${height} grid, because its height is even." }
        return getCellOrNull(x = width / 2, y = height / 2)
            ?: throw IllegalStateException("Internal error on getting center cell for ${width}x${height} grid.")
    }

fun Grid<*>.isEmpty(): Boolean = area == 0

fun <A, B> Grid<A>.map(transform: (value: A) -> B): Grid<B> = map { value, _ -> transform(value) }

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
