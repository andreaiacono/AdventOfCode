package `2023`

import java.io.File
import kotlin.math.min


class Puzzle01 {

    val charNumbers = CharArray(10) { (it + 48).toChar() }
    val spelled = listOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    val numbersAndSpelledNumbers = spelled + charNumbers.map { it.toString() }.toList()

    fun getNumber(row: String): Int {
        val firstDigit = row[row.indexOfAny(charNumbers)].digitToInt()
        val secondDigit = row[row.lastIndexOfAny(charNumbers)].digitToInt()
        return firstDigit * 10 + secondDigit
    }

    fun getSpelledNumber(row: String): Int {

        val firstIndex = row.indexOfAny(numbersAndSpelledNumbers)
        val firstDigit = if (row[firstIndex].isDigit()) row[firstIndex].digitToInt() else getSpelledValue(
            row.substring(firstIndex, min(row.length, firstIndex + 5))
        )
        val secondIndex = row.lastIndexOfAny(numbersAndSpelledNumbers)
        val secondDigit = if (row[secondIndex].isDigit()) row[secondIndex].digitToInt() else getSpelledValue(
            row.substring(secondIndex, min(row.length, secondIndex + 5))
        )
        return firstDigit * 10 + secondDigit
    }

    fun getSpelledValue(value: String): Int {
        spelled.forEach { if (value.startsWith(it)) return spelled.indexOf(it) }
        return 0
    }
}

fun main() {
    val lines = File("src/main/resources/2023/01.txt").readLines()
    println("Part 1: " + lines.sumOf { Puzzle01().getNumber(it) })
    println("Part 2: " + lines.sumOf { Puzzle01().getSpelledNumber(it) })
}