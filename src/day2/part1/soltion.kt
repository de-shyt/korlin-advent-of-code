package day2.part1

import java.io.File
import java.io.InputStream

fun init() =
    println(
        File("src/day2/input.txt").inputStream().bufferedReader().use { it.readText() }
            .split("\n")
            .map { it.split(' ') }
            .sumOf { round -> processRound(round[0][0], round[1][0]) }
    )

fun processRound(a: Char, b: Char): Int {
    val score_for_round = when (chooseWinner(a, b)) {
        1 -> 0
        2 -> 6
        else -> 3
    }
    val score_for_figure = b - 22 - 'A'
    return score_for_round + score_for_figure
}

fun chooseWinner(a: Char, b: Char): Int =
    when (a - (b - 23)) {
        1 -> 1
        -1 -> 2
        -2 -> 1
        2 -> 2
        else -> 0
    }