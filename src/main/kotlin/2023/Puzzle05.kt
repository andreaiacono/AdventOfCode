package `2023`

import java.io.File
import kotlin.math.min

class Puzzle05 {
    fun getClosestLocation(lines: List<String>): Long {
        val almanac: Almanac = Almanac.from(lines)
        return getLocations(almanac).min()
    }

    fun getClosestLocationRange(lines: List<String>): Long {
        val almanac: Almanac = Almanac.fromWithoutSeeds(lines)
        val seeds = lines[0].substring(7).split(" ").map { it.toLong() }
        var result = Long.MAX_VALUE
        (0 until seeds.size step 2).forEach {
            (seeds[it]..seeds[it] + seeds[it + 1] - 1).forEach {
                result = min(result, getLocation(it, almanac))
            }
        }
        return result
    }

    private fun getLocations(almanac: Almanac): List<Long> {
        return almanac.seeds.map { getLocation(it, almanac) }
    }

    private fun getLocation(seed: Long, almanac: Almanac): Long {
        var current = seed
        (0..6).map { n ->
            current = getMapping(current, almanac.maps[n])
        }
        return current
    }

    private fun getMapping(current: Long, ranges: MutableList<Range>): Long {
        ranges.forEach { range ->
            if (current >= range.source && current <= range.source + range.range) {
                return range.dest + current - range.source
            }
        }
        return current
    }

    data class Almanac(
        val seeds: List<Long>,
        val maps: MutableList<MutableList<Range>>,
    ) {
        companion object {
            fun from(rows: List<String>): Almanac {
                val seeds = rows[0].substring(7).split(" ").map { it.toLong() }
                return Almanac(seeds, getMaps(rows))
            }

            fun fromWithoutSeeds(rows: List<String>): Almanac {
                return Almanac(listOf(), getMaps(rows))
            }

            fun getMaps(rows: List<String>): MutableList<MutableList<Range>> {
                val maps = mutableListOf<MutableList<Range>>()
                var current = mutableListOf<Range>()
                rows.filterIndexed { index, row -> index > 1 && row.isNotEmpty() }.forEach { row ->
                    if (row.indexOf(":") > 0) {
                        current = mutableListOf()
                        maps.add(current)
                    } else if (row.isNotEmpty()) {
                        current.add(Range.from(row))
                    }
                }
                return maps
            }
        }
    }

    data class Range(val source: Long, val dest: Long, val range: Long) {
        companion object {
            fun from(row: String): Range {
                val values = row.split(" ")
                return Range(values[1].toLong(), values[0].toLong(), values[2].toLong())
            }
        }

        override fun toString(): String {
            return "($source, $dest, $range)"
        }
    }
}

fun main() {
    val lines = File("src/main/resources/2023/05.txt").readLines()
    println("Part 1: " + Puzzle05().getClosestLocation(lines))
    println("Part 2: " + Puzzle05().getClosestLocationRange(lines))
}


