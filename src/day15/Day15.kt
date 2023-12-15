package day15

import readInput

fun main() {
    val start = System.currentTimeMillis()

    part1()

    println("Your code blows! Ended in ${System.currentTimeMillis().minus(start)}ms")
}

fun part1() {
    val input = readInput("day15", "input")

    var start = 0L

    for (component in input.toString().replace("[", "").replace("]", "").split(",")) {
        var current = 0L

        for (c in component.trim()) {
            val ascii = c.code

            current += ascii
            current *= 17

            val rem = Math.round((current % 256).toDouble())

            current = rem
        }

        println("$component returned $current")

        start+=current
    }

    println("Result was $start")

}
