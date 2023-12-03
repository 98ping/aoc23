package day3

import readInput

fun main()
{
    val start = System.currentTimeMillis()
    part1()

    println("Your code blows! Ended in ${System.currentTimeMillis().minus(start)}ms")
}

fun part1()
{
    val testInput = readInput("day3", "test")
    val validNumbers = mutableListOf<Int>()

    for (i in testInput.indices)
    {
        val line = testInput[i]
        val numbers = getNumbers(line)

        for (number in numbers)
        {
            val indices = findIndicesFor(number, line)

            for (index in indices) {
                println("Index of number: $index")
            }
        }
    }

    for (number in validNumbers) {
        println("Found valid number: $number")
    }
}

fun getNumbers(line: String): MutableList<Int>
{
    val numbers = mutableListOf<Int>()
    var stillDigit = false
    val currentNumber = mutableListOf<Char>()

    for (i in line.indices)
    {
        val char = line[i]

        if (char.isDigit())
        {
            if (stillDigit)
            {
                currentNumber.add(char)
            } else
            {
                stillDigit = true
                currentNumber.add(char)
            }
        } else
        {
            if (currentNumber.isNotEmpty())
            {
                numbers.add(currentNumber.joinToString("").toInt())
            }

            stillDigit = false
            currentNumber.clear()
        }
    }

    return numbers
}


fun findIndicesFor(number: Int, l: String) : MutableList<Int> {
    val indices = mutableListOf<Int>()
    val numberString = number.toString()
    var line = l

    if (!line.contains(numberString)) return indices

    while (line.contains(numberString)) {
        val index = line.indexOf(numberString)
        val end = index + (numberString.length - 1)
        indices.add(index)

        for (int in 1 until numberString.length) {
            indices.add(index + int)
        }

        line = line.substring(0, end)
    }

    return indices
}

fun getSymbolsInString(string: String): MutableList<Int>
{
    val symbols = mutableListOf<Int>()

    for (i in string.indices)
    {
        val c = string[i]
        if (!c.isDigit() && c != '.')
        {
            symbols.add(i)
        }
    }

    return symbols
}