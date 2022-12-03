package day3

import java.io.File
import java.io.InputStream
import kotlin.math.min

fun getPriority(c: Char): Int {
    if (c.isUpperCase()) return c - 'A' + 27
    return c - 'a' + 1
}

fun findCommonLetters(fst: String, snd: String): String =
    fst.filter { c -> snd.contains(c) }.groupBy { it }.keys.joinToString("")

fun findLettersSum(src: String): Int = src.map { getPriority(it) }.sum()

fun init() {
    val inputStream: InputStream = File("src/day3/input.txt").inputStream()
    val input = inputStream.bufferedReader().use { it.readText() }.split("\n")
        .withIndex().groupBy { it.index / 3 }.map { it.value.map { e -> e.value } }
        .map { (fst, snd, thd) -> findLettersSum(findCommonLetters(findCommonLetters(fst, snd), thd)) }.sum()
    println(input)
}