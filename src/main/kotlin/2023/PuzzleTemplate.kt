package `2023`

import java.io.File

class PuzzleXX {

    fun partOne(lines: List<String>) = 1

    fun partTwo(lines: List<String>) = 1
}

fun main() {
    val lines = File("src/main/resources/2023/XX.txt").readLines()
    println("Part 1: " + PuzzleXX().partOne(lines))
    println("Part 2: " + PuzzleXX().partTwo(lines))
}

