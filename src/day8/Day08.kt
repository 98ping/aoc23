package day8

import readInput

fun main() {
    val start = System.currentTimeMillis()
    part1()

    println("Your code blows! Ended in ${System.currentTimeMillis().minus(start)}ms")
}

fun part1() {
    val testInput = readInput("day8", "input")

    val instructionSet = testInput[0].map { it == 'L' }

    val nodeIndicesMap = hashMapOf<Int, Node>()
    val nodeParentMap = hashMapOf<String, Node>()

    for (i in testInput.indices) {
        if (i == 0 || i == 1) continue

        val n = testInput[i]
        val parent = n.substring(0, 3)

        val directions = n.removeRange(0, 6)
            .replace("(", "")
            .replace(")", "")
            .split(", ")

        val left = directions[0]
        val right = directions[1]
        val node = Node(parent, left, right, i - 2)

        nodeIndicesMap[i - 2] = node
        nodeParentMap[parent] = node
    }

    println("Instructions: $instructionSet")

    var currentParent = "AAA"
    var steps = 0

    while (currentParent != "ZZZ") {
        val node = nodeParentMap[currentParent]!!

        currentParent = if (instructionSet[steps % instructionSet.size]) {
            node.left
        } else {
            node.right
        }

        steps++
    }

    println("Steps: $steps")
}

data class Node(
    val parent: String,
    val left: String,
    val right: String,
    val index: Int
)