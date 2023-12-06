package `2023`

import java.io.File

class Puzzle06 {
    fun getFullNoOfWays(lines: List<String>) = Race.from(lines).map { getNoOfWays(it) }.reduce { acc, it -> it * acc }

    fun getOnlyNoOfWays()= Race(59796575, 597123410321328).let { getNoOfWays(it) }

    private fun getNoOfWays(race: Race) =
        (1..race.time).map { getDistance(it, race.time) }.filter { it > race.distance }.count()
    private fun getDistance(buttonPress: Long, maxTime: Long): Long = (maxTime - buttonPress) * buttonPress

    data class Race(val time: Long, val distance: Long) {
        companion object {
            fun from(lines: List<String>): List<Race> {
                val times = lines[0].substring(5).split("\\s+".toRegex()).filter { it.isNotEmpty() }
                val distances = lines[1].substring(10).split("\\s+".toRegex()).filter { it.isNotEmpty() }
                return (0..3).map { Race(times[it].trim().toLong(), distances[it].trim().toLong()) }.toList()
            }
        }
    }
}

fun main() {
    val lines = File("src/main/resources/2023/06.txt").readLines()
    println("Part 1: " + Puzzle06().getFullNoOfWays(lines))
    println("Part 2: " + Puzzle06().getOnlyNoOfWays())
}


