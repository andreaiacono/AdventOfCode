package `2023`

import java.io.File

class Puzzle08 {
    fun partOne(lines: List<String>): Long {
        val moves = lines[0].toCharArray()
        val map = loadMap(lines.subList(2, lines.size))
        return countMoves(moves, map, "AAA") { it: String -> it == "ZZZ" }
    }

    fun partTwo(lines: List<String>): Long {
        val moves = lines[0].toCharArray()
        val map = loadMap(lines.subList(2, lines.size))
        var currents = map.keys.filter { it.endsWith("A") }.toList()
        val counts = currents.map {
            countMoves(moves, map, it) { it: String -> it.endsWith("Z") }
        }
        return lcm(counts)
    }

    private fun countMoves(
        moves: CharArray,
        map: Map<String, Pair<String, String>>,
        startingNode: String,
        endFunction: (String) -> Boolean,): Long {
        var count = 0L
        var current = startingNode
        while (!endFunction(current)) {
            moves.forEach { move ->
                current = when (move) {
                    'L' -> map[current]?.first!!
                    'R' -> map[current]?.second!!
                    else -> throw Exception("Invalid move")
                }
            }
            count += moves.size
        }
        return count
    }

    fun loadMap(lines: List<String>): Map<String, Pair<String, String>> {

        return lines.associate {
            it.split(" = ")[0].trim() to
                    Pair(
                        it.split(" = ")[1].trim().split(",")[0].substring(1, 4),
                        it.split(" = ")[1].trim().split(",")[1].substring(1, 4)
                    )
        }
            .toMap()
    }

    private fun lcm(nums: List<Long>): Long {
        var lcm = nums[0]
        for (i in 1 until nums.size) {
            lcm = lcm(lcm, nums[i])
        }
        return lcm
    }

    private fun lcm(a: Long, b: Long): Long {
        val biggerNum = if (a > b) a else b
        var lcm = biggerNum
        while (true) {
            if (((lcm % a) == 0L) && ((lcm % b) == 0L)) {
                break
            }
            lcm += biggerNum
        }
        return lcm
    }
}

fun main() {
    val lines = File("src/main/resources/2023/08.txt").readLines()
    println("Part 1: " + Puzzle08().partOne(lines))
    println("Part 2: " + Puzzle08().partTwo(lines))
}

