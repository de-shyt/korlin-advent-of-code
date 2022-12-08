package day8

import java.io.File
import java.io.InputStream
import kotlin.math.max

fun init() {
    val inputStream: InputStream = File("src/day8/input.txt").inputStream()
    val input = inputStream.bufferedReader().use { it.readText() }.split("\n")
        .map { line ->
            line.map { it.digitToInt() }
        }

    println(part1(input))
    println(part2(input))
}

fun getAmount(list: List<Int>, center: Int): Int {
    var tmp = list.indexOfFirst { it >= center }
    if (tmp == -1)
        return list.size
    return tmp + 1
}

fun part2(input: List<List<Int>>): Int {
    val m = input[0].size
    var answer = 0
    for (i in input.indices)
        for (j in input[i].indices) {
            var scenic_score =
                getAmount(input[i].subList(j + 1, m), input[i][j]) *
                        getAmount(input[i].subList(0, j).reversed(), input[i][j])

            val vertical = input.map { it[j] }

            scenic_score *=
                getAmount(vertical.subList(0, i).reversed(), input[i][j]) *
                        getAmount(vertical.subList(i + 1, m), input[i][j])

            answer = max(answer, scenic_score)
        }

    return answer
}

fun part1(input: List<List<Int>>): Int {
    val g: List<MutableList<Boolean>> = List(input.size) { MutableList(input[0].size) { false } }
    var answer = 0

    val n = input.size
    val m = input[0].size

    for (j in 0 until m) {
        var max_front = if (g[0][j]) input[0][j] else -1
        var max_end = if (g[n - 1][j]) input[n - 1][j] else -1
        for (i in 0 until n) {
            if (!g[i][j] && input[i][j] > max_front) {
                answer++
                max_front = input[i][j]
                g[i][j] = true
            } else if (g[i][j])
                max_front = max(max_front, input[i][j])
            if (!g[n - 1 - i][j] && input[n - 1 - i][j] > max_end) {
                answer++
                max_end = input[n - 1 - i][j]
                g[n - 1 - i][j] = true
            } else if (g[n - 1 - i][j]) {
                max_end = max(max_end, input[n - 1 - i][j])
            }
        }
    }

    for (i in 0 until n) {
        var max_front = if (g[i][0]) input[i][0] else -1
        var max_end = if (g[i][n - 1]) input[i][n - 1] else -1
        for (j in 0 until m) {
            if (!g[i][j] && input[i][j] > max_front) {
                answer++
                max_front = input[i][j]
                g[i][j] = true
            } else if (g[i][j])
                max_front = max(max_front, input[i][j])
            if (!g[i][m - 1 - j] && input[i][m - 1 - j] > max_end) {
                answer++
                max_end = input[i][m - 1 - j]
                g[i][m - 1 - j] = true
            } else if (g[i][m - 1 - j])
                max_end = max(max_end, input[i][m - 1 - j])
        }
    }

    return g.sumOf { it.count { value -> value } }
}