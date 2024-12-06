data class Vec2(val x: Int, val y: Int) {
    operator fun plus(other: Vec2): Vec2 = Vec2(x + other.x, y + other.y)
    override fun toString(): String = "($x,$y)"

    companion object {
        val UP = Vec2(0, -1)
        val DOWN = Vec2(0, 1)
        val RIGHT = Vec2(1, 0)
        val LEFT = Vec2(-1, 0)
        val STRAIGHTS = setOf(UP, DOWN, RIGHT, LEFT)
    }
}
