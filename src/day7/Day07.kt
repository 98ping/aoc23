package day7

import readInput

val STRENGTHS = arrayOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')

fun main() {
    val start = System.currentTimeMillis()
    part1()

    println("Your code blows! Ended in ${System.currentTimeMillis().minus(start)}ms")
}

fun part1() {
    val testInput = readInput("day7", "test")

    val cards: MutableMap<MutableList<Char>, Int> = mutableMapOf()

    for (line in testInput) {
        val lineCards = line.substring(0, 5)
            .trim()
            .toMutableList()

        println(lineCards)

        val lineBet = line.removeRange(0, 5).trim().toInt()

        cards[lineCards] = lineBet
    }

    val resultMap = mutableMapOf<MutableList<Char>, Int>()

    for (entry in cards.entries) {
        val hand = Hand(entry.key)

        resultMap[hand.chars] = hand.getHandRating()
    }

    println(resultMap.toString())
}

data class Hand(val chars: MutableList<Char>) {

    private val casePriority = mutableMapOf(
        "five-of-a-kind" to 7,
        "four-of-a-kind" to 6,
        "full-house" to 5,
        "three-of-a-kind" to 4,
        "two-pair" to 3,
        "one-pair" to 2,
        "high-card" to 1
    )

    fun getHandRating() : Int {
        val characters = chars.toMutableList()

        // all same type
        if (characters.all { it == characters[0] }) {
            return casePriority["five-of-a-kind"]!!
        }

        // 4 are same type
        if (characters.any { char -> characters.count { it == char } == 4}) {
            return casePriority["four-of-a-kind"]!!
        }

        // 3 are same type
        if (characters.any { char -> characters.count { it == char } == 3}) {
            val highestOccurringCharacter = characters.first { char -> characters.count { it == char} == 3}

            characters.removeIf { it == highestOccurringCharacter }

            // full house (all similar)
            return if (characters.all { it == characters[0] }) {
                casePriority["full-house"]!!
            } else {
                // not similar
                casePriority["three-of-a-kind"]!!
            }
        }

        // same process as above
        if (characters.any { char -> characters.count { it == char } == 2}) {
            val highestOccurringCharacter = characters.first { char -> characters.count { it == char} == 2}

            characters.removeIf { it == highestOccurringCharacter }

            return if (characters.firstOrNull { char -> characters.count { it == char} == 2} != null) {
                casePriority["two-pair"]!!
            } else {
                casePriority["one-pair"]!!
            }
        }

        // unique
        return casePriority["high-card"]!!
    }
}
