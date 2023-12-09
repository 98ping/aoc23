package day9

import readInput

fun main() {
    val start = System.currentTimeMillis()
    part2()

    println("Your code blows! Ended in ${System.currentTimeMillis().minus(start)}ms")
}


fun part1() {
    println(readInput("day9", "input").sumOf { line ->
        generateSequence(line.split(" ")
            .map { it.trim().toLong() }) { seq ->
            seq.zipWithNext { a, b -> b - a }
                .takeIf { it.any { diff -> diff != 0L } }
        }.fold(0L) { acc, seq -> acc + seq.last() }
    })
}

fun part2() {
    println(readInput("day9", "input").sumOf { line ->
        generateSequence(line.split(" ")
            .map { it.trim().toLong() }) { seq ->
            seq.zipWithNext { a, b -> b - a }
                .takeIf { it.any { diff -> diff != 0L } }
        }.toMutableList().reversed().fold(0L) { acc, seq -> seq.first() - acc }
    })
}
