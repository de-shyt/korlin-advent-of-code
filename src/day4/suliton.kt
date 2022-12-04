package day4

import java.io.File
import java.io.InputStream
import kotlin.math.max

fun oneInsideAnotherPart1(a: List<Int>, b: List<Int>): Boolean =
    b[0] <= a[0] && a[1] <= b[1] || a[0] <= b[0] && b[1] <= a[1]

fun oneInsideAnotherPart2(a: List<Int>, b: List<Int>): Boolean =
    !(a[1] < b[0] || b[1] < a[0])

fun init() {
    val inputStream: InputStream = File("src/day4/input.txt").inputStream()
    var answer = 0
    val input = inputStream.bufferedReader()
        .use { it.readText() }
        .split("\n")
        .map { pair ->
            pair.split(',')
                .map { elf -> elf.split('-').map { it.toInt() } }
        }
        .forEach { (e1, e2) -> if (oneInsideAnotherPart2(e1, e2)) answer++
        }

    println(answer)
}