package `2022`

import java.io.File

fun main() {
    val input = File("src/main/resources/2022/input04.txt").readLines()

    val fullyContainedSum = input.map { isFullyContained(it) }.sum()
    println(fullyContainedSum)

    val overlappingSum = input.map { isOverlapping(it) }.sum()
    println(overlappingSum)
}

fun isFullyContained(line: String): Int {
    val first = Range(line.split(",")[0])
    val second = Range(line.split(",")[1])
    return if (first.contains(second) || second.contains(first)) 1 else 0
}

fun isOverlapping(line: String): Int {
    val first = Range(line.split(",")[0])
    val second = Range(line.split(",")[1])
    return if (first.overlaps(second) || second.overlaps(first)) 1 else 0
}

data class Range(val line: String) {

    private val begin: Int = line.split("-")[0].toInt()
    private val end: Int = line.split("-")[1].toInt()

    fun contains(other: Range): Boolean = begin <= other.begin && end >= other.end

    fun overlaps(other: Range): Boolean = begin <= other.end && end >= other.begin
}
