data class Vec2(val x: Int, val y: Int) {
    operator fun plus(other: Vec2): Vec2 = Vec2(x + other.x, y + other.y)
    operator fun times(factor: Int): Vec2 = Vec2(x * factor, y * factor)
    override fun toString(): String = "($x,$y)"
}
