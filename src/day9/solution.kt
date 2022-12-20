package day9

import java.io.File
import java.io.InputStream
import java.lang.Exception
import kotlin.math.pow
import kotlin.math.sqrt

fun init() {
    val SIZE = 10
    val inputStream: InputStream = File("src/day9/input.txt").inputStream()
    val input = inputStream.bufferedReader().use { it.readText() }.split("\n").map { it.split(' ') }

    val snake: MutableList<Pair<Int, Int>> = MutableList(SIZE) { Pair(0, 0) }

    val visitedDots: MutableSet<Pair<Int, Int>> = mutableSetOf(Pair(0, 0))

    input.forEach { (dir, cnt) ->
        val oldSnake = snake.toMutableList()
        for (i in 0 until SIZE - 1) {
            if (i > 0 && snake[i - 1] == Pair(0, 0))  // prev number did not move
                break
            println("$dir $cnt:")
            var (headX, headY) = oldSnake[i]
            var (tailX, tailY) = oldSnake[i + 1]
            val (headDX, headDY) = updateHeadDelta(dir)
//        println("    head: (dx, dy) = ($headDX, $headDY)")
//        print("          ($headX, $headY) --> ")
            repeat(cnt.toInt() - i) {
//            val prevTailX = tailX; val prevTailY = tailY
                headX += headDX; headY += headDY
                if (dist(headX, headY, tailX, tailY) > sqrt(2.0)) {
                    val (tailDX, tailDY) = updateTailDelta(dir, headX - tailX, headY - tailY)
                    tailX += tailDX; tailY += tailDY
                    print("    ${Pair(tailX, tailY)}")
                    if (i + 1 == SIZE - 1) {
                        println("   tail")
                        visitedDots.add(Pair(tailX, tailY))
                    } else println()
//                println("    ${Pair(tailX, tailY)}")
                }
//            println("    tail: ($prevTailX, $prevTailY) --> ($tailX, $tailY)")
            }
            snake[i] = Pair(headX, headY)
            snake[i + 1] = Pair(tailX, tailY)
//            println(snake)
//        println("($headX, $headY)")
//        println()
        }

    }

//    println("\nvisited dots:")
//    for (elem in visitedDots)
//        println("  $elem")
    println(visitedDots.size)
}

fun dist(x1: Int, y1: Int, x2: Int, y2: Int): Double = sqrt((x1 - x2).toDouble().pow(2) + (y1 - y2).toDouble().pow(2))

fun updateHeadDelta(dir: String): Pair<Int, Int> = when (dir) {
    "R" -> Pair(1, 0)
    "L" -> Pair(-1, 0)
    "U" -> Pair(0, 1)
    "D" -> Pair(0, -1)
    else -> throw Exception("updateHeadDelta: wrong direction")
}

fun updateTailDelta(dir: String, deltaX: Int, deltaY: Int): Pair<Int, Int> = when (dir) {
    "R" -> Pair(1, deltaY)
    "L" -> Pair(-1, deltaY)
    "U" -> Pair(deltaX, 1)
    "D" -> Pair(deltaX, -1)
    else -> throw Exception("updateTailDelta: wrong direction")
}