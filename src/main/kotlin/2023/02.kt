package `2023`

import java.io.File
import kotlin.math.max

class Puzzle02 {

    private val R = 0
    private val G = 1
    private val B = 2

    private val REDS = 12
    private val GREENS = 13
    private val BLUES = 14

    private fun isImpossible(values: IntArray) = values[R] > REDS || values[G] > GREENS || values[B] > BLUES
    private fun getRed(colors: List<String>) = getColorValue("red", colors)
    private fun getGreen(colors: List<String>) = getColorValue("green", colors)
    private fun getBlue(colors: List<String>) = getColorValue("blue", colors)

    private fun getColorValue(searchedColor: String, values: List<String>): Int {
        val colorValues = values
            .filter { it.indexOf(searchedColor) >= 0 }
        if (colorValues.isEmpty()) {
            return 0
        }
        return colorValues[0].substring(0, colorValues[0].indexOf(" ")).toInt()
    }

    fun getGameValue(game: String): Int {
        val id = game.substring(5, game.indexOf(':')).toInt()
        val sets = game.substring(game.indexOf(':') + 1).split(';').map { it.trim() }
        sets.forEach {
            val colors = it.split(",").map { it.trim() }
            val values = intArrayOf(getRed(colors), getGreen(colors), getBlue(colors))
            if (isImpossible(values)) {
                return 0
            }
        }
        return id
    }

    fun getGamePower(game: String): Int {
        val maxs = intArrayOf(0, 0, 0)
        val sets = game.substring(game.indexOf(':') + 1).split(';').map { it.trim() }
        sets.forEach {
            val colors = it.split(",").map { it.trim() }
            val values = intArrayOf(getRed(colors), getGreen(colors), getBlue(colors))
            maxs[R] = max(maxs[R], values[R])
            maxs[G] = max(maxs[G], values[G])
            maxs[B] = max(maxs[B], values[B])
        }
        return maxs[R] * maxs[G] * maxs[B]
    }

}

fun main() {
    val lines = File("src/main/resources/2023/02.txt").readLines()
    println("Part 1: " + lines.sumOf { Puzzle02().getGameValue(it) })
    println("Part 2: " + lines.sumOf { Puzzle02().getGamePower(it) })
}

