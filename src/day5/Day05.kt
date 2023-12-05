package day5

import readInput
import kotlin.math.absoluteValue

fun main()
{
    val start = System.currentTimeMillis()

    part2()
    println("Your code blows! Ended in ${System.currentTimeMillis().minus(start)}ms")
}

fun part2()
{
    val testInput = readInput("day5", "input")

    val seedsInLine = testInput[0]
        .removeRange(0, 6)
        .split(" ")
        .mapNotNull { it.trim().toLongOrNull() }
        .windowed(2, 2)
        .map {
            it[0] until it[0] + it[1]
        }


    var stillWithinRange = false
    val linesForCurrentMap = mutableListOf<String>()
    var currentMapId = ""

    val ranges = mutableMapOf<String, RangeMap>()

    for (i in testInput.indices) {
        if (i == 0) continue

        val line = testInput[i]

        if (stillWithinRange) {
            if (line.isNotEmpty() && i != testInput.lastIndex) {
                linesForCurrentMap.add(line)
            } else {
                if (i == testInput.lastIndex) {
                    linesForCurrentMap.add(line)
                }

                stillWithinRange = false
                ranges[currentMapId] = RangeMap(currentMapId, linesForCurrentMap.toMutableList())

                linesForCurrentMap.clear()
                currentMapId = ""
            }

            continue
        }

        if (line.contains("map:")) {
            val metadataIndex = line.indexOf("map:")
            val idString = line.substring(0, metadataIndex)

            ranges[idString] = RangeMap(
                idString,
                mutableListOf()
            )

            currentMapId = idString.trim()
            stillWithinRange = true
        }
    }

    var minValue = Long.MAX_VALUE

    val seedToSoilRanges = ranges["seed-to-soil"]!!
    val soilToFertilizerRanges = ranges["soil-to-fertilizer"]!!
    val fertilizerToWaterRanges = ranges["fertilizer-to-water"]!!
    val waterToLightRanges = ranges["water-to-light"]!!
    val lightToTemperatureRanges = ranges["light-to-temperature"]!!
    val temperatureToHumidityRanges = ranges["temperature-to-humidity"]!!
    val humidityToLocationRanges = ranges["humidity-to-location"]!!

    for (location in 1 until 100_000_000L) {
        val humidity = humidityToLocationRanges.getInversedInterpretedValue(location)
        val temperature = temperatureToHumidityRanges.getInversedInterpretedValue(humidity)
        val light = lightToTemperatureRanges.getInversedInterpretedValue(temperature)
        val water = waterToLightRanges.getInversedInterpretedValue(light)
        val fertilizer = fertilizerToWaterRanges.getInversedInterpretedValue(water)
        val soil = soilToFertilizerRanges.getInversedInterpretedValue(fertilizer)
        val seed = seedToSoilRanges.getInversedInterpretedValue(soil)

        if (seedsInLine.any { it.contains(seed) }) {
            minValue = location
            break
        }
    }

    println("done: $minValue")
}

fun part1()
{
    val testInput = readInput("day5", "input")

    val seeds = testInput[0]
        .removeRange(0, 6)
        .split(" ")
        .mapNotNull { it.trim().toLongOrNull() }

    var stillWithinRange = false
    val linesForCurrentMap = mutableListOf<String>()
    var currentMapId = ""

    val ranges = mutableMapOf<String, RangeMap>()

    for (i in testInput.indices) {
        if (i == 0) continue

        val line = testInput[i]

        if (stillWithinRange) {
            if (line.isNotEmpty() && i != testInput.lastIndex) {
                linesForCurrentMap.add(line)
            } else {
                if (i == testInput.lastIndex) {
                    linesForCurrentMap.add(line)
                }

                stillWithinRange = false
                ranges[currentMapId] = RangeMap(currentMapId, linesForCurrentMap.toMutableList())

                linesForCurrentMap.clear()
                currentMapId = ""
            }

            continue
        }

        if (line.contains("map:")) {
            val metadataIndex = line.indexOf("map:")
            val idString = line.substring(0, metadataIndex)

            ranges[idString] = RangeMap(
                idString,
                mutableListOf()
            )

            currentMapId = idString.trim()
            stillWithinRange = true
        }
    }

    val seedToLocationMap = mutableMapOf<Long, Long>()

    for (seed in seeds) {
        val orderedInterpretations = listOf(
            ranges["seed-to-soil"]!!,
            ranges["soil-to-fertilizer"]!!,
            ranges["fertilizer-to-water"]!!,
            ranges["water-to-light"]!!,
            ranges["light-to-temperature"]!!,
            ranges["temperature-to-humidity"]!!,
            ranges["humidity-to-location"]!!
        )

        var previousInteger = seed

        for (range in orderedInterpretations) {
            previousInteger = range.getInterpretedValue(previousInteger)
        }

        seedToLocationMap[seed] = previousInteger
    }

    println("done")

    println(seedToLocationMap.minBy { it.value }.value)
}

data class RangeMap(
    val id: String,
    val lines: MutableList<String>
)
{
    private var ranges: MutableList<Pair<LongRange, LongRange>> = mutableListOf()

    init
    {
        for (line in lines)
        {
            if (line.isEmpty()) continue
            val integers = line.split(" ")

            val startSource = integers[1].trim().toLong()
            val startTarget = integers[0].trim().toLong()
            val range = integers[2].trim().toLong()

            ranges.add((startSource until startSource + range) to (startTarget until startTarget + range))
        }
    }

    fun getInterpretedValue(long: Long): Long
    {
        val rangeList = ranges.firstOrNull { it.first.contains(long) }
            ?: return long

        return rangeList.second.first + (long - rangeList.first.first).absoluteValue
    }

    fun getInversedInterpretedValue(long: Long) : Long {
        val rangeList = ranges.firstOrNull { it.second.contains(long) }
            ?: return long

        return rangeList.first.first + (long - rangeList.second.first).absoluteValue
    }
}