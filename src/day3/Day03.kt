package day3

import readInput

// gave up on this one
fun main()
{
    val start = System.currentTimeMillis()
    part1()

    println("Your code blows! Ended in ${System.currentTimeMillis().minus(start)}ms")
}

fun part1()
{
    val testInput = readInput("day3", "input")
    val validNumbers = mutableListOf<Int>()
    val occupiedIndices = mutableMapOf<Int, MutableList<Int>>()
    var sum = 0


    for (i in testInput.indices)
    {
        val line = testInput[i]
        val numbers = getNumbers(line)

        for (number in numbers)
        {
            val indices = findIndicesFor(number, line)

            for (index in indices)
            {
                val top = testInput.getOrNull(i - 1)
                val bottom = testInput.getOrNull(i + 1)

                if (hasSymbolNearby(index, line, top, bottom))
                {
                    val occupied = occupiedIndices.getOrDefault(i, mutableListOf())

                    // means there is duplicate
                    if (indices == occupied) {
                        validNumbers.add(number)
                        break
                    }

                    if (!occupied.contains(index))
                    {
                        validNumbers.add(number)

                        occupied.addAll(indices)
                        occupiedIndices[i] = occupied
                        break
                    }
                }
            }
        }
    }

    for (number in validNumbers)
    {
        sum += number
        println("Found valid number: $number")
    }

    println("Sum is: $sum")
}

fun getNumbers(line: String): MutableList<Int>
{
    val numbers = mutableListOf<Int>()
    var stillDigit = false
    val currentNumber = mutableListOf<Char>()

    for (i in line.indices)
    {
        val char = line[i]

        if (char.isDigit() || char == '-')
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
                val number = currentNumber.joinToString("").toIntOrNull()

                // just in case our test case is like
                // -------
                if (number != null)
                {
                    numbers.add(number)
                }
            }

            stillDigit = false
            currentNumber.clear()
        }
    }

    return numbers
}

fun hasSymbolNearby(index: Int, currentLine: String, topLine: String?, bottomLine: String?): Boolean
{
    val next = currentLine.getOrNull(index + 1)
    val prev = currentLine.getOrNull(index + 1)


    if (next != null && isSymbol(next)) return true
    if (prev != null && isSymbol(prev)) return true

    if (topLine != null)
    {
        val topCenter = topLine[index]
        val topLeft = topLine.getOrNull(index - 1)
        val topRight = topLine.getOrNull(index + 1)


        if (isSymbol(topCenter) || isSymbol(topLeft) || isSymbol(topRight))
        {
            return true
        }
    }

    if (bottomLine != null)
    {
        val bottomCenter = bottomLine[index]
        val bottomLeft = bottomLine.getOrNull(index - 1)
        val bottomRight = bottomLine.getOrNull(index + 1)

        if (isSymbol(bottomCenter) || isSymbol(bottomLeft) || isSymbol(bottomRight))
        {
            return true
        }
    }

    return false
}


fun findIndicesFor(number: Int, l: String): MutableList<Int>
{
    val indices = mutableListOf<Int>()
    val numberString = number.toString()
    var line = l
    var removedCharacters = 0

    if (!line.contains(numberString)) return indices

    while (line.contains(numberString))
    {
        val index = line.indexOf(numberString) + removedCharacters
        val end = index + (numberString.length)
        indices.add(index)

        for (int in 1 until numberString.length)
        {
            indices.add(index + int)
        }

        line = try
        {
            line.removeRange(0, end)
        } catch (e: NegativeArraySizeException) {
            line.removeRange(0, line.length)
        }

        removedCharacters = end
    }

    return indices
}

fun isSymbol(c: Char?): Boolean = c != null && !c.isDigit() && c != '-' && c != '.'