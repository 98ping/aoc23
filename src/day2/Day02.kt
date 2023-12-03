package day2

import readInput
import kotlin.math.max

val RED_MAX = 12
val GREEN_MAX = 13
val BLUE_MAX = 14

fun main()
{
    val start = System.currentTimeMillis()
    part2()

    println("Your code blows! Ended in ${System.currentTimeMillis().minus(start)}ms")
}

fun part2()
{
    val testInput = readInput("day2", "input")
    var sum = 0


    for (input in testInput)
    {
        val gameSum = getMinSumOfGame(input)

        sum += gameSum
    }

    println("Sum was: $sum")
}

fun getMinSumOfGame(game: String): Int
{
    val idSpliterator = game.split(":")
    val identifier = idSpliterator[0]

    val setsOfBalls = idSpliterator[1].split("; ")
    // R G B
    val mins = arrayOf(-1, -1, -1)

    for (set in setsOfBalls)
    {
        val individualBalls = set.split(", ")

        for (ball in individualBalls)
        {
            val count = getBallCount(ball)

            when (getBallColor(ball))
            {
                "red" ->
                {
                    val red = mins[0]

                    mins[0] = if (red == -1)
                    {
                        count
                    } else
                    {
                        max(red, count)
                    }
                }

                "green" ->
                {
                    val green = mins[1]

                    mins[1] = if (green == -1)
                    {
                        count
                    } else
                    {
                        max(green, count)
                    }
                }

                "blue" ->
                {
                    val blue = mins[2]

                    mins[2] = if (blue == -1)
                    {
                        count
                    } else
                    {
                        max(blue, count)
                    }
                }
            }
        }
    }

    var sum = 1

    for (answer in mins)
    {
        sum *= answer
    }

    return sum
}

fun part1()
{
    val testInput = readInput("day2", "input")
    var sum = 0


    for (input in testInput)
    {
        val gameSum = getSumOfGame(input)

        sum += gameSum
    }

    println("Sum was: $sum")
}

fun getSumOfGame(game: String): Int
{
    val idSpliterator = game.split(":")
    val identifier = idSpliterator[0]
    val char = identifier.split(" ")[1]
    val number = char.toIntOrNull() ?: 0

    val setsOfBalls = idSpliterator[1].split("; ")
    var blue = 0
    var red = 0
    var green = 0

    for (set in setsOfBalls)
    {
        val individualBalls = set.split(", ")

        for (ball in individualBalls)
        {
            val count = getBallCount(ball)

            when (getBallColor(ball))
            {
                "red" ->
                {
                    red = max(red, count)
                }

                "green" ->
                {
                    green = max(green, count)
                }

                "blue" ->
                {
                    blue = max(blue, count)
                }
            }
        }
    }

    if (blue <= BLUE_MAX && red <= RED_MAX && green <= GREEN_MAX)
    {
        println("Game $char was valid")
        return number
    }

    return 0
}

fun getBallCount(ball: String): Int
{
    val split = ball.removePrefix(" ").split(" ")
    val count = split[0]

    return count.toIntOrNull() ?: 0
}

fun getBallColor(ball: String): String
{
    val split = ball.removePrefix(" ").split(" ")

    return split[1]
}
