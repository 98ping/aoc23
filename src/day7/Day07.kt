package day7

import readInput

val PT2 = true

fun main() {
    val start = System.currentTimeMillis()
    part2()

    println("Your code blows! Ended in ${System.currentTimeMillis().minus(start)}ms")
}

fun part1() {
    val testInput = readInput("day7", "test")

    val games = mutableListOf<Game>()
    var sum = 0

    for (line in testInput) {
        val lineCards = line.substring(0, 5)
            .trim()

        val lineBet = line.removeRange(0, 5).trim().toInt()

        games.add(Game(lineCards, lineBet))
    }

    games.sortedWith(
        compareBy({ it.hand.getHandType() }, { it })
    ).forEachIndexed { i, v ->
        sum += (i + 1) * v.bid
    }

    println("Sum of all cards and their ranks is: $sum")
}

fun part2() {
    val testInput = readInput("day7", "input")

    val games = mutableListOf<Game>()
    var sum = 0

    for (line in testInput) {
        val lineCards = line.substring(0, 5)
            .trim()

        val lineBet = line.removeRange(0, 5).trim().toInt()

        games.add(Game(lineCards, lineBet))
    }

    games.sortedWith(
        compareBy({ it.hand.getHandTypeWithJoker() }, { it })
    ).forEachIndexed { i, v ->
        sum += (i + 1) * v.bid
    }

    println("Sum of all cards and their ranks is: $sum")
}

enum class HandType {
    HIGH, ONE_PAIR, TWO_PAIR, THREE, FULL, FOUR, FIVE;
}

fun String.distinct() = toSet().size

fun String.frequencies() = groupingBy { it }.eachCount()

fun String.getHandType(): HandType {
    return when (distinct()) {
        1 -> HandType.FIVE
        2 -> {
            val firstCardCount = count { it == this.first() }
            if (firstCardCount == 2 || firstCardCount == 3) {
                HandType.FULL
            } else {
                HandType.FOUR
            }
        }

        3 -> {
            val frequencies = frequencies()
            if (frequencies.any { it.value == 3 }) {
                HandType.THREE
            } else {
                HandType.TWO_PAIR
            }
        }

        4 -> HandType.ONE_PAIR
        5 -> HandType.HIGH
        else -> throw IllegalStateException()
    }
}

fun String.jokerHand(): String {
    val frequencies = frequencies().filter { it.key != 'J' }
    if (frequencies.isEmpty()) return "11111"

    val highestFrequency = frequencies.values.max()
    val mostFrequentCard = first { frequencies[it] == highestFrequency }
    return replace('J', mostFrequentCard)
}

fun String.getHandTypeWithJoker(): HandType {
    return jokerHand().getHandType()
}

fun Char.cardValue(): Int {
    val asString = this.toString()
    return if (Card.values().any { it.name == asString }) Card.valueOf(asString).n
    else this.digitToInt()
}

enum class Card(val n: Int) {
    A(14), K(13), Q(12), J(if (PT2) 1 else 11), T(10)
}

data class Game(
    var hand: String,
    var bid: Int
) : Comparable<Game> {
    override fun compareTo(other: Game): Int {
        hand.zip(other.hand).forEach { (our, other) ->
            val difference = our.cardValue() - other.cardValue()
            if (difference != 0) return difference
        }
        return 0
    }

}
