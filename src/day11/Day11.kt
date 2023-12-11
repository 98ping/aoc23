package day11

import readInput
import kotlin.math.abs

fun main()
{
    val start = System.currentTimeMillis()

    part2()
    println("Your code blows! Ended in ${System.currentTimeMillis().minus(start)}ms")
}

fun part2()
{
    val testInput = readInput("day11", "input")

    val galaxies = mutableListOf<GalaxyPart2>()
    var total = 0L
    var inc = 0

    for (i in 0 until testInput[0].length) {
        for (j in testInput.indices) {
            if (testInput[j][i] == '#') {
                inc += 1
                galaxies.add(GalaxyPart2(inc, i.toLong(), j.toLong()))
            }
        }
    }

    println(galaxies.toString())

    val pairs = mutableListOf<Pair<GalaxyPart2, GalaxyPart2>>()

    for (y in testInput.size - 1 downTo  0) {
        var allDots = true

        for (x in 0 until testInput[0].length) {
            if (testInput[y][x] != '.') {
                allDots = false
                break
            }
        }

        if (allDots) {
            for (g in 0 until galaxies.size) {
                if (galaxies[g].y > y) {
                    galaxies[g].y += 1000000L - 1L
                }
            }
        }
    }

    for (x in testInput[0].length - 1 downTo 0) {
        var allDots = true

        for (y in testInput.indices) {
            if (testInput[y][x] != '.') {
                allDots = false
                break
            }
        }

        if (allDots) {
            for (galaxy in galaxies) {
                if (galaxy.x > x) {
                    galaxy.x += 1000000L - 1L
                }
            }
        }
    }

    for (i in 0 until galaxies.size) {
        for (j in i+1 until galaxies.size) {
            pairs.add(
                Pair(galaxies[i], galaxies[j])
            )
        }
    }

    println(pairs.size)

    for (pair in pairs)
    {
        total += shortestPathBetween2(pair.first, pair.second)
    }

    println("Total: $total")
}

fun part1()
{
    val testInput = readInput("day11", "input")

    val galaxies = mutableListOf<Galaxy>()
    var total = 0
    var inc = 0

    val newRows = mutableListOf<String>()

    for (line in testInput) {
        if (line.all { it == '.' }) {
            newRows.add(line)
            newRows.add(line)
        } else {
            newRows.add(line)
        }
    }

    println(newRows.size)

    val newGrid = arrayOfNulls<String>(newRows.size)

    for (i in 0 until newRows[0].length) {
        var strictlyDots = true

        for (j in 0 until newRows.size) {
            if (newRows[j][i] != '.') {
                strictlyDots = false
                break
            }
        }

        if (strictlyDots) {
            for (j in 0 until newRows.size) {
                newGrid[j] += ".."
            }
        } else {
            for (j in 0 until newRows.size) {
                newGrid[j] += newRows[j][i].toString()
            }
        }
    }

    println(newGrid.joinToString("\n"))


    for (i in 0 until newGrid[0]!!.length) {
        for (char in newGrid.indices) {
            if (newGrid[char]!![i] == '#') {
                inc += 1
                galaxies.add(Galaxy(inc, i, char))
            }
        }
    }

    println(galaxies.toString())

    val pairs = mutableListOf<Pair<Galaxy, Galaxy>>()

    for (i in 0 until galaxies.size) {
        for (j in i+1 until galaxies.size) {
            pairs.add(
                Pair(galaxies[i], galaxies[j])
            )
        }
    }

    println(pairs.size)

    for (pair in pairs)
    {
        total += shortestPathBetween(pair.first, pair.second)
    }

    println("Total: $total")
}

fun shortestPathBetween2(galaxy: GalaxyPart2, second: GalaxyPart2) = abs(galaxy.x - second.x) + abs(galaxy.y - second.y)

fun shortestPathBetween(galaxy: Galaxy, second: Galaxy) = abs(galaxy.x - second.x) + abs(galaxy.y - second.y)

data class Galaxy(
    val id: Int,
    var x: Int,
    var y: Int
)

data class GalaxyPart2(
    val id: Int,
    var x: Long,
    var y: Long
)