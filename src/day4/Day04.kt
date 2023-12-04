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

    for (card in testInput) {
        val game = card.removeRange(0, 9)
        val winningNumbers = game.substring(0, 30)
        val winningList = mutableListOf<Int>()

        println("Short game ${card.substring(0, 9)}")

        var worth = 0
        var matches = 0

        println("Winning numbers are: " + winningNumbers.split(" "))

        for (number in winningNumbers.split(" ")) {
            var parsed = number
            if (number.startsWith(" ")) parsed = number.removePrefix(" ")

            val integer = parsed.toIntOrNull() ?: continue

            winningList.add(integer)
        }

        val ourGame = game.removeRange(0, 32)

        println("Game: " + game.removeRange(0, 32))

        for (gameNumber in ourGame.split(" ")) {
            var parsed = gameNumber
            if (gameNumber.startsWith(" ")) parsed = gameNumber.removePrefix(" ")

            val integer = parsed.toIntOrNull() ?: continue

            if (winningList.contains(integer)) {
                if (matches == 0) {
                    worth = 1
                    matches = 1
                } else {
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
    val testInput = readInput("day4", "test")
    var totalCards = 0

    for (card in testInput) {
        val game = card.removeRange(0, 7)
        val winningNumbers = game.substring(0, 16)
        val winningList = mutableListOf<Int>()

        val gameId = card.substring(4, 6).trim()

        var worth = 0
        var matches = 0

        for (number in winningNumbers.split(" ")) {
            var parsed = number
            if (number.startsWith(" ")) parsed = number.removePrefix(" ")

            val integer = parsed.toIntOrNull() ?: continue

            winningList.add(integer)
        }

        val ourGame = game.removeRange(0, 16)

        for (gameNumber in ourGame.split(" ")) {
            var parsed = gameNumber
            if (gameNumber.startsWith(" ")) parsed = gameNumber.removePrefix(" ")

            val integer = parsed.toIntOrNull() ?: continue

            if (winningList.contains(integer)) {
                if (matches == 0) {
                    worth = 1
                    matches = 1
                } else {
                    worth = (worth * 2)
                    matches++
                }
            }
        }

        val gameIdInteger = gameId.toInt()

        println("matches for $gameIdInteger is $matches")
        
    }

    println("Total extra scratchers: $totalCards")
}
