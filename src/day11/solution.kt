package day11

import java.io.File
import java.io.InputStream
import kotlin.math.floor

fun createLambda(op: Char, value: Long?): (Long) -> Long {
    return if (op == '+') { x: Long ->
        x + (value ?: x)
    }
    else { x: Long ->
        x * (value ?: x)
    }
}

fun createGetNextFunction(condition: Long, ifTrue: Long, ifFalse: Long): (Long) -> Int =
    { x: Long ->
        if (x % condition == 0L) ifTrue.toInt() else ifFalse.toInt()
    }

fun init() {
    val input = File("src/day11/input.txt").inputStream()
        .bufferedReader().use { it.readText() }
        .split("\n\n")
        .map { monkey ->
            monkey.split('\n').map { line ->
                line.split(' ')
                    .filter { it.isNotEmpty() && it[0] <= '9' }
                    .map { it.replace(",", "") }
            }
        }

    val monkeys: MutableList<Monkey> = mutableListOf()

    val totalAmount: MutableList<Long> = MutableList(MONKEYS_AMOUNT) { 0 }

    input.forEach { curMonkey ->
        monkeys.add(
            Monkey(
                curMonkey[starting].map { it.toLong() }.toMutableList(),
                createLambda(
                    curMonkey[operation][0].first(),
                    if (curMonkey[operation].size > 1) curMonkey[operation][1].toLong() else null
                ),
                createGetNextFunction(
                    curMonkey[test][0].toLong(),
                    curMonkey[ifTrue][0].toLong(),
                    curMonkey[ifFalse][0].toLong()
                )
            )
        )
    }

    repeat(20) {
        monkeys.forEachIndexed { index, monkey ->
            monkey.holds.forEach {
                totalAmount[index]++
                var worryLevel = monkey.operation(it) / 3
                monkeys[monkey.getNext(worryLevel)].holds.add(worryLevel)
            }
            monkey.holds.clear()
        }
    }

    println(totalAmount.sorted().takeLast(2).fold(1, Long::times))
}

val MONKEYS_AMOUNT = 8

data class Monkey(
    val holds: MutableList<Long>,
    val operation: (Long) -> Long,
    val getNext: (Long) -> Int
)

val starting = 1
val operation = 2
val test = 3
val ifTrue = 4
val ifFalse = 5