package day1

import readInput

fun main()
{
    val start = System.currentTimeMillis()
    part2()

    println("Your code blows! Ended in ${System.currentTimeMillis().minus(start)}ms")
}

fun part2()
{
    val testInput = readInput("day1", "input")
    var sum = 0

    for (input in testInput)
    {
        val wordSum = findWordedSum(input)

        println("Word sum for $input is $wordSum")
        sum += wordSum
    }

    println("Sum was: $sum")
}

fun findWordedSum(s: String): Int
{
    val nws = arrayOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    val digs = arrayOf(-1, -1)
    var firstIndex = -1
    var secondIndex = -1

    // start with digits for my sanity
    for (i in s.indices)
    {
        val c = s[i]

        if (!c.isDigit()) continue
        val dti = c.digitToInt()

        if (digs[0] == -1)
        {
            digs[0] = dti
            firstIndex = i
            continue
        }

        if (firstIndex > i)
        {
            val prevDigit = digs[0]
            val prevIndex = firstIndex

            digs[0] = dti
            firstIndex = i

            // Set next dig to prev dig if index is less
            if (secondIndex < prevIndex)
            {
                secondIndex = prevIndex
                digs[1] = prevDigit
            }

            continue
        }

        if (secondIndex < i)
        {
            digs[1] = dti
            secondIndex = i
        }
    }

    for (i in nws.indices)
    {
        val word = nws[i]
        val count = getTimesContained(s, word)
        var lastSearchIndex = 0

        for (iteration in 0 until count)
        {
            val raw = if (count == 1)
            {
                s.indexOf(word, 0)
            } else
            {
                s.indexOf(word, lastSearchIndex)
            }

            lastSearchIndex = (raw + (word.length - 1))

            if (digs[0] == -1)
            {
                digs[0] = i
                firstIndex = raw

                continue
            }

            if (firstIndex > raw)
            {
                val prevIndex = firstIndex
                val prevDigit = digs[0]

                digs[0] = i
                firstIndex = raw

                if (secondIndex < prevIndex)
                {
                    digs[1] = prevDigit
                    secondIndex = prevIndex
                }

                continue
            }

            if (secondIndex < raw)
            {
                digs[1] = i
                secondIndex = raw

                continue
            }
        }
    }


    if (digs[0] == -1) return 0
    if (digs[1] == -1) return digs[0].concat(digs[0])

    return digs[0].concat(digs[1])
}

fun part1()
{
    val testInput = readInput("day1", "input")
    var sum = 0

    for (input in testInput)
    {
        sum += findSum(input)
    }

    println("Sum was: $sum")
}

// Utility functions

fun findSum(s: String): Int
{
    val digs = arrayOf(-1, -1)

    for (c in s)
    {
        if (!c.isDigit()) continue
        val dti = c.digitToInt()

        if (digs[0] == -1)
        {
            digs[0] = dti
        } else
        {
            digs[1] = dti
        }
    }

    if (digs[0] == -1) return 0
    if (digs[1] == -1) return digs[0].concat(digs[0])

    return digs[0].concat(digs[1])
}

fun getTimesContained(string: String, word: String): Int
{
    var new = string
    var i = 0

    while (new.contains(word))
    {
        new = new.replaceFirst(word, "")
        i++
    }

    return i
}

fun Int.concat(y: Int): Int
{
    val x = this
    var xs = 1

    while (xs <= y)
    {
        xs *= 10
    }

    return x * xs + y
}
