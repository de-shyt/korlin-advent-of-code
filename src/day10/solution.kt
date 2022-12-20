package day10

import java.io.File
import java.io.InputStream

fun init() {
    var answer = 0
    var op_cnt = 0
    var X = 1
    File("src/day10/input.txt").inputStream()
        .bufferedReader().use { it.readText() }
        .split("\n")
        .forEach {
            val splitted = it.split(' ')
//            println(splitted)
//            println(op_cnt)
            when (splitted[0]) {
                "noop" -> {
//                    println("  noop")
                    op_cnt++
                    answer += answerDelta(op_cnt, X)
                }
                "addx" -> {
//                    println("  addx")
                    op_cnt++
                    answer += answerDelta(op_cnt, X)
                    op_cnt++
                    answer += answerDelta(op_cnt, X)
                    X += splitted[1].toInt()
                }
            }
        }
    println(answer)
}

fun answerDelta(op_cnt: Int, X: Int): Int {
    if (op_cnt >= 20 && (op_cnt - 20) % 40 == 0)
        return X * op_cnt
    return 0
}