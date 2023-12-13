package day13

import ltd.matrixstudios.helpers.max
import readInput

// totally gave up on this 1 too
fun main() {
    val start = System.currentTimeMillis()

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

fun getHorizontalReflection(puzzle: Puzzle) : Pair<Int, Int> {
    var leftColumns = 0
    var rightColumns = 0

    for (axis in puzzle.lines.indices) {
        val leftLines = mutableListOf<String>()
        val rightLines = mutableListOf<String>()

        println("Checking axis: $axis")

        for (line in puzzle.lines) {
            leftLines.add(line.substring(0, axis + 1))
            rightLines.add(line.substring(axis + 1, line.length))
        }

        if (verifyEquivalence(leftLines, rightLines)) {
            leftColumns = leftLines.size
            rightColumns = rightLines.size
        }
    }

    return Pair(leftColumns, rightColumns)
}

fun verifyEquivalence(leftLines: MutableList<String>, rightLines: MutableList<String>) : Boolean {
    var matching = true
    val maxIndex = max(leftLines.size, rightLines.size)

    println(leftLines.toString())
    println(rightLines.toString())

    for (check in maxIndex downTo 0) {
        val lCol = leftLines.getOrNull(check)
        val rCol = rightLines.getOrNull(check)

        if (lCol == rCol) {
            println("right column equals left column")
        } else {
            matching = false
            break
        }
    }

    return matching
}

data class Puzzle(val lines: MutableList<String>)
