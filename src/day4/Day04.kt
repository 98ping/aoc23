package day4

import readInput

fun main()
{
    val start = System.currentTimeMillis()
    part2()

    println("Your code blows! Ended in ${System.currentTimeMillis().minus(start)}ms")
}

fun part1()
{
    val testInput = readInput("day4", "test")
    var globalWorth = 0

    for (card in testInput)
    {
        val game = card.removeRange(0, 9)
        val winningNumbers = game.substring(0, 30)
        val winningList = mutableListOf<Int>()

        println("Short game ${card.substring(0, 9)}")

        var worth = 0
        var matches = 0

        println("Winning numbers are: " + winningNumbers.split(" "))

        for (number in winningNumbers.split(" "))
        {
            var parsed = number
            if (number.startsWith(" ")) parsed = number.removePrefix(" ")

            val integer = parsed.toIntOrNull() ?: continue

            winningList.add(integer)
        }

        val ourGame = game.removeRange(0, 32)

        println("Game: " + game.removeRange(0, 32))

        for (gameNumber in ourGame.split(" "))
        {
            var parsed = gameNumber
            if (gameNumber.startsWith(" ")) parsed = gameNumber.removePrefix(" ")

            val integer = parsed.toIntOrNull() ?: continue

            if (winningList.contains(integer))
            {
                if (matches == 0)
                {
                    worth = 1
                    matches = 1
                } else
                {
                    worth = (worth * 2)
                    matches++
                }
            }
        }

        globalWorth += worth
    }

    println("Total worth: $globalWorth")
}

// game range [0, 9]
// winning number range [0, 30]
// game id range [4, 8]
// playing area range [0, 32]
fun part2()
{
    val testInput = readInput("day4", "input")
    val listOfCards = mutableListOf<Card>()

    for (card in testInput)
    {
        val game = card.removeRange(0, 9)
        val winningNumbers = game.substring(0, 30)
        val winningList = mutableListOf<Int>()
        val ourNumbers = mutableListOf<Int>()

        val gameId = card.substring(4, 8).trim()

        for (number in winningNumbers.split(" "))
        {
            var parsed = number
            if (number.startsWith(" ")) parsed = number.removePrefix(" ")

            val integer = parsed.toIntOrNull() ?: continue

            winningList.add(integer)
        }

        val ourGame = game.removeRange(0, 32)

        for (gameNumber in ourGame.split(" "))
        {
            var parsed = gameNumber
            if (gameNumber.startsWith(" ")) parsed = gameNumber.removePrefix(" ")

            val integer = parsed.toIntOrNull() ?: continue

            ourNumbers.add(integer)
        }

        val gameIdInteger = gameId.toInt()

        val matchingNumbers = getNumberOfMatches(winningList, ourNumbers)

        listOfCards.add(
            Card(
                gameIdInteger,
                matchingNumbers
            )
        )
    }

    var i = 0
    var j = listOfCards.size

    while (i < j)
    {
        val card = listOfCards[i]
        for (k in 1..card.matches)
        {
            listOfCards.add(listOfCards[card.number + k - 1])
        }
        i++
        j = listOfCards.size
    }

    println(listOfCards.size)
}

data class Card(
    val number: Int,
    val matches: Int
)

fun getNumberOfMatches(winningNumbers: List<Int>, myNumbers: List<Int>): Int
{
    var matches = 0
    val map = HashMap<Int, Int>()

    for (number in myNumbers)
    {
        map[number] = map.getOrDefault(number, 0) + 1
    }

    for (number in winningNumbers)
    {
        if (map.getOrDefault(number, 0) > 0)
        {
            matches++
            map[number] = map.getValue(number) - 1
        }
    }

    return matches
}

