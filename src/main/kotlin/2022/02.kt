package `2022`

import java.io.File

fun main() {
    val input = File("src/main/resources/2022/input01.txt").readText().trim()
    val elfCalories = input.split("\n\n").map { elf -> elf.lines().sumOf { it.toInt() } }

    val max: Int = elfCalories.max()
    val maxThree: Int = elfCalories.sorted().takeLast(3).sum()
    println("Max: $max")
    println("Three max: $maxThree")
}
