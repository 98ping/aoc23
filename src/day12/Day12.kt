package day12

import ltd.matrixstudios.helpers.Tuple
import readInput


fun main()
{
    val start = System.currentTimeMillis()

    part2()
    println("Your code blows! Ended in ${System.currentTimeMillis().minus(start)}ms")
}

fun part2()
{
    val testInput = readInput("day12", "input")

    println(testInput.sumOf { spring ->
        val amountList = mutableListOf<Int>()

        repeat(5) {
            amountList.addAll(spring.split(" ")[1]
                .split(",")
                .map { it.toInt() })
        }

        countArrangements(
            hashMapOf(),
            java.lang.String.join(
                "?",
                spring.split(" ")[0],
                spring.split(" ")[0],
                spring.split(" ")[0],
                spring.split(" ")[0],
                spring.split(" ")[0]
            ),
            amountList.toIntArray(),
            0,
            0,
            0
        )
    })
}

fun part1()
{
    val testInput = readInput("day12", "test")

    println(testInput.sumOf { spring ->
        countArrangements(
            hashMapOf(),
            spring.split(" ")[0],
            spring.split(" ")[1]
                .split(",")
                .map { it.toInt() }
                .toIntArray(),
            0,
            0,
            0
        )
    })
}

fun countArrangements(
    blockMap: HashMap<Tuple<Int, Int, Int>, Long>,
    map: String,
    amounts: IntArray,
    i: Int,
    j: Int,
    cur: Int
): Long
{
    val key = Tuple(i, j, cur)

    if (blockMap.containsKey(key))
    {
        return blockMap[key]!!
    }

    if (i == map.length)
    {
        return if ((j == amounts.size && cur == 0) || (j == amounts.size - 1 && amounts[j] == cur)) 1 else 0
    }

    var total = 0L
    val c = map[i]

    if ((c == '.' || c == '?') && cur == 0)
    {
        total += countArrangements(blockMap, map, amounts, i + 1, j, 0)
    } else if ((c == '.' || c == '?') && (cur > 0) && (j < amounts.size) && (amounts[j] == cur))
    {
        total += countArrangements(blockMap, map, amounts, i + 1, j + 1, 0)
    }

    if (c == '#' || c == '?')
    {
        total += countArrangements(blockMap, map, amounts, i + 1, j, cur + 1)
    }

    blockMap[key] = total
    return total
}