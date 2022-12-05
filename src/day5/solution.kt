package day5

import java.io.File
import java.io.InputStream

fun init() {
    val inputStream: InputStream = File("src/day5/input.txt").inputStream()
    val stacks: MutableList<MutableList<Char>> = MutableList<MutableList<Char>>(10) { mutableListOf() }
    val input = inputStream.bufferedReader().use { it.readText() }.split("\n")

    var next_start = 0
    val whichPart = 1

    for (i in input.indices) {
        val cur_line = input[i]
        if (cur_line.contains('1')) {
            next_start = i + 2
            break
        }
        var cur_stack = 0
        for (j in cur_line.indices) {
            if (j % 4 == 0) cur_stack++
            val cur_symb = cur_line[j]
            if (cur_symb == ' ' || cur_symb == '[' || cur_symb == ']') continue
            stacks[cur_stack] += cur_symb
        }
    }

    stacks.forEach { it.reverse() }

    for (i in next_start until input.size) {
        val operation = input[i].split(' ')

        val amount = operation[1].toInt()
        if (amount < 1) continue

        val from = operation[3].toInt()
        val to = operation[5].toInt()

        val sublist = stacks[from].subList(stacks[from].size - amount, stacks[from].size)

        when (whichPart) {
            1 -> {
                stacks[to] += sublist.reversed()
                sublist.clear()
            }

            2 -> {
                stacks[to] += sublist
                sublist.clear()
            }
        }
    }

    val answer = stacks.map { if (it.isNotEmpty()) it.last() else "" }.joinToString("")
    println(answer)
}
