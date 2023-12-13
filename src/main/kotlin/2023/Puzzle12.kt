package `2023`

import java.io.File

class Puzzle12 {

    private val cache = mutableMapOf<Pair<String, List<Int>>, Long>()

    fun solve(lines: List<String>, unfold: Boolean): Long {
        return lines.sumOf { it ->
            val line = if (unfold) unfold(it) else it
            val config = line.split(" ")[0]
            val groups = line.split(" ")[1].split(",").map { it.toInt() }
            count(config, groups)
        }
    }

    private fun count(config: String, groups: List<Int>): Long {

        if (config.isEmpty()) {
            return if (groups.isEmpty()) 1 else 0
        }

        if (groups.isEmpty()) {
            return if ("#" in config) 0 else 1
        }

        val key = Pair(config, groups)
        if (cache.containsKey(key)) {
            return cache[key]!!
        }

        var result = 0L
        if (config[0] == '?' || config[0] == '.') {
            result += count(config.drop(1), groups)
        }

        if ((config[0] == '#' || config[0] == '?')
            && "." !in config.take(groups[0])
            && groups[0] <= config.length
            && (groups[0] == config.length || config[groups[0]] != '#')
        ) {
            result += count(config.drop(groups[0] + 1), groups.drop(1))
        }
        cache[key] = result

        return result
    }

    private fun unfold(row: String): String {
        val split = row.split(" ")
        return (split[0] + '?').repeat(5).dropLast(1) + " " + (split[1] + ",").repeat(5).dropLast(1)
    }
}

fun main() {
    val lines = File("src/main/resources/2023/12.txt").readLines()
    println("Part 1: " + Puzzle12().solve(lines, false))
    println("Part 2: " + Puzzle12().solve(lines, true))
}

