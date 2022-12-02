package day2.part2

import java.io.File

fun init() =
    println(
        File("src/day2/input.txt").inputStream().bufferedReader().use { it.readText() }
            .split("\n")
            .map { it.split(' ') }
            .sumOf { round -> processRound(round[0][0], round[1][0]) }
    )

fun processRound(a: Char, b: Char): Int {
    val score_for_round = (b - 'X') * 3
    val score_for_figure = getScoreForFigure(a, b)
    return score_for_round + score_for_figure
}

fun getScoreForFigure(a: Char, b: Char): Int {
    val opponent_figure = a - 'A' + 1
    val res: Int = when(b) {
        'X' -> if (opponent_figure - 1 == 0) 3 else opponent_figure - 1
        'Z' -> if (opponent_figure + 1 == 4) 1 else opponent_figure + 1
        else -> opponent_figure
    }
    return res
}
