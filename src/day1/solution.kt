package day1

import java.io.File
import java.io.InputStream

fun init() {
    val inputStream: InputStream = File("src/day1/input.txt").inputStream()
    val input = inputStream.bufferedReader().use { it.readText() }.split("\n\n")
    println(input.map { elf_items ->
        elf_items.split('\n').map { item -> item.toInt() }.sum()
    }.sortedDescending().take(3).sum())
}

