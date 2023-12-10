package day10

import readInput
import kotlin.math.absoluteValue

// not even gonna attempt this one ngl
fun main()
{
    val start = System.currentTimeMillis()

    part1()
    println("Your code blows! Ended in ${System.currentTimeMillis().minus(start)}ms")
}

fun part1()
{
    val testInput = readInput("day10", "test")

    // little hardcoding never hurt
    // og: |
    var S_REPLACEMENT = "F"

    // startingLocation[0] is X
    // startingLocation[1] is Y
    val startingLocation = arrayOf(0, 0)
    var maxDistance = 0

    for (i in testInput.indices) {
        if (testInput[i].contains("S")) {
            startingLocation[0] = testInput[i].indexOf("S")
            startingLocation[1] = i
            break
        }
    }

    val startingLine = testInput[startingLocation[1]]
}