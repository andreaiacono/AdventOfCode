package `2023`

import java.io.File

class Puzzle09 {
    fun partOne(lines: List<String>) = lines.sumOf { it.split(" ").map { it.toLong() }.getLast() }

    fun partTwo(lines: List<String>) = lines.sumOf { it.split(" ").map { it.toLong() }.getFirst() }

    private fun List<Long>.getFirst(): Long {
        val history: List<List<Long>> = getHistories(this)
        var sub = 0L
//        println("sub=$sub this=$this hist=$history")
        ((history.size - 1) downTo 0).forEach { sub = history[it].first() - sub }
//        println("sub=$sub firs=${this.first()} ")
        return this.first() - sub
    }

    fun List<Long>.getLast(): Long {
        val history: List<List<Long>> = getHistories(this)
        return this.last() + history.map { it.last() }.sum()
    }

    private fun getHistories(values: List<Long>): List<List<Long>> {
        val result: MutableList<List<Long>> = mutableListOf()
        var pred = (1 until values.size).map { index -> values[index] - values[index - 1] }
        while (!pred.all { it == 0L }) {
            result.add(pred)
            pred = (1 until pred.size).map { index -> pred[index] - pred[index - 1] }
        }
        return result
    }
}

fun main() {
    val lines = File("src/main/resources/2023/09.txt").readLines()
    println("Part 1: " + Puzzle09().partOne(lines))
    println("Part 2: " + Puzzle09().partTwo(lines))
}

