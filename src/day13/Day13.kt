package day13

import com.sun.org.apache.xpath.internal.operations.Bool
import readInput

fun main() {
    val start = System.currentTimeMillis()
    part1()

    println("Your code blows! Ended in ${System.currentTimeMillis().minus(start)}ms")
}


fun part1() {
    val input = readInput("day13", "test")

    val puzzles = mutableListOf<Puzzle>()

    val lines = mutableListOf<String>()
    for (i in input.indices) {
        val line = input[i]

        if (line.isNotEmpty()) {
            lines.add(line)
        } else {
            puzzles.add(Puzzle(lines.toMutableList()))
            lines.clear()
        }


        if (i == input.size - 1) {
            puzzles.add(Puzzle(lines.toMutableList()))
        }
    }

    val first = puzzles.first()

    println(getHorizontalReflection(first))
}

fun getHorizontalReflection(puzzle: Puzzle) : Int {
    var leftColumns = 0
    var rightColumns = 0

    for (axis in puzzle.lines.indices) {
        val leftLines = mutableListOf<String>()
        val rightLines = mutableListOf<String>()

        for (line in puzzle.lines) {
            leftLines.add(line.substring(0, axis + 1))
            rightLines.add(line.substring(axis + 1, line.length))
        }

        println(leftLines.toString())
        println(rightLines.toString())
    }

    return 0
}

fun verifyEquivalence(first: MutableList<String>, second: MutableList<String>) : Boolean {
    return true
}

data class Puzzle(val lines: MutableList<String>)
