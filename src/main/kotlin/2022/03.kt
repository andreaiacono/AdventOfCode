package `2022`

import java.io.File

fun main() {
    val input = File("src/main/resources/2022/input03.txt").readLines()

    val sum = input.map { getValue(getCommonChar(it)) }.sum()
    println(sum)

    val threeSum = input.chunked(3).map { getValue(getCommonCharOfThree(it)) }.sum()
    println(threeSum)
}

fun getCommonCharOfThree(threeElves: List<String>): Char {
    return threeElves[0].toSet()
        .intersect(threeElves[1].toSet())
        .intersect(threeElves[2].toSet())
        .first()
}

fun getValue(char: Char): Int {
    return if (char.isUpperCase()) char.code.toByte() - 65 + 27 else char.code.toByte() - 96;
}

fun getCommonChar(line: String): Char {
    val first = line.substring(0, line.length / 2).toSet()
    val second = line.substring(line.length / 2).toSet()
    return first.intersect(second).first()
}
