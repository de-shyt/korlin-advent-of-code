package templates

import java.io.File
import java.io.InputStream

fun init() {
    val inputStream: InputStream = File("src/templates/input.txt").inputStream()
    val input = inputStream.bufferedReader().use { it.readText() }.split("\n\n")
    println(input)
}