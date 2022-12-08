package day6

import java.io.File
import java.io.InputStream
import java.lang.Math.min
import kotlin.math.max

fun init() {
    val marker_size = 14
    val inputStream: InputStream = File("src/day6/input.txt").inputStream()
    val line = inputStream.bufferedReader().use { it.readText() }
    for (i in line.indices) {
        if (i + marker_size - 1 == line.length) break
        if (line.substring(i, i + marker_size).groupBy { it }.values.size == marker_size) {
            println(i + marker_size)
            break
        }
    }
}
