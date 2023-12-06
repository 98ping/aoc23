package day6

import readInput

fun main() {
    val start = System.currentTimeMillis()
    part2()

    println("Your code blows! Ended in ${System.currentTimeMillis().minus(start)}ms")
}

fun part2() {
    val testInput = readInput("day6", "input")
    var waysToWin = 0L

    val times = testInput[0]
        .removeRange(0, 5)
        .trim()
        .split(" ")
        .joinToString("")
        .toLong()

    val distances = testInput[1]
        .removeRange(0, 9)
        .trim()
        .split(" ")
        .joinToString("")
        .toLong()



    for (holdDownTime in 0..times) {
        val remainingAfterHeldDown = (times - holdDownTime)

        if (distances < (remainingAfterHeldDown * holdDownTime)) {
            waysToWin += 1L
        }
    }

    println("Ways to win are $waysToWin")
}

fun part1() {
    val testInput = readInput("day6", "test")
    var product = 1

    val times = testInput[0]
        .removeRange(0, 5)
        .trim()
        .split(" ")
        .mapNotNull { it.toIntOrNull() }

    val distances = testInput[1]
        .removeRange(0, 8)
        .trim()
        .split(" ")
        .mapNotNull { it.toIntOrNull() }

    val waysToWin = mutableMapOf<Int, Int>()

    for (int in times.indices) {
        val time = times[int]
        val distance = distances[int]

        for (holdDownTime in 0..time) {
            val remainingAfterHeldDown = (time - holdDownTime)

            if (distance < (remainingAfterHeldDown * holdDownTime)) {
                waysToWin[time] = (waysToWin.getOrDefault(time, 0) + 1)
            }
        }
    }

    for (wayToWin in waysToWin.values) {
        product *= wayToWin
    }

    println("Product is $product")
}