package `2023`

import java.io.File
import kotlin.math.abs

class Puzzle11 {

    fun solve(lines: List<String>, times: Long): Long {
        val map = loadMap(lines)
        val extraRows = map
            .mapIndexed { index, row -> if (row.all { it == '.' }) index else null }
            .filterNotNull()
        val extraCols =
            (0 until map[0].size)
                .filter { colIndex -> map.indices.all { rowIndex -> map[rowIndex][colIndex] == '.' } }
        val galaxies = getGalaxies(map, times, extraRows, extraCols)
        return getGalaxyPairs(galaxies).sumOf { getShortestPath(it) }
    }

    private fun loadMap(lines: List<String>) = lines.map { it.toCharArray() }.toTypedArray()

    private fun getGalaxies(expandedMap: Array<CharArray>, times: Long, extraRows: List<Int>, extraCols: List<Int>): List<Galaxy> {
        val galaxies = mutableSetOf<Galaxy>()
        expandedMap.indices.forEach { i ->
            (0 until expandedMap[0].size).forEach { j ->
                if (expandedMap[i][j] == '#') {
                    val rowsToAdd = extraRows.count { extraRowIndex -> extraRowIndex < i } * times
                    val colsToAdd = extraCols.count { extraColIndex -> extraColIndex < j } * times
                    galaxies.add(Galaxy(i + rowsToAdd, j + colsToAdd))
                }
            }
        }
        return galaxies.toList()
    }

    private fun getGalaxyPairs(galaxies: List<Galaxy>): List<Pair<Galaxy, Galaxy>> {
        val pairs = mutableListOf<Pair<Galaxy, Galaxy>>()
        galaxies.indices.forEach { i ->
            (i until galaxies.size).forEach { j -> if (i != j) pairs.add(Pair(galaxies[i], galaxies[j])) }
        }
        return pairs
    }

    private fun getShortestPath(pair: Pair<Galaxy, Galaxy>): Long {
        return abs(pair.first.x - pair.second.x) + abs(pair.first.y - pair.second.y)
    }

    data class Galaxy(val x: Long, val y: Long) {
        override fun toString() = "[$x, $y]"
    }
}

fun main() {
    val lines = File("src/main/resources/2023/11.txt").readLines()
    println("Part 1: " + Puzzle11().solve(lines, 1))
    println("Part 2: " + (Puzzle11().solve(lines, 999_999)))
}

