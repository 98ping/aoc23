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