package templates

import java.io.File
import java.io.InputStream

fun init() {
    val input = File("src/day11/input.txt").inputStream()
        .bufferedReader().use { it.readText() }
        .split("\n\n")
    println(input)
}