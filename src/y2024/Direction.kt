package y2024

import Vec2

enum class Direction(val vec: Vec2) {
    UP(vec = Vec2(0, -1)),
    DOWN(vec = Vec2(0, 1)),
    RIGHT(vec = Vec2(1, 0)),
    LEFT(vec = Vec2(-1, 0)),
    UP_RIGHT(vec = Vec2(1, -1)),
    DOWN_RIGHT(vec = Vec2(1, 1)),
    UP_LEFT(vec = Vec2(-1, -1)),
    DOWN_LEFT(vec = Vec2(-1, 1)),
}